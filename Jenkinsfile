pipeline {
    agent { label 'docker' }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -v'
                sh 'cd test && docker build -t ${DOCKER_IMAGE_REGISTRY}/test:1-B${BUILD_NUMBER} .'
                sh 'docker push ${DOCKER_IMAGE_REGISTRY}/test:1-B${BUILD_NUMBER}'
            }
        }
    }
}
