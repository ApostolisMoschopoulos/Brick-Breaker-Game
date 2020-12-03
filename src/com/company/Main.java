package com.company;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\User\\Downloads\\brickBreaker.png");
        Gameplay gameplay = new Gameplay();
        /*Defining our Frame and it's characteristics */
        JFrame Frame = new JFrame();
        Frame.setBounds(10,10,700,600);
        Frame.setTitle("Break all the bricks!");
        Frame.setResizable(true);
        Frame.setLocationRelativeTo(null);
        Frame.setVisible(true);
        Frame.setIconImage(icon);
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Frame.add(gameplay);

    }
}
