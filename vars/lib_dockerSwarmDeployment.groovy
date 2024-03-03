def deploySwarmDev1(
    webServerDevIp = null,
    webServerDevUser = null,
    appServerDevIp = null,
    appServerDevUser = null,
    dockerImageTag = null,
    serviceName = null,
    hostPort = null,
    containerPort = null,
    nexusUsername = null,
    nexusPassword = null,
    nexusRepo = null,
    logPathOnHost = null,
    logPathOnContainer = null,
    cmd_option = null)
{
    node('master') {
        if ( deployNodeLabel == 'web' ) {
            sh "ssh ${webServerDevUser}@${webServerDevIp} \"sudo docker login -u ${nexusUsername} -p ${nexusPassword} ${nexusRepo}\""
            sh "ssh ${webServerDevUser}@${webServerDevIp} \"sudo docker pull ${nexusRepo}/${serviceName}:${dockerImageTag}\""
        } else if ( deployNodeLabel == 'app' ) {
            sh "ssh ${appServerDevUser}@${appServerDevIp} \"sudo docker login -u ${nexusUsername} -p ${nexusPassword} ${nexusRepo}\""
            sh "ssh ${appServerDevUser}@${appServerDevIp} \"sudo docker pull ${nexusRepo}/${serviceName}:${dockerImageTag}\""
        } else {
            error "Invalid deployNodeLabel: ${deployNodeLabel}"
        }
        sh "ssh ${appServerDevUser}@${appServerDevIp} \"sudo docker service rm ${serviceName} | exit 0\""
        sh "ssh ${appServerDevUser}@${appServerDevIp} \"sudo docker service create -t -p ${hostPort}:${containerPort} --name ${serviceName} --mount type=bind,src=${logPathOnHost}/${serviceName},dst=${logPathOnContainer} ${cmd_option} ${nexusRepo}/${serviceName}:${dockerImageTag}\""
    }
}

def deploySwarmDev2(
    webServerDev2Ip = null,
    webServerDev2User = null,
    appServerDev2Ip = null,
    appServerDev2User = null,
    dockerImageTag = null,
    serviceName = null,
    hostPort = null,
    containerPort = null,
    nexusUsername = null,
    nexusPassword = null,
    nexusRepo = null,
    logPathOnHost = null,
    logPathOnContainer = null,
    cmd_option = null)
{
    node('master') {
        if ( deployNodeLabel == 'web' ) {
            sh "ssh ${webServerDev2User}@${webServerDev2Ip} \"sudo docker login -u ${nexusUsername} -p ${nexusPassword} ${nexusRepo}\""
            sh "ssh ${webServerDev2User}@${webServerDev2Ip} \"sudo docker pull ${nexusRepo}/${serviceName}:${dockerImageTag}\""
        } else if ( deployNodeLabel == 'app' ) {
            sh "ssh ${appServerDev2User}@${appServerDev2Ip} \"sudo docker login -u ${nexusUsername} -p ${nexusPassword} ${nexusRepo}\""
            sh "ssh ${appServerDev2User}@${appServerDev2Ip} \"sudo docker pull ${nexusRepo}/${serviceName}:${dockerImageTag}\""
        } else {
            error "Invalid deployNodeLabel: ${deployNodeLabel}"
        }
        sh "ssh ${appServerDev2User}@${appServerDev2Ip} \"sudo docker service rm ${serviceName} | exit 0\""
        sh "ssh ${appServerDev2User}@${appServerDev2Ip} \"sudo docker service create -t -p ${hostPort}:${containerPort} --name ${serviceName} --mount type=bind,src=${logPathOnHost}/${serviceName},dst=${logPathOnContainer} ${cmd_option} ${nexusRepo}/${serviceName}:${dockerImageTag}\""
    }
}