# TEAM 11 Homework 
## Dirty code for refactoring
### 제작 과정 
#### 1. Test Code 작성

#### 2. DirtySample 리팩토링

 - 이름 매칭 조건, sellIn조건 순으로 testmethod작성, 독립적이지 않은 것과 순차적 실행 조심
    --------------------------------------------------------------------------
    A,B,S의 이름이 매칭이 되지 않으며 quality > 0 인 경우 - quality1감소
    A,B,S의 이름이 매칭되지 않고 quality > 0 이고 sellIn < 0 인 경우 - quality1감소
    S의 이름이 매칭되지 않는 경우 - sellIn1감소
    --------------------------------------------------------------------------
    A나 B의 이름이 매칭되며, quality < 50 인 경우 - quality1증가
    A의 이름이 매칭되며, sellIn < 0 이며 quality < 50 인 경우 - quality1증가
    B의 이름이 매칭되며, sellIn < 11 이고 quality < 50 인 경우 - quality1증가
    B의 이름이 매칭되며, sellIn < 6 이고 quality < 50 인 경우 - quality1증가
    B의 이름이 매칭되며, sellIn < 0 인 경우 - quality 0으로 초기화
    ----------------------------------------------

> update and install this package first
```shell
$ brew update
$ brew install fvcproductions
```



