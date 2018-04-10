package itesm.orgalab.connection;

import itesm.orgalab.gui.Main;
import itesm.orgalab.messages.Message;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class PortReader implements SerialPortEventListener {
    private SerialPort serialPort;
    private Integer nextId;
    private Message actualMessage;

    public PortReader(SerialPort serialPort) {
        this.serialPort = serialPort;
        this.nextId = 1;
    }
    public synchronized void serialEvent(SerialPortEvent event) {
//        try {
//            System.out.print(serialPort.readString());
//        } catch (SerialPortException e) {
//            e.printStackTrace();
//        }
        String sensor = null;
        if(nextId!=null) {
            actualMessage = Main.map.get(nextId);
            sensor = Integer.toString(actualMessage.getSensor());
        }
        if (event.isRXCHAR()) {

            try {
                Thread.sleep(1000);
                String input = serialPort.readString();
                if(input!= null)
                    System.out.print(input);
                if (!input.contains("\"") && !input.contains("\\")) {
                    //System.out.print(input);
                }
                if(input.contains("\n") && !input.contains("\"") && !input.contains("\\")) {
                    if (nextId == 2)
                        nextId = 3;
                    else if (nextId == 3)
                        nextId = null;
                    else
                        nextId = 2;
                    if(nextId!=null) {
                        actualMessage = Main.map.get(nextId);
                        sensor = Integer.toString(actualMessage.getSensor());
                    }
                    else
                        sensor = "0";
                }
                if(!input.contains("\\"))
                {
                    serialPort.writeString(sensor);
                }
            }catch (NullPointerException e){

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}