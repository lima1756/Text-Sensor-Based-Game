package itesm.orgalab.gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import itesm.orgalab.messages.*;
import org.w3c.dom.ranges.Range;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static HashMap<Long, Message> map;

    public static void main(String[] args){
        Main.map = new HashMap<>();



        RangeMessage om = new RangeMessage("Test0","Hellooooo0", Message.RANGE, 1,1,2);
        om.saveMessage(map);
        int[] color= {100,100,100};
        Message rgb = new RGBMessage("Test1","Hellooooo1", Message.RGB, color);
        rgb.saveMessage(map);
        Message asdf = new OptionMessage("Test2","Hellooooo2", Message.BTN2, color);
        asdf.saveMessage(map);

        new MainFrame();
//
//        MenuBar.saveJSON("C:\\Users\\luisi\\Desktop\\help.json", Main.map);
//        HashMap<Integer, Message> map2 = Main.loadJSON("json.json");
//        for(int i=0; i<map2.size();i++){
//            System.out.println(map2.get(i));
//        }

    }

}
