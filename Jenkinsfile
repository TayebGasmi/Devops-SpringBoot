pipeline {
    agent any
    tools { 
        maven 'Maven 3.1.1' 
        jdk 'jdk8' 
    }
    stages {
          stage('install') {
            steps {
                sh 'mvn install'
            }
        }
    }
}
