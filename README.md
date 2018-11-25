# RSO: map microservice

## Prerequisites

```bash
docker run -d --name pg-map -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=mapTable -p 5436:5432 postgres:latest
```
Local run (warning: debugger needs to be attached):
```
java -agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=y -jar api/target/map-api-1.0.0-SNAPSHOT.jar
```

App info:
```
API at 8084/v1/map
GUI at 8084
```

App start:
```
docker build -t rso-teamlj-map:1.0 .
docker run -p 8084:8084 rso-teamlj-map:1.0
to change network host: docker run -p 8084:8084 --net=host rso-teamlj-map:1.0
```
