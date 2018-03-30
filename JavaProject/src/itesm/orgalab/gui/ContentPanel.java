package itesm.orgalab.gui;

import itesm.orgalab.messages.Message;
import itesm.orgalab.messages.RGBMessage;
import itesm.orgalab.messages.RangeMessage;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.acl.Group;

public class ContentPanel extends JPanel implements ActionListener{

    private Message selectedMessage;
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
                   blueLabel,
                   pathsLabel;

    private JTable pathsTable;
    private DefaultTableModel dtm;
    private JComboBox sensorComboBox;
    private JScrollPane pathsPane;
    private JPanel RGBPane, rangePane, optionPane;


    private final static String[] comboBoxOptions = {"BTN2", "BTN16", "SWITCH", "LIGHT", "SOUND"};

    public ContentPanel(){

        this.fieldInitialization();
        this.setLayout();


    }

    public void update(Message message)
    {
        this.selectedMessage = message;
        setMessageFields(this.selectedMessage.getTitle(), this.selectedMessage.getText(), this.selectedMessage.getSensor());
        this.setRangeFields(0,0,0);
        int rgb[] = {0,0,0};
        this.setColorFields(rgb);
        switch(this.selectedMessage.getSensor()){
            case Message.RANGE:
                RGBPane.setVisible(false);
                rangePane.setVisible(true);
                optionPane.setVisible(false);
                RangeMessage rm = (RangeMessage) this.selectedMessage;
                setRangeFields(rm.getNoRanges(), rm.getMin(), rm.getMax());
                break;
            case Message.RGB:
                RGBPane.setVisible(true);
                rangePane.setVisible(false);
                optionPane.setVisible(false);
                RGBMessage rgbm = (RGBMessage) this.selectedMessage;
                setColorFields(rgbm.getColor());
                break;
            default:
                RGBPane.setVisible(false);
                rangePane.setVisible(false);
                optionPane.setVisible(true);
                break;
        }
        loadPaths(this.selectedMessage);
    }

    private void setLayout(){
        generalLayout = new GroupLayout(this);
        this.setLayout(generalLayout);
        generalLayout.setAutoCreateGaps(true);
        generalLayout.setAutoCreateContainerGaps(true);

        GroupLayout RGBLayout = new GroupLayout(RGBPane);
        RGBPane.setLayout(RGBLayout);
        RGBLayout.setHorizontalGroup(
                RGBLayout.createSequentialGroup()
                    .addComponent(redLabel)
                    .addComponent(redField)
                    .addComponent(greenLabel)
                    .addComponent(greenField)
                    .addComponent(blueLabel)
                    .addComponent(blueField)
        );
        RGBLayout.setVerticalGroup(
                RGBLayout.createParallelGroup()
                        .addComponent(redLabel)
                        .addComponent(redField)
                        .addComponent(greenLabel)
                        .addComponent(greenField)
                        .addComponent(blueLabel)
                        .addComponent(blueField)
        );

        GroupLayout rangeLayout = new GroupLayout(rangePane);
        rangePane.setLayout(rangeLayout);
        rangeLayout.setHorizontalGroup(
                rangeLayout.createSequentialGroup()
                        .addComponent(noRangesLabel)
                        .addComponent(noRangesField)
                        .addComponent(minRangeLabel)
                        .addComponent(minRangeField)
                        .addComponent(maxRangeLabel)
                        .addComponent(maxRangeField)
        );
        rangeLayout.setVerticalGroup(
                rangeLayout.createParallelGroup()
                        .addComponent(noRangesLabel)
                        .addComponent(noRangesField)
                        .addComponent(minRangeLabel)
                        .addComponent(minRangeField)
                        .addComponent(maxRangeLabel)
                        .addComponent(maxRangeField)
        );

        GroupLayout optionLayout = new GroupLayout(optionPane);
        optionPane.setLayout(optionLayout);
        optionLayout.setHorizontalGroup(
                optionLayout.createSequentialGroup()
                        .addComponent(sensorLabel)
                        .addComponent(sensorComboBox)
        );
        optionLayout.setVerticalGroup(
                optionLayout.createSequentialGroup()
                    .addGroup(optionLayout.createParallelGroup()
                        .addComponent(sensorLabel)
                        .addComponent(sensorComboBox)
                    )

        );

        generalLayout.setHorizontalGroup(
        generalLayout.createSequentialGroup()
                .addGroup(generalLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(titleLabel)
                        .addComponent(titleField)
                        .addComponent(textContentLabel)
                        .addComponent(textContentField)
                        .addComponent(optionPane)
                        .addComponent(rangePane)
                        .addComponent(RGBPane)
                        .addComponent(pathsLabel)
                        .addComponent(pathsPane)
                )
        );

        generalLayout.setVerticalGroup(
                generalLayout.createSequentialGroup()
                        .addComponent(titleLabel)
                        .addComponent(titleField)
                        .addComponent(textContentLabel)
                        .addComponent(textContentField)
                        .addComponent(optionPane)
                        .addComponent(rangePane)
                        .addComponent(RGBPane)
                        .addComponent(pathsLabel)
                        .addComponent(pathsPane)
        );
    }

    private void fieldInitialization(){
        textContentField = new JTextField();
        titleField = new JTextField();
        textContentLabel = new JLabel("Message Content: ");
        titleLabel = new JLabel("Message Title: ");

        optionPane = new JPanel();
        sensorLabel = new JLabel("Sensor: ");
        sensorComboBox = new JComboBox(ContentPanel.comboBoxOptions);
        sensorComboBox.addActionListener(this);

        rangePane = new JPanel();
        noRangesLabel = new JLabel("No. Ranges: ");
        noRangesField = new JTextField();
        noRangesField.addActionListener(this);
        minRangeLabel = new JLabel("Min value: ");
        minRangeField = new JTextField();
        maxRangeLabel = new JLabel("Max value: ");
        maxRangeField = new JTextField();

        RGBPane = new JPanel();
        redLabel = new JLabel("Red: ");
        redField = new JTextField();
        greenLabel = new JLabel("Green: ");
        greenField = new JTextField();
        blueLabel = new JLabel("Blue: ");
        blueField = new JTextField();

        pathsLabel = new JLabel("Paths: ");
        dtm = new DefaultTableModel();
        this.pathsTable = new JTable(dtm);
        this.pathsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        pathsPane = new JScrollPane(pathsTable);
        dtm.addColumn("Path");
        dtm.addColumn("Message");

    }

    private void setMessageFields(String title, String content, int sensor){
        titleField.setText(title);
        textContentField.setText(content);
        switch (sensor){
            case Message.BTN2:
                sensorComboBox.setSelectedIndex(0);
                break;
            case Message.BTN16:
                sensorComboBox.setSelectedIndex(1);
                break;
            case Message.SWITCH:
                sensorComboBox.setSelectedIndex(2);
                break;
            case Message.LIGHT:
                sensorComboBox.setSelectedIndex(3);
                break;
            case Message.SOUND:
                sensorComboBox.setSelectedIndex(4);
                break;
        }
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

    private void loadPaths(Message message){
        while(dtm.getRowCount()>0)
            dtm.removeRow(0);
        switch(message.getSensor()){
            case Message.RANGE:
                RangeMessage rangeMessage = (RangeMessage) message;
                fillTable(rangeMessage.getNoRanges(), rangeMessage.getNextMessages());
                break;
            case Message.RGB:
                fillTable(1, message.getNextMessages());
                break;
            case Message.BTN2:
            case Message.LIGHT:
            case Message.SOUND:
            case Message.SWITCH:
                fillTable(2, message.getNextMessages());
                break;
            case Message.BTN16:
                fillTable(16, message.getNextMessages());
                break;
        }

    }

    private void fillTable(int max, int[] values){
        for(int i = 0; i < values.length; i++)
        {
            dtm.addRow(new Object[] {i+1,values[i]});
        }
    }

    private void updateTable(int rows){
        if(dtm.getRowCount()>rows) {
            while (dtm.getRowCount() > rows)
                dtm.removeRow(dtm.getRowCount() - 1);
        }
        else
        {
            int startValue = dtm.getRowCount();
            while(dtm.getRowCount() < rows)
            {
                dtm.addRow(new Object[] {++startValue, 1});
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == sensorComboBox)
        {
            switch(sensorComboBox.getSelectedIndex())
            {
                case 0:
                case 2:
                case 3:
                case 4:
                    updateTable(2);
                    break;
                case 1:
                    updateTable(16);
                    break;
            }
        }
        else if(e.getSource() == noRangesField)
        {
            try {
                int rows = Integer.parseInt(noRangesField.getText());
                updateTable(rows);
            }
            catch(Exception ex){}
        }
    }

    public Message getSelectedMessage()
    {
        return selectedMessage;
    }

    public void saveMessage()
    {
        this.selectedMessage.setTitle(titleField.getText());
        this.selectedMessage.setText(textContentField.getText());
        switch(this.selectedMessage.getSensor()){
            case Message.RANGE:
                RangeMessage msg = (RangeMessage) this.selectedMessage;
                msg.setMax(Integer.parseInt(maxRangeField.getText()));
                msg.setMin(Integer.parseInt(minRangeField.getText()));
                msg.setNoRanges(Integer.parseInt(noRangesField.getText()));
                this.selectedMessage = msg;
                break;
            case Message.RGB:
                RGBMessage rgbMsg = (RGBMessage) this.selectedMessage;
                rgbMsg.setColor(Integer.parseInt(redField.getText()),Integer.parseInt(greenField.getText()),Integer.parseInt(blueField.getText()));
                this.selectedMessage = rgbMsg;
                break;
            default:
                switch(sensorComboBox.getSelectedIndex())
                {
                    case 0:
                        this.selectedMessage.setSensor(Message.BTN2);
                        break;
                    case 1:
                        this.selectedMessage.setSensor(Message.BTN16);
                        break;
                    case 2:
                        this.selectedMessage.setSensor(Message.SWITCH);
                        break;
                    case 3:
                        this.selectedMessage.setSensor(Message.LIGHT);
                        break;
                    case 4:
                        this.selectedMessage.setSensor(Message.SOUND);
                        break;
                }
        }
        int[] paths = new int[dtm.getRowCount()];
        for(int i = 0; i < dtm.getRowCount(); i++)
        {
            paths[i] = Integer.parseInt(dtm.getValueAt(i,1).toString());
        }
        selectedMessage.setNextMessages(paths);
    }
}

