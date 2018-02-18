package itesm.orgalab.gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import itesm.orgalab.messages.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args){
        //new MainFrame();
        HashMap<Integer, Message> map = new HashMap<>();

        OptionMessage om = new OptionMessage(0,"Test0","Hellooooo", Message.NONE);
        map.put(om.getId(),om);
        om.saveMessage(map);
        int[] color= {100,100,100};
        Message rgb = new RGBMessage(1,"Test1","Hellooooo", Message.RGB, color);
        map.put(rgb.getId(),rgb);
        rgb.saveMessage(map);

        Main.saveJSON("json.json", map);
        HashMap<Integer, Message> map2 = Main.loadJSON("json.json");
        for(int i=0; i<map2.size();i++){
            System.out.println(map2.get(i));
        }

    }

    public static void jsonToMessages(HashMap<Integer, Message> map, String path) throws IOException{
        JsonReader jsonReader = null;
        try {
            jsonReader = new JsonReader(new FileReader(path));
        }
        catch (FileNotFoundException ex){
            System.out.println(ex);
        }
        jsonReader.beginObject();
        while(jsonReader.hasNext()){
            int id = Integer.parseInt(jsonReader.nextName());
            int[] rgb = null;
            ArrayList<Integer> nextMessages= new ArrayList<>();
            String text = null, title = null;
            int noRanges = -1, sensor = -1, min = -1, max = -1;
            jsonReader.beginObject();
            while(jsonReader.hasNext()) {
                String name = jsonReader.nextName();
                switch(name){
                    case "rgb":
                        jsonReader.beginArray();
                        rgb = new int[3];
                        for(int i = 0; jsonReader.hasNext(); i++){
                            rgb[i] = jsonReader.nextInt();
                        }
                        jsonReader.endArray();
                        break;
                    case "nextMessages":
                        jsonReader.beginArray();
                        nextMessages = new ArrayList<Integer>();
                        for(int i = 0; jsonReader.hasNext(); i++){
                            nextMessages.add(jsonReader.nextInt());
                        }
                        jsonReader.endArray();
                        break;
                    case "title":
                        title = jsonReader.nextString();
                        break;
                    case "text":
                        text = jsonReader.nextString();
                        break;
                    case "sensor":
                        sensor = jsonReader.nextInt();
                        break;
                    case "noRanges":
                        noRanges = jsonReader.nextInt();
                        break;
                    case "min":
                        min = jsonReader.nextInt();
                        break;
                    case "max":
                        max = jsonReader.nextInt();
                        break;
                    case "id":
                        jsonReader.nextInt();
                        break;
                    default:
                        break;
                }
            }
            Message toSave = null;
            if(noRanges != -1)
            {
                toSave = new RangeMessage(id, title, text, sensor, convertIntegers(nextMessages), noRanges, min, max);
            }
            else if(rgb != null){
                toSave = new RGBMessage(id, title, text, sensor, rgb);
            }
            else{
                toSave = new OptionMessage(id, title, text, sensor, convertIntegers(nextMessages));
            }
            map.put(id,toSave);
            jsonReader.endObject();
        }
    }

    public static int[] convertIntegers(ArrayList<Integer> integers){
        int[] ret = new int[integers.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = integers.get(i).intValue();
        }
        return ret;
    }

    public static int saveJSON(String path, HashMap<Integer, Message> map){
        FileOutputStream fos;
        GsonBuilder gBuilder = new GsonBuilder();
        Gson gson = gBuilder.create();
        byte[] json = gson.toJson(map).getBytes();
        try {
            fos = new FileOutputStream(path);
        } catch (FileNotFoundException ex){
            return -1;
        }
        try {
            fos.write(json);
            fos.close();
        } catch (IOException ex){
            return -1;
        }
        return 1;
    }

    public static HashMap<Integer, Message> loadJSON(String path){
        HashMap<Integer, Message> map = new HashMap<>();
        try {
            jsonToMessages(map, path);
        }
        catch(IOException ex){
            System.out.println(ex);
        }
        return map;
    }
}
