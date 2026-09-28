Day18 — Docker Image / Build 심화

1. docker image ls
   → 로컬 이미지 확인

2. docker history
   → 이미지가 여러 Layer로 구성됨을 확인

3. Build Cache
   → 변경 없으면 CACHED
   → 57초대 빌드가 2초대로 단축되는 것도 확인

4. Cache 무효화
   → Spring 코드 수정
   → JAR 변경
   → COPY Layer는 다시 실행
   → 기존 Base Image는 캐시 재사용

5. .dockerignore
   → Build Context에서 필요 없는 파일 제외
   → 제외한 파일을 COPY하면 not found 발생하는 것까지 실험

6. WORKDIR /app
   → 컨테이너 내부 작업 디렉터리 지정
   → app.jar 위치가 /app/app.jar

7. Git
   → 변경사항 staging 완료
