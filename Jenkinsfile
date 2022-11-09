pipeline {
    agent any

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

    }
}