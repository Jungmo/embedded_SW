#!/usr/bin/python

import SimpleHTTPServer
import SocketServer
import cgi
import os
from subprocess import call

HOST = '127.0.0.1'
PORT = 33332

class ServerHandler(SimpleHTTPServer.SimpleHTTPRequestHandler):

    def __init__(self, r, c, s):
      SimpleHTTPServer.SimpleHTTPRequestHandler.__init__(self, r, c, s)
      self.running = self.isRunning()

    def isRunning(self):
      # /var/run/motion/motion.pid
      return os.path.exists('/var/run/motion/motion.pid')

    def getLatestFile(self):
      d = '/var/lib/motion/'
      filelist = os.listdir(d)
      filelist = filter(lambda x: not os.path.isdir(x), filelist)
      return max(filelist, key=lambda x: os.stat(d + x).st_mtime)

    def do_GET(self):
        self.res_header()
        self.running = self.isRunning()
        result = '%r'% (self.running)
        if self.running:
          result += '/' + self.getLatestFile()
        self.wfile.write(result)

    def res_header(self):
      self.send_response(200)
      self.send_header('Content-type', 'text/html')
      self.end_headers()

    def res_error(self):
      self.res_header()
      self.wfile.write('ERROR')

    def res_success(self):
      self.res_header()
      self.wfile.write('OK')

    def do_POST(self):
        form = cgi.FieldStorage(
            fp=self.rfile,
            headers=self.headers,
            environ={'REQUEST_METHOD':'POST',
                     'CONTENT_TYPE':self.headers['Content-Type'],
                     })

        cmd = ''
        try:
          cmd = form['cmd'].value[0:16]
        except:
          res_error()
          return

        if cmd == 'turnon':
          if self.isRunning():
            self.res_error()
            return
          call(["service", "motion", "start"])
          self.res_success()
        elif cmd == 'turnoff':
          if not self.isRunning():
            self.res_error()
            return
          call(["service", "motion", "stop"])
          self.res_success()


Handler = ServerHandler

httpd = SocketServer.TCPServer((HOST, PORT), Handler)

print "Serving at: http://%(interface)s:%(port)s" % dict(interface=HOST, port=PORT)
httpd.serve_forever()

