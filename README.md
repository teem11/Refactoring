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
> 1, 2,3 에 해당 
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
 
> update and install this package first
```shell
$ brew update
$ brew install fvcproductions
```



