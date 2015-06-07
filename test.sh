#!/bin/bash
source tune.sh
sbt -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv6Addresses=false test
