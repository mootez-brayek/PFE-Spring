pipeline {
    agent any

    tools {
        maven 'M2_HOME'
        jdk 'JAVA_HOME'
    }
    environment {
         dockerRegistry = 'bmootez/spring-pfe'
         dockerCredential = 'dockerhub_id'
         dockerImage = ''
         latestDockerImage=''

    }

    stages {
        stage('Checkout GIT') {
            steps {
                echo 'Pulling...'
                git branch: 'master',
                    url: 'git@github.com:mootez-brayek/PFE-Sping.git',
                    credentialsId: '30b84a81-08b9-4be5-85bd-be8ad70e7964'
            }
        }
        stage('cleaning java Project'){
            steps{
                sh 'mvn clean compile'
                }
        }
        stage('build artifact'){
            steps{
                sh 'mvn package'
            }
        }

        stage('testing maven'){
             steps{
                 sh 'mvn -version'
             }
        }
        stage('Building docker images') {
             steps {
                   script {
                   dockerImage = docker.build dockerRegistry + ":1.0"
                   latestDockerImage = docker.build dockerRegistry + ":latest"
                   }
             }
        }
        stage('Docker Login') {
             steps {
                    sh 'docker login -u bmootez -p mootezbrayek98'
             }
        }
        stage('Deploy docker images') {
             steps {
                    script {
                         withDockerRegistry([credentialsId: 'dockerhub_id', url: '']) {
                             dockerImage.push()
                             latestDockerImage.push()
                         }
                    }
             }
        }

        stage('MVN SONARQUBE'){
             steps{
                    sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=1998'
             }
        }

        stage('docker-compose'){
            steps{
                sh 'docker-compose up -d'
            }
        }

        stage('deploy jar to nexus'){
              steps{
                  sh 'mvn deploy:deploy-file -DgroupId=Pi.Spring \
                        -DartifactId=PiProject \
                        -Dversion=1.0 \
                        -Dpackaging=jar \
                        -Dfile=./target/PiProject-1.0-RELEASE.jar \
                        -DrepositoryId=deploymentRepo \
                        -Durl=http://172.10.0.140:8081/repository/maven-releases/'
              }
        }

    }
}
