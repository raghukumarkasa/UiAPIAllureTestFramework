pipeline {
    agent any

    environment {
        YAML_PATH = "src/test/resources/config.yaml"
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Inject credentials into YAML') {
            steps {
                script{
                    withCredentials ([
                        string(credentialsId: "QP_API_TOKEN_PASSWORD", variable: 'API_TOKEN_PASSWORD'),
                        string(credentialsId: "QP_API_TOKEN_USERNAME", variable: 'API_TOKEN_USERNAME')
                    ]) {

                            // Load the YAML config as text
                            def yamlText = readFile("${env.YAML_PATH}")

                            // Replace placeholders with secrets
                            yamlText = yamlText
                                        .replaceAll('\\$\\{QP_API_TOKEN_PASSWORD}',API_TOKEN_PASSWORD)
                                        .replaceAll('\\$\\{QP_API_TOKEN_USERNAME}',API_TOKEN_USERNAME)

                            //Overwrite the YAML file
                            writeFile file: "${env.YAML_PATH}", text: yamlText
                        }
                }
            }
        }

        stage('Run Automation Suite') {
            steps {
                // Example: use Maven for Java test execution (update command if needed)
                sh 'mvn clean test'
            }
        }
    }
}