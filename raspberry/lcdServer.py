#!/usr/bin/python

import SimpleHTTPServer
import SocketServer
import cgi
import lcd

HOST = '127.0.0.1'
PORT = 33330

class ServerHandler(SimpleHTTPServer.SimpleHTTPRequestHandler):
    def do_GET(self):
        self.send_response(200)
        self.send_header('Content-type', 'text/html')
        self.end_headers()
        self.wfile.write('NOTHING')

    def do_POST(self):
        form = cgi.FieldStorage(
            fp=self.rfile,
            headers=self.headers,
            environ={'REQUEST_METHOD':'POST',
                     'CONTENT_TYPE':self.headers['Content-Type'],
                     })
        if 'line1' in form:
            line1 = ''
            try:
                line1 = form['line1'].value[0:16]
            except:
                pass
            lcd.lcd_string(line1, lcd.LCD_LINE_1)

        if 'line2' in form:
            line2 = ''
            try:
                line2 = form['line2'].value[0:16]
            except:
                pass
            lcd.lcd_string(line2, lcd.LCD_LINE_2)

        self.send_response(200)
        self.send_header('Content-type', 'text/html')
        self.end_headers()
        self.wfile.write('OK')


lcd.lcd_init()

Handler = ServerHandler

httpd = SocketServer.TCPServer((HOST, PORT), Handler)

print "Serving at: http://%(interface)s:%(port)s" % dict(interface=HOST, port=PORT)
httpd.serve_forever()
