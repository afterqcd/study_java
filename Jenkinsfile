pipeline {
    agent { label 'docker' }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -v'
                sh 'cd test && \
                    sed -i "s^#JENKINS_JOB#^${JOB_NAME}^g" Dockerfile && \
                    sed -i "s^#JENKINS_BUILD_NUMBER#^${BUILD_NUMBER}^g" Dockerfile && \
                    docker build -t ${DOCKER_IMAGE_REGISTRY}/test:B${BUILD_NUMBER} .'
                sh 'docker push ${DOCKER_IMAGE_REGISTRY}/test:1${BUILD_NUMBER}'
            }
        }
    }
}
