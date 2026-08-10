## Spring Member - DTO / Service 리팩터링

### 1. Lombok 적용

반복되는 생성자와 Getter/Setter 코드를 Lombok으로 정리했다.

- @Getter
- @Setter
- @NoArgsConstructor
- @RequiredArgsConstructor

### 2. Controller 단순화

Controller는 HTTP 요청을 받고 Service를 호출한 뒤
ResponseEntity로 응답하는 역할에 집중하도록 수정했다.

흐름:

Client
→ Controller
→ Service
→ Repository
→ DB

### 3. Request DTO → Entity 변환

회원 생성 시 Service에서 직접 Entity를 만드는 코드를 줄였다.

기존:

Member member = new Member();
member.setName(dto.getName());

변경:

Member member = Member.from(dto);

Member.from(dto)는 MemberDto를 받아
Member Entity로 변환한다.

### 4. Entity → Response DTO 변환

기존:

new MemberResponseDto(member.getId(), member.getName());

변경:

MemberResponseDto.from(member);

Member Entity에서 클라이언트에게 필요한 데이터만
MemberResponseDto로 변환한다.

### 5. 전체 조회 Stream 리팩터링

기존:

.map(member -> MemberResponseDto.from(member))

변경:

.map(MemberResponseDto::from)

:: 는 메서드 참조이다.

### 6. static

from() 메서드는 static이므로 객체를 먼저 만들지 않고
클래스 이름으로 바로 호출할 수 있다.

Member.from(dto);
MemberResponseDto.from(member);

### 7. update 리팩터링

기존:

Controller
→ service.update()
→ service.findById()

변경:

Controller
→ service.update()
→ MemberResponseDto 바로 반환

Service 호출을 줄이고 Controller를 단순하게 만들었다.

### 8. 최종 데이터 흐름

요청:

JSON
→ MemberDto
→ Controller
→ Service
→ Member.from(dto)
→ Member Entity
→ Repository
→ DB

응답:

DB
→ Member Entity
→ MemberResponseDto.from(member)
→ Controller
→ ResponseEntity<MemberResponseDto>
→ JSON

### 오늘 헷갈린 부분

DTO와 Entity 사이의 변환 과정은 추가 복습 필요.

특히:

- Member.from(dto)
- MemberResponseDto.from(member)
- static 메서드
- MemberResponseDto::from 메서드 참조
