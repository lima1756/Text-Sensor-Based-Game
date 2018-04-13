#include <Keypad.h>

String input;
const int BLEFT = 2;
const int BRIGHT = 3;

const byte ROWS = 4; // Four rows
const byte COLS = 4; // Three columns
// Define the Keymap
char keys[ROWS][COLS] = {
  {'a','b','c','d'},
  {'e','f','g','h'},
  {'i','j','k','l'},
  {'m','n','o','p'}
};
// Connect keypad ROW0, ROW1, ROW2 and ROW3 to these Arduino pins.
byte rowPins[ROWS] = { 9, 8, 7, 6 };
// Connect keypad COL0, COL1 and COL2 to these Arduino pins.
byte colPins[COLS] = {13, 12, 11, 10 }; 

// Create the Keypad
Keypad kpd = Keypad( makeKeymap(keys), rowPins, colPins, ROWS, COLS );

void setup() {
  Serial.begin(9600);
  pinMode(23, OUTPUT);
  pinMode(25, OUTPUT);
  pinMode(27, OUTPUT);
  pinMode(BLEFT, INPUT);
  pinMode(BRIGHT, INPUT);
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
        if (digitalRead(BLEFT) == HIGH) {
          Serial.println("Button left was pressed");
          waitTilButton = false;
        } else if (digitalRead(BRIGHT) == HIGH) {
          Serial.println("Button right was pressed");
          waitTilButton = false;
        }
      }
  }
  else if(input=="2") {
      char key = kpd.getKey();
        if(key) {  // Check for a valid key.
          switch (key) {
            case 'a':
              Serial.println("1 was pressed");
              break;
            case 'b':
              Serial.println("2 was pressed");
              break;
            case 'c':
              Serial.println("3 was pressed");
              break;
            case 'd':
              Serial.println("4 was pressed");
              break;
            case 'e':
              Serial.println("5 was pressed");
              break;
            case 'f':
              Serial.println("6 was pressed");
              break;
            case 'g':
              Serial.println("7 was pressed");
              break;
            case 'h':
              Serial.println("8 was pressed");
              break;
            case 'i':
              Serial.println("9 was pressed");
              break;
            case 'j':
              Serial.println("10 was pressed");
              break;
            case 'k':
              Serial.println("11 was pressed");
              break;
            case 'l':
              Serial.println("12 was pressed");
              break;
            case 'm':
              Serial.println("13 was pressed");
              break;
            case 'n':
              Serial.println("14 was pressed");
              break;
            case 'o':
              Serial.println("15 was pressed");
              break;
            case 'p':
              Serial.println("16 was pressed");
              break;
             default:
              Serial.println(key);
            }
        }
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
  else {
    // Imprimir al LCD
  }
  // input = "";
}

void returnSensor(String sensor){
  Serial.println("The_received_sensor_was:_" + sensor);
  delay(5000);
}

