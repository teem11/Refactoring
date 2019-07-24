# TEAM 11 Homework 
## Dirty code for refactoring
### 제작 과정 
#### 1. Test Code 작성
- DirtyCode Sample을 분석하여 코드의 분기와 조건을 정리하였습니다.
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

- SRP 점검 
    - DirtySample은 SRP에 위반되는 내용이 없다고 판단되어 코드의 일부를 다른 Class로 분리하지 않았습니다
- Method는 20라인보다 간결하게
    - 기존에 method의 이름은 20라인이 넘어가는 것이 없어 수정하지 않았습니다.
- 중복 코드가 있는지 확인 
    - 기존의 method는 중복이 되는 부분이 없어 수정하지 않았습니다.
- 코드의 길이를 줄이자
    - DirtySample은 lombok을 써야 할 정도로 변수가 많지 않아서 따로 @Data를 달지 않았습니다.
    - 코드의 길이가 길어서 서로 독립적인 부분을 기준으로 3등분 하였습니다. 각각의 부분은 정확히
    어떤 목적으로 작성되었는지 알 수 없어서 이름은 임의로 정했습니다.
    > before
    ```shell
    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            if (!items[i].name.equals("Aged Brie")
                    && !items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                if (items[i].quality > 0) {
                    if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                        items[i].quality = items[i].quality - 1;
                    }
                }
            } else {
                if (items[i].quality < 50) {
                    items[i].quality = items[i].quality + 1;

                    if (items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
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
                    }
                }
            }
    ```
    > after 
    ```shell
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
    ```
    
    > before
    ``` shell
    if (items[i].sellIn < 0) {
                if (!items[i].name.equals("Aged Brie")) {
                    if (!items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (items[i].quality > 0) {
                            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                                items[i].quality = items[i].quality - 1;
                            }
                        }
                    } else {
                        items[i].quality = items[i].quality - items[i].quality;
                    }
                } else {
                    if (items[i].quality < 50) {
                        items[i].quality = items[i].quality + 1;
                    }
                }
            }
        }
    ```
    > after
    ``` shell
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
    ```
    
- conditional block은 method로 따로 extract
    - conditional block은 Item의 name을 확인하는 부분만 따로 method로 만들었습니다.
    - 다른 conditional block은 따로 method로 만드는것보다 그대로 놔두는게 깔끔해서 수정하지 않았습니다.

- 변수명, 메소드명은 길어도 바로 이해 가능하게 
    - Item의 attribute(name, quality, sellin)는 많지 않고 직관적으로 이해 할 수 있어서 이름을 따로 수정하지 않았습니다.
    - Item의 name을 확인하는 method를 extract 하였습니다.
    ```shell
    private boolean is_Sulfuras(Item item) {
        return item.name.equals("Sulfuras, Hand of Ragnaros");
    }

    private boolean is_Back_Stage(Item item) {
        return item.name.equals("Backstage passes to a TAFKAL80ETC concert");
    }

    private boolean is_Aged_brie(Item item) {
        return item.name.equals("Aged Brie");
    }
    ```
- 불필요한 if, else 제거
    - if의 conditional block을 하나로 묶을 수 있는 부분을 수정하였습니다.
    - 제거한 if, else는 여러개가 있지만 대개 중복되어서 소개는 한개만 하겠습니다.
    > before
    ```shell
     if (items[i].quality > 0) {
                if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                    items[i].quality = items[i].quality - 1;
                }
            }
    ```
    >after
    ```shell
    if (item.quality > 0 && !is_Sulfuras(item)) {
            item.quality = item.quality - 1;
        }
    ```


