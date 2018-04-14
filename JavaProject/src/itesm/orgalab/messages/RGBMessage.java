package itesm.orgalab.messages;


import itesm.orgalab.connection.PortReader;
import jssc.SerialPortException;

public class RGBMessage extends Message {
    private Integer[] rgb;

    public RGBMessage(String title, String text, int sensor) {
        super(title, text, sensor, new int[1]);
    }

    public RGBMessage(String title, String text, int sensor, Integer[] rgb, int[] nextMessages) {
        this(title, text, sensor);
        this.rgb = rgb;
        this.setNextMessages(nextMessages);
    }

    public RGBMessage(){
        this("New RGB message", "", Message.RGB, new Integer[]{0,0,0}, new int[] {1});
    }

    @Override
    public int nextMessage(double answer) {
        return this.getNextMessages()[0];
    }


    public void setColor(int red, int green, int blue){
        this.rgb[0] = red;
        this.rgb[1] = green;
        this.rgb[2] = blue;
    }

    public void setColor(Integer[] rgb){
        this.rgb = rgb;
    }

    public Integer[] getColor(){
        return rgb;
    }

    @Override
    public void run(){
        super.run();
        try {
            Thread.sleep(1000);
            PortReader.serialPort.writeString(this.rgb[0]+","+this.rgb[1]+","+this.rgb[2]);
        } catch (SerialPortException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
