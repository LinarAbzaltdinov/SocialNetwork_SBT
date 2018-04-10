# SocialNetwork_SBT
Examination task of SberTech Java School

## Окружение
Для запуска необходимо иметь:
- [Docker](https://www.docker.com/)
- [Docker-compose](https://docs.docker.com/compose/)

## Запуск всего приложения
- БД: Из корня репозитория в консоли выполнить `docker-compose up -d`
- Запустить все микросервисы приложения

## Запуск отдельного микросервиса
- БД: Из корня репозитория в консоли выполнить (выбрать нужный): 
```
docker build -t db_users ./users/
docker run -d -p 5433:5432 --name=db_users db_users
```
```
docker build -t db_chats ./chat/
docker run -d -p 5434:5432 --name=db_chats db_chats
```
```
docker build -t db_messages ./message/
docker run -d -p 27018:27017 --name=db_messages db_messages
```
- Запустить нужный микросервис
