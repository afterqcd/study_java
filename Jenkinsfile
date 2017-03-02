pipeline {
    agent { label 'docker' }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -v'
                sh 'docker version'
            }
        }
    }
}
