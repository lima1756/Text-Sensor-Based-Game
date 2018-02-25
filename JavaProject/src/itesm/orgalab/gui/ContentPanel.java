package itesm.orgalab.gui;

import itesm.orgalab.messages.Message;

import javax.swing.*;

public class ContentPanel extends JPanel {

    private GroupLayout generalLayout;
    private JTextField textContentField,
                       titleField;
    private JLabel textContentLabel,
                   titleLabel,
                   sensorLabel;
    private JComboBox sensorComboBox;

    public ContentPanel(){


        textContentField = new JTextField();
        titleField = new JTextField();
        textContentLabel = new JLabel("Message Content: ");
        titleLabel = new JLabel("Message Title: ");
        sensorLabel = new JLabel("Sensor: ");
        sensorComboBox = new JComboBox(Message.SENSORS);



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
        );

    }

    public void update(Message message)
    {
        
    }
}

