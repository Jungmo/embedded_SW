#!/usr/bin/python

import SimpleHTTPServer
import SocketServer
import cgi
from MPU6050 import *
import json

HOST = '127.0.0.1'
PORT = 33331

class ServerHandler(SimpleHTTPServer.SimpleHTTPRequestHandler):
    def do_GET(self):
        self.send_response(200)
        self.send_header('Content-type', 'text/html')
        self.end_headers()
        accel_data = sensor.get_accel_data()
        gyro_data = sensor.get_gyro_data()
        self.wfile.write(json.dumps({"accel_data": accel_data, "gyro_data": gyro_data}))


sensor = MPU6050(0x68)

Handler = ServerHandler

httpd = SocketServer.TCPServer((HOST, PORT), Handler)

print "Serving at: http://%(interface)s:%(port)s" % dict(interface=HOST, port=PORT)
httpd.serve_forever()

