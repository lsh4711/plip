#!/bin/bash
BUILD_JAR=$(ls /home/ec2-user/action/teamdev/server/build/libs/teamdev.jar)
JAR_NAME=$(basename $BUILD_JAR)

echo "> 현재 시간: $(date)" >> /home/ec2-user/action/teamdev/server/deploy.log
echo "> build 파일명: $JAR_NAME" >> /home/ec2-user/action/teamdev/server/deploy.log
echo "> build 파일 복사" >> /home/ec2-user/action/teamdev/server/deploy.log
DEPLOY_PATH=/home/ec2-user/action/teamdev/server/
cp $BUILD_JAR $DEPLOY_PATH
echo "> 현재 실행중인 애플리케이션 pid 확인" >> /home/ec2-user/action/teamdev/server/deploy.log
CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> /home/ec2-user/action/teamdev/server/deploy.log
else
  echo "> kill -9 $CURRENT_PID" >> /home/ec2-user/action/teamdev/server/deploy.log
  sudo kill -9 $CURRENT_PID
  sleep 5
fi

JAVA_OPTS="${JAVA_OPTS} -Dserver.tomcat.accesslog.enabled=true"
JAVA_OPTS="${JAVA_OPTS} -Dserver.tomcat.basedir=/home/ec2-user"
JAVA_OPTS="${JAVA_OPTS} -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8534"

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
source ~/.bashrc
echo "> DEPLOY_JAR 배포"    >> /home/ec2-user/action/teamdev/server/deploy.log
# nohup java -jar $DEPLOY_JAR >> /home/ec2-user/action/teamdev/server/deploy.log 2>/home/ec2-user/action/teamdev/server/deploy_err.log &
nohup java ${JAVA_OPTS} -jar $DEPLOY_JAR --logging.file.path=/home/ec2-user/ --logging.level.org.hibernate.SQL=DEBUG --logging.level.org.springframework.web=DEBUG >> /home/ec2-user/action/teamdev/server/deploy.log 2>/home/ec2-user/action/teamdev/server/deploy_err.log &
