# Docker Day17 - Volume / 데이터 영속성

## 1. Volume

Volume은 컨테이너와 분리된 데이터 저장공간이다.

컨테이너 생성 → 실행 → 삭제
Volume 생성 → 데이터 저장 → 유지 → 삭제

컨테이너와 Volume은 생명주기가 분리되어 있다.


## 2. MySQL Volume

compose.yaml 예:

volumes:
  - mysql-data:/var/lib/mysql

형식:

볼륨 이름 : 컨테이너 내부 경로

mysql-data
↕ mount
/var/lib/mysql

MySQL이 /var/lib/mysql에 저장한 데이터가
Volume에 보존된다.


## 3. Volume 확인

docker volume ls

docker volume inspect spring-member_mysql-data

주요 항목:

Name
Mountpoint
Driver


## 4. 컨테이너 삭제 후 데이터 유지 실험

기존 회원 데이터 확인:

curl http://localhost:8080/members

MySQL 중지 및 컨테이너 삭제:

docker compose stop mysql
docker compose rm -f mysql

Volume 확인:

docker volume ls

결과:
MySQL 컨테이너는 삭제됐지만
spring-member_mysql-data는 유지됨.

새 MySQL 컨테이너 생성:

docker compose up -d mysql

다시 회원 데이터 확인:

curl http://localhost:8080/members

기존 데이터가 그대로 유지됨.


## 5. Mount 확인

docker inspect mysql | grep -A 12 '"Mounts"'

주요 항목:

Type: volume
Name: spring-member_mysql-data

Source:
Docker가 관리하는 저장 위치

Destination:
/var/lib/mysql
컨테이너 내부에서 사용하는 위치

Mode: rw
읽기 + 쓰기 가능


## 6. Volume vs Bind Mount

Named Volume:

mysql-data:/var/lib/mysql

- Docker가 저장 위치 관리
- DB 데이터 같은 영속 데이터에 사용

Bind Mount:

./bind-test:/data

- 호스트의 실제 폴더를 컨테이너에 연결
- 소스코드, 설정파일 공유 등에 사용


## 7. Bind Mount 실습

호스트 파일 생성:

mkdir bind-test
echo "hello from host" > bind-test/hello.txt

컨테이너에서 읽기:

docker run --rm \
-v "$(pwd)/bind-test:/data" \
alpine cat /data/hello.txt

컨테이너에서 수정:

docker run --rm \
-v "$(pwd)/bind-test:/data" \
alpine sh -c \
'echo "hello from container" > /data/hello.txt'

호스트에서 확인:

cat bind-test/hello.txt

결과:
hello from container


## 8. 테스트 Volume 생명주기

생성:

docker volume create day17-test

데이터 저장:

docker run --rm \
-v day17-test:/data \
alpine sh -c \
'echo "volume data survives" > /data/test.txt'

새 컨테이너에서 확인:

docker run --rm \
-v day17-test:/data \
alpine cat /data/test.txt

결과:

volume data survives

첫 번째 컨테이너는 삭제됐지만
Volume 데이터는 유지되었다.

Volume 삭제:

docker volume rm day17-test

Volume 자체를 삭제하면
그 Volume에 저장된 데이터도 삭제된다.


## 핵심 정리

컨테이너 삭제 != Volume 삭제

Volume
→ Docker 관리 저장공간

Bind Mount
→ 호스트 폴더 직접 연결

DB 데이터
→ 보통 Volume 사용

컨테이너를 다시 만들어도
같은 Volume을 연결하면 데이터를 다시 사용할 수 있다.
