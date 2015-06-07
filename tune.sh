#!/bin/bash
#sysctl -w kern.maxfilesperproc=300000
#sysctl -w kern.maxfiles=300000
#sysctl -w net.inet.ip.portrange.first=1024
ulimit -n 65536
#ulimit -n 2560
ulimit -a
