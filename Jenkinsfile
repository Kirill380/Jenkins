pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }
        stage('Deploy') {
           steps {
              sh 'mv build/libs/Jenkins-1.0-SNAPSHOT.jar simple.jar'
              sshPublisher(
                 continueOnError: false,
                 failOnError: true,
                 publishers: [
                    sshPublisherDesc(
                       configName: "simple-spring-boot",
                       verbose: true,
                       transfers: [
                            sshTransfer(sourceFiles: 'simple.jar'),
                            sshTransfer(execCommand: "kill \$(ps aux | grep java | grep -v grep | awk \'{print \$2}\')"),
                            sshTransfer(execCommand: "java -jar simple.jar &")
                       ]
                    )
                 ]
              )
           }
         }
    }
}