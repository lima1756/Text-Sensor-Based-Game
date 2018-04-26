#include <Keypad.h>
#include <NewPing.h>
#include <LiquidCrystal.h>

// Ultrasonido
#define TRIGGER_PIN 9
#define ECHO_PIN 8
#define MAX_DISTANCE 400
NewPing sonar(TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE);

// The display
const int rs = 23, en = 25, d4 = 27, d5 = 29, d6 = 31, d7 = 33;
LiquidCrystal lcd(rs, en, d4, d5, d6, d7);
String data;

String input;
String rgb[3];
const int BLEFT = 2;
const int BRIGHT = 3;
const int SOUND = 52;
const int REDPIN = 5;
const int GREENPIN = 6;
const int BLUEPIN = 7;

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
byte rowPins[ROWS] = { 36, 34, 32, 30 };
// Connect keypad COL0, COL1 and COL2 to these Arduino pins.
byte colPins[COLS] = { 28, 26, 24, 22 }; 

// Create the Keypad
Keypad kpd = Keypad( makeKeymap(keys), rowPins, colPins, ROWS, COLS );

void setup() {
  Serial.begin(9600);
  pinMode(BLEFT, INPUT);
  pinMode(BRIGHT, INPUT);
  pinMode(SOUND, INPUT);
  pinMode(REDPIN, OUTPUT);
  pinMode(GREENPIN, OUTPUT);
  pinMode(BLUEPIN, OUTPUT);
  lcd.begin(16, 2);
  // Print a message to the LCD.
  lcd.print("Hi, how are you?");
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
          updateLCD("Haz pulsado el", "boton izquierdo");
          break;
        } else if (digitalRead(BRIGHT) == HIGH) {
          Serial.println("1");
          updateLCD("Haz pulsado el", "boton derecho");
          break;
        }
        delay(1);
      }
  }
  else if(input=="2") {
//    returnSensor("keypad");
      boolean isKey = true;
      while(isKey) {

        char key = kpd.getKey();
      
        if(key) {  // Check for a valid key.
          switch (key) {
            case 'a':
              Serial.println("0");
              updateLCD("Haz pulsado el", "boton 0");
              isKey = false;
              break;
            case 'b':
              Serial.println("1");
              isKey = false;
              updateLCD("Haz pulsado el", "boton 1");
              break;
            case 'c':
              Serial.println("2");
              isKey = false;
              updateLCD("Haz pulsado el", "boton 2");
              break;
            case 'd':
              Serial.println("3");
              isKey = false;
              updateLCD("Haz pulsado el", "boton 3");
              break;
            case 'e':
              Serial.println("4");
              isKey = false;
              updateLCD("Haz pulsado el", "boton 4");
              break;
            case 'f':
              Serial.println("5");
              isKey = false;
              updateLCD("Haz pulsado el", "boton 5");
              break;
            case 'g':
              Serial.println("6");
              isKey = false;
              updateLCD("Haz pulsado el", "boton 6");
              break;
            case 'h':
              Serial.println("7");
              isKey = false;
              updateLCD("Haz pulsado el", "boton 7");
              break;
            case 'i':
              Serial.println("8");
              isKey = false;
              updateLCD("Haz pulsado el", "boton 8");
              break;
            case 'j':
              Serial.println("9");
              isKey = false;
              updateLCD("Haz pulsado el", "boton 9");
              break;
            case 'k':
              Serial.println("10");
              isKey = false;
              updateLCD("Haz pulsado el", "boton 10");
              break;
            case 'l':
              Serial.println("11");
              updateLCD("Haz pulsado el", "boton 11");
              isKey = false;
              break;
            case 'm':
              Serial.println("12");
              updateLCD("Haz pulsado el", "boton 12");
              isKey = false;
              break;
            case 'n':
              Serial.println("13");
              updateLCD("Haz pulsado el", "boton 13");
              isKey = false;
              break;
            case 'o':
              Serial.println("14");
              updateLCD("Haz pulsado el", "boton 14");
              isKey = false;
              break;
            case 'p':
              Serial.println("15");
              updateLCD("Haz pulsado el", "boton 15");
              isKey = false;
              break;
             default:
              Serial.println(key);
            }
        }
        
      }
  }
  else if(input=="3"){
//      returnSensor("SWITCH");
//      Si la entrada esta en alto return 1 sino pos nmo no
      delay(5000);
      if (digitalRead(46) == HIGH) {
        updateLCD("Haz activado el", "switch");
        Serial.println("1"); 
      }
       else {
          updateLCD("No activaste el", "switch");
          Serial.println("0");
       }
         
  }
  else if(input=="4"){
        // Fotoresistencia return in a certain range
//      returnSensor("LIGHT");
      delay(5000);
      int light = analogRead(0);
      if (light > 200) {
        updateLCD("Ha recibido luz", "");
        Serial.println("1");
      } else{
        updateLCD("No se ha detectado", "luz");
        Serial.println("0");
      }
  }
  else if(input=="5"){
      // Retornar el valor tal cual
//    returnSensor("RANGE");
      int uS = 0;
      while(uS==0)
        uS = sonar.ping_cm();
      //Serial.println(uS);
      String temporal = String(uS) + " cm";
      updateLCD("La distancia fue ", temporal);
      Serial.println(uS);
      
  }
  else if(input=="6"){
        // Returns one or 0
//      returnSensor("SOUND");
      boolean wasSound = false;

      for (int i = 0; i < 250; i++) {
        if (digitalRead(SOUND)==HIGH) {
          Serial.println("1");
          updateLCD("Si hubo sonido", "");
          wasSound = true;
          break; 
        }
        delay(20); 
      }
      if (!wasSound) {
        Serial.println("0");
        updateLCD("No hubo sonido", "");
      }
      
        
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

      analogWrite(REDPIN,red); // Se enciende color rojo
      analogWrite(GREENPIN,green); // Se enciende color rojo
      analogWrite(BLUEPIN,blue); // Se enciende color rojo

      String temporalColor = "RGB(" + String(red) + "," + String(green) + "," + String(blue) + ")";
      updateLCD("Codigo RGB:", "temporalColor");
      Serial.println("0");
      rgbString=="";
      rgb[0] = "";
      rgb[1] = "";
      rgb[2] = "";
  }
  input = "";
}

void updateLCD(String part1, String part2) {
  lcd.setCursor(0,0);
  lcd.print(part1);
  lcd.setCursor(0,1);
  lcd.print(part2);
}

//void returnSensor(String sensor){
//  Serial.println("The_received_sensor_was:_" + sensor);
//  delay(5000);
//}
