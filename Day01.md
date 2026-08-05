# Day01 - Spring Boot 프로젝트 시작

## 1. Spring Boot 프로젝트 생성

- Spring Initializr 사용
- Gradle 프로젝트 생성
- WSL Ubuntu에서 실행
- VS Code로 프로젝트 열기
- ./gradlew bootRun 실행

---

# 프로젝트 구조

spring-member

src/main/java/com/example/spring_member

- controller
- service
- repository
- entity
- dto

역할

Controller : 요청 받기

Service : 비즈니스 로직

Repository : DB 접근

Entity : DB 테이블과 매핑

DTO : 데이터 전달 객체

---

# Entity

```java
@Entity
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
```

암기

@Entity
→ DB 테이블과 연결

@Id
→ 기본키(PK)

@GeneratedValue
→ 기본키 자동 생성

---

# Repository

```java
public interface MemberRepository
        extends JpaRepository<Member, Long> {

}
```

암기

JpaRepository<Member, Long>

Member
→ 관리할 Entity

Long
→ @Id 타입

save()
findById()
findAll()
delete()

자동 제공

---

# Service

```java
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public void join(Member member){
        memberRepository.save(member);
    }
}
```

암기

Service는

직접 DB 접근 X

Repository에게 저장 요청

```java
memberRepository.save(member);
```

---

# Controller

```java
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

}
```

암기

@RestController

↓

브라우저 요청 받기

↓

Service 호출

---

@PostMapping

```java
@PostMapping("/members")
```

의미

브라우저에서

POST /members

요청이 오면

아래 메서드를 실행한다.

---

# DTO

```java
public class MemberDto {

    private String name;

    public MemberDto(){}

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

}
```

DTO 역할

브라우저(JSON)

↓

DTO

↓

Entity

↓

DB

---

# Getter / Setter

Getter

```java
public String getName()
```

값을 반환

return 사용

Setter

```java
public void setName(String name)
```

값을 저장

void 사용

암기

Getter

↓

가져오기(GET)

↓

return

Setter

↓

넣기(SET)

↓

void

---

# 생성자 주입(DI)

```java
private final MemberRepository memberRepository;

public MemberService(MemberRepository memberRepository){
    this.memberRepository = memberRepository;
}
```

암기

Spring이 객체를 생성해서 넣어준다.

우리는 new 하지 않는다.

---

# 오늘 가장 중요한 흐름

브라우저

↓

Controller

↓

Service

↓

Repository

↓

DB

---

브라우저(JSON)

↓

DTO

↓

Entity

↓

Repository

↓

DB

---

# 오늘 배운 핵심

- Spring 프로젝트 생성
- 프로젝트 구조 이해
- Entity 생성
- Repository 생성
- Service 생성
- Controller 생성
- DTO 생성
- 생성자 주입(DI)
- JpaRepository 사용
- @Entity
- @Id
- @GeneratedValue
- @RestController
- @PostMapping
- Getter / Setter 원리
- final 의미
- Repository는 DB 접근
- Service는 비즈니스 로직
- Controller는 요청 처리

---

# 내일 할 것

- @RequestBody
- MemberDto → Entity 변환
- Controller → Service 연결
- 실제 회원 저장
- H2 DB 확인
- Postman으로 API 테스트
