# Day11 - Spring Boot + Docker + MySQL + Docker Compose

## 1. 오늘의 목표

Spring Boot 프로젝트를 JAR 파일로 빌드하고,
Docker 이미지로 만든 뒤 MySQL 컨테이너와 연결한다.

최종적으로 Docker Compose를 사용하여
Spring Boot + MySQL을 한 번에 실행한다.

---

# 2. 전체 흐름

Spring 코드
↓
Gradle Build
↓
JAR 생성
↓
Dockerfile 작성
↓
Docker Image 생성
↓
Spring Container 실행
↓
MySQL Container 연결
↓
Docker Compose로 통합 실행

---

# 3. Gradle Build

```bash
./gradlew clean build

역할:

Java 코드 컴파일
테스트 실행
Spring Boot 애플리케이션 패키징
실행 가능한 JAR 생성

생성된 파일 확인:

ls -lh build/libs

예:

spring-member-0.0.1-SNAPSHOT.jar
Gradle

Gradle은 빌드 자동화 도구이다.

Build

소스코드를 실제 실행/배포할 수 있는 결과물로 만드는 과정이다.

4. Dockerfile

작성:

nano Dockerfile

내용:

FROM eclipse-temurin:21-jre

COPY build/libs/spring-member-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

의미:

FROM
→ Java 21을 실행할 수 있는 기반 이미지

COPY
→ Spring Boot JAR 파일을 Docker 이미지 안으로 복사

ENTRYPOINT
→ 컨테이너가 시작될 때 Spring Boot 실행

5. Docker Image 생성
docker build -t spring-member .

확인:

docker images

흐름:

Dockerfile
↓
docker build
↓
Docker Image
↓
Container

6. Docker Network
docker network create spring-network

확인:

docker network ls

Docker Network를 사용하면
같은 네트워크에 있는 컨테이너끼리 이름으로 통신할 수 있다.

예:

Spring
↓
mysql:3306
↓
MySQL Container

중요:

Spring 컨테이너에서 localhost는
MySQL 컨테이너가 아니라 Spring 컨테이너 자기 자신이다.

7. Docker Volume

MySQL 데이터 보존을 위해 Volume을 사용한다.

개념:

Container 삭제
↓
Volume 유지
↓
DB 데이터 유지

예:

mysql-data
↓
/var/lib/mysql
8. Spring과 MySQL 연결

Spring에서 사용할 DB 주소:

jdbc:mysql://mysql:3306/memberdb

여기서 mysql은 Docker 내부에서 사용하는
MySQL 컨테이너/서비스 이름이다.

9. Docker Compose

Docker Compose는 여러 컨테이너의 설정과 실행을
하나의 YAML 파일로 관리한다.

이번 구성:

Docker Compose
├── mysql
│   ├── MySQL 8.0
│   ├── 환경변수
│   ├── Volume
│   └── Healthcheck
│
└── app
    ├── Spring Boot
    ├── Port 8080
    ├── DB 환경변수
    └── MySQL 의존성

실행:

docker compose up --build

종료:

docker compose down

백그라운드 실행:

docker compose up -d --build
10. Healthcheck

처음에는 Spring 컨테이너가 MySQL보다 너무 빨리 시작되어

Communications link failure
Connection refused

오류가 발생했다.

단순 depends_on은 컨테이너 시작 순서만 지정한다.

그래서 MySQL healthcheck를 추가했다.

healthcheck:
  test: ["CMD", "mysqladmin", "ping", "-h", "localhost", "-p${MYSQL_ROOT_PASSWORD}"]
  interval: 5s
  timeout: 5s
  retries: 10

Spring:

depends_on:
  mysql:
    condition: service_healthy

최종 실행 순서:

MySQL Container 시작
↓
Healthcheck
↓
MySQL Healthy
↓
Spring Container 시작
↓
DB 연결
↓
Spring Boot 실행

성공 로그:

Container mysql Healthy
HikariPool-1 - Start completed.
Tomcat started on port 8080
Started SpringMemberApplication
11. 오늘 중요하게 이해한 것

Docker Image
= 컨테이너를 만들기 위한 실행 설계도/패키지

Container
= Image를 실제 실행한 상태

Dockerfile
= Image를 어떻게 만들지 정의

Volume
= Container가 삭제되어도 데이터를 보존

Network
= Container끼리 통신

Docker Compose
= 여러 Container를 하나의 설정으로 관리

Healthcheck
= 서비스가 실제 사용할 준비가 되었는지 확인

12. 아직 헷갈리는 부분
Dockerfile을 처음부터 직접 작성하는 것
compose.yaml의 YAML 구조와 들여쓰기
environment 설정
Docker Network 설정
Volume 설정
depends_on / healthcheck 설정


```
