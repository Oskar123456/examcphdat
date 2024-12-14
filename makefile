run: build
	mvn exec:java

debug: build
	mvnDebug exec:java

full: clean build
	mvn exec:java

build:
	mvn compile

.PHONY: clean
clean: 
	mvn clean

test: build
	mvn test



























