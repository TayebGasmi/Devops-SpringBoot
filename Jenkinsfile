pipeline {
    agent any
    environment{
    SONAR_LOGIN= '4f1b8dd395e6e44a81bfbbce3c415c7b0a5bc34e'
    SONAR_KEY = 'devops'
    SONAR_URL = 'http://192.168.1.14:9000'
    }
    stages {
       /* stage('clean') {
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
                sh 'mvn sonar:sonar -Dsonar.host.url=$SONAR_URL -Dsonar.login=$SONAR_LOGIN -Dsonar.projectKey=$SONAR_KEY'
                echo 'sonar'
            }
        }*/
         stage('Build Docker Image') {
              steps {
                sh 'docker build -t tayeb99/devops-spring .'
              }
         }
    }
}