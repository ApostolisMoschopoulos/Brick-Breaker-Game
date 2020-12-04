package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Gameplay extends JPanel implements ActionListener, KeyListener {

    private boolean play = false;
    private int score = 0;
    private int total_times_played;
    private int bricks_remaining = 21;
    private final Timer timer;

    private int playerX = 310;

    private int ball_positionX ;
    private int ball_positionY ;
    private int ball_speedX;
    private int ball_speedY;
    private int difficulty = -1;

    private MapOfBricks map ;

    public Gameplay(){
        ball_positionX = randomXPositionStart();
        ball_positionY = randomYPositionStart();
        while(difficulty > 7 || difficulty < 0)
            difficulty = Integer.parseInt(JOptionPane.showInputDialog("Set difficulty: 1-Easiest , 7-Hardest"));
        ball_speedX = difficulty;
        ball_speedY = difficulty;

        map = new MapOfBricks(3,7);
        total_times_played = 1;
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(1, this);
        timer.start();
    }

    public void paint(Graphics graphics){

        //background
        graphics.setColor(Color.BLACK);
        graphics.fillRect(1,1,682,592);     //x,y positions width,height size

        //map
        map.draw((Graphics2D)graphics);

        //important
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("serif", Font.BOLD, 20));
        graphics.drawString("Black lives matter",10,30);

        //score
        graphics.setColor(Color.lightGray);
        graphics.setFont(new Font("serif", Font.BOLD, 20));
        graphics.drawString("Score: "+score,590,30);

        //borders
        graphics.setColor(Color.green);
        graphics.fillRect(0,0,3,592);
        graphics.fillRect(0,0,692,3);
        graphics.fillRect(682,0,3,592);

        //paddle
       graphics.setColor(Color.orange);
       graphics.fillRect(playerX,550,100,8);

        //ball
        graphics.setColor(Color.cyan);
        graphics.fillOval(ball_positionX,ball_positionY,20,20);

        /*Now we check if the ball fell*/

        if(ball_positionY>555){
            play = false;
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("serif", Font.BOLD, 35));
            graphics.drawString("Game over! Your final score is "+score, 90, 300);
            graphics.drawString("Press enter to restart.", 140, 400);
        }

        if(bricks_remaining==0){            //works the same with condition score == 105
            play = false;
            graphics.setColor(Color.magenta);
            graphics.setFont(new Font("serif", Font.BOLD, 35));
            graphics.drawString("Congratulations! You won!", 90, 300);
            graphics.drawString("Total times played: "+total_times_played, 140,400);
            graphics.drawString("Press enter to play again.", 140, 500);
        }

        graphics.dispose();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if(play){

            if(new Rectangle(ball_positionX,ball_positionY,20,20).intersects(new Rectangle(playerX,550,100,8))){
                ball_speedY = -ball_speedY;
            }

           A: for(int i = 0; i < map.map.length; i++){            //in map.map first map is the variable in class Gameplay
                for(int j = 0; j < map.map[0].length; j++){     //and second map is the 2d array in MapOfBricks class
                    if(map.map[i][j] > 0){
                        int brickX = j * map.brick_width + 80;
                        int brickY = i * map.brick_height + 50;
                        int brick_width = map.brick_width;
                        int brick_height = map.brick_height;

                        Rectangle rectangle = new Rectangle(brickX,brickY,brick_width,brick_height);
                        Rectangle ball_rectangle = new Rectangle(ball_positionX,ball_positionY,20,20);

                        if(ball_rectangle.intersects(rectangle)){
                            map.setBrickValue(0,i,j);
                            bricks_remaining--;
                            score+=5;
                            ball_speedY = -ball_speedY;
                            ball_speedX = -ball_speedX;
                            /* Needs precision */
                            if(ball_positionX + 20 < rectangle.x || ball_positionX > rectangle.width){
                                ball_speedX = -ball_speedX;
                            } else {
                                ball_speedY = -ball_speedY;
                            }
                            break A;
                        }

                    }
                }
            }
            ball_positionX+= ball_speedX;
            ball_positionY+= ball_speedY;
            if(ball_positionX < 0)
                ball_speedX = -ball_speedX;
            if(ball_positionY < 0)
                ball_speedY = -ball_speedY;
            if(ball_positionX > 665)
                ball_speedX = -ball_speedX;


        }

        repaint();
    }

    public int randomXPositionStart(){
        Random start_position_of_X = new Random();
        return start_position_of_X.nextInt(480-40) + 40;    // Needs more precision
    }

   public int randomYPositionStart(){
        Random start_position_of_Y = new Random();
        return start_position_of_Y.nextInt(200) + 200;
   }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playerX>560){
                playerX = 559;
            }
            else{
                move_Right();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerX < 10){
                playerX = 9;
            }
            else {
                move_Left();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                total_times_played++;
                score = 0;
                playerX = 310;
                bricks_remaining = 21;
                ball_positionX = randomXPositionStart();
                ball_positionY = randomYPositionStart();
                 ball_speedX = difficulty;
                 ball_speedY = difficulty;
                 map = new MapOfBricks(3,7);
                 repaint();
            }
        }

        if((e.getKeyCode() == KeyEvent.VK_SPACE) && play)
            play = false;
        if((e.getKeyCode() == KeyEvent.VK_SPACE) && !play)
            play = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void move_Right(){
        play = true;
        playerX+=20;
    }
    public void move_Left(){
        play = true;
        playerX-=20;
    }

}
