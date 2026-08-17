# Docker Day 15 - 장애 대응 실습

## 기본 장애 대응 순서

1. 상태 확인

   - docker compose ps -a

2. 로그 확인
   - docker logs --tail 100 <컨테이너>

3. 로그의 ERROR / Caused by 확인

4. 원인 수정

5. 서비스 복구 후 상태 및 실제 요청 확인


## 1. Spring 컨테이너 중지

- 상태: Exited (143)
- 로그: graceful shutdown
- 의미: 오류가 아니라 종료 신호를 받고 정상 종료

복구:
docker start spring-member-app


## 2. DB 포트 오류

잘못된 설정:
jdbc:mysql://mysql:3307/memberdb

로그:
Connection refused
Communications link failure

의미:
- 호스트는 찾았음
- 해당 포트에서 연결할 수 없음

확인:
- 포트
- 서비스 실행 상태


## 3. DB 호스트 이름 오류

잘못된 설정:
jdbc:mysql://wrong-mysql:3306/memberdb

로그:
UnknownHostException
No address associated with hostname

의미:
Docker DNS가 해당 이름을 찾지 못함

확인:
- 서비스 이름
- hostname
- Docker network


## 4. DB 인증 오류

로그:
Access denied for user

의미:
DB까지 접근했지만 인증 실패

확인:
- 사용자 이름
- 비밀번호
- DB 권한


## 5. MySQL 서비스 중단

상태:
mysql -> Exited
spring-member-app -> healthy

하지만:
/members -> HTTP 500

로그:
UnknownHostException: mysql

MySQL 복구 후:
/members -> HTTP 200

중요:
컨테이너가 healthy라고 해서
서비스의 모든 기능이 정상이라는 뜻은 아니다.


## 로그 확인 핵심

ERROR
↓
Caused by
↓
가장 구체적인 원인 확인

UnknownHostException
-> DNS / hostname

Connection refused
-> 포트 / 서비스 상태

Access denied
-> 계정 / 비밀번호 / 권한


## HTTP 확인

curl -i http://localhost:8080/members

-i:
HTTP 응답 헤더까지 함께 출력

200 -> 정상
500 -> 서버 내부 처리 오류
