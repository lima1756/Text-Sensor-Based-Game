package itesm.orgalab.gui;

import itesm.orgalab.messages.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame{

    private final MenuBar menuBar;
    private final ContentPanel contentPanel;
    private final JComboBox comboBox;
    private JButton save,
                    remove;
    private JPanel southPanel;
    private JScrollPane scrollPane;


    public MainFrame(){
        super("Text-Sensor Based Game");

        setLayout(new BorderLayout());

        menuBar=new MenuBar(this, this::updateComboBox, new PanelDisableable() {
            @Override
            public void disablePanel(boolean disable) {
                MainFrame.this.disablePanel(disable);
            }

            @Override
            public boolean isDisable() {
                return MainFrame.this.isDisable();
            }
        });
        contentPanel = new ContentPanel();
        comboBox = new JComboBox();
        save = new JButton("Save Msg");
        save.addActionListener(e->{
            contentPanel.saveMessage();
            Message selected = contentPanel.getSelectedMessage();
            Main.map.put(selected.getId(),selected);
        });
        remove = new JButton("Remove Msg");
        remove.addActionListener(e->{
            Message selectedMsg = contentPanel.getSelectedMessage();
            Main.map.remove(selectedMsg.getId());
            updateComboBox();
        });

        scrollPane = new JScrollPane(contentPanel);

        comboBox.addActionListener(e -> {
            if((Message)((JComboBox)e.getSource()).getSelectedItem()!=null) {
                contentPanel.update((Message) ((JComboBox) e.getSource()).getSelectedItem());
                String obj = ((Message) ((JComboBox) e.getSource()).getSelectedItem()).toString();
            }
        });
        updateComboBox();

        southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.LINE_AXIS));
        southPanel.add(comboBox);
        southPanel.add(save);
        southPanel.add(remove);

        add(menuBar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        setSize(600,500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void updateComboBox(){
        comboBox.removeAllItems();
        for (Message msg : Main.map.values()) {
            comboBox.addItem(msg);
        }
        comboBox.setSelectedIndex(comboBox.getItemCount()-1);
    }

    public void disablePanel(boolean disable){
        southPanel.setVisible(!disable);
        scrollPane.setVisible(!disable);
    }

    public boolean isDisable()
    {
        return southPanel.isVisible() && scrollPane.isVisible();
    }

}
