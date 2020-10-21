# Alexis Chat Server

Сервис запускается на порту 5003

##Системные требования
- Gradle 6.5
- OpenJDK 11
- Docker 19.03.0+

## Локальный запуск
```shell script
gradle run
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
