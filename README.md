# RSO: map microservice

## Prerequisites

```bash
docker run -d --name pg-map -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=mapTable -p 5432:5432 postgres:latest
```
Local run (warning: debugger needs to be attached):
```
java -agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=y -jar api/target/map-api-1.0.0-SNAPSHOT.jar
```

```
docker build -t map:1.0 .
docker run -p 8081:8081 map:1.0
to change network host: docker run -p 8081:8081 --net=host map:1.0
```
