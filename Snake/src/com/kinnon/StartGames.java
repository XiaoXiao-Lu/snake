package com.kinnon;

import javax.swing.*;

/**
 * @author Kinnon
 * @create 2021-10-23 11:00
 */
public class StartGames {
    public static void main(String[] args) {
        JFrame jf = new JFrame("贪吃蛇");
        jf.setBounds(10,10,920,750);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jf.add(new GamePanel());
        jf.setVisible(true);
    }
}
