pipeline {
    agent any
    tools { 
        maven 'maven 3.1.1' 
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
