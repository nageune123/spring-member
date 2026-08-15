# Docker Day 13 - Docker 관리 및 모니터링

## 1. Docker 리소스 확인

### 컨테이너

docker ps
docker ps -a

### 이미지

docker images

### 네트워크

docker network ls

### 볼륨

docker volume ls

### Docker 디스크 사용량

docker system df

## 2. Docker 리소스 정리

docker container prune

- 중지된 컨테이너 정리

docker image prune

- dangling 이미지 정리

docker network prune

- 사용하지 않는 네트워크 정리

docker volume prune

- 사용하지 않는 볼륨 정리
- DB 데이터가 저장된 볼륨은 특히 주의

docker system prune

- 중지 컨테이너, 미사용 네트워크, dangling 이미지,
  사용하지 않는 build cache 등을 종합 정리

## 3. Volume 관리

docker volume inspect <볼륨명>

- 볼륨 상세정보 확인

docker volume rm <볼륨명>

- 특정 볼륨 삭제

docker ps -a --filter volume=<볼륨명>

- 해당 볼륨을 사용하는 컨테이너 확인

중요:
컨테이너가 볼륨을 사용 중이면 볼륨을 삭제할 수 없다.

## 4. 로그 확인

docker logs <컨테이너>

docker logs --tail 20 <컨테이너>

- 마지막 20줄

docker logs -f <컨테이너>

- 실시간 로그 확인

docker logs --since 10m <컨테이너>

- 최근 10분 로그 확인

## 5. 컨테이너 모니터링

docker stats

- CPU, 메모리, Network I/O, Block I/O 등 확인

docker stats <컨테이너>

- 특정 컨테이너만 확인

docker top <컨테이너>

- 컨테이너 내부에서 실행 중인 프로세스 확인

## 6. inspect

docker inspect <컨테이너>

- 상태
- 포트
- 네트워크
- IP
- 환경변수
- 볼륨
- 실행 명령 등 상세정보 확인

docker inspect -f '{{.State.Status}}' <컨테이너>

- 원하는 정보만 추출

## 7. Docker 장애 대응 순서

1. docker compose ps -a
   - 컨테이너 상태 확인

2. docker logs --tail 50 <컨테이너>
   - 에러 로그 확인

3. docker stats
   - CPU / 메모리 확인

4. docker inspect <컨테이너>
   - 포트 / 환경변수 / 네트워크 / 볼륨 확인

5. docker compose restart <서비스>
   - 문제 해결 후 재시작

암기:
상태 → 로그 → 자원 → 설정 → 재시작
