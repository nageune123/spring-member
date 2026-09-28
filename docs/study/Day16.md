# Docker Day16 - Docker Network / DNS

## 1. Docker Compose Network

docker compose를 실행하면 프로젝트용 네트워크가 생성된다.

확인:
docker network ls
docker network inspect spring-member_default

현재 구조:

spring-member_default
- spring-member-app
- mysql

같은 Docker Network에 있는 컨테이너끼리 통신할 수 있다.


## 2. Docker DNS

Spring 컨테이너에서:

getent hosts mysql

결과:
mysql -> 172.19.0.3

Docker DNS가 서비스 이름 mysql을
MySQL 컨테이너 IP로 변환해준다.

Spring:
172.19.0.2

MySQL:
172.19.0.3


## 3. JDBC 연결

jdbc:mysql://mysql:3306/memberdb

mysql
-> MySQL 컨테이너의 서비스 이름

3306
-> MySQL 포트

memberdb
-> 사용할 DB

실제 흐름:

Spring
-> mysql 이름 조회
-> Docker DNS
-> MySQL IP
-> 3306
-> memberdb


## 4. localhost

localhost는 현재 자기 자신을 의미한다.

Spring 컨테이너 내부:

localhost
-> Spring 컨테이너 자신

mysql
-> MySQL 컨테이너

따라서:

jdbc:mysql://localhost:3306/memberdb
-> Spring 자신의 3306으로 접속
-> MySQL이 없으므로 실패

jdbc:mysql://mysql:3306/memberdb
-> MySQL 컨테이너로 접속
-> 정상


## 5. Port Mapping

compose.yaml:

ports:
  - "8080:8080"

형식:
호스트 포트 : 컨테이너 포트

localhost:8080
-> Docker port mapping
-> Spring 컨테이너:8080

예:

9090:8080

외부 접속:
localhost:9090

Spring은 컨테이너 내부에서 계속 8080 사용.


## 6. DB 연결 성공 로그

HikariPool-1 - Added connection
HikariPool-1 - Start completed

Database JDBC URL:
jdbc:mysql://mysql:3306/memberdb

위 로그가 나오면 Spring -> MySQL 연결 성공.


## 7. 네트워크 장애 구분

UnknownHostException
-> DNS / hostname / 서비스 이름 확인

Connection refused
-> 대상은 찾았지만 포트 연결 실패
-> 포트 번호 / 서비스 실행 상태 확인

Access denied
-> DB 계정 / 비밀번호 / 권한 확인


## 8. localhost 장애 실습

잘못된 설정:

jdbc:mysql://localhost:3306/memberdb

결과:

Communications link failure
Caused by:
java.net.ConnectException: Connection refused

원인:

localhost는 MySQL이 아니라
Spring 컨테이너 자기 자신이기 때문.

복구:

jdbc:mysql://mysql:3306/memberdb

