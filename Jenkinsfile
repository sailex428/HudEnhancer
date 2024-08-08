pipeline {
    agent { label "agent1" }

    environment {
        MOD_VERSION = '2.1.1'
        GITHUB_CREDENTIALS_ID = '96096c2a-dbfe-4652-93ac-61b172ccf130'
        GITHUB_USERNAME = 'sailex428'
        GITHUB_REPO = "${env.GITHUB_USERNAME}/HudEnhancer"
        GITHUB_API_URL = 'https://api.github.com'
    }

    stages {

        stage('Init') {
            steps {
                script {
                    def branchParts = env.BRANCH_NAME.split('/')
                    def mcVersion = branchParts[0]
                    env.MC_VERSION = mcVersion
                    env.TAG_NAME = "v${MOD_VERSION}-release-${MC_VERSION}"
                    env.RELEASE_TITLE = "v${MOD_VERSION}-release - mc${TAG_NAME}"
                }
            }
        }

        stage('Checkout') {
            steps {
                git branch: "${env.BRANCH_NAME}",
                    url: "https://github.com/${env.GITHUB_REPO}.git",
                    credentialsId: "${env.GITHUB_CREDENTIALS_ID}"
            }
        }

        stage('Build Package') {
            steps {
                sh './gradlew build'
            }
        }

        stage('Create Tag') {
            steps {
                script {
                   sh "git config user.name ${env.GITHUB_USERNAME}"

                   def tagName = "${env.TAG_NAME}"
                   sh "git tag ${tagName}"

                    withCredentials([string(credentialsId: "${GITHUB_CREDENTIALS_ID}", variable: 'GITHUB_PAT')]) {
                        sh "git push https://${GITHUB_PAT}@github.com/${GITHUB_REPO}.git ${TAG_NAME}"
                    }
                }
            }
        }

        stage('Create GitHub Release') {
            steps {
                script {
                    withCredentials([string(credentialsId: "${GITHUB_CREDENTIALS_ID}", variable: 'GITHUB_PAT')]) {
                        def releaseResponse = sh(
                            script: """
                                curl -L \
                                  -X POST \
                                  -H "Authorization: token ${GITHUB_PAT}" \
                                  -H "Accept: application/vnd.github+json" \
                                  -H "X-GitHub-Api-Version: 2022-11-28" \
                                  ${GITHUB_API_URL}/repos/${GITHUB_REPO}/releases \
                                  -d '{
                                        "tag_name": "${TAG_NAME}",
                                        "name": "${RELEASE_TITLE}",
                                        "body": "",
                                        "draft": false,
                                        "prerelease": false,
                                        "generate_release_notes": true
                                      }'
                            """,
                            returnStdout: true
                        ).trim()
                        echo "GitHub Release Response: ${releaseResponse}"

                        def jsonResponse = readJSON text: releaseResponse
                        def releaseID = jsonResponse.id
                        echo "releaseID : ${jsonResponse.id}"

                        def jarFile = sh(script: "find versions/${env.MC_VERSION}/build/libs/hud-enhancer-${env.MOD_VERSION}.jar", returnStdout: true).trim()
                        echo "jarFile: ${jarFile}"

                        if (jarFile) {
                            def packageResponse = sh(
                                script:
                                    """
                                        curl -L \
                                          -X POST \
                                          -H "Authorization: token ${GITHUB_PAT}" \
                                          -H "Accept: application/vnd.github+json" \
                                          -H "X-GitHub-Api-Version: 2022-11-28" \
                                          -H "Content-Type: application/java-archive" \
                                          https://uploads.github.com/repos/${env.GITHUB_REPO}/releases/${releaseID}/assets?name=${env.RELEASE_TITLE} \
                                          --data-binary "@${jarFile}"
                                    """,
                                returnStdout: true)
                            .trim()
                            echo "GitHub Upload Package Response: ${packageResponse}"

                        } else {
                            error 'No JAR file found to upload.'
                        }
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'JAR package published successfully!'
        }
        failure {
            echo 'Failed to publish JAR package.'
        }
    }
}
