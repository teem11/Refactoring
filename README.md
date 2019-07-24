# TEAM 11 Homework 
## Dirty code for refactoring
### 제작 과정 
#### 1. Test Code 작성
- DirtyCode Sample을 분석하여 코드의 분기와 조건을 정리하였습니다.
- sellIn과 quality를 변경하는 구문이 8개가 있었고 이 값들을 변경하는 요인인
name, sellIn, quality에 따라서 어떻게 sellIn과 quality가 변하는 지를
모두 따진 12개의 Testcode로 작성했습니다.
> 테스트 조건
```shell
 이름 매칭 조건, sellIn조건 순으로 testmethod작성, 독립적이지 않은 것과 순차적 실행 조심
    A(Aged Brie), B(BackStage...), S(Sulfuras...) 
    --------------------------------------------------------------------------
    1. A,B,S의 이름이 매칭이 되지 않으며 quality > 0 인 경우 - quality1감소
    2. A,B,S의 이름이 매칭되지 않고 quality > 0 이고 sellIn < 0 인 경우 - quality1감소
    3. S의 이름이 매칭되지 않는 경우 - sellIn1감소
    --------------------------------------------------------------------------
    4. A나 B의 이름이 매칭되며, quality < 50 인 경우 - quality1증가
    5. A의 이름이 매칭되며, sellIn < 0 이며 quality < 50 인 경우 - quality1증가
    6. B의 이름이 매칭되며, sellIn < 11 이고 quality < 50 인 경우 - quality1증가
    7. B의 이름이 매칭되며, sellIn < 6 이고 quality < 50 인 경우 - quality1증가
    8. B의 이름이 매칭되며, sellIn < 0 인 경우 - quality 0으로 초기화
```
- 다른 테스트 조건에 독립적인 경우(1개의 조건만 해당하는 경우) 해당 조건만을 테스트로 만들었습니다.
- 다른 테스트 조건에 독립적이지 않은 경우(2개 이상의 조건에 해당하는 경우) 발생가능한 경우의 수를 계산하여 테스트를 만들었습니다.
- 만든 테스트와 해당하는 조건들
> 3, 5 에 해당
```shell
updateQuality_name은A_sellIn은0이하_quality는50이하면_quality2증가_quality는최대50_sellIn1감소 
```
> 3, 4 에 해당
```shell
updateQuality_name은A_sellIn은1이상_quality는50이하면_quality1증가_quality는최대50_sellIn1감소
```
> 3에 해당하고 4, 5에 해당하지 않음
```shell
updateQuality_name은A_sellIn상관없이_quality는50이상이면_quality그대로_sellIn1감소
```
> 3에 해당
```shell
updateQuality_name은B_sellIn11이상_quality는50이하면_quality1증가_quality는최대50_sellIn1감소
```
> 3, 6에 해당
```shell
updateQuality_name은B_sellIn6이상11미만_quality는50이하면_quality2증가_quality는최대50_sellIn1감소
```
> 3, 7에 해당
```shell
updateQuality_name은B_sellIn1이상6미만_quality는50이하면_quality3증가_quality는최대50_sellIn1감소
```
> 3, 8에 해당 
```shell
updateQuality_name은B_sellIn0이하_quality는상관없이_quality는0으로초기화_sellIn1감소
```
> 3에 해당하고 6, 7, 8에 해당하지 않음
```shell
updateQuality_name은B_sellIn1이상_quality는50이상이면_quality그대로_sellIn1감소
```
> 1-8 에 해당하지 않음
```shell
updateQuality_name은S_sellIn상관없이_quality상관없이_quality그대로_sellIn그대로
```
> 1, 2, 3 에 해당 
```shell
updateQuality_name은이외것_sellIn0미만_quality는0이상이면_quality2감소_quality는최소0_sellIn1감소
```
> 1, 3에 해당
```shell
updateQuality_name은이외것_sellIn0이상_quality는0이상이면_quality1감소_quality는최소0_sellIn1감소
```
> 3 에 해당
```shell
updateQuality_name은이외것_sellIn상관없이_quality는0미만이면_quality그대로_sellIn1감소
```
#### 2. DirtySample 리팩토링

- 리팩토링 주요 기준
    - 변하지 않고, 독립적인 조건이 되는 name에 따라서 동작하도록 switch문을 이용하고 메소드를 나누었습니다.
    > main
    ```shell
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
    ```
    - 각각의 메소드에서 sellIn과 quality의 조건에 따라 단순히 순차적으로 코드를 읽으며 결과를 산출할 수 있도록 testcode를 작성했습니다.
    - nesting하는 if문을 작성하지 않고 단순히 작성하여 가독성이 좋게 작성했습니다.
        - 아래의 메소드들은 모두 if else 구문을 중첩하여 코드를 작성하지 않기 위해 item.name을 기준으로 만들었습니다.
        ```shell
        private void updateQualityAndSellInOfItemNameAged_Brie(Item item) 
        private void updateQualityAndSellInOfItemNameBackstage_passes_to_a_TAFKAL80ETC_concert(Item item) 
        private void updateQualityAndSellInOfItemNameOthers(Item item) 
        ```
    - 중간에 checkSellInScope()처럼 단순 조건때문에 메소드가 길어지는 경우 따로 메소드를 뺐습니다. 
    > before
    ```shell
     if (items[i].sellIn < 11) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }

                        if (items[i].sellIn < 6) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }
    ```
    > after
    ``` shell
    private int checkSellInScope(int sellInScope){
        if(sellInScope >= 1 && sellInScope < 6)
            return 3;
        else if(sellInScope >= 6 && sellInScope < 11)
            return 2;
        else if(sellInScope >= 11)
            return 1;
        return 0;
    }
    ```
    - 메소드명도 기존의 updateQuality보다 sellIn도 변경하는 updateQualityAndSellInOfItems()로 적절히 바꿔줬습니다. 
    
    - 다수의 메소드에서 중복적으로 사용하는 quality가 증가할 때 50이 넘으면 50으로 고정시키는 동작을 updateQualityAndSellInOfItems()라는 메소드로 묶어서 간단하게 하였습니다.

    - SRP 점검 
        - DirtySample은 SRP에 위반되는 내용이 없다고 판단되어 코드의 일부를 다른 Class로 분리하지 않았습니다
    - 변수명, 메소드명은 길어도 바로 이해 가능하게 
        >예시
        ```shell
        public void updateQualityAndSellInOfItems() 
        private void updateQualityAndSellInOfItemNameAged_Brie(Item item) 
        private void updateQualityAndSellInOfItemNameBackstage_passes_to_a_TAFKAL80ETC_concert(Item item) 
        private void updateQualityAndSellInOfItemNameOthers(Item item) 
        private int setMaximumQuality50IfExcessTo50(int originalItemQuality, int updatedItemQuality)
        private int checkSellInScope(int sellInScope)
        private int setMaximumQuality0IfExcessTo0(int originalItemQuality, int updatedItemQuality)
        ```

