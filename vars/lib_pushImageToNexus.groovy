def pushImageToNexus(nexusUsername=null,nexusPassword=null,serviceName=null) {
    sh "sudo docker login -u ${nexusUsername} -p ${nexusPassword} ${nexusRepo}"
    sh "sudo docker push ${nexusRepo}/${serviceName}:${dockerImageTag}"
}   