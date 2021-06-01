package main.panel;

import java.awt.event.*;
import javax.swing.*;
import main.Content;
import main.Main;
import main.base.Utils;

public class Start extends Base {
    public JButton single, multiple;
    private int width, height, gap; // setting for button

    public Start(Main frame) {
        super(frame);

        // basic setting
        width = 500;
        height = 50;
        gap = 50;
        backgroundImage = Utils.getImage(getBackgroundImage("background/start"));

        // setting 1 player button
        single = new JButton("1 Player");
        single.setBounds(Content.FRAME_WIDTH / 2 - width / 2, Content.FRAME_HEIGHT / 2 - height - gap, width, height);
        single.setOpaque(true);
        single.setBorder(null);
        single.setContentAreaFilled(false);

        // setting 2 player button
        multiple = new JButton("2 Player");
        multiple.setBounds(Content.FRAME_WIDTH / 2 - width / 2, Content.FRAME_HEIGHT / 2 + gap, width, height);
        multiple.setOpaque(true);
        multiple.setBorder(null);
        multiple.setContentAreaFilled(false);

        // add action Listener
        startListener listener = new startListener();
        single.addActionListener(listener);
        multiple.addActionListener(listener);

        // add component
        add(single);
        add(multiple);
    }

    public class startListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == single) {
                // press start button, change to game panel
                switchPanel(new Character(frame, "single"));
            } else if (e.getSource() == multiple) {
                switchPanel(new Character(frame, "multiple"));
            }
        }
    }
}
