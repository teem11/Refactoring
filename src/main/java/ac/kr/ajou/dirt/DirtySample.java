package ac.kr.ajou.dirt;

class DirtySample {
    final Item[] items;

    public DirtySample(Item[] items) {
        this.items = items;
    }

    public void updateQualityAndSellInOfItems() {
        for (Item item : items) {
            switch (item.name) {
                case "Aged Brie":
                    updateQualityAndSellInOfItemNameAged_Brie(item);
                    break;
                case "Backstage passes to a TAFKAL80ETC concert":
                    updateQualityAndSellInOfItemNameBackstage_passes_to_a_TAFKAL80ETC_concert(item);
                    break;
                case "Sulfuras, Hand of Ragnaros":
                    //do nothing
                    break;
                default:
                    updateQualityAndSellInOfItemNameOthers(item);
                    break;
            }
        }
    }

    private void updateQualityAndSellInOfItemNameAged_Brie(Item item) {
        int originalItemQuality = item.quality;
        int updatedItemQuality = item.quality;

        if(item.quality >= 50){
            item.sellIn--;
            return;
        }

        if(item.sellIn <= 0){
            updatedItemQuality += 2;
        } else
            updatedItemQuality += 1;

        item.quality = setMaximumQuality50IfExcessTo50(originalItemQuality, updatedItemQuality);
        item.sellIn--;
    }

    private void updateQualityAndSellInOfItemNameBackstage_passes_to_a_TAFKAL80ETC_concert(Item item) {
        int originalItemQuality = item.quality;
        int updatedItemQuality = item.quality;
        int sellInScope;

        sellInScope = checkSellInScope(item.sellIn);
        if (sellInScope == 0){
            item.quality = 0;
            item.sellIn--;
            return;
        }
        if (item.quality >= 50){
            item.sellIn--;
            return;
        }
        switch (sellInScope){
            case 1:
                updatedItemQuality += 1;
                break;
            case 2:
                updatedItemQuality += 2;
                break;
            case 3:
                updatedItemQuality += 3;
                break;
        }
        item.quality = setMaximumQuality50IfExcessTo50(originalItemQuality, updatedItemQuality);
        item.sellIn--;
    }

    private void updateQualityAndSellInOfItemNameOthers(Item item) {
        int originalItemQuality = item.quality;
        int updatedItemQuality = item.quality;

        if(item.quality < 0){
            item.sellIn--;
            return;
        }

        if(item.sellIn < 0)
            updatedItemQuality -= 2;
        else
            updatedItemQuality -= 1;

        item.sellIn--;
        item.quality = setMaximumQuality0IfExcessTo0(originalItemQuality, updatedItemQuality);
    }

    private int setMaximumQuality50IfExcessTo50(int originalItemQuality, int updatedItemQuality){
        if (originalItemQuality <= 50 && updatedItemQuality >= 50){
            return 50;
        } else
            return updatedItemQuality;
    }

    private int checkSellInScope(int sellInScope){
        if(sellInScope >= 1 && sellInScope < 6)
            return 3;
        else if(sellInScope >= 6 && sellInScope < 11)
            return 2;
        else if(sellInScope >= 11)
            return 1;
        return 0;
    }

    private int setMaximumQuality0IfExcessTo0(int originalItemQuality, int updatedItemQuality){
        if (originalItemQuality >= 0 && updatedItemQuality <= 0){
            return 0;
        } else
            return updatedItemQuality;
    }
}
