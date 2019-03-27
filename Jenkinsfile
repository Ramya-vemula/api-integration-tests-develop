#!/usr/bin/env groovy
import static groovy.json.JsonOutput.*

appName = 'API Integration Tests'

properties([
        parameters([
                choice(name: 'environment', choices: 'dev\nstaging\nprod', description: 'Select a environment')
        ]),
        disableConcurrentBuilds()
])

def gitUrl = "git@github.com:tissl/api-integration-tests.git"

ansiColor('xterm') {
    node("Mac") {
        stage('Checkout') {
            checkout scm

            if (environment != null) {
            echo "Selected environment: ${environment}"
            }

            else {
               echo "NOT Selected environment to test"
               throw "NOT Selected environment to test"
               }
        }

        stage('Tests') {
            try {
                sh "gradle clean test"
                notifySlack(":man_dancing: Build succeeded", BRANCH_NAME, true)
             } catch (error) {
                  generateReport()
                notifySlack(":octagonal_sign: Build failed", BRANCH_NAME, false)
                  throw error
             }
        }

        stage('Report') {
             generateReport()
             }
    }
}

def notifySlack(message, branch, success) {
    def color = success ? "good" : "danger"
    slackSend channel: "jenkins", color: color, message: "${message}: ${appName} (*${branch}*)"
}

def generateReport() {
    script {
        allure([
                includeProperties: false,
                jdk              : '',
                properties       : [],
                reportBuildPolicy: 'ALWAYS',
                results          : [[path: 'build/allure-results']]
        ])
    }
}