import socket
import rclpy
from rclpy.node import Node
from std_msgs.msg import String
import smbus
import time

bus = smbus.SMBus(1)
address = 0x08

class Subscriber(Node):
 
  def __init__(self):
    super().__init__('subscriber')
    self.subscription = self.create_subscription(String, 'controlTopic', self.callback, 10)
    '''self.s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    self.s.bind((socket.gethostname(), 65501))
    self.s.listen(5)'''
    
  def callback(self, msg):
    if msg.data == '9':
      try:
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.bind((socket.gethostname(), 65501))
        s.listen(5)
        while True:
          conn, addr = s.accept()
          print(f'Connection established for {addr}')
        
          while True:
            voltage = int(bus.read_byte(address))
            if voltage in range(60,80):
              voltage = 1
            elif voltage in range(80,100):
              voltage = 2
            elif voltage in range(100,120):
              voltage = 3
            elif voltage in range(120,140):
              voltage = 4
            elif voltage in range(140,160):
              voltage = 5
            elif voltage in range(160,180):
              voltage = 6
            elif voltage in range(180,200):
              voltage = 7
            elif voltage in range(200,220):
              voltage = 8
            elif voltage in range(220,240):
              voltage = 9
            elif voltage in range(240,260):
              voltage = 10
            else:
              voltage = 0
            conn.send(voltage.to_bytes(1, byteorder='big'))
            print(f'Sent {voltage}')
            time.sleep(1)
      except KeyboardInterrupt:
        print('Exit')

def main(args=None):
    rclpy.init(args=args)
    subscriber = Subscriber()
    rclpy.spin(subscriber)

    subscriber.destroy_node()
    rclpy.shutdown()

if __name__ == '__main__':
  main()
