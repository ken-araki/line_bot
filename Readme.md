# 環境構築

## 前提

以下がインストールされていること

- Java8
- docker

## アプリケーション起動

```
git clone ${このリポジトリ}

## start postgresql
cd docker
docker-compose up -d

## build & start application( with Migrate)
cd ../
./gradlew -x test clean build bootRun
java -Dserver.port=$PORT -jar build/libs/line_bot-0.0.1-SNAPSHOT.jar

```

