pipeline{

    agent any

    stages{

        stage('code-compile'){
            steps {
                   sh '''
                     changed_folders=`git diff --name-only $SHIPPABLE_COMMIT_RANGE | grep / | awk 'BEGIN {FS="/"} {print $1}' | uniq`
                     echo "changed folders "$changed_folders
                   '''
            }
        }


   }

}