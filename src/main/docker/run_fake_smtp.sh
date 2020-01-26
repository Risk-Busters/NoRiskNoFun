#!/bin/bash
# Simple script to build and run fake smtp server
FAKE_SMTP_VERSION=latest

wget -N http://nilhcem.github.com/FakeSMTP/downloads/fakeSMTP-$FAKE_SMTP_VERSION.zip -P ~/fakeSMTP/
unzip ~/fakeSMTP/fakeSMTP-$FAKE_SMTP_VERSION.zip -d ~/fakeSMTP/
java -jar ~/fakeSMTP/fakeSMTP-2.0.jar --start-server --background --port 2525 --bind-address 127.0.0.1
