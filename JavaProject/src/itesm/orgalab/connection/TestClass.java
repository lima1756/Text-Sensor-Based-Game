package itesm.orgalab.connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.util.Enumeration;
import itesm.orgalab.connection.Arduino;
import java.io.IOException;

public class TestClass
{
    public static BufferedReader input;
    public static OutputStream output;

    public static synchronized void writeData(String data) {
        System.out.println("Sent: " + data);
        try {
            output.write(data.getBytes());
        } catch (Exception e) {
            System.out.println("could not write to port");
        }
    }

    public static void main(String[] ag)
    {
        Arduino arduino = new Arduino();
        try
        {
            String text="";
            arduino.initialize();

            Thread.sleep(4000);
/*
            input = arduino.getInput();

            output = arduino.getOutput();
            output.flush();
            output.write("1".getBytes());

*/


            arduino.close();

        }
        catch(Exception e){
            System.out.println(e);
            arduino.close();
        }

    }
}
