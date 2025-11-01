#!/bin/bash
java -jar /Users/mush/src/m3u8-fixer/target/m3u8-fixer-1.0-SNAPSHOT-jar-with-dependencies.jar https://uzzu.tv/app2/arelem@mac.com -o /Users/mush/volumes/channels/m3u8/uzzu.m3u8
curl -X POST http://localhost:8089/providers/m3u/sources/Uzzu/refresh
