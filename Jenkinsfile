pipeline {
    agent {
        node {
            label 'master'
            customWorkspace 'monorepo-experiments-master'
        }
    }
    stages {
        stage('init') {
            steps {
                script {
                    def scmVars = checkout scm
                    env.MY_GIT_PREVIOUS_SUCCESSFUL_COMMIT = scmVars.GIT_PREVIOUS_SUCCESSFUL_COMMIT
                }
            }
        }

        stage('akka-http') {
            when {
                expression {
                    matches = sh(returnStatus: true, script: "git diff --name-only $MY_GIT_PREVIOUS_SUCCESSFUL_COMMIT|egrep -q '^akka-http'")
                    return !matches
                }
            }
            steps {
                sh '''
                     cd akka-http
                     ./akka.sh
                '''
            }
        }
        stage('lagom-service') {
            when {
                expression {
                    matches = sh(returnStatus: true, script: "git diff --name-only $MY_GIT_PREVIOUS_SUCCESSFUL_COMMIT|egrep -q '^lagom-service'")
                    return !matches
                }
            }
            steps {
                sh '''
                     cd lagom-service
                     ./lagom.sh
                '''
            }
        }
    }
}