package itesm.orgalab.gui;

import arduino.Arduino;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import itesm.orgalab.connection.SerialPortCom;
import itesm.orgalab.messages.*;
import org.w3c.dom.ranges.Range;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static HashMap<Integer, Message> map;

    public static void main(String[] args) {
        Main.map = new HashMap<>();


        int[] color = {100, 100, 100};
        Message rgb = new RGBMessage("Test1", "Hellooooo1", Message.RGB, color);
        rgb.saveMessage(map);

        RangeMessage om = new RangeMessage("Test0", "Hellooooo0", Message.RANGE, 3, 1, 3);
        om.setNextMessages(new int[]{1, 2, 3});
        om.saveMessage(map);


        Message asdf = new OptionMessage("Test2", "Hellooooo2", Message.BTN2, new int[]{1, 2});
        asdf.saveMessage(map);

        MainFrame mf = new MainFrame();


        try {
            SerialPortCom serialPortCom = new SerialPortCom();
            serialPortCom.initialize("COM6");

            Thread.sleep(2000);

            while(mf.isVisible()){
                Thread.sleep(100);
            }

            serialPortCom.closeConnection();
            System.out.println("Serial communication has finished");

        }
        catch(Exception ex){}





    }
}
