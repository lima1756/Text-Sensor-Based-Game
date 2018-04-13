String input;
const int button1 = 7;
const int button2 = 8;
void setup() {
  Serial.begin(9600);
  pinMode(23, OUTPUT);
  pinMode(25, OUTPUT);
  pinMode(27, OUTPUT);
  pinMode(button1, INPUT);
  pinMode(button2, INPUT);
  digitalWrite(23, LOW);
  digitalWrite(25, LOW);
  digitalWrite(27, LOW);
  input = "1";
  Serial.println("\"");
}

void loop() {
  delay(20);
  while (Serial.available()) {
     input = input + char(Serial.read());
     delay(1);
     Serial.println("Delay");
  }
  if(input=="1"){
      boolean waitTilButton = true;
      while (waitTilButton) {
        if (digitalRead(button1) == HIGH) {
          Serial.println("Button 1 was pressed");
          waitTilButton = false;
        } else if (digitalRead(button2) == HIGH) {
          Serial.println("Button 2 was pressed");
          waitTilButton = false;
        }
      }
  }
  else if(input=="2"){
      //returnSensor("BTN16");
      Serial.println("5");
  }
  else if(input=="3"){
      //returnSensor("SWITCH");
      Serial.println("0");
  }
  else if(input=="4"){
      //returnSensor("LIGHT");
      Serial.println("0");
  }
  else if(input=="5"){
      //returnSensor("RANGE");
      Serial.println("16.5");
  }
  else if(input=="6"){
      //returnSensor("SOUND");
      Serial.println("1");
  }
  else if(input=="7"){
      returnSensor("RGB");
  }
  else if(input=="0"){
      Serial.println("\\");
  }
  // input = "";
}

void returnSensor(String sensor){
  Serial.println("The_received_sensor_was:_" + sensor);
  delay(5000);
}

