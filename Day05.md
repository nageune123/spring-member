# Spring Day 5

## 배운 내용

- Validation(@Valid, @NotBlank)
- DTO 검증
- GlobalExceptionHandler
- ErrorResponse DTO
- Custom Exception(MemberNotFoundException)
- 400 Bad Request 처리
- 404 Not Found 처리

## 핵심 흐름

Client
→ Controller
→ Service
→ Repository
→ Database
→ Exception
→ GlobalExceptionHandler
→ ErrorResponse
→ JSON Response

## 오늘 느낀 점

Validation과 Exception 처리를 직접 구현하면서
Spring이 예외를 어떻게 관리하는지 이해했다.
ResponseEntity와 ErrorResponse를 이용해
실무에서 사용하는 형태의 API 응답을 만들 수 있었다.
