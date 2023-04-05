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
        #self.get_logger().info(f'Publishing: {msg.data}')
        if msg.data == '0':
            print('Drive')
            writeNumber(int(msg.data))
        if msg.data == '1':
            print('Reverse')
            writeNumber(int(msg.data))
        if msg.data == '2':
            print('Left')
            writeNumber(int(msg.data))
        if msg.data == '3':
            print('Right')
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
