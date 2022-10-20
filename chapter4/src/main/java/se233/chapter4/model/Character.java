package se233.chapter4.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se233.chapter4.Launcher;
import se233.chapter4.view.Platform;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class Character extends Pane {
    Logger logger= LoggerFactory.getLogger(Character.class);
    public static final int CHARACTER_WIDTH= 32;
    public static final int CHARACTER_HEIGHT= 64;
    private Image characterImg;
    private AnimatedSprite imageView;
    private int x;
    private int y;
    private KeyCode leftKey;
    private KeyCode rightKey;
    private KeyCode upKey;
    int yVelocity = 0;
    boolean isMoveLeft =false;
    boolean isMoveRight =false;
    int xVelocity = 0;
    boolean isFalling = true;
    boolean canJump=false;
    boolean isJumping=false;
    int xAcceleration= 1;
    int yAcceleration= 1;
    int xMaxVelocity= 7;
    int yMaxVelocity= 17;

    public Character(int x,int y,int offsetX,int offsetY,KeyCode leftKey,KeyCode rightKey,KeyCode upKey,int imgNum,int xMaxVelocity,int yMaxVelocity) {
        this.x=x;
        this.y=y;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.xMaxVelocity = xMaxVelocity;
        this.yMaxVelocity = yMaxVelocity;
        if(imgNum == 0) {
            this.characterImg = new Image(Launcher.class.getResourceAsStream("assets/MarioSheet.png"));
            this.imageView = new AnimatedSprite(characterImg, 4, 4, 1, offsetX, offsetY, 16, 32);
        }
        else {
            this.characterImg = new Image(Launcher.class.getResourceAsStream("assets/meg.png"));
            this.imageView = new AnimatedSprite(characterImg, 10, 10, 1, offsetX, offsetY, 36, 36);
        }
        this.imageView.setFitWidth(CHARACTER_WIDTH);
        this.imageView.setFitHeight(CHARACTER_HEIGHT);
        this.leftKey=leftKey;
        this.rightKey=rightKey;
        this.upKey=upKey;
        this.getChildren().addAll(this.imageView);


    }
    public void jump() {
        if(canJump) {
            yVelocity= yMaxVelocity;
            canJump=false;
            isJumping=true;
            isFalling=false;
        }
    }
    public void checkReachHighest() {
        if(isJumping&&yVelocity<= 0) {
            isJumping=false;
            isFalling=true;
            yVelocity =0;
        }
    }

    public void checkReachFloor() {
        if(isFalling && y >= Platform.GROUND - CHARACTER_HEIGHT){
            isFalling = false;
            canJump =true;
            yVelocity =0;
        }
    }
    public void checkReachGameWall() {
        if(x<= 0) {
            x= 0;
        }else if(x+getWidth() >=Platform.WIDTH) {
            x=Platform.WIDTH-(int)getWidth();
        }
    }
    public void moveX() {
        setTranslateX(x);
        if(isMoveLeft) {
            xVelocity=xVelocity>=xMaxVelocity?xMaxVelocity:xVelocity+xAcceleration;
            x=x-xVelocity; }
        if(isMoveRight) {
            xVelocity=xVelocity>=xMaxVelocity?xMaxVelocity:xVelocity+xAcceleration;
            x=x+xVelocity; }
    }
    public void moveY() {
        setTranslateY(y);
        if(isFalling){
            yVelocity=yVelocity>=yMaxVelocity?yMaxVelocity:yVelocity+yAcceleration;
            y = y+yVelocity;
        } else if (isJumping) {
            yVelocity=yVelocity<= 0 ? 0 :yVelocity-yAcceleration;
            y = y-yVelocity;
        }
    }

    public void repaint(){
        moveX();
        moveY();
    }

    public KeyCode getLeftKey() {
        return leftKey;
    }

    public KeyCode getRightKey() {
        return rightKey;
    }

    public KeyCode getUpKey() {
        return upKey;
    }

    public void stop() {
        isMoveRight =false;
        isMoveLeft =false;
    }

    public void moveLeft() {
        isMoveLeft =true;
        isMoveRight =false;
    }

    public void moveRight() {
        isMoveLeft =false;
        isMoveRight =true;
    }

    public AnimatedSprite getImageView() {
        return imageView;
    }

    public void trace() {
        LocalDate localDate = LocalDate.now();
        logger.info("x:{}y:{}vx:{}vy:{},{}", x, y, xVelocity, yVelocity, DayOfWeek.from(localDate));
    }


}
