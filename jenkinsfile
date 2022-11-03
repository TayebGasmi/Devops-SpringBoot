pipeline {
    agent any

    stages {
        stage('compile') {
            steps {
                echo 'complie'
                sh 'mvn compile'
            }
        }

        stage('mvn build') {
            steps {
                echo "build project"
                sh 'mvn -Dmaven.test.skip=true clean package'
            }
        }

 /*       stage('test') {
            steps {
                echo "Tests"
                sh 'mvn test'

            }

        }*/


        stage('install') {
            steps {
                echo 'install'
                sh 'mvn  -DskipTest clean install '

            }

        }

        stage('Dokcer build image') {
            steps {
                script {
                    echo "Dokcer build image"
                    sh 'docker build -t chjasser/tp-achat-validation -f Dockerfile .'
                }
            }
        }
        stage('Dokcer push') {
            steps {
                script {
                    echo "Dokcer push"
                    withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                        sh 'docker login -u chjasser -p ${dockerhubpwd}'
                        sh 'docker tag  chjasser/tp-achat-validation chjasser/tp-achat-validation:1.0'
                        sh 'docker push chjasser/tp-achat-validation:1.0'
                        sh 'docker logout'
                    }

                }
            }
        }

        stage('Dokcer pull') {
            steps {
                script {
                    echo "Dokcer pull"
                    withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                        sh 'docker login -u chjasser -p ${dockerhubpwd}'
                        sh 'docker pull chjasser/tpachatprojecttest:1.0'
                        sh 'docker logout'
                    }

                }
            }
        }

        stage('Dokcer Cleaning up') {
            steps{
                sh "docker rmi chjasser/tpachatprojecttest:1.0"
            }
        }


    }
    post {
        failure {
            emailext to: "cjasser40@gmail.com",
                    subject: "jenkins build:${currentBuild.currentResult}: ${env.JOB_NAME}",
                    body: "${currentBuild.currentResult}: Job ${env.JOB_NAME}\nMore Info can be found here: ${env.BUILD_URL}",
                    attachLog: true,
                    compressLog: true,
                    recipientProviders: [buildUser(), developers(), brokenTestsSuspects(), brokenBuildSuspects()]
        }

    }
}