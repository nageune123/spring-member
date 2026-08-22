# Day20 - Docker Hub & Docker 최종 복습

## 1. Docker Registry / Docker Hub

Docker Registry는 Docker 이미지를 저장하고 공유하는 저장소이다.

Docker Hub는 대표적인 Docker Registry 서비스이다.

흐름:

Spring Source
→ Docker Build
→ Docker Image
→ Docker Hub
→ 다른 PC / 서버에서 Pull
→ Container 실행


## 2. Docker Hub Login

```bash
docker login
```

Docker Hub 계정을 인증한다.


## 3. Docker Image Tag

```bash
docker tag spring-member-app:latest ziririsky8/spring-member:latest
```

기존 이미지에 Docker Hub에 올릴 새로운 이름(Tag)을 붙인다.

이미지를 새로 복사하는 것이 아니라 같은 이미지에 다른 이름을 붙인다.


## 4. Docker Push

```bash
docker push ziririsky8/spring-member:latest
```

로컬 Docker 이미지를 Docker Hub에 업로드한다.


## 5. Docker Pull

```bash
docker pull ziririsky8/spring-member:latest
```

Docker Hub에 저장된 이미지를 로컬 PC 또는 서버로 가져온다.

나중에 AWS EC2에서도 같은 방식으로 Docker 이미지를 가져올 수 있다.


## 6. Docker Compose 기본 흐름

```bash
docker compose down
docker compose ps
docker images
docker compose build app
docker compose up -d
docker compose ps
```

- `down` : Compose 컨테이너와 네트워크 제거
- `ps` : Compose 컨테이너 상태 확인
- `images` : 로컬 Docker 이미지 확인
- `build app` : app 서비스 이미지 빌드
- `up -d` : 컨테이너를 생성하고 백그라운드 실행


## 7. API 동작 확인

```bash
curl -i http://localhost:8080/members
```

Spring API에 HTTP 요청을 보내고 응답 헤더와 본문을 확인한다.


## 8. Docker Volume

MySQL 데이터는 컨테이너 자체가 아니라 Docker Volume에 저장한다.

MySQL Container
→ /var/lib/mysql
→ mysql-data Volume

따라서 일반적인:

```bash
docker compose down
```

후 다시 컨테이너를 생성해도 Volume이 유지되면 DB 데이터가 남는다.

주의:

```bash
docker compose down -v
```

`-v`를 사용하면 Volume까지 제거될 수 있다.


## 9. Docker Network / DNS

Spring의 DB 연결:

```text
jdbc:mysql://mysql:3306/memberdb
```

`mysql`은 IP 주소가 아니라 Docker Compose의 서비스 이름이다.

Docker DNS가:

mysql
→ MySQL Container IP

로 찾아준다.


## 10. 환경변수

compose.yaml:

```yaml
MYSQL_PASSWORD: ${DB_PASSWORD}
```

실제 값은 `.env`에 분리한다.

`.env`에는 비밀번호 등이 들어갈 수 있으므로 `.gitignore`에 등록하여 GitHub에 올라가지 않도록 한다.


## 11. Healthcheck / depends_on

MySQL Healthcheck를 사용하여 MySQL이 실제 사용 가능한 상태인지 확인한다.

Spring은:

```yaml
depends_on:
  mysql:
    condition: service_healthy
```

를 통해 MySQL이 healthy 상태가 된 후 시작하도록 구성한다.


## 12. Multi-stage Build 복습

전체 흐름:

Spring Source
→ JDK 환경에서 Gradle Build
→ JAR 생성
→ JRE + JAR
→ Docker Image
→ Container 실행

JDK:
Java 프로그램을 개발하고 빌드하는 데 사용

JRE:
빌드된 Java 프로그램을 실행하는 데 사용

최종 이미지에는 실행에 필요한 JRE + JAR만 포함한다.


## 13. ENTRYPOINT

```dockerfile
ENTRYPOINT ["java", "-jar", "app.jar"]
```

컨테이너가 시작될 때:

```bash
java -jar app.jar
```

를 실행하여 Spring Boot 애플리케이션을 시작한다.


## 14. 장애 확인

컨테이너 상태 확인:

```bash
docker compose ps
```

Spring 최근 로그 확인:

```bash
docker compose logs --tail 50 app
```

실시간 로그:

```bash
docker compose logs -f app
```


## Docker 전체 흐름

Spring Source
→ JDK에서 Build
→ JAR 생성
→ JRE + JAR Docker Image
→ Container 생성/실행
→ Port Mapping
→ Spring Container
→ Docker Network / DNS
→ MySQL Container
→ Docker Volume에 DB 데이터 저장


## 핵심 명령어

```bash
docker images
docker compose build app
docker compose up -d
docker compose down
docker compose ps
docker compose logs app
docker compose logs -f app

docker login
docker tag
docker push
docker pull
```
