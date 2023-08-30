def call(Map pipelineParams)

pipeline {
    agent any
    parameters {
        string(name: 'KeyName', defaultValue: 'Putty-Test')
        choice(name: 'awsRegion', defaultValue: 'us-east-2')
    }

    environment {
        AWS_DEFAULT_REGION = "${params.awsRegion}"
        KeyName = "${params.KeyName}"
         }
    
    stages {
        stage('Git Checkout') {
            steps {
              sh "ls -lat"  
              git branch: 'master', url: 'https://github.com/vamshikank9032/Sharedjencob.git'  
            }
        }
        stage('Deploy CF Template') {
                steps {
                    script {
                       //def amiId = 'ami-0ccabb5f82d4c9af5'
                         sh 'echo "Running cfn template Validation"'
                   sh """
                   ls
                   pwd
                   cd cloudformation/
                   ls
                   aws cloudformation deploy --region ${AWS_DEFAULT_REGION} --template-file EC2.yaml --stack-name sample-EC2-CF1 
                    """
                }
                }
                }
                           
    }
    post {
        success {
            echo "CloudFormation stack is deployed successfully"
        }
        failure {
            echo "CloudFormation stack failed"
        }
    }
}
