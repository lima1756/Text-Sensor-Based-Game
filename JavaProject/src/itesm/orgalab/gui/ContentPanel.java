package itesm.orgalab.gui;

import itesm.orgalab.messages.Message;
import itesm.orgalab.messages.RGBMessage;
import itesm.orgalab.messages.RangeMessage;

import javax.swing.*;

public class ContentPanel extends JPanel {

    private GroupLayout generalLayout;
    private JTextField textContentField,
                       titleField,
                       noRangesField,
                       minRangeField,
                       maxRangeField,
                       redField,
                       greenField,
                       blueField;

    private JLabel textContentLabel,
                   titleLabel,
                   sensorLabel,
                   noRangesLabel,
                   minRangeLabel,
                   maxRangeLabel,
                   redLabel,
                   greenLabel,
                   blueLabel;

    private JComboBox sensorComboBox;

    public ContentPanel(){

        this.fieldInitialization();
        this.setLayout();


    }

    public void update(Message message)
    {
        setMessageFields(message.getTitle(), message.getText(), message.getSensor());
        this.setRangeFields(0,0,0);
        int rgb[] = {0,0,0};
        this.setColorFields(rgb);
        switch(message.getSensor()){
            case Message.RANGE:
                switchColorComponents(false);
                switchRangeComponents(true);
                RangeMessage rm = (RangeMessage) message;
                setRangeFields(rm.getNoRanges(), rm.getMin(), rm.getMax());
                break;
            case Message.RGB:
                switchColorComponents(true);
                switchRangeComponents(false);
                RGBMessage rgbm = (RGBMessage) message;
                setColorFields(rgbm.getColor());
                break;
            default:
                switchColorComponents(false);
                switchRangeComponents(false);
                break;
        }

    }

    private void switchRangeComponents(boolean value){
        noRangesLabel.setVisible(value);
        noRangesField.setVisible(value);
        minRangeLabel.setVisible(value);
        minRangeField.setVisible(value);
        maxRangeLabel.setVisible(value);
        maxRangeField.setVisible(value);
    }
    private void switchColorComponents(boolean value){
        redLabel.setVisible(value);
        redField.setVisible(value);
        greenLabel.setVisible(value);
        greenField.setVisible(value);
        blueLabel.setVisible(value);
        blueField.setVisible(value);
    }
    private void setLayout(){
        generalLayout = new GroupLayout(this);
        this.setLayout(generalLayout);
        generalLayout.setAutoCreateGaps(true);
        generalLayout.setAutoCreateContainerGaps(true);
        generalLayout.setHorizontalGroup(
                generalLayout.createSequentialGroup()
                        .addGroup(generalLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(titleLabel)
                                .addComponent(titleField)
                                .addComponent(textContentLabel)
                                .addComponent(textContentField)
                                .addComponent(sensorLabel)
                                .addComponent(sensorComboBox)
                                .addGroup(generalLayout.createSequentialGroup()
                                        .addComponent(noRangesLabel)
                                        .addComponent(noRangesField)
                                        .addComponent(minRangeLabel)
                                        .addComponent(minRangeField)
                                        .addComponent(maxRangeLabel)
                                        .addComponent(maxRangeField)
                                )
                                .addGroup(generalLayout.createSequentialGroup()
                                        .addComponent(redLabel)
                                        .addComponent(redField)
                                        .addComponent(greenLabel)
                                        .addComponent(greenField)
                                        .addComponent(blueLabel)
                                        .addComponent(blueField)
                                )
                        )

        );
        generalLayout.setVerticalGroup(
                generalLayout.createSequentialGroup()
                        .addComponent(titleLabel)
                        .addComponent(titleField)
                        .addComponent(textContentLabel)
                        .addComponent(textContentField)
                        .addComponent(sensorLabel)
                        .addComponent(sensorComboBox)
                        .addGroup(generalLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(noRangesLabel)
                                .addComponent(noRangesField)
                                .addComponent(minRangeLabel)
                                .addComponent(minRangeField)
                                .addComponent(maxRangeLabel)
                                .addComponent(maxRangeField)
                        )
                        .addGroup(generalLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(redLabel)
                                .addComponent(redField)
                                .addComponent(greenLabel)
                                .addComponent(greenField)
                                .addComponent(blueLabel)
                                .addComponent(blueField)
                        )
        );
    }
    private void fieldInitialization(){
        textContentField = new JTextField();
        titleField = new JTextField();
        textContentLabel = new JLabel("Message Content: ");
        titleLabel = new JLabel("Message Title: ");
        sensorLabel = new JLabel("Sensor: ");
        sensorComboBox = new JComboBox(Message.SENSORS);

        noRangesLabel = new JLabel("No. Ranges: ");
        noRangesField = new JTextField();
        minRangeLabel = new JLabel("Min value: ");
        minRangeField = new JTextField();
        maxRangeLabel = new JLabel("Max value: ");
        maxRangeField = new JTextField();

        redLabel = new JLabel("Red: ");
        redField = new JTextField();
        greenLabel = new JLabel("Green: ");
        greenField = new JTextField();
        blueLabel = new JLabel("Blue: ");
        blueField = new JTextField();
    }
    private void setMessageFields(String title, String content, int sensor){
        titleField.setText(title);
        textContentField.setText(content);
        sensorComboBox.setSelectedIndex(sensor-1);
    }
    private void setRangeFields(Integer noRanges, Integer min, Integer max){
        noRangesField.setText(noRanges.toString());
        minRangeField.setText(min.toString());
        maxRangeField.setText(max.toString());
    }
    private void setColorFields(int[] rgb){
        redField.setText(Integer.toString(rgb[0]));
        greenField.setText(Integer.toString(rgb[1]));
        blueField.setText(Integer.toString(rgb[2]));
    }
}

