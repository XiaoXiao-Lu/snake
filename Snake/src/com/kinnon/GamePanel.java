package com.kinnon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**
 * @author Kinnon
 * @create 2021-10-23 11:23
 */
public class GamePanel extends JPanel implements KeyListener, ActionListener {
    int score;
    int length;//蛇的长度
    int[] snakeX = new int[600];//蛇的x坐标
    int[] snakeY = new int[500];//蛇的y坐标

    int foodX ;
    int foodY ;
    Random random = new Random();
    boolean isStart = false;
    boolean isFail;
    String die;//头部方向

    Timer timer = new Timer(100,this);

    public GamePanel() {

        init();
       this.setFocusable(true);
       this.addKeyListener(this);

       timer.start();

    }
//    //随机生成食物坐标
//    public void randomFood(){
//        foodX = new Random().nextInt(835) + 15;
//        foodY =  new Random().nextInt(600) + 75;
//
//    }

//    public void drawFood(){
//        randomFood();
//        Graphics g = this.getGraphics();
//        Data.food.paintIcon(this,g,foodX,foodY);
//        repaint();
//    }

    public void init(){
        score = 0;
        length = 3;
        snakeX[0] = 100; snakeY[0] = 100;
        snakeX[1] = 75; snakeY[1] = 100;
        snakeX[2] = 50; snakeY[2] = 100;
        die = "R";
        isFail = false;
        foodX = random.nextInt(34) *25 + 25;
        foodY = random.nextInt(24) *25+ 75;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.WHITE);
        Data.header.paintIcon(this,g,25,11);
        g.fillRect(25,75,850,600);
        g.setColor(Color.WHITE);
        g.setFont(new Font("微软雅黑",Font.BOLD,18));
        g.drawString("长度：" + length,750,30);
        g.drawString("分数：" + score,750,50);
        if ("R".equals(die)){
            Data.right.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if ("L".equals(die)) {
            Data.left.paintIcon(this, g, snakeX[0], snakeY[0]);
        }else if ("D".equals(die)){
            Data.down.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if ("U".equals(die)){
            Data.up.paintIcon(this,g,snakeX[0],snakeY[0]);
        }
        //生成蛇
        for (int i = 1; i < length; i++) {

            Data.body.paintIcon(this,g,snakeX[i],snakeY[i]);
        }
        //生成食物
        Data.food.paintIcon(this,g,foodX,foodY);

        //提示游戏是否开始
        if (!isStart){
            g.setColor(Color.WHITE);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("按下空格开始游戏",300,400);
        }
        //游戏是否失败
        if (isFail){
            g.setColor(Color.WHITE);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("游戏失败，是否重新开始",300,300);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int up = KeyEvent.VK_UP;
        int down = KeyEvent.VK_DOWN;
        int right = KeyEvent.VK_RIGHT;
        int left = KeyEvent.VK_LEFT;

        if (keyCode == KeyEvent.VK_SPACE){
            if (isFail){
                isFail = false;
                init();
            }
            isStart = !isStart;
        }
        if (keyCode == up){
            if (die == "D"){
                return;
            }
            die = "U";
        }else if (keyCode == down){
            if (die == "U"){
                return;
            }
            die = "D";
        }else if (keyCode == right){
            if (die == "L"){
                return;
            }
            die = "R";
        }else if (keyCode == left){
            if (die == "R"){
                return;
            }
            die = "L";
        }
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isStart && !isFail ){
            for (int i = length - 1; i > 0; i--) {
                snakeX[i] = snakeX[i-1];
                snakeY[i] = snakeY[i-1];
            }
            //判断头部移动方向
           if ("R".equals(die)){
               snakeX[0] = snakeX[0] + 25;
           }else if ("D".equals(die)){//下移
               snakeY[0] = snakeY[0] + 25;
           }else if ("L".equals(die)){
               snakeX[0] = snakeX[0] - 25;
           }else if ("U".equals(die)){
               snakeY[0] = snakeY[0] - 25;
           }

        }
        //判断是否吃掉食物
        if(snakeX[0] == foodX && snakeY[0] == foodY){
            length++;
            score += 10;
            foodX = random.nextInt(34) *25 + 25;
            foodY = random.nextInt(24) *25+ 75;
        }
        //判断是否碰到身体
        for (int i = 1; i < length; i++) {
            if (snakeX[i] == snakeX[0] && snakeY[i] == snakeY[0]){
                isFail = true;
            }

        }
        //边界判定
        if (snakeX[0] > 850){
            snakeX[0] = 15;
        }else if (snakeX[0] < 25){
            snakeX[0] = 850;
        }else if (snakeY[0] < 75){
            snakeY[0] = 650;
        }else if (snakeY[0] > 650){
            snakeY[0] = 75;
        }
        repaint();

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }



    @Override
    public void keyReleased(KeyEvent e) {

    }
}
