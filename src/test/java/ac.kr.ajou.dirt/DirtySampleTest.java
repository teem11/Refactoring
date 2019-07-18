package ac.kr.ajou.dirt;


import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

// 한글로 된 메소드는 오류가 발생하여 간단한 이름으로 적고 한글로 주석을 달았습니다.
public class DirtySampleTest {
    private final String aged_Brie = "Aged Brie";
    private final String backstage = "Backstage passes to a TAFKAL80ETC concert";
    private final String sulfuras= "Sulfuras, Hand of Ragnaros";

    @Test
    public void test4(){ // 이름이 sulfuras가 아닌 경우 sellin = sellin - 1
        Item[] testItems = new Item[1];
        testItems[0] = new Item("test4", 0, 0 );
        DirtySample dirtySample = new DirtySample(testItems);
        dirtySample.updateQuality();
        assertThat(testItems[0].getSellIn(), is(-1));
    }

    @Test
    public void test5(){ // 이름이 backstage, aged가 아니면서 qaulity > 0, sellin > 0 인 경우 quality = quality - 1
        Item[] testItems = new Item[1];
        testItems[0] = new Item("test5", 1, 10);
        DirtySample dirtySample = new DirtySample(testItems);
        dirtySample.updateQuality();
        assertThat(testItems[0].getQuality(), is(9));
    }

    @Test
    public void test6(){ // 이름이 backstage, aged가 아니면서 quality < 0,sellin > 0 인 경우 넘어가기
        Item[] testItems = new Item[1];
        testItems[0] = new Item("test5", 1, -1);
        DirtySample dirtySample = new DirtySample(testItems);
        dirtySample.updateQuality();
        assertThat(testItems[0].getQuality(), is(-1));
    }

    @Test
    public void test7(){ // 이름이 aged 또는 backstage 이고 quality > 50 인 경우 pass
        Item[] testItems = new Item[2];
        testItems[0] = new Item(aged_Brie, 1, 51);
        testItems[1] = new Item(backstage, 1, 51);
        DirtySample dirtySample = new DirtySample(testItems);
        dirtySample.updateQuality();
        assertThat(testItems[0].getQuality(), is(51));
        assertThat(testItems[1].getQuality(), is(51));
    }

    @Test
    public void test8(){ // 이름이 aged 또는 backstage 일 때 quality < 50 && sellin >= 11 인 경우 quality = quality + 1
        Item[] testItems = new Item[2];
        testItems[0] = new Item(aged_Brie, 11, 3);
        testItems[1] = new Item(backstage, 11, 4);

        DirtySample dirtySample = new DirtySample(testItems);
        dirtySample.updateQuality();
        assertThat(testItems[0].getQuality(), is(4));
        assertThat(testItems[1].getQuality(), is(5));
    }

    @Test
    public void test9(){ // 이름이 aged 또는 backstage 일 때 quality < 50 && 0 < sellin < 11 인 경우
        Item[] testItems = new Item[3];
        testItems[0] = new Item(aged_Brie, 5, 3);
        testItems[1] = new Item(backstage, 5, 3);
        testItems[2] = new Item(backstage, 6, 3);

        DirtySample dirtySample = new DirtySample(testItems);
        dirtySample.updateQuality();
        assertThat(testItems[0].getQuality(), is(4)); // 이름이 aged 인 경우 quality = quality + 1
        assertThat(testItems[1].getQuality(), is(6)); // sellin < 6 인 경우 quality = quality + 3
        assertThat(testItems[2].getQuality(), is(5)); // sellin >= 6 인 경우 quality = quality + 2
    }

    @Test
    public void test10(){ // sellin < 0 이고 이름이 aged인 경우
        Item[] testItems = new Item[2];
        testItems[0] = new Item(aged_Brie, -1, 3); // quality < 50
        testItems[1] = new Item(aged_Brie, -1, 50); // quality >= 50

        DirtySample dirtySample = new DirtySample(testItems);
        dirtySample.updateQuality();
        assertThat(testItems[0].getQuality(), is(5)); // quality = quality + 2
        assertThat(testItems[1].getQuality(), is(50)); // pass

    }

    @Test
    public void test11(){ // sellin < 0 이고 이름이 backstage인 경우
        Item[] testItems = new Item[1];
        testItems[0] = new Item(backstage, -1, 3); // quality < 50

        DirtySample dirtySample = new DirtySample(testItems);
        dirtySample.updateQuality();
        assertThat(testItems[0].getQuality(), is(0)); // quality = 0
    }

    @Test
    public void test12() { // sellin < 0 이고 이름이 backstage가 아닌 경우
        Item[] testItems = new Item[3];
        testItems[0] = new Item(backstage, -1, 3); // quality > 0
        testItems[1] = new Item("abc", -1, -4); // quality < 0
        testItems[2] = new Item(sulfuras, -1, 3);

        DirtySample dirtySample = new DirtySample(testItems);
        dirtySample.updateQuality();
        assertThat(testItems[0].getQuality(), is(0)); // quality = quality - 1
        assertThat(testItems[1].getQuality(), is(-4)); // pass
        assertThat(testItems[2].getQuality(), is(3)); // pass
    }
}