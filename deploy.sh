#!/bin/bash
LOCALF=0
PKGF=1
DEPLOYF=1
ERRORF=0
WEBXML="webapp/src/main/webapp/WEB-INF/web.xml"
LINENUMBER=$(cat ${WEBXML} | grep -n 'spring.profiles.active' | cut -d : -f 1)
LINENUMBER=$((LINENUMBER + 1))

F_READ_RET=""
function readFile {
    F_READ_RET=""
    F_READ_RET=$(cat ${WEBXML} | sed -n ${LINENUMBER},${LINENUMBER}p | egrep -o "default|production")
      if [ ${F_READ_RET} == "" ]
      then
           echo "Error reading ${WEBXML}"
           exit 0
      fi
}
readFile
LASTCONF=${F_READ_RET}


function replace {
           readFile
           sed -i ${LINENUMBER}s/${F_READ_RET}/${1}/ ${WEBXML}
}

while test $# -gt 0; do
        case "$1" in
                -h|--help)
                        echo "-local                    package for a local repository and avoid deploying"
                        echo " "
                        echo "-no-pkg                   avoid recompiling the package"
                        echo " "
                        echo "-no-deploy                avoid deploying the webapp"
                        echo " "
                        echo "-h, --help                show brief help"
                        exit 0
                        ;;
                -local)
                        LOCALF=1
                        DEPLOYF=0
                        ;;
                -no-pkg)
                        PKGF=0
                        ;;
                -no-deploy)
                        DEPLOYF=0
                        ;;
        esac
        shift
done

if [ ${LOCALF} -eq 1 ]
then
    replace default
fi

if [ ${PKGF} -eq 0 ]
then
    if [ ${DEPLOYF} -eq 0 ]
    then
    echo "Nothing was done"
    exit 0
    fi
fi

if [ ${PKGF} -eq 1 ]
then
    if [ ${DEPLOYF} -eq 1 ]
    then
        replace production
    else
        replace default
    fi

    echo "Building package......"
    mvn clean package | egrep "WARNING|ERROR" && ERRORF=1

   if [ ${ERRORF} -eq 1 ]
   then
        echo "Error building the package"
        exit 0
   fi

   echo "Package Built Successfully"
   replace ${LASTCONF}

fi

if [ ${DEPLOYF} -eq 1 ]
then
    echo "Deploying..."
    rm app.war
    mv ./webapp/target/webapp.war app.war
    echo "Enter username"
    read username
    echo "Enter password"
    read -s pass
    filename=app.war
    cmd="echo -ne \"cd web/ \n put $filename \n \" | sshpass -p ooc4Choo sftp -oStrictHostKeyChecking=no paw-2017a-4@10.16.1.110"
    #cmd="ls"
    echo $cmd
    echo -ne "pwd\n put $filename\n" | sshpass -p $pass sftp $username@pampero.itba.edu.ar &&
    echo -ne $cmd | sshpass -p $pass ssh $username@pampero.itba.edu.ar
    echo "Deployed"
fi