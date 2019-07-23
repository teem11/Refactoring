# ajou-dirtycode

DirtySampleTest 작성 및 DirtySample refactoring을 진행했습니다.

먼저 DirtySample코드를 분석하여 값을 변경하는 요인과 결과를 모두 따져봤습니다.
sellIn과 quality를 변경하는 구문이 8개가 있었고 이 값들을 변경하는 요인인
name, sellIn, quality에 따라서 어떻게 sellIn과 quality가 변하는 지를
모두 따진 12개의 Testcode로 작성했습니다.
여러 Testcode와 Testcode안의 조건도 여러가지를 둬서 잘 돌아가는지 확인 후에
본 코드인 DirtySample의 refactoring을 시작을 했습니다.
일단 너무 조건-결과가 명확히 정리되지 않아서 불변의, 독립적인 조건이 되는 name에 따라서
동작하도록 switch문을 이용하고 메소드를 나누었습니다.
각각의 메소드에서 sellIn과 quality의 조건에 따라 단순히 순차적으로
코드를 읽으며 결과를 산출할 수 있도록 testcode를 작성했습니다.
nesting하는 if문을 작성하지 않고 단순히 작성하여 가독성이 좋게 작성했습니다.
중간에 checkSellInScope()처럼 단순 조건때문에 메소드가 길어지는 경우 따로 메소드를 뺐습니다.
메소드명도 기존의 updateQuality보다 sellIn도 변경하는 updateQualityAndSellInOfItems()로 적절히 바꿔줬습니다.
다수의 메소드에서 중복적으로 사용하는 quality가 증가할 때 50이 넘으면 50으로 고정시키는 동작을
updateQualityAndSellInOfItems()라는 메소드로 묶어서 간단하게 하였습니다.
이외에 수업시간에 배운 refactoring 기법들을 사용하여 코드를 정리 정돈을 했습니다.


