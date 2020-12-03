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
    private Timer timer;

    private int playerX = 310;

    private int ball_positionX ;        //used to be around 120
    private int ball_positionY ;        //used to be around 120
    private int ball_directionX = -1;
    private int ball_directionY = -2;

    private MapOfBricks map ;

    public Gameplay(){
        ball_positionX = randomXPositionStart();
        ball_positionY = randomYPositionStart();
        map = new MapOfBricks(3,7);
        total_times_played = 1;
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        //determines how fast the ball will move
        int delay = 1;
        timer = new Timer(delay,
                this);
        timer.start();
    }

    public void paint(Graphics graphics){

        //background
        graphics.setColor(Color.BLACK);
        graphics.fillRect(1,1,692,592);     //x,y positions width,height size

        //map
        map.draw((Graphics2D)graphics);

        //score
        graphics.setColor(Color.lightGray);
        graphics.setFont(new Font("serif", Font.BOLD, 20));
        graphics.drawString("Score: "+score,590,30);

        //borders
        graphics.setColor(Color.green);
        graphics.fillRect(0,0,3,592);
        graphics.fillRect(0,0,692,3);
        graphics.fillRect(691,0,3,592);

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
                ball_directionY = - ball_directionY;
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
                            ball_directionY = - ball_directionY;
                            ball_directionX = - ball_directionX;

                            if(ball_positionX + 20 < rectangle.x || ball_positionX > rectangle.width){
                                ball_directionX = -ball_directionX;
                            } else {
                                ball_directionY = - ball_directionY;
                            }
                            break A;
                        }

                    }
                }
            }
            ball_positionX+= ball_directionX;
            ball_positionY+= ball_directionY;
            if(ball_positionX < 0)
                ball_directionX = -ball_directionX;
            if(ball_positionY < 0)
                ball_directionY = -ball_directionY;
            if(ball_positionX > 670)
                ball_directionX = -ball_directionX;


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
            if(playerX>599){
                playerX = 600;      //thus the paddle will not leave the screen from the right side
            }
            else{
                move_Right();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerX < 10){
                playerX = 9;   //thus the paddle will not leave the screen from the left side
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
                 ball_directionX = -1;
                 ball_directionY = -2;
                 map = new MapOfBricks(3,7);
                 repaint();
            }
        }

        if((e.getKeyCode() == KeyEvent.VK_SPACE) && play)
            play = false;
        if ((e.getKeyCode() == KeyEvent.VK_SPACE) && !play)
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
