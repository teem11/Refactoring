package ac.kr.ajou.dirt;

class DirtySample {
    Item[] items;

    public DirtySample(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            modifyQualityFirstStep(items[i]);

            if (!is_Sulfuras(items[i])) {
                items[i].sellIn = items[i].sellIn - 1;
            }

            if (items[i].sellIn < 0) {
                modifyQualitySecondStep(items[i]);
            }
        }
    }

    private void modifyQualityFirstStep(Item item) {
        if (!is_Aged_brie(item) && !is_Back_Stage(item)){
            if (item.quality > 0 && !is_Sulfuras(item)) {
                item.quality = item.quality - 1;
            }
        } else {
            if (item.quality < 50) {
                item.quality = item.quality + 1;

                if (is_Back_Stage(item)) {
                    if (item.sellIn < 11 && item.quality < 50) {
                        item.quality = item.quality + 1;
                    }
                    if (item.sellIn < 6 && item.quality < 50) {
                        item.quality = item.quality + 1;
                    }
                }
            }
        }
    }

    private void modifyQualitySecondStep(Item item) {
        if (!is_Aged_brie(item)) {
            if (!is_Back_Stage(item)) {
                if (item.quality > 0 && !is_Sulfuras(item)) {
                    item.quality = item.quality - 1;
                }
            } else {
                item.quality = 0;
            }
        } else {
            if (item.quality < 50) {
                item.quality = item.quality + 1;
            }
        }
    }

    private boolean is_Sulfuras(Item item) {
        return item.name.equals("Sulfuras, Hand of Ragnaros");
    }

    private boolean is_Back_Stage(Item item) {
        return item.name.equals("Backstage passes to a TAFKAL80ETC concert");
    }

    private boolean is_Aged_brie(Item item) {
        return item.name.equals("Aged Brie");
    }

    private boolean is_Valid_Condition(String name, int sellin, int quality, Item item) {
        return item.getName().equals(name) && item.getSellIn() < sellin && item.getQuality() < quality;
    }

}