#!/bin/bash
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
