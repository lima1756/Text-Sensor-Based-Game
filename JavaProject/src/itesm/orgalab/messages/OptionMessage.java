package itesm.orgalab.messages;

public class OptionMessage extends Message {

    public OptionMessage(int id, String title, String text, int sensor){
        super(id,title,text,sensor, new int[1]);
    }

    public OptionMessage(int id, String title, String text, int sensor, int[] nextMessages){
        super(id,title,text,sensor, nextMessages);
    }

    @Override
    public int nextMessage(double answer) {
        return getNextMessages()[(int)answer];
    }
}
