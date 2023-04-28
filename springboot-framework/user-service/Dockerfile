# user-service 컨테이너 생성
docker build -t user-service:latest ./springboot-framework/user-service

# 이미 실행 중인 user-service 컨테이너가 있다면 중단하기
if (docker ps | grep user-service) then docker stop user-service; fi

# user-service 컨테이너 실행
docker run -d --rm --name user-service -p 8081:8080 user-service

# 중단한 뒤 남아있는 이미지들 삭제
docker image prune -f
