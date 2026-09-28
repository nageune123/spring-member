# Spring 학습 - Response DTO 리팩터링

## 1. Response DTO

Entity를 클라이언트에게 직접 반환하지 않고
필요한 데이터만 Response DTO로 변환해서 반환한다.

흐름:
DB
→ Entity
→ Service
→ Response DTO
→ Controller
→ Client

## 2. MemberResponseDto

- id
- name
- 생성자
- getter
- setter는 사용하지 않음

응답에 필요한 정보만 담는다.

## 3. 단건 조회

Repository에서 Member Entity 조회
→ Service에서 MemberResponseDto로 변환
→ Controller에서 응답

## 4. 전체 조회

List<Member>
→ stream()
→ map()
→ MemberResponseDto로 변환
→ toList()
→ List<MemberResponseDto>

### Stream 핵심

- stream() : 데이터를 하나씩 처리할 준비
- map() : 하나의 형태를 다른 형태로 변환
- toList() : 변환된 결과를 List로 모음

## 5. 수정 / 삭제

수정과 삭제는 실제 DB 데이터를 변경해야 하므로
MemberResponseDto가 아니라 Member Entity를 사용한다.

- DB 작업 → Entity
- 클라이언트 응답 → DTO

## 6. ResponseEntity

HTTP 상태 코드와 응답 데이터를 함께 반환한다.

- POST → 201 Created
- GET → 200 OK
- PUT → 200 OK
- DELETE → 204 No Content

## 오늘 핵심

Request DTO → 클라이언트가 보내는 데이터
Entity → DB 작업
Response DTO → 클라이언트에게 보여줄 데이터
Repository → DB 접근
Service → 비즈니스 로직 + Entity/DTO 변환
Controller → 요청 받고 응답
