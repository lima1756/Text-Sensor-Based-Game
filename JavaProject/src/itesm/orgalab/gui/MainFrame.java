package itesm.orgalab.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private JButton btn;
    private JTextArea textArea;

    public MainFrame(){
        super("Text-Sensor Based Game");

        setLayout(new BorderLayout());

        btn = new JButton("Holi");
        textArea  = new JTextArea();

        btn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.append("Holi\n");
            }
        });

        add(textArea, BorderLayout.CENTER);
        add(btn, BorderLayout.SOUTH);

        setSize(600,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
