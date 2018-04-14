#include <Key.h>
#include <Keypad.h>


String input;
String rgb[3];
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
  pinMode(BLEFT, INPUT);
  pinMode(BRIGHT, INPUT);
  rgb[0]="";
  rgb[1]="";
  rgb[2]="";
}

void loop() {
  delay(20);
  while (Serial.available()) {
     input = input + char(Serial.read());
     delay(1);
  }
  
  if(input=="1"){
//    returnSensor("btn2");
      while (true) {
        if (digitalRead(BLEFT) == HIGH) {
          Serial.println("0");
          break;
        } else if (digitalRead(BRIGHT) == HIGH) {
          Serial.println("1");
          break;
        }
        delay(1);
      }
  }
  else if(input=="2") {
//    returnSensor("keypad");
      char key = kpd.getKey();
        if(key) {  // Check for a valid key.
          switch (key) {
            case 'a':
              Serial.println("0");
              break;
            case 'b':
              Serial.println("1");
              break;
            case 'c':
              Serial.println("2");
              break;
            case 'd':
              Serial.println("3");
              break;
            case 'e':
              Serial.println("4");
              break;
            case 'f':
              Serial.println("5");
              break;
            case 'g':
              Serial.println("6");
              break;
            case 'h':
              Serial.println("7");
              break;
            case 'i':
              Serial.println("8");
              break;
            case 'j':
              Serial.println("9");
              break;
            case 'k':
              Serial.println("10");
              break;
            case 'l':
              Serial.println("11");
              break;
            case 'm':
              Serial.println("12");
              break;
            case 'n':
              Serial.println("13");
              break;
            case 'o':
              Serial.println("14");
              break;
            case 'p':
              Serial.println("15");
              break;
             default:
              Serial.println(key);
            }
        }
  }
  else if(input=="3"){
    Serial.println(input);
//      returnSensor("SWITCH");
      Serial.println("0");
  }
  else if(input=="4"){
//      returnSensor("LIGHT");
      Serial.println("0");
  }
  else if(input=="5"){
//    returnSensor("RANGE");
      Serial.println("25");
  }
  else if(input=="6"){
//      returnSensor("SOUND");
      Serial.println("1");
  }
  else if(input=="7"){
//      returnSensor("RGB");
    delay(1000);
    String rgbString = "";
    while (Serial.available() || rgbString=="") {
        rgbString = rgbString + char(Serial.read()); 
        delay(1);
      }
      for(int i = 0, color = 0; i< rgbString.length(); i++){
        if(rgbString[i]==','){
          color++;
          continue;
        }
        rgb[color]=rgb[color]+rgbString[i];
      }
      int red = rgb[0].toInt();
      int green = rgb[1].toInt();
      int blue = rgb[2].toInt();
      // TODO setear los valores analogicos para el led rgb
      Serial.println("0");
      rgbString=="";
      rgb[0] = "";
      rgb[1] = "";
      rgb[2] = "";
  }
  else if(input!="") {
//    Serial.println(input);
  }
  input = "";
}

//void returnSensor(String sensor){
//  Serial.println("The_received_sensor_was:_" + sensor);
//  delay(5000);
//}
