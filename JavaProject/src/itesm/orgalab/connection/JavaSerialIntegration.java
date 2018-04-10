package itesm.orgalab.connection;

import java.util.Scanner;

public class JavaSerialIntegration {
    public static void main(String[] args) throws Exception {
        SerialPortCom serialPortCom = new SerialPortCom();
        String[] ports = serialPortCom.getAvailableSerialPorts();
        System.out.println("Serial ports on the computer are:");
        for (int i = 0; i < ports.length; i++) {
            System.out.println((i + 1) + ". " + ports[i]);
        }

        System.out.println("Chose the serial port desired (1 , 2 , anything), and press enter: ");
        Scanner scanner = new Scanner(System.in);
        int selectedPort = scanner.nextInt();
        String port = ports[selectedPort - 1];

        System.out.println("You will use the port: " + port);
        serialPortCom.initialize(port);

        Thread.sleep(2000);


        serialPortCom.sendData("7");



        serialPortCom.sendData("5");

        serialPortCom.sendData("1");


        serialPortCom.closeConnection();
        System.out.println("Serial communication has finished");
    }
}