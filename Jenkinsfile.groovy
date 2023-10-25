//file:noinspection HardCodedStringLiteral

def repoUrl = "https://github.com/TriNext/framework-json";
pipeline {
    agent any
    options {
        // This is required if you want to clean before build
        skipDefaultCheckout(true)
    }
    stages {
        stage('Prepare') {
            steps {
                cleanWorkspace()
            }
        }
        stage('Test') {
            steps {
                echo 'Testing'
                runTestsAndPublishResults()
                jacocoSetup()
            }
        }
        stage('Checks') {
            steps {
                runGradleChecks()
            }
        }
        stage('Deploy to Prod') {
            when {
                anyOf {
                    branch 'main';
                }
            }
            steps {
                deploy('main')
            }
        }
        stage('Deploy to Staging') {
            when {
                branch 'staging'
            }
            steps {
                deploy('staging')
            }
        }
        stage('Javadoc') {
            steps {
                generateAndPublishJavadoc()
            }
        }
    }
    post {
        success {
            setBuildStatus(repoUrl, "kannst mergen", "SUCCESS")
        }
        failure {
            setBuildStatus(repoUrl, "Sach ma hackst? Das willst du mergen? Nicht mit mir!", "FAILURE")
        }
    }
}