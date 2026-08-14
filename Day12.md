# Day 12 - Docker Compose 실습

## 1. Docker Compose

Docker Compose를 이용해 Spring Boot와 MySQL을 함께 실행했다.

- app : Spring Boot
- mysql : MySQL
- compose.yaml에서 컨테이너 실행 방법을 설정
- `docker compose up -d`로 실행

## 2. 주요 명령어

```bash
docker compose up -d
docker compose up -d --build
docker compose ps
docker compose ps -a
docker compose logs app
docker compose stop app
docker compose start app
docker compose exec app sh
docker compose exec mysql mysql -u springuser -p
```

3. Docker Network

Compose를 실행하면 기본 네트워크가 생성된다.

docker network ls
docker network inspect spring-member_default

Spring 컨테이너와 MySQL 컨테이너가 같은 네트워크에 연결되어 있다.

따라서 Spring에서는 MySQL 컨테이너의 IP를 직접 사용하는 대신 서비스 이름을 사용할 수 있다.

mysql:3306

확인:

docker compose exec app sh
getent hosts mysql 4. Docker Volume

MySQL 데이터를 Volume에 저장하여 컨테이너가 재생성되어도 데이터를 유지하도록 했다.

확인 결과 기존 데이터가 유지되었다.

id: 1
name: volume-user 5. 컨테이너 내부 확인

Spring 컨테이너 내부에 직접 접속했다.

docker compose exec app sh
pwd
ls

컨테이너 내부에서 app.jar를 확인했다.

6. 코드 수정 후 Docker 반영 과정

Spring Controller에 /docker-test API를 추가했다.

@GetMapping("/docker-test")
public String dockerTest() {
return "Docker Build Test!";
}

새 JAR 생성:

./gradlew clean build -x test

Docker 이미지 재빌드 및 컨테이너 실행:

docker compose up -d --build

Postman 확인:

GET http://localhost:8080/docker-test

200 OK
Docker Build Test! 7. 오늘의 핵심 흐름
Java 코드 수정
↓
Gradle Build
↓
JAR 생성
↓
Docker Build
↓
Docker Image 생성
↓
Container 실행
↓
HTTP 요청
핵심 정리
Gradle Build = JAR 생성
Docker Build = Docker Image 생성
Image = 컨테이너를 만들기 위한 실행 환경/설계 결과물
Container = Image를 기반으로 실행되는 실제 프로세스
Compose = 여러 컨테이너의 실행 방법을 YAML로 관리
Docker Network = 컨테이너끼리 통신
Docker Volume = 컨테이너가 삭제/재생성되어도 데이터 유지

저장:

```text
Ctrl + O
Enter
Ctrl + X
```
