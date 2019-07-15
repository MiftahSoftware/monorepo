pipeline {
    agent any
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
                echo "hello azmat"
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
                echo hello lagom
            }
        }
    }
}