package itesm.orgalab.messages;

public class RangeMessage extends Message {

    private int noRanges,
                min,
                max;

    public RangeMessage(int id, String title, String text, int sensor, int[] nextMessages){
        super(id, title, text, sensor, nextMessages);
    }

    public RangeMessage(int id, String title, String text, int sensor) {
        this(id, title, text, sensor, new int[1], 1, 0, 30);
    }

    public RangeMessage(int id, String title, String text, int sensor, int noRanges, int min, int max){
        this(id, title, text, sensor, new int[noRanges], noRanges, min, max);
    }

    public RangeMessage(int id, String title, String text, int sensor, int[] nextMessages, int noRanges, int min, int max){
        this(id, title, text, sensor, nextMessages);
        this.noRanges = noRanges;
        this.min = min;
        this.max = max;
    }

    @Override
    public int nextMessage(double answer) {
        int range = this.max-this.min;
        double spaces = (double) range / this.noRanges;
        for(int i = 0; i < this.noRanges; i++){
            if(answer >= spaces*i && answer < spaces*(i+1)){
                return this.getNextMessages()[i];
            }
        }
        return this.getNextMessages()[0];
    }

    public int getNoRanges() {
        return noRanges;
    }

    public void setNoRanges(int noRanges) {
        this.noRanges = noRanges;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
