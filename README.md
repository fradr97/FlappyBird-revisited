# FlappyBird revisited

Reinterpretation of the famous game FlappyBird, written in java.
There are 3 types of games:
 - FlappyClassic
 - FlappyRun
 - FlappyShoot

Maven Wrapper was used for the build of the program.
To run the game you need to:
  - Create a database in MySQL with the name flappybird and import the dump present in the /dump folder
  - Change the db access credentials if necessary in the file /src/main/java/com/flappybird/connectionDb.java
  - Run the following commands:
    - cd flappybird
    - mvnw clean package
    - cd target
    - java -jar flappybird-1.0.jar