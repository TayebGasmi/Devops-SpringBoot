pipeline {
    agent any
    environment{
    SONAR_LOGIN= '976e886814457ad6082e408f8cb796908686eafe'
    SONAR_KEY = 'devops'
    SONAR_URL = 'http://172.10.0.140:9000'
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
        stage('build') {
                    steps {
                        sh 'mvn package -DskipTests'
                        echo 'building'
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
                    sh 'docker login -u $DOCKER_LOGIN -p $DOCKER_PASSWORD'
                    echo 'Docker login'

                     }
                    }
           stage('Pushing Docker Image') {
                steps {
                      sh 'docker push tayeb99/devops'
                       echo 'Pushing Docker Image'
                       }
                     }
           stage('Run Spring && MySQL Containers') {
                 steps {

                   sh 'docker-compose up -d'

                   echo 'Run Spring && MySQL Containers'
                        }
                    }

}
post {
        success {
	        mail to: "tayeb.gasmi@esprit.tn",
            subject: "Pipeline Backend Success ",
            body: "Welcome to DevOps project Backend : Success on job ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}, Build URL: ${env.BUILD_URL}"
        }
	    failure {
            mail to: "tayeb.gasmi@esprit.tn",
            subject: "Pipeline backend Failure",
            body: "Welcome to DevOps project Backend : Failure on job ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}, Build URL: ${env.BUILD_URL} "
            }
    }
}