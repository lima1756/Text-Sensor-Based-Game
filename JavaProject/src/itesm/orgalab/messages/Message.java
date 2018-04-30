package itesm.orgalab.messages;

import itesm.orgalab.connection.PortReader;
import jssc.SerialPortException;

import javax.swing.*;
import java.util.HashMap;

/**
 *
 */
public abstract class Message {
    /**
     *
     */
    private int sensor;
    private int id;
    private String text,
                   title;
    private int[] nextMessages;
    public final static int BTN2 = 1,
                            BTN16 = 2,
                            SWITCH = 3,
                            LIGHT = 4,
                            RANGE = 5,
                            SOUND = 6,
                            RGB = 7,
                            NONE = 0;


    public static int COUNTER = 0;
    /**
     * @param title
     * @param text
     * @param sensor
     */
    public Message(String title, String text, int sensor){
        Message.COUNTER += 1;
        this.id = Message.COUNTER ;
        this.title = title;
        this.text = text;
        this.sensor= sensor;
    }

    public Message(String title, String text, int sensor, int[] nextMessages){
        this(title,text,sensor);
        this.nextMessages = nextMessages;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {return title;}

    public int[] getNextMessages() {
        return nextMessages;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNextMessages(int[] nextMessages) {
        this.nextMessages = nextMessages;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSensor()
    {
        return this.sensor;
    }

    public void setSensor(int sensor) {
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return this.id + " - " + this.title;
    }


    public abstract int nextMessage(double answer);

    public void run(){
        try {
            //JOptionPane.showMessageDialog(null,this.getText());
            JOptionPane.showMessageDialog(null, this.getText());


            PortReader.serialPort.writeString(this.getText());
            Thread.sleep(1000);
            PortReader.serialPort.writeString(this.getSensor().toString());
        } catch (SerialPortException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void saveMessage(HashMap<Integer, Message> map){
        map.put(this.id, this);
    }
}
