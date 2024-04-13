# SpringBoot Project #Shoppingmall

## Danggeuni Shop

Danggeuni Shopping mall

---

### **프로젝트 목표**

admin 기능과 client의 기능을 구분하여 구현한다. 

---

### **프로젝트 상세 내역**

**구현 기능 (VIEW)**

- 어드민
아이템 추가, 아이템 코드 생성, 아이템 삭제, 배송 정보 확인
- 클라이언트
회원 가입, 로그인, 아이템 구매, 카트에 담기, 카트 목록 구매, 카트 목록 빼기, 배송 정보 확인

**구현 기능 (SKILL)**

- Mybatis
기존 JdbcTemplate의 고질적 문제인 rowMapper를 이용한 mapping의 번거로움 해결
- Redis
cache 처리를 구현하여 서버 자원의 낭비를 줄임

**테이블 정보**

기존과 달리 테이블의 구성이 상당히 복잡해져서 단순히 생각 만으로 구현하기에 어려움을 겪었다. ERD을 이용해 각각의 테이블을 규정하여 조금이라도 복잡성을 줄이고자 하였다.

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/a3f143b6-ac57-4695-8470-bb93764898a2/4ae82374-cfa1-4fcc-8f9e-c585f507e75c/Untitled.png)

ERD 프로그램을 이용해 처음 해보는 작업이어서 테이블을 설계하고 관계를 짓는데 꼬박 하루가 걸렸다. 어떻게 테이블을 구성해야 할지 이전에 진행한 프로젝트에서는 최대 3개 정도의 테이블로 어려움이 없었으나, 구조가 복잡해짐에 따라 테이블의 column을 정하는 것도 여간 쉽지 않았다.
