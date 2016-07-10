# !/bin/bash

modprobe bcm2835-v4l2
python AxisServer.py &
python lcdServer.py &
python motionServer.py &
