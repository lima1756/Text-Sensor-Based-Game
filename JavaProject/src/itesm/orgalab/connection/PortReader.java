package itesm.orgalab.connection;

import itesm.orgalab.gui.Main;
import itesm.orgalab.messages.Message;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class PortReader implements SerialPortEventListener {
    public static SerialPort serialPort;
    private Integer nextId;
    private Message actualMessage;
    private String input, sensor;

    public PortReader(SerialPort serialPort) {
        PortReader.serialPort = serialPort;
        this.nextId = 1;
        sensor = null;
        if(nextId!=null) {
            actualMessage = Main.map.get(nextId);
            sensor = Integer.toString(actualMessage.getSensor());
        }
    }

    public synchronized void serialEvent(SerialPortEvent event) {
        if (event.isRXCHAR()) {
            try {
                Thread.sleep(1000);
                this.input = serialPort.readString();
                if (input != null){
                    System.out.print(input);
                    if (input.contains("\r\n")) {
                        String[] vars = this.input.split("\r\n");
                        String value = vars[0];
                        if (vars.length > 1)
                            this.input = vars[1];
                        else
                            this.input = "";
                        this.nextId = actualMessage.nextMessage(Double.parseDouble(value));
                        actualMessage = Main.map.get(this.nextId);
                        actualMessage.run();
                    }
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}