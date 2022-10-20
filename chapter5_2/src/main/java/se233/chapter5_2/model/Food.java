package se233.chapter5_2.model;

import javafx.geometry.Point2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se233.chapter5_2.view.Platform;

import java.util.Random;

public class Food {

    Logger logger = LoggerFactory.getLogger(Food.class);
    private Point2D position;
    private Random rn;
    private int score = 1 ;
    public boolean isSpecialFood = false;

    public Food(Point2D position) {
        this.rn=new Random();
        this.position=position;
    }
    public Food() {
        this.rn=new Random();
        respawn();
    }
    public void respawn() {
        Point2D prev_position = this.position;
        do{
            this.position = new Point2D(rn.nextInt(Platform.WIDTH), rn.nextInt(Platform.HEIGHT));
        }while (prev_position == this.position);
        logger.info("Food: x:{} y:{}",this.position.getX(), this.position.getY());
    }
    public Point2D getPosition() { return position; }
    public int getScore() {
        return this.score;
    }

}
