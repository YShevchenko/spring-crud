node() {

    properties([[$class       : 'GithubProjectProperty', displayName: '',
                 projectUrlStr: 'https://github.com/YShevchenko/spring-crud/'],
                disableConcurrentBuilds(),
                pipelineTriggers([githubPush()])])

    def dockerImageToCreate;
    def dockerHubRepository = "yshevchenko/feedback";

    stage('Pull latest code') {
        git 'https://github.com/YShevchenko/spring-crud.git'
    }

    stage('build') {
        sh "chmod +x ./gradlew"
        sh "./gradlew clean build"
    }

    stage('publish unit test result') {
        junit 'build/test-results/test/*.xml'
    }

    withDockerRegistry([credentialsId: 'docker_hub']) {
        stage('build docker image') {
            dockerImageToCreate = docker.build(dockerHubRepository, "--build-arg JAR_FILE=./build/libs/*.jar -t ${dockerHubRepository} .")
        }

        stage('push docker image') {
            dockerImageToCreate.push()
        }
    }

    def hubExistingImage = docker.image(dockerHubRepository);

    stage('clean up docker images') {
        sh '''docker stop $(docker ps -a -q) || true'''
        sh '''docker rm $(docker ps -a -q) || true'''
        sh ''' docker rmi $(docker images -q) || true'''
    }

    withDockerRegistry([credentialsId: 'docker_hub']) {
        stage('pull docker image') {
            hubExistingImage.pull()
        }
    }

    stage('Run application') {
        hubExistingImage.run("-p 80:8097 -d")
    }
}