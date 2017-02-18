pipeline {
    agent { docker 'maven:3.3-alpine' }
    stages {
        stage('build') {
            steps {
                sh 'mvn --version'
            }
        }
    }
}
