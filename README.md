# gs-teamup-bot
getting started teamup bot

# project 세팅
add lombok https://projectlombok.org/features/index.html

# 파일설정

config.properties 에 수정

* oauth.client.id={client id}
* oauth.client.secret={client secret}
* teamup.id={teamup bot id}
* teamup.pw={teamup bot password}

client id / client secret 발급은 문의

/data/etc/gs-teamup-bot/config.properties 경로에 파일 생성해 classpath 외부에서 설정 가능

# build
gradlew build

빌드 성공 하면 /build/libs 에 war 파일 생성

# 실행
nohup java -jar $WAR_FILE >> nohup.out &
