package itesm.orgalab.messages;


public class RGBMessage extends Message {
    private int[] rgb;

    public RGBMessage(String title, String text, int sensor) {
        super(title, text, sensor, new int[1]);
    }

    public RGBMessage(String title, String text, int sensor, int[] rgb) {
        this(title, text, sensor);
        this.rgb = rgb;
    }

    public RGBMessage(){
        this("New RGB message", "", Message.RGB, new int[]{0,0,0});
    }

    @Override
    public int nextMessage(double answer) {
        return 0;
    }

    @Override
    public void sendMessage(){
        // Sends the color rgb to the arduino
    }

    public void setColor(int red, int green, int blue){
        this.rgb[0] = red;
        this.rgb[1] = green;
        this.rgb[2] = blue;
    }

    public void setColor(int[] rgb){
        this.rgb = rgb;
    }

    public int[] getColor(){
        return rgb;
    }
}
