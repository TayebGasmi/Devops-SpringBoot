pipeline {
    agent any
    environment{
    sonarqube.login= '4f1b8dd395e6e44a81bfbbce3c415c7b0a5bc34e'
    sonar.key = 'devops'
    sonar.url = 'http://192.168.1.14:9000'
    }
    stages {
        stage('clean') {
            steps {
                sh 'mvn clean'
                echo 'cleaning'
            }
        }
        stage('build') {
            steps {
                sh 'mvn package -DskipTests'
                echo 'building'
            }
        }
        stage('test') {
            steps {
                sh 'mvn test'
                echo 'testing'
            }
        }
        stage('sonar') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.host.url=${sonar.url} -Dsonar.login=${sonarqube.login} -Dsonar.projectKey=${sonar.key}'
                echo 'sonar'
            }
        }
    }
}