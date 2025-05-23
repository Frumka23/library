## Перед запуском
1. Создайте базу данных 
2. В файле конфигурации "application.properties" замените на необходимые данные параметры подключения БД:
-spring.datasource.url={url базы данных} 
-spring.datasource.username={имя пользователя от базы данных}
-spring.datasource.password={пароль от базы данных}

## Запуск

Запуск приложения через cmd 

----
    java -jar target/library-0.0.1-SNAPSHOT.jar
----

После запуска, приложение будет доступно по адресу: http://localhost:8080 


