# Spring Boot Day04 - Validation & Exception Handling

## 오늘 목표

- Validation 적용
- ResponseEntity 이해
- Global Exception Handler 이해
- 실무 API 응답 방식 이해

---

# 1. Validation

DTO에서 입력값을 검증한다.

```java
@NotBlank(message = "이름은 필수입니다.")
private String name;
```

### 배운 점

- null 검사
- 빈 문자열 검사
- 공백 문자열 검사

Service에서 if문으로 검사하지 않아도 된다.

---

# 2. @Valid

```java
@PostMapping("/members")
public void join(@Valid @RequestBody MemberDto dto)
```

### 역할

RequestBody로 받은 DTO를 Validation한다.

순서

JSON

↓

RequestBody

↓

DTO

↓

@Valid

↓

@NotBlank 검사

---

# 3. Validation 실패

예)

```json
{
  "name": ""
}
```

↓

```http
400 Bad Request
```

### 배운 점

잘못된 요청은 DB까지 가지 않는다.

Controller 단계에서 Validation이 막아준다.

---

# 4. ResponseEntity

기존

```java
return member;
```

↓

Spring이

200 OK

자동 응답

---

ResponseEntity

```java
return ResponseEntity.ok(member);
```

### 장점

상태코드를 직접 지정할 수 있다.

예)

```java
ResponseEntity.ok()
```

200 OK

```java
ResponseEntity.badRequest()
```

400 Bad Request

```java
ResponseEntity.noContent()
```

204 No Content

```java
ResponseEntity.notFound()
```

404 Not Found

---

# 5. @RestControllerAdvice

```java
@RestControllerAdvice
public class GlobalExceptionHandler
```

### 역할

프로젝트 전체 Controller의 예외를 한 곳에서 처리한다.

---

# 6. @ExceptionHandler

```java
@ExceptionHandler(MethodArgumentNotValidException.class)
```

### 역할

Validation 실패 예외를 잡는다.

---

# 7. 예외 처리 흐름

사용자 요청

↓

Controller

↓

@Valid

↓

Validation 실패

↓

GlobalExceptionHandler

↓

ResponseEntity

↓

사용자에게 메시지 전달

---

# 오늘 가장 중요한 개념

- Validation
- @NotBlank
- @Valid
- ResponseEntity
- @RestControllerAdvice
- @ExceptionHandler

---

# HTTP 상태 코드 정리

200 OK

성공

201 Created

생성 성공

204 No Content

삭제 성공

400 Bad Request

잘못된 요청

404 Not Found

리소스를 찾을 수 없음

500 Internal Server Error

서버 내부 오류

---

# 오늘 이해한 흐름

Client

↓

Controller

↓

Validation

↓

Service

↓

Repository

↓

Database

↓

ResponseEntity

↓

Client

---

# 내일 할 것

- ErrorResponse DTO 만들기
- ResponseEntity<ErrorResponse>
- Custom Exception
- MemberNotFoundException
- 404 응답 직접 만들기
- 실무 스타일 API 응답 구조
