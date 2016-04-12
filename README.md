# gs-teamup-bot
getting started teamup bot

# project 세팅
add lombok
https://projectlombok.org/features/index.html

# 설정파일

config.properties 에 수정
( /data/etc/gs-teamup-bot/config.properties 경로에 넣어 classpath 외부에서 설정 가능 )

oauth.client.id={client id}
oauth.client.secret={client secret}

teamup.id={teamup bot id}
teamup.pw={teamup bot password}


* client id / client secret 발급은 문의

# build
gradlew build

# 실행
nohup java -jar $WAR_FILE  >> nohup.out &

