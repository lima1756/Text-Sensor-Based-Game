package itesm.orgalab.gui;

import itesm.orgalab.connection.SerialPortCom;
import itesm.orgalab.messages.*;

import java.util.HashMap;

public class Main {

    public static HashMap<Integer, Message> map;

    public static void main(String[] args) {
        Main.map = new HashMap<>();


//        Integer[] color = {100, 100, 100};
//        Message rgb = new RGBMessage("Test1", "Hellooooo1", Message.RGB, color, new int[] {2});
//        rgb.saveMessage(map);
//
//        RangeMessage om = new RangeMessage("Test0", "Hellooooo0", Message.RANGE, 3, 1, 3);
//        om.setNextMessages(new int[]{1, 2, 3});
//        om.saveMessage(map);
//
//
//        Message asdf = new OptionMessage("Test2", "Hellooooo2", Message.BTN2, new int[]{1, 2});
//        asdf.saveMessage(map);

        MainFrame mf = new MainFrame();








    }
}
