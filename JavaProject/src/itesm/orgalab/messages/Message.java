package itesm.orgalab.messages;

import arduino.Arduino;

import java.util.HashMap;

/**
 *
 */
public abstract class Message {
    /**
     *
     */
    private int sensor;
    private long id;
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
    public final static String[] SENSORS = {"BTN2", "BTN16", "SWITCH", "LIGHT", "RANGE", "SOUND", "RGB"};

    private static Arduino arduino;
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

    public long getId() {
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

    public int getSensor()
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

    public void sendMessage(){
        double answer;
        arduino.openConnection();
        arduino.serialWrite(Integer.toString(Integer.toString(this.sensor).charAt(0)));
        answer = Double.parseDouble(arduino.serialRead());
        arduino.closeConnection();
        int nextId = this.nextMessage(answer);
        // ------------- IMPORTANT --------------------
        // Read the JSON Files in search of the object with the corresponding ID
        // Create the new object and execute what is needed of it
    }

    public abstract int nextMessage(double answer);

    public void saveMessage(HashMap<Long, Message> map){
        map.put(this.id, this);
    }
}
