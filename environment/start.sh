#!/bin/bash

docker-compose up -d --no-recreate

sleep 5

echo "Waiting for mongodb1 startup.."
until curl http://mongodb1:27017/serverStatus\?text\=1 2>&1 | grep uptime | head -1; do
  printf '.'
  sleep 1
done

docker exec -it mongodb1 mongo --eval "var cfg = {_id : 'local-mongodb-replica-set',members: [{ _id : 0, host : 'mongodb1:27017', priority:2 },{ _id : 1, host : 'mongodb2:27017', priority:1 }]}; rs.initiate(cfg, { force: true });
rs.reconfig(cfg, { force: true });
rs.secondaryOk();
db.getMongo().setReadPref('nearest');
db.getMongo().setSecondaryOk();"


