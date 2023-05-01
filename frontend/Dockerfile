# node.js로 빌드
FROM node:18 as build-stage

# 경로 설정
WORKDIR /app

# ADD <복사할 파일 경로> <이미지에서 파일이 위치할 경로>
ADD . .

# 의존성 설치
RUN npm install

# 빌드 -> dist 폴더 생성됨
RUN npm run build

# nginx로 실행
FROM nginx:stable-alpine as production-stage

# 컨테이너가 리스닝할 포트
EXPOSE 3000

# nginx.conf를 default.conf로 복사
COPY  ./nginx.conf /etc/nginx/conf.d/default.conf 

# /app/dist를 /usr/share/nginx/html로 복사
COPY --from=build-stage /app/dist /usr/share/nginx/html

# 컨테이너를 실행할 때 실행할 커맨드
CMD ["nginx", "-g", "daemon off;"]
