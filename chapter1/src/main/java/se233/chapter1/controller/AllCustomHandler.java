package se233.chapter1.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se233.chapter1.Launcher;
import se233.chapter1.model.DamageType;
import se233.chapter1.model.character.BasedCharacter;
import se233.chapter1.model.item.Armor;
import se233.chapter1.model.item.BasedEquipment;
import se233.chapter1.model.item.Weapon;

import java.util.ArrayList;


public class AllCustomHandler
{
    static Logger logger = LoggerFactory.getLogger(AllCustomHandler.class);
    public static class GenCharacterHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            Launcher.setMainCharacter(GenCharacter.setUpCharacter());
            Launcher.setAllEquipments(GenItemList.setUpItemList());
            Launcher.setEquippedArmor(null);
            Launcher.setEquippedWeapon(null);
            Launcher.refreshPane();
        }
    }
    public static class UnEquipItemHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();
            Weapon equippedWeapon = (Weapon) Launcher.getEquippedWeapon();
            Armor equippedArmor = (Armor) Launcher.getEquippedArmor();
            if(equippedWeapon != null)
            {
                allEquipments.add(equippedWeapon);
                Launcher.setEquippedWeapon(null);
                Launcher.getMainCharacter().unEquipWeapon();
            }
            if(equippedArmor != null)
            {
                allEquipments.add(equippedArmor);
                Launcher.setEquippedArmor(null);
                Launcher.getMainCharacter().unEquipArmor();
            }
            Launcher.setAllEquipments(allEquipments);
            Launcher.refreshPane();
        }
    }
    public static void onDragDetected(MouseEvent event, BasedEquipment equipment, ImageView imgView)
    {
        Dragboard db = imgView.startDragAndDrop(TransferMode.ANY);
        db.setDragView(imgView.getImage());
        ClipboardContent content = new ClipboardContent();
        content.put(BasedEquipment.DATA_FORMAT, equipment);
        db.setContent(content);
        event.consume();
    }
    public static void onDragOver(DragEvent event, String type)
    {
        Dragboard dragboard = event.getDragboard();
        BasedEquipment retrievedEquipment = (BasedEquipment)dragboard.getContent(BasedEquipment.DATA_FORMAT);
        if(dragboard.hasContent(BasedEquipment.DATA_FORMAT) && retrievedEquipment.getClass().getSimpleName().equals(type))
            event.acceptTransferModes(TransferMode.MOVE);
    }
    public static void onDragDropped(DragEvent event, Label lbl, StackPane imgGroup)
    {
        boolean dragComplete = false;
        Dragboard dragboard = event.getDragboard();
        ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();

        if(dragboard.hasContent(BasedEquipment.DATA_FORMAT))
        {
            BasedEquipment retrievedEquipment = (BasedEquipment)dragboard.getContent(BasedEquipment.DATA_FORMAT);
            BasedCharacter character = Launcher.getMainCharacter();
            if(retrievedEquipment.getClass().getSimpleName().equals("Weapon"))
            {
                if((Launcher.getMainCharacter().getType() == ((Weapon)retrievedEquipment).getDamageType() || Launcher.getMainCharacter().getType() == DamageType.battleMage)) {
                    if (Launcher.getEquippedWeapon() != null) {
                        allEquipments.add(Launcher.getEquippedWeapon());
                    }
                    Launcher.setEquippedWeapon((Weapon) retrievedEquipment);
                    character.equipWeapon((Weapon) retrievedEquipment);
                }else
                {
                    allEquipments.add(retrievedEquipment);
                }
            }
            else {

                if(Launcher.getMainCharacter().getType() == DamageType.battleMage)
                {
                    return;
                }
                if(Launcher.getEquippedArmor() != null)
                {
                    allEquipments.add(Launcher.getEquippedArmor());
                }
                Launcher.setEquippedArmor((Armor) retrievedEquipment);
                character.equipArmor((Armor) retrievedEquipment);
            }

            Launcher.setMainCharacter(character);
            Launcher.setAllEquipments(allEquipments);
            Launcher.refreshPane();
            logger.info("Armor:{} Weapon::{}",Launcher.getEquippedArmor(), Launcher.getEquippedWeapon());
            ImageView imgView = new ImageView() ;
            if(imgGroup.getChildren().size() != 1){
                imgGroup.getChildren().remove(1);
                Launcher.refreshPane();
            }
            lbl.setText(retrievedEquipment.getClass().getSimpleName() + ":\n" + retrievedEquipment.getName());
            imgView.setImage(new Image(Launcher.class.getResource(retrievedEquipment.getImagePath()).toString()));
            imgGroup.getChildren().add(imgView);
            dragComplete = true ;
        }
        event.setDropCompleted(dragComplete);
        event.consume();
    }
    public static void onEquipDone(DragEvent event)
    {
        Dragboard dragboard = event.getDragboard();
        ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();
        BasedEquipment retrievedEquipment = (BasedEquipment) dragboard.getContent(BasedEquipment.DATA_FORMAT);
        int pos = -1;
        for(int i = 0; i < allEquipments.size(); i++)
        {
            if(allEquipments.get(i).getName().equals(retrievedEquipment.getName()))
            {
                pos = i;
            }
        }
        if(pos != -1)
        {
            allEquipments.remove(pos);
        }
        Launcher.setAllEquipments(allEquipments);

        Launcher.refreshPane();
    }
    public static void onUnEquipDone(DragEvent event) {
        Dragboard  dragboard = event.getDragboard();
        ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();
        BasedEquipment retrievedEquipment = (BasedEquipment) dragboard.getContent( BasedEquipment.DATA_FORMAT) ;
        allEquipments.add(retrievedEquipment) ;

        Launcher.setAllEquipments(allEquipments);
        if(retrievedEquipment instanceof Armor){
            Launcher.setEquippedArmor(null);
            Launcher.getMainCharacter().unEquipArmor();
        }else{
            Launcher.setEquippedWeapon(null);
            Launcher.getMainCharacter().unEquipWeapon();
        }
        Launcher.refreshPane();
    }

    public static void onDragFailed(DragEvent event) {
        Dragboard  dragboard = event.getDragboard();
        ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();
        BasedEquipment retrievedEquipment = (BasedEquipment) dragboard.getContent( BasedEquipment.DATA_FORMAT) ;
        allEquipments.add(retrievedEquipment) ;
        Launcher.refreshPane();
    }

}
