package itesm.orgalab.gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import itesm.orgalab.messages.*;
import org.w3c.dom.ranges.Range;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static HashMap<Integer, Message> map;

    public static void main(String[] args){
        Main.map = new HashMap<>();



        int[] color= {100,100,100};
        Message rgb = new RGBMessage("Test1","Hellooooo1", Message.RGB, color);
        rgb.saveMessage(map);

        RangeMessage om = new RangeMessage("Test0","Hellooooo0", Message.RANGE, 5,1,2);
        om.setNextMessages(new int[]{ 100,200,300,400,500});
        om.saveMessage(map);


        Message asdf = new OptionMessage("Test2","Hellooooo2", Message.BTN2, new int[]{40000,50000});
        asdf.saveMessage(map);

        new MainFrame();


    }

}
