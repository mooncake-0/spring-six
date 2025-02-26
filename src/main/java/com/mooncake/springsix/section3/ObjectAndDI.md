3강 - Object 와 의존관계

1. Class & Object
> Object : 프로그램을 실행했을 때 움직이는 무엇 (주문 제품)
> Class : Object 를 만들어내기 위해 필요한 것 (주문서, 청사진)
> Instance : "~~의 Instance" 로 표현 - 앞에 나오는 어떤 추상적인 것에 대한 실체라는 뜻
> Class Instance == Object (Java 에서 기술하는 표현)
> 자바에서는 배열 (array) 도 Object 로 취급한다 (Java 에서 Object 란? Class Instance / Array 가 기술적으로 정확)

2. 의존관계 Dependency
> A, B 인 두가지가 있어야 함 (A --> B Dependency 표현)
> "Client Class 가 Supplier Class 에 의존한다" => Supplier 를 공급받아야 Client 가 존재 or 일을 수행할 수 있다
> (**) Supplier 가 변경되면 Client 가 영향을 받는다
> (****) Object 간 의존관계는 Runtime 환경에서 만들어지기 때문에, Class Level (Compile 시점) 의 의존관계와 Object Level (Runtime 시점) 의 의존관계가 다를 수 있다 (Spring 핵심 기능)
>> Object Diagram 과 Class Diagram 은 다른 부분!

* 코드 개선 *
1. 기능 및 효율성 개선
2. 구조를 개선 (refactoring)

3. 관심사의 분리 Separation of Concern (SoC)
> SW 분야에 굉장히 오래 전부터 존재, 중요 원리
> 관심사란, "변경이라는 관점을 설명할 수 있다" -> "이 코드는 언젠간 변경될 것이다" (기술적인 혹은 비즈니스 로직 둘다) -> 하지만 두 로직의 변경 시점은 보통 다르다

4. 관계 설정 책임 -> Interface 구현체를 어떤 것을 쓸지 매핑 해주는 곳
> V5과 V6 참조
> 이 부분에 대한 그래프 (본 강의 확인)
> 디자인 패턴에서 사용처들이 다 Client 입장으로서 알게 되는 느낌을 다시 한번 확인 

![관계 설정 책임 분리](src/main/resources/image/section3_6.png)