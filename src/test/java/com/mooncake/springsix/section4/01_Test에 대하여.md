# test 패키지에 section4>01_Test에대하여.md
# 수동 테스트의 한계
ex) 프린트된 메세지 확인, UI 적용 확인 등
- 프린트된 메세지 수동 확인은 불편
- 웹 UI 까지 반영된 뒤에 확인 -> 실패했을 때 확인해야할 코드가 너무 많음 -> 그리고 웹 API 통신을 위한 개발까지 붙여야 함 (ex: Controller)
- 테스트할 대상이 많아질수록 반영에 시간이 오래걸림

# 작은 크기의 Automated Test
- 기능 단위별로 Test 하는 것 (ex: 환율 가져오기, 계산된 값 제공 등)
- 검증 기능을 코드로 작성 / Junit 테스팅 프레임워크 사용
- 테스트 작성과 실행도 개발 과정의 일부!

# Junit 5 - Spring 에서 제공하는 Test Framework
- 알아야할 것 > @Test, @BeforeEach
- 테스트마다 새로운 인스턴스가 만들어짐(*)

--------------------4-4강
# 3강에서 만든 PaymentService prepareTestV1 은 과연 잘 만든 Test 일까?
- 환율 정보를 받아오는 곳이 점검 중이거나 끊겼음. "제어할 수 없는 것이 관여하는 것" 이 문제이다
- ExRateProvider 가 제공하는 환율 값으로 계산한게 맞는겨?
- 환율 유효 시간 계산은 정확한가? 이거 중요한 값인데?

## 위 문제점들은 공통적인 특성이 있는데, [Test 의 구성 요소]라는 이름으로 설명해보겠다

<그림 참조>

- SUT (테스트 되는 시스템) 가 존재, 테스트가 수행되는 Object 도 존재할 것이다
- 협력자들이 붙음 (Collaborator). 테스트 Object 가 의존하고 있는 다른 협력자들이 존재
- 이 상황에서 본다면, WebApiExRateProvider 가 Collaborator 이다 -> 과연 이 협력자는 우리가 Test 하고 싶은 대상인가?
- 얘가 통과/실패 하는지 궁금하진 않지만, 없을 수는 없는 상황 > 이 상황에서 사용하는 것이 Stub
- Stub 는 테스트용 Object 를 말한다. (Mock 아닌가 할 수도 있는데, Stub 도 같지만 "예상되는 결과" 제공에 사용된다)
- Test Double 이라고도 부른다. 주인공이 아닌데 주인공 처럼 꾸미고 나와서 대신 다른 역할을 해줌

## prepareTestV2 를 보고 알 수 있는 점 4-5강
- DI 와 테스트는 굉장히 긴밀한 관계에 있다 - DI 를 사용해서 (우리가 원하는 Custom 구현체 주입) Test 에게 자유를 주었음 (PSA 라고 한다)

## PaymentServiceSpringTest 4-6강
- 협력하는 의존관계가 많고 그 일이 중점될 경우, Container 를 띄운 상태로 Test 를 해도 좋음
- 즉, 결과 기반 Test 하는데 많은 클래스들의 상호작용을 보고 싶고, **내가 원하는대로 Container 상황**을 set 해주고 싶다
- 이럴 경우 매우 좋음!! 써야겠당
- 이 때 알아두면 좋은 Annotation - @ContextConfiguration, @Autowired
- 이 방식으로 Test 할 땐 이번 예제처럼 Stub Class 를 직접 말아줘야 할 것 같다. Mock 객체를 주입시키진 않을 것이기 때문인듯?
- Mock 의 역할만 하면 Mock 클래스라고 하고, Stub 의 역할까지 하면 Stub Class 라고 부른다고 생각하자


![관계 설정 책임 분리](/src/main/resources/image/section4_5.png)

- PaymentService 가 SUT (System Under Test)
- 테스트 객체가 시스템으로 주입되고, 외부는 Stub 을 사용하는 모습