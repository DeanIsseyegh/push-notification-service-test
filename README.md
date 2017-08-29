# push-notification-service-test
Coding challenge to build a simple push notification service

# How to run
Use `./gradlew bootRun`
Alternatively, just run it using the Application.kt `main` method

# Notes
Made using `Kotlin` and `Spring Boot`

Done using TDD (except ExceptionHandler and Domain objects)

First time using `Kotlin` and `Spring Boot` together so might be some obvious things I've missed with them 

# Given more time...
- I would have written the ExceptionHandler using TDD and improved the Exception handling, plus handled more cases

- Written end-to-end integration tests

- Improved response messaging of the push response (its a bit bare bones at the moment)

- Tested the @Lock to make sure it works with concurrent writes properly (I've just taken the word of the JPA documentation that it will work!)

- Spent some more time reading about the create-push request from PushBullet and extending it from my side to allow things like files, url param for link and target parametrs.


