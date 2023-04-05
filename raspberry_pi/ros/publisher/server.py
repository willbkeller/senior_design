import socket
import rclpy
from rclpy import publisher
from rclpy.node import Node
from std_msgs.msg import String

class Publisher(Node):

    def __init__(self):
        super().__init__('publisher')
        self.publisher_ = self.create_publisher(String, 'controlTopic', 10)
        self.s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.s.bind((socket.gethostname(), 65500))
        self.s.listen(5)
        while True:
            try:
                self.timer_callback(self.s)
            except KeyboardInterrupt:
                print('\nSystem Off')
                exit()

    def timer_callback(self, s):
        msg = String()
        conn, addr = s.accept()
        print(f'Connection established for {addr}')
        msg.data = conn.recv(1024).decode('UTF-8')
        self.publisher_.publish(msg)
        self.get_logger().info(f'Publishing: {msg.data}')

def main(args = None):
    rclpy.init(args=args)
    publisher = Publisher()
    rclpy.spin(publisher)

    publisher.destroy_node()
    rclpy.shutdown()

if __name__ == '__main__':
    main()
