# containerize a react app
# see https://dzone.com/articles/how-to-dockerize-reactjs-app

# Step 1
FROM node:18-alpine AS build-step

WORKDIR /app
ARG REACT_APP_API_URL=http://localhost:8080
ENV REACT_APP_API_URL=$REACT_APP_API_URL
COPY package.json package-lock.json ./

RUN npm ci
COPY . .

RUN npm run build

# Stage 2
FROM nginx:1.17.1-alpine
COPY --from=build-step /app/build /usr/share/nginx/html

