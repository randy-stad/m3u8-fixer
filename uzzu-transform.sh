#!/bin/bash
java -jar /Users/mush/src/m3u8-fixer/target/m3u8-fixer-1.0-SNAPSHOT-jar-with-dependencies.jar https://uzzu.tv/app2/arelem@mac.com -s uzzu -o /Users/mush/volumes/channels/m3u8/uzzu.m3u8
curl -X POST http://localhost:8089/providers/m3u/sources/Uzzu/refresh
java -jar /Users/mush/src/m3u8-fixer/target/m3u8-fixer-1.0-SNAPSHOT-jar-with-dependencies.jar https://raw.githubusercontent.com/iptv-org/iptv/refs/heads/master/streams/us_tubi.m3u -s tubi -o /Users/mush/volumes/channels/m3u8/tubi.m3u
curl -X POST http://localhost:8089/providers/m3u/sources/Tubi/refresh
