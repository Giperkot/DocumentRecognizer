
# DocumentRecognizer



## Запуск приложения

Для запуска потребуетя
1. **Java 8** или выше
2. maven 3.3.9 или выше. Либо встроенный в Intellij Idea
3. БД **PostgreSQL** 10 или выше.
4. SenchaCMD 7.

Шаги для запуска.

1. Собрать фронтенд.
    >cd frontend/DocumentRecognizer \
    >sencha app build
    

1. Выполнить команду   
    >**mvn clean install**

2. Создать БД  
    >CREATE DATABASE document_recognizer  
        WITH  
        OWNER = document_recognizer  
        ENCODING = 'UTF8'  
        CONNECTION LIMIT = -1;  
        
Создать пользователя 
login: document_recognizer
password: document_recognizer

Создать БД document_recognizer

        
3. Сконфигурировать подключение к БД в **application.properties**  
        
4. Запустить  
    >java -jar target/DocumentRecognizer-1.0-SNAPSHOT.jar

#### Приложение можно открыть по ссылке:
http://localhost:8080/DocumentRecognizer/
