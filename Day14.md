# Docker Day 14 - 컨테이너 운영 설정

## 1. Restart Policy

현재 설정 확인:

docker inspect -f '{{.HostConfig.RestartPolicy.Name}}' spring-member-app

Compose 설정:

restart: unless-stopped

주요 정책:
- no: 자동 재시작 안 함
- on-failure: 오류 종료 시 재시작
- always: 종료되면 계속 재시작
- unless-stopped: 종료되면 재시작하지만 사용자가 직접 stop한 경우 유지


## 2. Healthcheck

Healthcheck는 컨테이너 내부의 서비스가 실제로 정상 동작하는지 검사한다.

app 설정:

healthcheck:
  test: ["CMD", "wget", "-q", "--spider", "http://localhost:8080/docker-test"]
  interval: 10s
  timeout: 5s
  retries: 5

상태:
- health: starting → 검사 중
- healthy → 검사 통과
- unhealthy → 검사 실패

중요:
running과 healthy는 다르다.

running = 컨테이너가 실행 중
healthy = Healthcheck까지 정상 통과


## 3. wget

wget은 HTTP 요청이나 파일 다운로드 등에 사용할 수 있는 명령어다.

이번 실습에서는:

wget -q --spider http://localhost:8080/docker-test

를 이용하여 Spring 서버가 정상 응답하는지 검사했다.


## 4. 메모리 제한

현재 제한 확인:

docker inspect -f '{{.HostConfig.Memory}}' spring-member-app

Compose 설정:

mem_limit: 512m

Spring 컨테이너가 사용할 수 있는 메모리를 최대 512MB로 제한한다.

확인:

docker stats spring-member-app


## 5. CPU 제한

현재 제한 확인:

docker inspect -f '{{.HostConfig.NanoCpus}}' spring-member-app

Compose 설정:

cpus: 1.0

CPU 1개 분량까지 사용할 수 있도록 제한한다.

1 CPU = 1,000,000,000 NanoCPUs


## 6. Healthcheck 장애 실습

Healthcheck 주소를 일부러 잘못 설정:

/docker-test
→ /not-found

결과:

health: starting
→ unhealthy

Healthcheck 상세 확인:

docker inspect -f '{{json .State.Health}}' spring-member-app

확인할 항목:
- Status
- FailingStreak
- ExitCode
- Log

주소를 다시 /docker-test로 수정하고:

docker compose config
docker compose up -d

결과:

health: starting
→ healthy


## 7. 핵심 운영 설정

restart
→ 컨테이너 종료 시 재시작 정책

healthcheck
→ 서비스가 실제 정상인지 검사

mem_limit
→ 컨테이너 메모리 제한

cpus
→ 컨테이너 CPU 제한


## 8. 장애 대응 순서

상태 → 로그 → 자원 → 설정 → 재시작

1. docker compose ps -a
2. docker logs --tail 50 <컨테이너>
3. docker stats
4. docker inspect <컨테이너>
5. 원인 수정 후 재시작