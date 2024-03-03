def sastScanGate(gitBranch=null,gitRepositoryUrl=null,serviceName=null,projectName=null,APP_ID=null,APP_NAME=null,VERSION_ID=null) {
    if ("${build}" == "Yes" && "${sit_deploy}" == "true") {
        node('STMSSCSSU1') {
            stage('Run Scan SAST') {
                git branch: "${gitBranch}", credentialsId: 'jenkins_autoForBitbucket', url: "${gitRepositoryUrl}/${service_name}.git"
                dir("./corefunc"){
                    git branch: "master", credentialsId: "${bitbucketCredentials}" , url: "${corefunc_url}"
                }
                corefunc = load("./corefunc/coremethod.groovy")
                corefunc.sastscanfortify("${APP_ID}_${APP_NAME}", "${VERSION_ID}" , "${SERVICE_NAME}")
            }
            stage('SAST - CheckGate') {
                def (highestSeverity , severityValue) = corefunc.sastgatefortify("${VERSION_ID}")
                if (highestSeverity == "Critical" | highestSeverity == "High") {
                    // unstable("Fail")
                    error("SAST - CheckGate is FAILED!!.")
                }
                echo "$highestSeverity has $severityValue issue(s)"
            }
        }
    }
}

  