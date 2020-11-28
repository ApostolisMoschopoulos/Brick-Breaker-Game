package com.company;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

       // ImageIcon image = new  ImageIcon('C:\Users\User\Downloads\brickbreaer.png');   // doesn't work somewhy!

        Gameplay gameplay = new Gameplay();
        /*Defining our Frame and it's characteristics */
        JFrame Frame = new JFrame();
        Frame.setBounds(10,10,700,600);
        Frame.setTitle("Break all the bricks!");
        Frame.setResizable(true);
        Frame.setLocationRelativeTo(null);
        Frame.setVisible(true);
       // Frame.setIconImage(image);
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Frame.add(gameplay);

    }
}
