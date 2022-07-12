# Alexis Chat Server

Сервис запускается на порту 5003

##Системные требования
- Gradle 7.4.2
- OpenJDK 17
- Docker 19.03.0+

## Локальный запуск
```shell script
gradle bootRun
```

## Запуск в с использованием Docker Compose

Собираем исходники
```shell script
gradle installDist
```

Поднимаем контейнеры докера
```shell script
docker-compose up -d
```
