package se233.chapter5;

import javafx.embed.swing.JFXPanel;
import javafx.scene.input.KeyCode;
import org.junit.Before;
import org.junit.Test;
import se233.chapter5.controller.DrawingLoop;
import se233.chapter5.controller.GameLoop;
import se233.chapter5.model.Character;
import se233.chapter5.view.Platform;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;


import static org.junit.Assert.*;

public class CharacterTest {
    private Character floatingCharacter;
    private Character groundCharacter;
    private Character borderCharacter;
    private Character getCollidedCharacter;
    private Character nextToGroundCharacter;
    private ArrayList<Character> characterListUnderTest;
    private Platform platformUnderTest;
    private GameLoop gameLoopUnderTest;
    private DrawingLoop drawingLoopUnderTest;
    private Method updateMethod,redrawMethod;
    @Before
    public void setup()
    {
        JFXPanel jfxPanel = new JFXPanel();
        floatingCharacter = new Character(30,30,0,0,KeyCode.A,KeyCode.D,KeyCode.W);
        groundCharacter = new Character(30, Platform.GROUND-Character.CHARACTER_HEIGHT-1,0,0, KeyCode.LEFT,KeyCode.RIGHT,KeyCode.UP);
        borderCharacter = new Character(-1,Platform.GROUND-Character.CHARACTER_HEIGHT-1,0,0,KeyCode.LEFT,KeyCode.RIGHT,KeyCode.UP);
        getCollidedCharacter = new Character(30,31,0,0,KeyCode.LEFT,KeyCode.RIGHT,KeyCode.UP);
        nextToGroundCharacter = new Character(30 + Character.CHARACTER_WIDTH + 1,Platform.GROUND-Character.CHARACTER_HEIGHT-1,0,0,KeyCode.A,KeyCode.D,KeyCode.W);
        characterListUnderTest = new ArrayList<Character>();
        characterListUnderTest.add(floatingCharacter);
        characterListUnderTest.add(groundCharacter);
        characterListUnderTest.add(borderCharacter);
        characterListUnderTest.add(getCollidedCharacter);
        characterListUnderTest.add(nextToGroundCharacter);
        platformUnderTest = new Platform();
        gameLoopUnderTest = new GameLoop(platformUnderTest);
        drawingLoopUnderTest = new DrawingLoop(platformUnderTest);

        try
        {
            updateMethod = GameLoop.class.getDeclaredMethod("update", ArrayList.class);
            redrawMethod = DrawingLoop.class.getDeclaredMethod("paint", ArrayList.class);
            updateMethod.setAccessible(true);
            redrawMethod.setAccessible(true);
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
            updateMethod = null;
            redrawMethod = null;
        }
    }
    @Test
    public void characterInitialValuesShouldMatchConstructorArguments()
    {
        assertEquals("Initial x",30,floatingCharacter.getX(),0);
        assertEquals("Initial y",30,floatingCharacter.getY(),0);
        assertEquals("Offset x", 0,floatingCharacter.getOffsetX(),0.0);
        assertEquals("Offset y", 0,floatingCharacter.getOffsetY(),0.0);
        assertEquals("Left Key",KeyCode.A,floatingCharacter.getLeftKey());
        assertEquals("Right Key",KeyCode.D,floatingCharacter.getRightKey());
        assertEquals("Up Key",KeyCode.W,floatingCharacter.getUpKey());
    }

    @Test
    public void characterShouldMoveToTheLeftAfterTheLeftKeyIsPressed() throws IllegalAccessException, InvocationTargetException, NoSuchFieldException
    {
        Character characterUnderTest = characterListUnderTest.get(0);
        int startX = characterUnderTest.getX();
        platformUnderTest.getKeys().add(KeyCode.A);
        updateMethod.invoke(gameLoopUnderTest, characterListUnderTest);
        redrawMethod.invoke(drawingLoopUnderTest, characterListUnderTest);
        Field isMoveLeft = characterUnderTest.getClass().getDeclaredField("isMoveLeft");
        isMoveLeft.setAccessible(true);
        assertTrue("Controller: Left key pressing is acknowledged", platformUnderTest.getKeys().isPressed(KeyCode.A));
        assertTrue("Model: Character moving left state is set", isMoveLeft.getBoolean(characterUnderTest));
        assertTrue("View: Character is moving left", characterUnderTest.getX() < startX);
    }
    //No.1
    @Test
    public void characterShouldMoveToTheRightAtRightSpeedAfterTheRightKeyIsPressed() throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Character characterUnderTest = characterListUnderTest.get(0);
        int startX = characterUnderTest.getX();
            platformUnderTest.getKeys().add(KeyCode.D);
            updateMethod.invoke(gameLoopUnderTest, characterListUnderTest);
            redrawMethod.invoke(drawingLoopUnderTest, characterListUnderTest);
            Field isMoveRight = characterUnderTest.getClass().getDeclaredField("isMoveRight");
            isMoveRight.setAccessible(true);
            assertTrue("Controller: Right key pressing is acknowledged", platformUnderTest.getKeys().isPressed(KeyCode.D));
            assertTrue("Model: Character moving right state is set", isMoveRight.getBoolean(characterUnderTest));
            assertTrue("View: Character is moving right", characterUnderTest.getX() > startX);
            assertTrue(characterUnderTest.getxVelocity() == 1);
    }
    //No.2
    @Test
    public void  characterJumpsAfterPressingUpKeyWhenOnTheGround() throws InvocationTargetException,IllegalAccessException,NoSuchFieldException
    {
        Character characterUnderTest = characterListUnderTest.get(1);
        int startY = characterUnderTest.getY();
        redrawMethod.invoke(drawingLoopUnderTest, characterListUnderTest);
        characterUnderTest.checkReachFloor();
        platformUnderTest.getKeys().add(KeyCode.UP);
        updateMethod.invoke(gameLoopUnderTest, characterListUnderTest);
        redrawMethod.invoke(drawingLoopUnderTest, characterListUnderTest);
        Field isJumping = characterUnderTest.getClass().getDeclaredField("isJumping");
        Field isFalling = characterUnderTest.getClass().getDeclaredField("isFalling");
        isFalling.setAccessible(true);
        isJumping.setAccessible(true);
        assertFalse(isFalling.getBoolean(characterUnderTest));
        assertTrue("Up is pressed", platformUnderTest.getKeys().isPressed(KeyCode.UP));
        assertTrue("Character jumping state is set", isJumping.getBoolean(characterUnderTest));
        assertTrue("Character is Jumping", characterUnderTest.getY() < startY);
    }
    //No.3
    @Test
    public void characterJumpAfterPressingJumpingWhenOnTheAir() throws InvocationTargetException,IllegalAccessException,NoSuchFieldException
    {
       Character characterUnderTest = characterListUnderTest.get(0);
       int startY = characterUnderTest.getY();
       platformUnderTest.getKeys().add(KeyCode.W);
       updateMethod.invoke(gameLoopUnderTest,characterListUnderTest);
       redrawMethod.invoke(drawingLoopUnderTest, characterListUnderTest);
       Field isJumping = characterUnderTest.getClass().getDeclaredField("isJumping");
       Field isFalling = characterUnderTest.getClass().getDeclaredField("isFalling");
       isJumping.setAccessible(true);
       isFalling.setAccessible(true);
       assertTrue("Up Button is pressed",platformUnderTest.getKeys().isPressed(KeyCode.W));
       assertTrue("isFalling is True", isFalling.getBoolean(characterUnderTest));
       assertFalse("isJumping is False", isJumping.getBoolean(characterUnderTest));
       assertTrue("Character is Falling", characterUnderTest.getY() > startY);
    }
    //No.4
    @Test
    public void characterShouldStopAtTheBorderAfterAnyAttemptToMoveForward() throws InvocationTargetException,IllegalAccessException,NoSuchFieldException
    {
        Character characterUnderTest = characterListUnderTest.get(2);
        int startX = characterUnderTest.getX();
        redrawMethod.invoke(drawingLoopUnderTest, characterListUnderTest);
        characterUnderTest.checkReachGameWall();
        platformUnderTest.getKeys().add(KeyCode.LEFT);
        updateMethod.invoke(gameLoopUnderTest,characterListUnderTest);
        redrawMethod.invoke(drawingLoopUnderTest,characterListUnderTest);
        Field isMoveLeft = characterUnderTest.getClass().getDeclaredField("isMoveLeft");
        isMoveLeft.setAccessible(true);
        assertTrue("Left button is pressed", platformUnderTest.getKeys().isPressed(KeyCode.LEFT));
        assertTrue("Character moving left state is set", isMoveLeft.getBoolean(characterUnderTest));
        assertEquals("Character should stop at the border",startX, characterUnderTest.getX());
    }
    //No.6
    @Test
    public void consequenceAfterCharacterHasStompsOverTheOther() throws InvocationTargetException, IllegalAccessException {
       Character characterUpperOne = characterListUnderTest.get(0);
       Character characterLowerOne = characterListUnderTest.get(3);
       int startScore = characterUpperOne.getScore();
       int startYofTheLowerOne = characterLowerOne.getY();
       redrawMethod.invoke(drawingLoopUnderTest, characterListUnderTest);
       characterUpperOne.collided(characterLowerOne);
       updateMethod.invoke(gameLoopUnderTest,characterListUnderTest);
       assertTrue("Score is increase", characterUpperOne.getScore() > startScore);
       assertEquals("characterLowerOne will respawn at the same place" , characterLowerOne.getY(), startYofTheLowerOne);

    }
    //No.5
    @Test
    public void consequenceAfterCharacterHasCollidedAnotherCharacter() throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Character characterA = characterListUnderTest.get(1);
        Character characterB = characterListUnderTest.get(4);
        int startX = characterB.getX();
        platformUnderTest.getKeys().add(KeyCode.A);
        updateMethod.invoke(gameLoopUnderTest,characterListUnderTest);
        redrawMethod.invoke(drawingLoopUnderTest,characterListUnderTest);
        characterB.collided(characterA);
        redrawMethod.invoke(drawingLoopUnderTest,characterListUnderTest);

        Field isMoveLeft = characterB.getClass().getDeclaredField("isMoveLeft");
        isMoveLeft.setAccessible(true);

        assertTrue("Left key pressing is acknowledge", platformUnderTest.getKeys().isPressed(KeyCode.A));
        assertEquals("CharacterB should not move",startX,characterB.getX());
    }

}
