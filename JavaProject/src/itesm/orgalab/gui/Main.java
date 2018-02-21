package itesm.orgalab.gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import itesm.orgalab.messages.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static HashMap<Integer, Message> map;

    public static void main(String[] args){
        Main.map = new HashMap<>();



        OptionMessage om = new OptionMessage(0,"Test0","Hellooooo", Message.NONE);
        map.put(om.getId(),om);
        om.saveMessage(map);
        int[] color= {100,100,100};
        Message rgb = new RGBMessage(1,"Test1","Hellooooo", Message.RGB, color);
        map.put(rgb.getId(),rgb);
        rgb.saveMessage(map);

        new MainFrame();
//
//        MenuBar.saveJSON("C:\\Users\\luisi\\Desktop\\help.json", Main.map);
//        HashMap<Integer, Message> map2 = Main.loadJSON("json.json");
//        for(int i=0; i<map2.size();i++){
//            System.out.println(map2.get(i));
//        }

    }


}
