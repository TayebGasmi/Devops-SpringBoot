pipeline {
    agent any
    environment{
    SONAR_LOGIN= '976e886814457ad6082e408f8cb796908686eafe'
    SONAR_KEY = 'devops'
    SONAR_URL = 'http://192.168.230.188:9000'
    DOCKER_LOGIN = 'tayeb99'
    DOCKER_PASSWORD = 'Tayeb@1999'
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
                sh 'mvn sonar:sonar -Dsonar.host.url=$SONAR_URL -Dsonar.login=$SONAR_LOGIN -Dsonar.projectKey=$SONAR_KEY'
                echo 'sonar'
            }
        }
         stage('Publish to Nexus') {
                     steps {
                         script {
                                sh 'mvn deploy -e -DskipTests'
                                echo 'Publish to Nexus'

                     }
                 }
                 }
         stage('build docker image') {
            steps {
                script {
                    sh 'docker build -t tayeb99/devops .'
                    echo 'build docker image'
                    }
                    }
             }
           stage('Docker login') {
                                 steps {
                                     script {

                                         sh 'docker login -u $DOCKER_LOGIN -p $DOCKER_PASSWORD'
                                 }
                                 }
                           stage('Pushing Docker Image') {
                                 steps {
                                     script {

                                      sh 'docker push tayeb99/devops'
                                     }
                                 }
                           }
                           stage('Run Spring && MySQL Containers') {
                                 steps {
                                     script {
                                       sh 'docker-compose up -d'
                                     }
                                 }
                             }
}
}