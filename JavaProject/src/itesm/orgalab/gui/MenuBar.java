package itesm.orgalab.gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import itesm.orgalab.connection.SerialPortCom;
import itesm.orgalab.messages.Message;
import itesm.orgalab.messages.OptionMessage;
import itesm.orgalab.messages.RGBMessage;
import itesm.orgalab.messages.RangeMessage;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
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
    private ComboBoxUpdatable comboBoxUpdate;
    private PanelDisableable panelDisableable;
    private MainFrame mf;
    private Thread game;
    public MenuBar(MainFrame mf, ComboBoxUpdatable comboBoxUpdate, PanelDisableable panelDisableable){
        fileChooser = new JFileChooser();
        FileNameExtensionFilter jsonFilter = new FileNameExtensionFilter("json files (*.json)", "json");
        fileChooser.addChoosableFileFilter(jsonFilter);

        this.mf = mf;

        fileItems = new JMenuItem[5];
        fileItems[0] = new JMenuItem("New Game");
        fileItems[1] = new JMenuItem("Save Game");
        fileItems[2] = new JMenuItem("Load Game");
        fileItems[3] = new JMenuItem("Play Game");
        fileItems[4] = new JMenuItem("Stop Game");

        messageItems = new JMenuItem[3];
        messageItems[0] = new JMenuItem("New Range Message");
        messageItems[1] = new JMenuItem("New Option Message");
        messageItems[2] = new JMenuItem("New RGB Message");

        barItems = new JMenu[2];
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

        //fileChooser = new JFileChooser();
        for(JMenu item: barItems)
            this.add(item);

        this.comboBoxUpdate = comboBoxUpdate;
        this.panelDisableable = panelDisableable;
        createGame();
    }

    private static int saveJSON(String path, HashMap<Integer, Message> map){
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

    private static HashMap<Integer, Message> loadJSON(String path){
        HashMap<Integer, Message> map = new HashMap<>();
        try {
            jsonToMessages(map, path);
        }
        catch(IOException ex){
            System.out.println(ex);
        }
        return map;
    }

    private static void jsonToMessages(HashMap<Integer, Message> map, String path) throws IOException {
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
            Integer[] rgb = null;
            ArrayList<Integer> nextMessages= new ArrayList<>();
            String text = null, title = null;
            int noRanges = -1, sensor = -1, min = -1, max = -1;
            jsonReader.beginObject();
            while(jsonReader.hasNext()) {
                String name = jsonReader.nextName();
                switch(name){
                    case "rgb":
                        jsonReader.beginArray();
                        rgb = new Integer[3];
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
                toSave = new RGBMessage(title, text, sensor, rgb, convertIntegers(nextMessages));
            }
            else{
                toSave = new OptionMessage(title, text, sensor, convertIntegers(nextMessages));
            }
            toSave.setId(id);
            Message.COUNTER = id;
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
            comboBoxUpdate.updateComboBox();
            panelDisableable.disablePanel(true);
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
                    panelDisableable.disablePanel(false);
                    Main.map = loadJSON(file.getPath());
                    comboBoxUpdate.updateComboBox();
                }
            }
            catch(NullPointerException ex)
            {
                JOptionPane.showMessageDialog(null,"Archivo no encontrado, porfavor vuelva a intentarlo.");
            }
        }
        else if(source == messageItems[0])
        {
            RangeMessage newMessage = new RangeMessage();
            Main.map.put(newMessage.getId(), newMessage);
            comboBoxUpdate.updateComboBox();

        }
        else if(source == messageItems[1])
        {
            OptionMessage newMessage = new OptionMessage();
            Main.map.put(newMessage.getId(), newMessage);
            comboBoxUpdate.updateComboBox();

        }
        else if(source == messageItems[2])
        {
            RGBMessage newMessage = new RGBMessage();
            Main.map.put(newMessage.getId(), newMessage);
            comboBoxUpdate.updateComboBox();
        }
        else if(source == fileItems[3]){
            this.game.start();
            Message msg = Main.map.get(1);
            msg.run();
        }
        else if(source == fileItems[4]){
            this.game.interrupt();
            this.game.stop();
        }
    }

    private void createGame(){
        this.game = new Thread(){
            public void run(){
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
                catch(Exception ex){
                    System.out.println(ex);
                }
            }
        };
    }
}
