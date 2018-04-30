package itesm.orgalab.messages;

public class RangeMessage extends Message {

    private int noRanges,
                min,
                max;
    public RangeMessage()
    {
        this("New Range Message", "", Message.RANGE, new int[] {1}, 1, 0, 30);
    }
    public RangeMessage(String title, String text, int sensor, int[] nextMessages){
        super(title, text, sensor, nextMessages);
    }

    public RangeMessage(String title, String text, int sensor) {
        this(title, text, sensor, new int[1], 1, 0, 30);
    }

    public RangeMessage(String title, String text, int sensor, int noRanges, int min, int max){
        this(title, text, sensor, new int[noRanges], noRanges, min, max);
    }

    public RangeMessage(String title, String text, int sensor, int[] nextMessages, int noRanges, int min, int max){
        this(title, text, sensor, nextMessages);
        this.noRanges = noRanges;
        this.min = min;
        this.max = max;
    }

    @Override
    public int nextMessage(double answer) {
        int range = this.max-this.min;
        answer = this.min + (answer * range / 9.0); // TODO el 30 va a ser la distancia real maxima del sensor
        double spaces = (double) range / this.noRanges;
        for(int i = 0; i < this.noRanges; i++){
            if(answer >= this.min+(spaces*i) && answer < this.min + (spaces*(i+1))){
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
