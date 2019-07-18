package ac.kr.ajou.dirt;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DirtySampleTest {

    //이름 매칭 조건, sellIn조건 순으로 testmethod작성, 독립적이지 않은 것과 순차적 실행 조심
    //--------------------------------------------------------------------------
    //A,B,S의 이름이 매칭이 되지 않으며 quality > 0 인 경우 - quality1감소
    //A,B,S의 이름이 매칭되지 않고 quality > 0 이고 sellIn < 0 인 경우 - quality1감소
    //S의 이름이 매칭되지 않는 경우 - sellIn1감소
    //--------------------------------------------------------------------------
    //A나 B의 이름이 매칭되며, quality < 50 인 경우 - quality1증가
    //A의 이름이 매칭되며, sellIn < 0 이며 quality < 50 인 경우 - quality1증가
    //B의 이름이 매칭되며, sellIn < 11 이고 quality < 50 인 경우 - quality1증가
    //B의 이름이 매칭되며, sellIn < 6 이고 quality < 50 인 경우 - quality1증가
    //B의 이름이 매칭되며, sellIn < 0 인 경우 - quality 0으로 초기화
    //--------------------------------------------------------------------------
    //A클리어, B클리어, S클리어, 모두 아닌 경우 클리어

    @Test
    public void updateQuality_name은A_sellIn은0이하_quality는50이하면_quality2증가_quality는최대50_sellIn1감소() {
        Item[] testitems = new Item[4];
        int originalQuality[] = new int[4];
        int updatedQuality[] = new int[4];
        int originalSellIn[] = new int[4];
        int updatedSellIn[] = new int[4];

        testitems[0] = new Item("Aged Brie", 0,47);
        testitems[1] = new Item("Aged Brie", -1,48);
        testitems[2] = new Item("Aged Brie", -2,49);
        testitems[3] = new Item("Aged Brie", -3,50);
        DirtySample dirtySample = new DirtySample(testitems);

        setOriginalValues(originalQuality, originalSellIn, dirtySample);
        dirtySample.updateQuality();
        setUpdatedValues(updatedQuality, updatedSellIn, dirtySample);

        assertThat(updatedQuality[0], is(originalQuality[0] + 2));
        assertThat(updatedQuality[1], is(originalQuality[1] + 2));
        assertThat(updatedQuality[2], is(50));
        assertThat(updatedQuality[3], is(50));
        assertThat(updatedSellIn[0], is(originalSellIn[0] - 1));
        assertThat(updatedSellIn[1], is(originalSellIn[1] - 1));
        assertThat(updatedSellIn[2], is(originalSellIn[2] - 1));
        assertThat(updatedSellIn[3], is(originalSellIn[3] - 1));
    }

    @Test
    public void updateQuality_name은A_sellIn은1이상_quality는50이하면_quality1증가_quality는최대50_sellIn1감소() {
        Item[] testitems = new Item[4];
        int originalQuality[] = new int[4];
        int updatedQuality[] = new int[4];
        int originalSellIn[] = new int[4];
        int updatedSellIn[] = new int[4];

        testitems[0] = new Item("Aged Brie", 1,47);
        testitems[1] = new Item("Aged Brie", 2,48);
        testitems[2] = new Item("Aged Brie", 3,49);
        testitems[3] = new Item("Aged Brie", 4,50);
        DirtySample dirtySample = new DirtySample(testitems);

        setOriginalValues(originalQuality, originalSellIn, dirtySample);
        dirtySample.updateQuality();
        setUpdatedValues(updatedQuality, updatedSellIn, dirtySample);

        assertThat(updatedQuality[0], is(originalQuality[0] + 1));
        assertThat(updatedQuality[1], is(originalQuality[1] + 1));
        assertThat(updatedQuality[2], is(originalQuality[2] + 1));
        assertThat(updatedQuality[3], is(50));
        assertThat(updatedSellIn[0], is(originalSellIn[0] - 1));
        assertThat(updatedSellIn[1], is(originalSellIn[1]- 1));
        assertThat(updatedSellIn[2], is(originalSellIn[2]- 1));
        assertThat(updatedSellIn[3], is(originalSellIn[3]- 1));
    }

    @Test
    public void updateQuality_name은A_sellIn상관없이_quality는50이상이면_quality그대로_sellIn1감소() {
        Item[] testitems = new Item[4];
        int originalQuality[] = new int[4];
        int updatedQuality[] = new int[4];
        int originalSellIn[] = new int[4];
        int updatedSellIn[] = new int[4];

        testitems[0] = new Item("Aged Brie", 0,50);
        testitems[1] = new Item("Aged Brie", -1,51);
        testitems[2] = new Item("Aged Brie", 2,52);
        testitems[3] = new Item("Aged Brie", -3,53);
        DirtySample dirtySample = new DirtySample(testitems);

        setOriginalValues(originalQuality, originalSellIn, dirtySample);
        dirtySample.updateQuality();
        setUpdatedValues(updatedQuality, updatedSellIn, dirtySample);

        assertThat(updatedQuality[0], is(originalQuality[0]));
        assertThat(updatedQuality[1], is(originalQuality[1]));
        assertThat(updatedQuality[2], is(originalQuality[2]));
        assertThat(updatedQuality[3], is(originalQuality[3]));
        assertThat(updatedSellIn[0], is(originalSellIn[0] - 1));
        assertThat(updatedSellIn[1], is(originalSellIn[1] - 1));
        assertThat(updatedSellIn[2], is(originalSellIn[2] - 1));
        assertThat(updatedSellIn[3], is(originalSellIn[3] - 1));
    }

    @Test
    public void updateQuality_name은B_sellIn11이상_quality는50이하면_quality1증가_quality는최대50_sellIn1감소() {
        Item[] testitems = new Item[4];
        int originalQuality[] = new int[4];
        int updatedQuality[] = new int[4];
        int originalSellIn[] = new int[4];
        int updatedSellIn[] = new int[4];

        testitems[0] = new Item("Backstage passes to a TAFKAL80ETC concert", 11,47);
        testitems[1] = new Item("Backstage passes to a TAFKAL80ETC concert", 11,49);
        testitems[2] = new Item("Backstage passes to a TAFKAL80ETC concert", 12,50);
        testitems[3] = new Item("Backstage passes to a TAFKAL80ETC concert", 13,47);
        DirtySample dirtySample = new DirtySample(testitems);

        setOriginalValues(originalQuality, originalSellIn, dirtySample);
        dirtySample.updateQuality();
        setUpdatedValues(updatedQuality, updatedSellIn, dirtySample);

        assertThat(updatedQuality[0], is(originalQuality[0] + 1));
        assertThat(updatedQuality[1], is(originalQuality[1] + 1));
        assertThat(updatedQuality[2], is(50));
        assertThat(updatedQuality[3], is(originalQuality[3] + 1));
        assertThat(updatedSellIn[0], is(originalSellIn[0] - 1));
        assertThat(updatedSellIn[1], is(originalSellIn[1] - 1));
        assertThat(updatedSellIn[2], is(originalSellIn[2] - 1));
        assertThat(updatedSellIn[3], is(originalSellIn[3] - 1));
    }

    @Test
    public void updateQuality_name은B_sellIn6이상11미만_quality는50이하면_quality2증가_quality는최대50_sellIn1감소() {
        Item[] testitems = new Item[4];
        int originalQuality[] = new int[4];
        int updatedQuality[] = new int[4];
        int originalSellIn[] = new int[4];
        int updatedSellIn[] = new int[4];

        testitems[0] = new Item("Backstage passes to a TAFKAL80ETC concert", 6,47);
        testitems[1] = new Item("Backstage passes to a TAFKAL80ETC concert", 7,49);
        testitems[2] = new Item("Backstage passes to a TAFKAL80ETC concert", 8,46);
        testitems[3] = new Item("Backstage passes to a TAFKAL80ETC concert", 10,47);
        DirtySample dirtySample = new DirtySample(testitems);

        setOriginalValues(originalQuality, originalSellIn, dirtySample);
        dirtySample.updateQuality();
        setUpdatedValues(updatedQuality, updatedSellIn, dirtySample);

        assertThat(updatedQuality[0], is(originalQuality[0] + 2));
        assertThat(updatedQuality[1], is(50));
        assertThat(updatedQuality[2], is(originalQuality[2] + 2));
        assertThat(updatedQuality[3], is(originalQuality[3] + 2));
        assertThat(updatedSellIn[0], is(originalSellIn[0] - 1));
        assertThat(updatedSellIn[1], is(originalSellIn[1] - 1));
        assertThat(updatedSellIn[2], is(originalSellIn[2] - 1));
        assertThat(updatedSellIn[3], is(originalSellIn[3] - 1));
    }

    @Test
    public void updateQuality_name은B_sellIn1이상6미만_quality는50이하면_quality3증가_quality는최대50_sellIn1감소() {
        Item[] testitems = new Item[4];
        int originalQuality[] = new int[4];
        int updatedQuality[] = new int[4];
        int originalSellIn[] = new int[4];
        int updatedSellIn[] = new int[4];

        testitems[0] = new Item("Backstage passes to a TAFKAL80ETC concert", 1,46);
        testitems[1] = new Item("Backstage passes to a TAFKAL80ETC concert", 3,48);
        testitems[2] = new Item("Backstage passes to a TAFKAL80ETC concert", 4,45);
        testitems[3] = new Item("Backstage passes to a TAFKAL80ETC concert", 5,46);
        DirtySample dirtySample = new DirtySample(testitems);

        setOriginalValues(originalQuality, originalSellIn, dirtySample);
        dirtySample.updateQuality();
        setUpdatedValues(updatedQuality, updatedSellIn, dirtySample);

        assertThat(updatedQuality[0], is(originalQuality[0] + 3));
        assertThat(updatedQuality[1], is(50));
        assertThat(updatedQuality[2], is(originalQuality[2] + 3));
        assertThat(updatedQuality[3], is(originalQuality[3] + 3));
        assertThat(updatedSellIn[0], is(originalSellIn[0] - 1));
        assertThat(updatedSellIn[1], is(originalSellIn[1] - 1));
        assertThat(updatedSellIn[2], is(originalSellIn[2] - 1));
        assertThat(updatedSellIn[3], is(originalSellIn[3] - 1));
    }

    @Test
    public void updateQuality_name은B_sellIn0이하_quality는상관없이_quality는0으로초기화_sellIn1감소() {
        Item[] testitems = new Item[4];
        int originalQuality[] = new int[4];
        int updatedQuality[] = new int[4];
        int originalSellIn[] = new int[4];
        int updatedSellIn[] = new int[4];

        testitems[0] = new Item("Backstage passes to a TAFKAL80ETC concert", 0,46);
        testitems[1] = new Item("Backstage passes to a TAFKAL80ETC concert", -1,53);
        testitems[2] = new Item("Backstage passes to a TAFKAL80ETC concert", -10,-3);
        testitems[3] = new Item("Backstage passes to a TAFKAL80ETC concert", -100,0);
        DirtySample dirtySample = new DirtySample(testitems);

        setOriginalValues(originalQuality, originalSellIn, dirtySample);
        dirtySample.updateQuality();
        setUpdatedValues(updatedQuality, updatedSellIn, dirtySample);

        assertThat(updatedQuality[0], is(0));
        assertThat(updatedQuality[1], is(0));
        assertThat(updatedQuality[2], is(0));
        assertThat(updatedQuality[3], is(0));
        assertThat(updatedSellIn[0], is(originalSellIn[0] - 1));
        assertThat(updatedSellIn[1], is(originalSellIn[1] - 1));
        assertThat(updatedSellIn[2], is(originalSellIn[2] - 1));
        assertThat(updatedSellIn[3], is(originalSellIn[3] - 1));
    }

    @Test
    public void updateQuality_name은B_sellIn1이상_quality는50이상이면_quality그대로_sellIn1감소() {
        Item[] testitems = new Item[4];
        int originalQuality[] = new int[4];
        int updatedQuality[] = new int[4];
        int originalSellIn[] = new int[4];
        int updatedSellIn[] = new int[4];

        testitems[0] = new Item("Backstage passes to a TAFKAL80ETC concert", 1,50);
        testitems[1] = new Item("Backstage passes to a TAFKAL80ETC concert", 10,51);
        testitems[2] = new Item("Backstage passes to a TAFKAL80ETC concert", 100,52);
        testitems[3] = new Item("Backstage passes to a TAFKAL80ETC concert", 1000,100);
        DirtySample dirtySample = new DirtySample(testitems);

        setOriginalValues(originalQuality, originalSellIn, dirtySample);
        dirtySample.updateQuality();
        setUpdatedValues(updatedQuality, updatedSellIn, dirtySample);

        assertThat(updatedQuality[0], is(originalQuality[0]));
        assertThat(updatedQuality[1], is(originalQuality[1]));
        assertThat(updatedQuality[2], is(originalQuality[2]));
        assertThat(updatedQuality[3], is(originalQuality[3]));
        assertThat(updatedSellIn[0], is(originalSellIn[0] - 1));
        assertThat(updatedSellIn[1], is(originalSellIn[1] - 1));
        assertThat(updatedSellIn[2], is(originalSellIn[2] - 1));
        assertThat(updatedSellIn[3], is(originalSellIn[3] - 1));
    }

    @Test
    public void updateQuality_name은S_sellIn상관없이_quality상관없이_quality그대로_sellIn그대로() {
        Item[] testitems = new Item[6];
        int originalQuality[] = new int[6];
        int updatedQuality[] = new int[6];
        int originalSellIn[] = new int[6];
        int updatedSellIn[] = new int[6];

        testitems[0] = new Item("Sulfuras, Hand of Ragnaros", -1, 49);
        testitems[1] = new Item("Sulfuras, Hand of Ragnaros", -1, 50);
        testitems[2] = new Item("Sulfuras, Hand of Ragnaros", 0, 51);
        testitems[3] = new Item("Sulfuras, Hand of Ragnaros", 0, 49);
        testitems[4] = new Item("Sulfuras, Hand of Ragnaros", 1, 50);
        testitems[5] = new Item("Sulfuras, Hand of Ragnaros", 1, 51);
        DirtySample dirtySample = new DirtySample(testitems);

        setOriginalValues(originalQuality, originalSellIn, dirtySample);
        dirtySample.updateQuality();
        setUpdatedValues(updatedQuality, updatedSellIn, dirtySample);

        assertThat(updatedQuality[0], is(originalQuality[0]));
        assertThat(updatedQuality[1], is(originalQuality[1]));
        assertThat(updatedQuality[2], is(originalQuality[2]));
        assertThat(updatedQuality[3], is(originalQuality[3]));
        assertThat(updatedQuality[4], is(originalQuality[4]));
        assertThat(updatedQuality[5], is(originalQuality[5]));
        assertThat(updatedSellIn[0], is(originalSellIn[0]));
        assertThat(updatedSellIn[1], is(originalSellIn[1]));
        assertThat(updatedSellIn[2], is(originalSellIn[2]));
        assertThat(updatedSellIn[3], is(originalSellIn[3]));
        assertThat(updatedSellIn[4], is(originalSellIn[4]));
        assertThat(updatedSellIn[5], is(originalSellIn[5]));
    }

    @Test
    public void updateQuality_name은이외것_sellIn0미만_quality는0이상이면_quality2감소_quality는최소0_sellIn1감소() {
        Item[] testitems = new Item[4];
        int originalQuality[] = new int[4];
        int updatedQuality[] = new int[4];
        int originalSellIn[] = new int[4];
        int updatedSellIn[] = new int[4];

        testitems[0] = new Item("OtherName", -1,0);
        testitems[1] = new Item("OtherName", -1,2);
        testitems[2] = new Item("OtherName", -1,5);
        testitems[3] = new Item("OtherName", -100,50);
        DirtySample dirtySample = new DirtySample(testitems);

        setOriginalValues(originalQuality, originalSellIn, dirtySample);
        dirtySample.updateQuality();
        setUpdatedValues(updatedQuality, updatedSellIn, dirtySample);

        assertThat(updatedQuality[0], is(0));
        assertThat(updatedQuality[1], is(0));
        assertThat(updatedQuality[2], is(originalQuality[2] - 2));
        assertThat(updatedQuality[3], is(originalQuality[3] - 2));
        assertThat(updatedSellIn[0], is(originalSellIn[0] - 1));
        assertThat(updatedSellIn[1], is(originalSellIn[1] - 1));
        assertThat(updatedSellIn[2], is(originalSellIn[2] - 1));
        assertThat(updatedSellIn[3], is(originalSellIn[3] - 1));
    }

    @Test
    public void updateQuality_name은이외것_sellIn0이상_quality는0이상이면_quality1감소_quality는최소0_sellIn1감소() {
        Item[] testitems = new Item[4];
        int originalQuality[] = new int[4];
        int updatedQuality[] = new int[4];
        int originalSellIn[] = new int[4];
        int updatedSellIn[] = new int[4];

        testitems[0] = new Item("OtherName", 0,0);
        testitems[1] = new Item("OtherName", 0,1);
        testitems[2] = new Item("OtherName", 1,2);
        testitems[3] = new Item("OtherName", 100,50);
        DirtySample dirtySample = new DirtySample(testitems);

        setOriginalValues(originalQuality, originalSellIn, dirtySample);
        dirtySample.updateQuality();
        setUpdatedValues(updatedQuality, updatedSellIn, dirtySample);

        assertThat(updatedQuality[0], is(0));
        assertThat(updatedQuality[1], is(originalQuality[1] - 1));
        assertThat(updatedQuality[2], is(originalQuality[2] - 1));
        assertThat(updatedQuality[3], is(originalQuality[3] - 1));
        assertThat(updatedSellIn[0], is(originalSellIn[0] - 1));
        assertThat(updatedSellIn[1], is(originalSellIn[1] - 1));
        assertThat(updatedSellIn[2], is(originalSellIn[2] - 1));
        assertThat(updatedSellIn[3], is(originalSellIn[3] - 1));
    }

    @Test
    public void updateQuality_name은이외것_sellIn상관없이_quality는0미만이면_quality그대로_sellIn1감소() {
        Item[] testitems = new Item[6];
        int originalQuality[] = new int[6];
        int updatedQuality[] = new int[6];
        int originalSellIn[] = new int[6];
        int updatedSellIn[] = new int[6];

        testitems[0] = new Item("OtherName", -1,-1);
        testitems[1] = new Item("OtherName", -1,-2);
        testitems[2] = new Item("OtherName", 0,-1);
        testitems[3] = new Item("OtherName", 0,-3);
        testitems[4] = new Item("OtherName", 1,-1);
        testitems[5] = new Item("OtherName", 1,-4);
        DirtySample dirtySample = new DirtySample(testitems);

        setOriginalValues(originalQuality, originalSellIn, dirtySample);
        dirtySample.updateQuality();
        setUpdatedValues(updatedQuality, updatedSellIn, dirtySample);

        assertThat(updatedQuality[0], is(originalQuality[0]));
        assertThat(updatedQuality[1], is(originalQuality[1]));
        assertThat(updatedQuality[2], is(originalQuality[2]));
        assertThat(updatedQuality[3], is(originalQuality[3]));
        assertThat(updatedQuality[4], is(originalQuality[4]));
        assertThat(updatedQuality[5], is(originalQuality[5]));
        assertThat(updatedSellIn[0], is(originalSellIn[0] - 1));
        assertThat(updatedSellIn[1], is(originalSellIn[1] - 1));
        assertThat(updatedSellIn[2], is(originalSellIn[2] - 1));
        assertThat(updatedSellIn[3], is(originalSellIn[3] - 1));
        assertThat(updatedSellIn[4], is(originalSellIn[4] - 1));
        assertThat(updatedSellIn[5], is(originalSellIn[5] - 1));
    }

    private void setUpdatedValues(int[] updatedQuality, int[] updatedSellIn, DirtySample dirtySample) {
        for (int i = 0; i < dirtySample.items.length; i++) {
            updatedQuality[i] = dirtySample.items[i].quality;
            updatedSellIn[i] = dirtySample.items[i].sellIn;
        }
    }

    private void setOriginalValues(int[] originalQuality, int[] originalSellIn, DirtySample dirtySample) {
        for (int i = 0; i < dirtySample.items.length; i++) {
            originalQuality[i] = dirtySample.items[i].quality;
            originalSellIn[i] = dirtySample.items[i].sellIn;
        }
    }
}