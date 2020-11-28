package com.company;

import java.awt.*;

public class MapOfBricks {
    public int map[][];
    public int brick_width;
    public int brick_height;
    public MapOfBricks(int row, int col){
        map = new int[row][col];
        for(int i = 0; i < map.length; i++){
            for (int j = 0; j<map[0].length; j++){
                map[i][j] = 1;
            }
        }
        brick_width = 540/col;
        brick_height = 300/col;
    }

    public void draw(Graphics2D graphics2D){
        for(int i = 0; i < map.length; i++){
            for (int j = 0; j<map[0].length; j++){
               if(map[i][j] > 0){
                   graphics2D.setColor(Color.pink);
                   graphics2D.fillRect(j*brick_width+80, i*brick_height + 50, brick_width, brick_height);

                   graphics2D.setStroke(new BasicStroke(3));
                   graphics2D.setColor(Color.BLACK);
                   graphics2D.drawRect(j*brick_width+80, i*brick_height + 50, brick_width, brick_height);
               }
            }
        }
    }

    public void setBrickValue(int value, int row, int col){
        map[row][col] = value;
    }
}
