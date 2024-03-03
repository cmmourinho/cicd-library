def springBoot(nexusRepo=null,serviceName=null,dockerImageTag=null) {
    sh """
        sudo docker build --add-host nexus.tmbbank.local:10.209.50.1 -t ${nexusRepo}/${serviceName}:${dockerImageTag} -f Dockerfile .
    """
}

def springBoot(nexusRepo=null,serviceName=null,dockerImageTag=null) {
    sh """
        rm -rf dist
        npm install --verbose
        npm run build"
        npm run updateBuild -- ${buildVersion}"
        find ${rootDir}/dist -type d -exec chmod 755 {} \\; |exit 0"
        find ${rootDir}/dist -type f -exec chmod 644 {} \\; |exit 0" 
        sudo docker build --add-host nexus.tmbbank.local:10.209.50.1 -t ${nexusRepo}/${serviceName}:${dockerImageTag} -f Dockerfile .
    """
}