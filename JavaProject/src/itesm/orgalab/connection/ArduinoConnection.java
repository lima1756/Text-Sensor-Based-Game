package itesm.orgalab.connection;


import arduino.Arduino;

import java.util.Scanner;
// https://github.com/HirdayGupta/Java-Arduino-Communication-Library
public class ArduinoConnection  {
    public static void main(String[] args) {
        Scanner ob = new Scanner(System.in);
        Arduino arduino = new Arduino("COM4", 9600); //enter the port name here, and ensure that Arduino is connected, otherwise exception will be thrown.
        arduino.openConnection();
        System.out.println("Enter whatever: ");
        Integer input = Integer.parseInt(ob.nextLine());
        while(input != 'n'){
            arduino.serialWrite(input.toString());
            System.out.println(arduino.serialRead());
            System.out.println(arduino.serialRead());
            input = Integer.parseInt(ob.nextLine());
        }
        ob.close();
        arduino.closeConnection();
    }
}