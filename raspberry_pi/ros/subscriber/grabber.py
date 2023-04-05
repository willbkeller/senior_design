import rclpy
from rclpy.node import Node
from std_msgs.msg import String
import smbus

bus = smbus.SMBus(1)
address = 0x08

class Subscriber(Node):

    def __init__(self):
        super().__init__('subscriber')
        self.subscription = self.create_subscription(String, 'controlTopic', self.listener_callback, 10)

    def listener_callback(self, msg):
        if msg.data == '4':
            print('up')
            writeNumber(int(msg.data))
        if msg.data == '5':
            print('down')
            writeNumber(int(msg.data))
        if msg.data == '6':
            print('close')
            writeNumber(int(msg.data))
        if msg.data == '7':
            print('open')
            writeNumber(int(msg.data))
        if msg.data == '8':
            print('stop')
            writeNumber(int(msg.data))

def writeNumber(x):
    bus.write_byte(address, x)

def main(args = None):
    rclpy.init(args=args)
    subscriber = Subscriber()
    rclpy.spin(subscriber)

    subscriber.destroy_node()
    rclpy.shutdown()

if __name__ == '__main__':
    main()
