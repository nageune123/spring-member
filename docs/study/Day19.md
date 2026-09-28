# Day19 - Docker Multi-stage Build

## 1. Multi-stage Build

Spring 소스코드를 Docker 내부에서 직접 빌드하고
실행에 필요한 결과물만 최종 이미지에 포함한다.

### Build Stage
- eclipse-temurin:21-jdk 사용
- Spring 소스코드 복사
- Gradle로 JAR 생성

```dockerfile
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test
Run Stage
eclipse-temurin:21-jre 사용
builder에서 생성한 JAR만 복사
컨테이너 시작 시 Spring Boot 실행
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/build/libs/spring-member-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

2. JDK와 JRE
JDK: Java 프로그램 개발/빌드에 사용
JRE: 만들어진 Java 프로그램 실행에 사용
최종 이미지에는 빌드 도구가 필요 없으므로 JRE + JAR만 사용

3. Compose 실행 및 확인
docker compose build app
docker compose up -d
docker compose ps
curl -i http://localhost:8080/members
up -d: 컨테이너를 터미널과 분리하여 백그라운드 실행
ps: Compose 컨테이너 상태 확인
curl -i: HTTP 요청 후 응답 헤더와 본문 확인

4. 로그
docker compose logs app
docker compose logs -f app
-f: follow, 새 로그를 계속 실시간으로 확인
5. Build Cache

소스가 변경되지 않은 상태에서 다시 빌드하면 기존 레이어 캐시를 사용한다.

첫 빌드: 약 137초
재빌드: 약 2초

CACHED가 표시된 단계는 다시 실행하지 않고 기존 결과를 사용한다.

6. Compose 종료/재실행
docker compose down
docker compose up -d

docker compose down은 컨테이너와 Compose 네트워크를 제거하지만
기본적으로 named volume은 유지한다.

docker compose down -v는 volume까지 제거하므로 주의한다.
