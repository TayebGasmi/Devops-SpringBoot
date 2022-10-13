pipeline {
    agent any
   
    stages {
          stage('clean') {
            steps {
                sh 'mvn clean install'
            }
        }
         stage('package') {
            steps {
                sh 'mvn package'
            }
        }
    }
}
