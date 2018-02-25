package itesm.orgalab.gui;

import itesm.orgalab.messages.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private final MenuBar menuBar;
    private final ContentPanel contentPanel;
    private final JComboBox comboBox;
    private JButton save;
    private JPanel southPanel;
    private JScrollPane scrollPane;

    public MainFrame(){
        super("Text-Sensor Based Game");

        setLayout(new BorderLayout());

        menuBar=new MenuBar();
        contentPanel = new ContentPanel();
        comboBox = new JComboBox();
        save = new JButton("Save");

        scrollPane = new JScrollPane(contentPanel);

        for (Message msg : Main.map.values()) {
            comboBox.addItem(msg);
        }

        comboBox.addActionListener(e -> {
            contentPanel.update((Message)((JComboBox)e.getSource()).getSelectedItem());
            String obj =  ((Message)((JComboBox)e.getSource()).getSelectedItem()).toString();
            System.out.println(obj);
        });

        southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.LINE_AXIS));
        southPanel.add(comboBox);
        southPanel.add(save);

        add(menuBar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        setSize(600,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
