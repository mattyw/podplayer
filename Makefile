run:
	lein ring server-headless

build:
	lein uberjar

jarrun: build
	java -jar target/podplayer-0.1.0-SNAPSHOT-standalone.jar

deploy: build
	scp target/podplayer-0.1.0-SNAPSHOT-standalone.jar pi@${IP}:/home/pi
