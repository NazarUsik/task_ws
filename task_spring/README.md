# Task: Spring

Docker command to build maven project:
```
docker build -t name-docker .
docker run -p 8080:8080 name-docker
```

Create docker-compose file to run jar file and database:
```
docker-compose build
docker-compose up
```