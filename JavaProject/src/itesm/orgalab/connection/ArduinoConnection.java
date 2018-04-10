package itesm.orgalab.connection;


import arduino.Arduino;

import java.util.Scanner;
// https://github.com/HirdayGupta/Java-Arduino-Communication-Library
public class ArduinoConnection  {
    public static void main(String[] args) {
        Arduino arduino = new Arduino("COM6", 9600); //enter the port name here, and ensure that Arduino is connected, otherwise exception will be thrown.
        arduino.openConnection();

        arduino.serialWrite("1");
        System.out.println(arduino.serialRead());

        arduino.closeConnection();
    }
}