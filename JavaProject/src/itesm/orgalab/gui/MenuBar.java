package itesm.orgalab.gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import itesm.orgalab.messages.Message;
import itesm.orgalab.messages.OptionMessage;
import itesm.orgalab.messages.RGBMessage;
import itesm.orgalab.messages.RangeMessage;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MenuBar extends JMenuBar implements ActionListener{

    private final JMenu[] barItems;
    private final JMenuItem[] fileItems,
                              messageItems;
    private final JFileChooser fileChooser;

    public MenuBar(){
        fileChooser = new JFileChooser();
        FileNameExtensionFilter jsonFilter = new FileNameExtensionFilter("json files (*.json)", "json");
        fileChooser.addChoosableFileFilter(jsonFilter);

        fileItems = new JMenuItem[3];
        fileItems[0] = new JMenuItem("New Game");
        fileItems[1] = new JMenuItem("Save Game");
        fileItems[2] = new JMenuItem("Load Game");

        messageItems = new JMenuItem[4];
        messageItems[0] = new JMenuItem("New Range Message");
        messageItems[1] = new JMenuItem("New Option Message");
        messageItems[2] = new JMenuItem("New RGB Message");
        messageItems[3] = new JMenuItem("Remove selected message");

        barItems = new JMenu[3];
        barItems[0] = new JMenu("File");
        for(JMenuItem item:fileItems) {
            item.addActionListener(this);
            barItems[0].add(item);
        }

        barItems[1] = new JMenu("Messages");
        for(JMenuItem item:messageItems) {
            item.addActionListener(this);
            barItems[1].add(item);
        }

        barItems[2] = new JMenu("Play game");

        //fileChooser = new JFileChooser();
        for(JMenu item: barItems)
            this.add(item);
    }

    private static int saveJSON(String path, HashMap<Long, Message> map){
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

    private static HashMap<Long, Message> loadJSON(String path){
        HashMap<Long, Message> map = new HashMap<>();
        try {
            jsonToMessages(map, path);
        }
        catch(IOException ex){
            System.out.println(ex);
        }
        return map;
    }

    private static void jsonToMessages(HashMap<Long, Message> map, String path) throws IOException {
        JsonReader jsonReader = null;
        try {
            jsonReader = new JsonReader(new FileReader(path));
        }
        catch (FileNotFoundException ex){
            System.out.println(ex);
        }
        jsonReader.beginObject();
        while(jsonReader.hasNext()){
            long id = Integer.parseInt(jsonReader.nextName());
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
                toSave = new RangeMessage(title, text, sensor, convertIntegers(nextMessages), noRanges, min, max);
            }
            else if(rgb != null){
                toSave = new RGBMessage(title, text, sensor, rgb);
            }
            else{
                toSave = new OptionMessage(title, text, sensor, convertIntegers(nextMessages));
            }
            map.put(id,toSave);
            jsonReader.endObject();
        }
        jsonReader.close();
    }

    private static int[] convertIntegers(ArrayList<Integer> integers){
        int[] ret = new int[integers.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = integers.get(i).intValue();
        }
        return ret;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) e.getSource();
        if(source ==  fileItems[0]){
            Main.map = new HashMap<>();
        }
        else if(source ==  fileItems[1]){
            int ok = fileChooser.showSaveDialog(new JPanel());
            File file = fileChooser.getSelectedFile();
            if (ok == JFileChooser.APPROVE_OPTION) {
                if (!FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("json")) {
                    file = new File(file.getParentFile(), FilenameUtils.getBaseName(file.getName())+".json");
                }
                if(saveJSON(file.getPath(), Main.map)!=1){
                    JOptionPane.showMessageDialog(null,"Error al guardar, porfavor vuelva a intentar.");
                }
            }
        }
        else if(source ==  fileItems[2]){
            int ok = fileChooser.showOpenDialog(new JPanel());
            try {
                File file = fileChooser.getSelectedFile();
                if (ok == JFileChooser.APPROVE_OPTION) {
                    Main.map = loadJSON(file.getPath());
                }
            }
            catch(NullPointerException ex)
            {
                JOptionPane.showMessageDialog(null,"Archivo no encontrado, porfavor vuelva a intentarlo.");
            }
        }
        else if(source == messageItems[0])
        {
            System.out.println("New range message");
        }
        else if(source == messageItems[1])
        {
            System.out.println("New option message");
        }
        else if(source == messageItems[2])
        {
            System.out.println("New RGB message");
        }
        else if(source == messageItems[3])
        {
            System.out.println("Remove message");
        }
    }
}
