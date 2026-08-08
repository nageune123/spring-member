# Spring Boot Day03 - CRUD API 구현

## 오늘 목표

- CRUD(Create, Read, Update, Delete) 구현
- JPA Repository 사용
- Controller → Service → Repository 흐름 이해
- Postman으로 API 테스트

---

# 프로젝트 구조

```
Controller
    ↓
Service
    ↓
Repository
    ↓
Database(H2)
```

---

# 구현한 기능

## 1. 회원 등록(Create)

POST /members

```java
memberRepository.save(member);
```

### 배운 점

- save()는 JPA가 제공하는 메서드
- Entity를 DB에 저장한다.

---

## 2. 회원 전체 조회(Read)

GET /members

```java
return memberRepository.findAll();
```

### 배운 점

- 회원이 여러 명이라 List<Member> 반환
- return으로 Controller에 전달

---

## 3. 회원 단건 조회(Read)

GET /members/{id}

```java
return memberRepository.findById(id)
        .orElseThrow();
```

### 배운 점

- findById()는 Optional<Member> 반환
- 회원이 없으면 예외 발생
- @PathVariable 사용

---

## 4. 회원 수정(Update)

PUT /members/{id}

```java
Member member = findById(id);
member.setName(dto.getName());
```

### 배운 점

- 먼저 조회
- setter로 값 변경
- @Transactional
- Dirty Checking

---

## 5. 회원 삭제(Delete)

DELETE /members/{id}

```java
Member member = findById(id);
memberRepository.delete(member);
```

### 배운 점

- 먼저 조회
- delete() 사용
- 삭제 후에는 반환할 객체가 없음

---

# 새롭게 배운 어노테이션

## @RestController

JSON을 반환한다.

---

## @RequestBody

Body(JSON)를 Java 객체(DTO)로 변환한다.

---

## @PathVariable

URL의 값을 변수로 가져온다.

예)

```
GET /members/1
```

↓

```java
@PathVariable Long id
```

↓

id = 1

---

## @Transactional

하나의 작업을 하나의 트랜잭션으로 묶는다.

변경 감지(Dirty Checking)가 동작한다.

---

# JPA 메서드

```java
save()
```

저장

```java
findAll()
```

전체 조회

```java
findById()
```

단건 조회

```java
delete()
```

삭제

---

# CRUD 정리

| Method | URL           | 기능      |
| ------ | ------------- | --------- |
| POST   | /members      | Create    |
| GET    | /members      | Read(All) |
| GET    | /members/{id} | Read(One) |
| PUT    | /members/{id} | Update    |
| DELETE | /members/{id} | Delete    |

---

# 오늘 이해한 핵심

Controller

↓

Service

↓

Repository

↓

Database

Controller는 요청을 받고

Service는 비즈니스 로직을 처리하고

Repository는 DB와 통신한다.

---

# 오늘 가장 중요한 개념

- DTO와 Entity의 역할
- 생성자 주입
- Controller → Service → Repository 구조
- Optional
- List
- return
- @RequestBody
- @PathVariable
- @Transactional
- Dirty Checking

---

# 다음 시간

- Validation(@Valid)
- 예외 처리(ExceptionHandler)
- ResponseEntity
- Lombok
- MySQL 연동
