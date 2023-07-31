#!/bin/bash
BUILD_JAR=$(ls /home/ec2-user/action/plip/server/build/libs/plip-0.2.jar)
JAR_NAME=$(basename $BUILD_JAR)
LOG_PATH=/home/ec2-user/logs/plip/

echo "> 현재 시간: $(date)" >> $LOG_PATH/deploy.log
echo "> build 파일명: $JAR_NAME" >> $LOG_PATH/deploy.log
echo "> build 파일 복사" >> $LOG_PATH/deploy.log
DEPLOY_PATH=/home/ec2-user/action/plip/server/
cp $BUILD_JAR $DEPLOY_PATH
echo "> 현재 실행중인 애플리케이션 pid 확인" >> $LOG_PATH/deploy.log
CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> $LOG_PATH/deploy.log
else
  echo "> kill -9 $CURRENT_PID" >> $LOG_PATH/deploy.log
  sudo kill -9 $CURRENT_PID
  sleep 5
fi

JAVA_OPTS="${JAVA_OPTS} -Dserver.tomcat.accesslog.enabled=true"
JAVA_OPTS="${JAVA_OPTS} -Dserver.tomcat.accesslog.directory=access-logs"
JAVA_OPTS="${JAVA_OPTS} -Dserver.tomcat.accesslog.prefix=access"
JAVA_OPTS="${JAVA_OPTS} -Dserver.tomcat.basedir=/home/ec2-user/logs/plip/"
JAVA_OPTS="${JAVA_OPTS} -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:4711"

JAR_OPTS="${JAR_OPTS} --logging.file.path=/home/ec2-user/logs/plip/"

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
source ~/.bashrc
echo "> DEPLOY_JAR 배포"    >> $LOG_PATH/deploy.log
nohup java ${JAVA_OPTS} -jar $DEPLOY_JAR ${JAR_OPTS} >> $LOG_PATH/deploy.log 2>$LOG_PATH/deploy_err.log &
