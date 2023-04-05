#include <Wire.h>

#define SLAVE_ADDRESS 0x08

// Pololu Motor Driver
#define MD1_EN_PIN 23
#define M1PWM_PIN 10
#define M2PWM_PIN 8
#define M1DIR_PIN 45
#define M2DIR_PIN 27

// Other one
#define MD2_M1PWM 12
#define MD2_M2PWM 13
#define MD2_M1DIR 31
#define MD2_M2DIR 33

// Veritical Lin Act
#define LAV_LPWM 2 //up
#define LAV_RPWM 3 //down
#define LAV_EN 41

// Horizontal Lin Act
#define LAH_LPWM 4 //open
#define LAH_RPWM 5 //close
#define LAH_EN 39

#define SWITCH 42



int number = 0;
int object;

void setup() {

  pinMode(MD1_EN_PIN, OUTPUT);
  pinMode(M1PWM_PIN, OUTPUT);
  pinMode(M2PWM_PIN, OUTPUT);
  pinMode(M1DIR_PIN, OUTPUT);
  pinMode(M2DIR_PIN, OUTPUT);
  
  pinMode(MD2_M1PWM, OUTPUT);
  pinMode(MD2_M2PWM, OUTPUT);
  pinMode(MD2_M1DIR, OUTPUT);
  pinMode(MD2_M2DIR, OUTPUT);

  pinMode(LAV_EN, OUTPUT);
  pinMode(LAV_LPWM, OUTPUT);
  pinMode(LAV_RPWM, OUTPUT);

  pinMode(LAH_EN, OUTPUT);
  pinMode(LAH_LPWM, OUTPUT);
  pinMode(LAH_RPWM, OUTPUT);
  
  pinMode(SWITCH, INPUT);
  
  Serial.begin(9600); // start serial for output
  // initialize i2c as slave
  Wire.begin(SLAVE_ADDRESS);
  
  // define callbacks for i2c communication
  Wire.onReceive(receiveData);
  Wire.onRequest(sendData);
}

void loop() {
  if (digitalRead(SWITCH) == HIGH){
    delay(200);
    analogWrite(LAH_LPWM, 0);
  }
  Serial.println(digitalRead(SWITCH));
  delay(200);
}

// callback for received data
void receiveData(int byteCount){  
  while(Wire.available()) {
    number = Wire.read();
    Serial.println(number);
    
    //system stop
    if (number == 8){      
      analogWrite(M1PWM_PIN, 0);
      analogWrite(M2PWM_PIN, 0);
      analogWrite(MD2_M1PWM, 0);
      analogWrite(MD2_M2PWM, 0);

      analogWrite(LAV_LPWM, 0);
      analogWrite(LAV_RPWM, 0);
      analogWrite(LAH_LPWM, 0);
      analogWrite(LAH_RPWM, 0);      
    } 
    
    //drive
    else if (number == 0){      
      digitalWrite(MD1_EN_PIN, HIGH);
      digitalWrite(M1DIR_PIN, HIGH);
      digitalWrite(M2DIR_PIN, LOW);
      //analogWrite(MD2_M1PWM, 100);
      digitalWrite(MD2_M1DIR, HIGH);
      //analogWrite(MD2_M2PWM, 100);
      digitalWrite(MD2_M2DIR, LOW);

     
      analogWrite(M1PWM_PIN, 100);
      analogWrite(M2PWM_PIN, 100);
      analogWrite(MD2_M1PWM, 75);
      analogWrite(MD2_M2PWM, 75);      
    }
        
    //reverse
    else if (number == 1){
      digitalWrite(MD1_EN_PIN, HIGH);
      digitalWrite(M1DIR_PIN, LOW);
      digitalWrite(M2DIR_PIN, HIGH);
      digitalWrite(MD2_M1DIR, LOW);
      digitalWrite(MD2_M2DIR, HIGH);
      
      analogWrite(M1PWM_PIN, 100);
      analogWrite(M2PWM_PIN, 100);
      analogWrite(MD2_M1PWM, 75);
      analogWrite(MD2_M2PWM, 75);
    }

    //left turn
    else if (number == 2){
      digitalWrite(MD1_EN_PIN, HIGH);
      digitalWrite(M1DIR_PIN, LOW);
      digitalWrite(M2DIR_PIN, LOW);
      digitalWrite(MD2_M1DIR, HIGH);
      digitalWrite(MD2_M2DIR, HIGH);
      
      analogWrite(M1PWM_PIN, 150);
      analogWrite(M2PWM_PIN, 150);
      analogWrite(MD2_M1PWM, 125);
      analogWrite(MD2_M2PWM, 125); 
    }

    //right turn
    else if (number == 3){
      digitalWrite(MD1_EN_PIN, HIGH);
      digitalWrite(M1DIR_PIN, HIGH);
      digitalWrite(M2DIR_PIN, HIGH);
      digitalWrite(MD2_M1DIR, LOW);
      digitalWrite(MD2_M2DIR, LOW);
      
      analogWrite(M1PWM_PIN, 150);
      analogWrite(M2PWM_PIN, 150);
      analogWrite(MD2_M1PWM, 125);
      analogWrite(MD2_M2PWM, 125);
    }

    else if (number == 4){
      digitalWrite(LAV_EN, HIGH);
      analogWrite(LAV_LPWM, 255);     
    }

    else if (number == 5){
       digitalWrite(LAV_EN, HIGH);
       analogWrite(LAV_RPWM, 255); 
    }

    else if (number == 6){
       digitalWrite(LAH_EN, HIGH);
       analogWrite(LAH_LPWM, 255);   
    }

    else if (number == 7){
      digitalWrite(LAH_EN, HIGH);
      analogWrite(LAH_RPWM, 255);    
    }
  }
}

// callback for sending data
void sendData(){
  byte voltage = analogRead(A8);
  Wire.write(voltage);
}
