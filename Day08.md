## Spring Member - MySQL 연동

### 1. H2에서 MySQL로 변경

기존 H2 메모리 DB에서 MySQL로 데이터베이스를 변경했다.

MySQL 설치 및 실행 확인:

- MySQL 8.0
- MySQL Server 실행
- memberdb 데이터베이스 생성
- springuser 계정 생성
- memberdb 사용 권한 부여

### 2. MySQL JDBC Driver

build.gradle에서 H2 의존성을 제거하고 MySQL JDBC Driver를 추가했다.

runtimeOnly 'com.mysql:mysql-connector-j'

JDBC Driver는 Java/Spring 애플리케이션과 MySQL이 통신할 수 있도록 연결해준다.

### 3. application.properties

Spring Boot가 MySQL에 접속하도록 DB 연결 정보를 설정했다.

spring.datasource.url=jdbc:mysql://localhost:3306/memberdb
spring.datasource.username=springuser

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

### 4. JPA와 MySQL 연결 흐름

Spring Boot
→ Spring Data JPA
→ JPA
→ Hibernate
→ JDBC Driver
→ MySQL
→ memberdb

### 5. Entity와 실제 테이블

Member Entity:

Long id
String name

Hibernate가 Entity를 분석하여 MySQL에 다음 테이블을 생성했다.

member

- id : bigint / Primary Key
- name : varchar(255)

member_seq

- @GeneratedValue의 ID 생성에 사용

### 6. 실제 데이터 저장 확인

Postman에서:

POST /members

{
"name": "mysql-test"
}

요청 후 MySQL에서:

SELECT \* FROM member;

결과:

id = 1
name = mysql-test

Spring → JPA → MySQL 데이터 저장이 정상적으로 동작하는 것을 확인했다.

### 핵심 흐름

요청:

JSON
→ @RequestBody
→ MemberDto
→ Controller
→ Service
→ Member.from(dto)
→ Member Entity
→ Repository
→ JPA/Hibernate
→ JDBC Driver
→ MySQL

응답:

MySQL
→ Member Entity
→ MemberResponseDto.from(member)
→ Controller
→ ResponseEntity
→ JSON

### 오늘 핵심 복습

- DTO가 Entity로 변신하는 것이 아니라 새로운 Entity 객체를 생성한다.
- Member.from(dto)는 dto의 값을 이용해 새로운 Member를 만든다.
- MemberResponseDto.from(member)는 Member의 값을 이용해 새로운 ResponseDto를 만든다.
- static 메서드는 객체를 먼저 만들지 않고 클래스 이름으로 호출할 수 있다.
- POST는 새로운 Entity를 생성한다.
- PUT은 기존 Entity를 조회해서 수정한다.
- @Transactional 안의 Entity 변경은 Dirty Checking으로 DB에 반영된다.
- JpaRepository<Entity, PK타입> 형태로 사용한다.
- DB를 H2에서 MySQL로 변경해도 Controller/Service/Repository 구조는 대부분 그대로 유지된다.
