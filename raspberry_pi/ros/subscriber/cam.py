import rclpy
from rclpy import publisher
from rclpy.node import Node
from std_msgs.msg import String
import cv2 as cv
import numpy as np
import time

class Publisher(Node):

  def __init__(self):
    super().__init__('publisher')
    self.publisher_ = self.create_publisher(String, 'controlTopic', 10)
    self.timer_callback()

  def timer_callback(self):
    msg = String()
    msg.data = '$'
    sub = Subscriber()
    bound = [([0,0,0],[51,51,51])]
    
    for (lower, upper) in bound:
      lower = np.array(lower, dtype="uint8")
      upper = np.array(upper, dtype="uint8")
    
    def canny(img):
      gray = cv.cvtColor(img, cv.COLOR_BGR2GRAY)
      canny = cv.Canny(gray, 50, 150)
      return canny

    vid = cv.VideoCapture(1)
    array = []
    while (vid.isOpened()):
      _, frame = vid.read()
      lane_canny = canny(frame)
      #cropped_canny = drive_zone(lane_canny)
      mask = cv.inRange(frame, lower, upper)
      mask_out = cv.bitwise_and(lane_canny, mask)
      lines = cv.HoughLinesP(mask_out, 2, np.pi/180, 100, np.array([]),
                                 minLineLength=40, maxLineGap=50)

      if lines is None:
        #cv.imshow('', frame)
        if msg.data != '8':
          msg.data = '8'
          print(msg.data)
          self.publisher_.publish(msg)
      else:
        for line in lines:
          x1, y1, x2, y2 = line[0]
          fit = np.polyfit((int(x1),int(y1)), (int(x2),int(y2)), 1)
          m = int(fit[0])
          b = int(fit[1])
          array.append(line)
          cv.line(frame, (x1,y1), (x2,y2), (0, 255, 0), 5)
          time.sleep(0)
          array = []
        cv.imshow('', frame)
        if msg.data != '0':
          msg.data = '0'
          print(msg.data)
          self.publisher_.publish(msg)

      if cv.waitKey(1) == ord('q'):
        break
    vid.release()
    rclpy.spin(sub)
  
class Subscriber(Node):

  def __init__(self):
    super().__init__('subscriber')
    self.subscription = self.create_subscription(String, 'controlTopic', self.callback, 10)
        
  def callback(self, msg):
    if msg.data == 'S':
      pub = Publisher()
      rclpy.spin(pub)
            	
def main(args = None):
  rclpy.init(args=args)
  sub = Subscriber()
  rclpy.spin(sub)

if __name__ == '__main__':
  main()
