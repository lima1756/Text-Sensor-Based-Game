package itesm.orgalab.messages;

public class OptionMessage extends Message {

    public OptionMessage(String title, String text, int sensor, int[] nextMessages){
        super(title,text,sensor, nextMessages);
    }

    public OptionMessage(String title, String text, int sensor){
        this(title,text,sensor, new int[1]);
    }

    @Override
    public int nextMessage(double answer) {
        return getNextMessages()[(int)answer];
    }
}
