pipeline {
    agent any

    stages {
       stage('Clean') {
            steps {
                sh 'mvn clean' 
               
            }
        }
        stage('package') {
            steps {
                sh 'mvn package'
            }
        }
         stage('install') {
            steps {
                sh 'mvn install'
            }
        }
    }
}
