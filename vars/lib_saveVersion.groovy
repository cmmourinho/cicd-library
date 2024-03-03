def saveVersionControlDev(envNameDev=null,gitRepositoryUrl=null,jenkinsBitbucketClone=null,serviceName=null,dockerImageTag=null) {
    dir("version_control"){
        git branch: "master", credentialsId: "${jenkinsBitbucketClone}", url: "${versionConfigUrl}" 
        def image_tag
        def regex_fullimagetag = /${service_name}:([0-9a-zA-Z-:.]+)/
        def all_version = readFile "./version_${envNameDev}.txt"
        echo "${all_version}"
        if (all_version.contains("${serviceName}")) {
            image_tag = getfullimagetag(all_version,regex_fullimagetag)
            echo "we will replace:${image_tag} to ${DockerImageTag}"
            all_version_after = "${all_version}".replaceAll("${serviceName}:${image_tag}", "${serviceName}:${DockerImageTag}")
        } else {
            sh "echo ${serviceName}:${DockerImageTag} >> ./version_${envNameDev}.txt"
        }
        if ("${image_tag}" == "${DockerImageTag}"){
          echo "Skip this step because tag version equal to the previous version"
        } else {
        withCredentials([usernamePassword(credentialsId: "jenkins_autoForBitbucket", usernameVariable: 'user_git', passwordVariable: 'pass_git')]) {
            sh "git remote set-url origin https://${user_git}:${pass_git}@bitbucket.tmbbank.local:7990/scm/newkyc/eob-control-verison.git"
            def all_version_after_trim = all_version_after.trim();
            sh "echo \'${all_version_after_trim}\' > ./version_${deploy_env}.txt"
            sh "git config --global user.email cicd@ttbbank.com"
            sh "git config --global user.name devpos"
            sh "git add ."
            sh "git commit -m '${service_name}:${image_tag} to ${service_name}:${buildVersion}'"
            sh "git pull origin master"
            sh "git push -u origin master"
        }
        }
    }
}   