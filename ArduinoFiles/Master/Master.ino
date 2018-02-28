#include <Wire.h> // I2C Library for communication

int x = 0;

void setup() {
  // I2C Bus
  Wire.begin(); 
}

void loop() {
  Wire.beginTransmission(9); // Transmit to device #1
  Wire.write(x);              // sends x 
  Wire.endTransmission();    // Stop the transmition with #1
 
  x++; // Increment x
  if (x > 5) x = 0; // `reset x once it gets 6
  
  delay(500);
}

