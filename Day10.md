# Day10 - Docker 기초

## 1. Docker 기본 개념

- Image = 컨테이너를 만들기 위한 설계도
- Container = Image를 이용해 만든 실제 실행 환경

Image
↓ docker run
Container

## 2. 기본 명령어

docker --version
docker images
docker ps
docker ps -a

- docker images : 이미지 목록
- docker ps : 실행 중인 컨테이너
- docker ps -a : 모든 컨테이너

## 3. Container 실행

docker run hello-world

docker run -it ubuntu bash

- run : 새로운 컨테이너 생성 + 실행
- -it : 터미널에서 직접 조작

## 4. Container 시작 / 중지 / 삭제

docker stop 컨테이너이름
docker start 컨테이너이름
docker rm 컨테이너이름

- stop : 컨테이너 중지
- start : 기존 컨테이너 다시 실행
- rm : 컨테이너 삭제

## 5. Nginx와 Port Mapping

docker run -d -p 8081:80 --name my-nginx nginx

-p 호스트포트:컨테이너포트

8081 = Host 포트
80 = Container 포트

localhost:8081
↓
Host 8081
↓
Container 80
↓
Nginx

## 6. Volume

docker volume create my-volume
docker volume ls

컨테이너에 Volume 연결:

docker run -it --name volume-test \
-v my-volume:/data ubuntu bash

- Container가 삭제되어도 Volume의 데이터는 유지할 수 있다.
- DB 데이터를 보존할 때 중요하다.

## 7. docker exec

docker exec -it my-nginx bash

- run = 새로운 Container 생성 + 실행
- exec = 이미 실행 중인 Container에서 명령 실행

## 핵심 정리

Image → Container를 만드는 설계도
Container → 실제 실행 환경
Port → 외부와 Container 연결
Volume → 데이터를 Container 밖에 보존
exec → 실행 중인 Container 내부에서 명령 실행
