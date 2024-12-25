pipeline {

    agent any

    environment {
        NEW_VERSION = '1.0.0'
    }

    //tools { // tools to use, only supported: MAVEN, GRADLE and JDK
        // gradle 'Gradle'
    //}

    parameters {
        string(name: 'APP', defaultValue: 'AuthHub', description: 'Application name')
        choice(name: 'SERVER', choices: ['fedora', 'centos', 'ubuntu'], description: 'Server to deploy the application')
        booleanParam(name: 'TEST', defaultValue: true, description: 'Indicates if the test must be exec')
    }

    stages {

        stage('build') {

            steps {
                echo "building... version ${NEW_VERSION}"
            }
        }

        stage('test') {
            when {
                expression {
                    params.TEST
                }
            }
            steps {
                echo 'testing...'
            }
        }

        stage('deploy') {

            steps {
                echo "deploying on server: ${params.SERVER}"
            }
        }
    }

    post {
    // exec after all stages
        always {
            echo 'it\'s all'
        }
        success {
            echo 'the file is perfect state'
        }
        failure {
            echo 'idk w happen'
        }
    }
}