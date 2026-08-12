# Day09 - Spring Boot + MySQL 복습 및 CRUD 확인

## 1. DTO → Entity 변환

클라이언트에서 JSON 요청이 들어오면:

JSON
→ @RequestBody
→ MemberDto

MemberDto는 요청 데이터를 받는 객체이다.

MemberDto를 DB에 바로 저장하는 것이 아니라
Member Entity로 변환한다.

Member.from(dto)

중요:

MemberDto 자체가 Member로 변하는 것이 아니다.

MemberDto의 값을 이용하여
새로운 Member 객체를 생성해서 반환한다.

흐름:

MemberDto
→ Member.from(dto)
→ 새로운 Member Entity

## 2. Entity → Response DTO 변환

DB에서 조회한 Member Entity를
클라이언트에게 그대로 반환하지 않고 Response DTO로 변환한다.

MemberResponseDto.from(member)

Member의 필요한 값을 이용하여
새로운 MemberResponseDto 객체를 생성한다.

흐름:

Member
→ MemberResponseDto.from(member)
→ 새로운 MemberResponseDto

## 3. DTO를 사용하는 이유

MemberDto
→ 클라이언트 요청 데이터를 받기 위한 객체

Member
→ JPA가 관리하고 DB와 연결되는 Entity

MemberResponseDto
→ 클라이언트에게 필요한 데이터만 응답하기 위한 객체

## 4. static from()

Member.from(dto)

MemberResponseDto.from(member)

from()은 static 메서드이므로
객체를 미리 생성하지 않고 클래스 이름으로 호출할 수 있다.

클래스이름.메서드()

예:

Member.from(dto)

## 5. Controller / Service / Repository 역할

Controller
→ HTTP 요청과 응답 처리

Service
→ 비즈니스 로직 처리

Repository
→ DB 접근

전체 흐름:

Client
→ Controller
→ Service
→ Repository
→ DB

## 6. @RequestBody / @Valid

@RequestBody

JSON 요청 데이터를 Java 객체로 변환한다.

JSON
→ @RequestBody
→ MemberDto

@Valid

DTO에 작성된 검증 규칙을 검사한다.

예:

@NotBlank
→ 빈 문자열이나 null 등을 허용하지 않음

## 7. JPA / Hibernate / JDBC

Spring Data JPA
→ Repository를 편하게 사용할 수 있도록 도와주는 Spring 기능

JPA
→ Java에서 ORM을 사용하기 위한 표준/규칙

Hibernate
→ JPA의 대표 구현체
→ Entity 관리
→ SQL 생성

JDBC Driver
→ Java와 MySQL이 통신할 수 있도록 연결

흐름:

Spring Data JPA
→ JPA
→ Hibernate
→ JDBC Driver
→ MySQL

## 8. JpaRepository

MemberRepository:

JpaRepository<Member, Long>

Member
→ Repository가 관리할 Entity 타입

Long
→ Member Entity의 기본키(PK) 타입

## 9. Dirty Checking

update()에서는 기존 Member를 조회한 후 값을 변경한다.

@Transactional
public MemberResponseDto update(Long id, MemberDto dto) {

    Member member = memberRepository.findById(id)
            .orElseThrow(MemberNotFoundException::new);

    member.setName(dto.getName());

    return MemberResponseDto.from(member);

}

memberRepository.save(member)를 다시 호출하지 않아도
JPA가 관리 중인 Entity의 변경을 감지하여
트랜잭션 종료 시 DB에 UPDATE를 반영한다.

이를 Dirty Checking(변경 감지)이라고 한다.

## 10. MySQL 데이터 유지 확인

Spring 서버를 종료했다가 다시 실행한 후:

GET /members

요청을 보내 기존 mysql-test 데이터가
그대로 유지되는 것을 확인했다.

H2 메모리 DB와 달리
MySQL에 저장한 데이터는 Spring 서버를 종료해도 유지된다.

## 11. MySQL CRUD 확인

CREATE
POST /members

READ
GET /members
GET /members/{id}

UPDATE
PUT /members/{id}

DELETE
DELETE /members/{id}

MySQL 환경에서 CRUD가 정상 동작하는 것을 확인했다.

## 12. 환경변수

application.properties에 실제 DB 비밀번호를 작성하지 않는다.

spring.datasource.password=${DB_PASSWORD}

실행 환경에서:

export DB_PASSWORD='비밀번호'

Spring은 DB_PASSWORD 환경변수 값을 읽어
MySQL 접속 비밀번호로 사용한다.

목적:

실제 비밀번호가 Git/GitHub에 노출되는 것을 방지한다.

## 13. 예외 처리

findById()에서 회원이 없으면:

.orElseThrow(MemberNotFoundException::new)

MemberNotFoundException 발생

→ GlobalExceptionHandler
→ ErrorResponse
→ 404 Not Found

Validation 실패:

@NotBlank
→ @Valid
→ 400 Bad Request

## 오늘 최종 흐름

요청:

JSON
→ @RequestBody
→ MemberDto
→ Controller
→ MemberService
→ Member.from(dto)
→ Member Entity
→ MemberRepository
→ Spring Data JPA
→ Hibernate
→ JDBC Driver
→ MySQL

응답:

MySQL
→ Member Entity
→ MemberResponseDto.from(member)
→ Controller
→ ResponseEntity
→ JSON

## 핵심 암기

- Request DTO = 요청 받기
- Entity = DB 작업
- Response DTO = 응답하기
- Controller = 요청/응답
- Service = 비즈니스 로직
- Repository = DB 접근
- JPA = 규칙
- Hibernate = JPA 구현 + SQL
- JDBC Driver = DB 통신
- MySQL = 실제 DB
