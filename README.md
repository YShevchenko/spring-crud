# Demo spring project 

### Technologies used: 
* Spring data
* Spring boot
* Spring mvc
* Thymeleaf
* Gradle
* Docker

### Run the project from local:
use ```./gradlew bootRun```

### Generate "fat" jar
use ```./gradlew clean build``` and find jar file in build/libs

#### Generage docker image 
execute ```docker build --build-arg JAR_FILE=./build/libs/{jar_name} -t {image_name} .``` from project root folder (the one with Dockerfile)
for example ```docker build --build-arg JAR_FILE=./build/libs/feedback-0.0.1-SNAPSHOT.jar -t yshevchenko/feedback .```

#### Pull docker existing dockkr image from public docker hub repo 
execute ```docker pull yshevchenko/feedback```
#### Run docker image in container
execute ```docker run -p {your_port}:8097 -d {image_name} ```
for example ```docker run -p 8097:8097 -d yshevchenko/feedback ```


#### By default application is available on 8097 port

#### Aplication is hosted at http://feedbackportal.ga on port 80
