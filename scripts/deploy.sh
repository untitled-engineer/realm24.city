#!/usr/bin/env bash

mvn clean package

echo "Copy files..."

scp -i ~/.ssh/hetzner_rsa \
  target/switter-1.0-SNAPSHOT.jar \
  root@realm24.app:/root/

echo "Restart server..."

ssh -i ~/.ssh/hetzner_rsa root@realm24.app << EOF
  pgrep java | xargs kill -9
  nohup java -jar switter-1.0-SNAPSHOT.jar > app.log &
EOF

