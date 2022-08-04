package se233.chapter1.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import se233.chapter1.Launcher;
import se233.chapter1.model.character.BasedCharacter;
import se233.chapter1.model.item.Armor;
import se233.chapter1.model.item.BasedEquipment;
import se233.chapter1.model.item.Weapon;

import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

//1.30
public class AllCustomHandler {
    public static class GenCharacterHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event){
            Launcher.setMainCharacter(GenCharacter.setUpCharacter());

            //Exercise(5)
            Launcher.setEquippedWeapon(null);
            Launcher.setEquippedArmor(null);
            ArrayList<BasedEquipment> allEquipments = GenItemList.setUpItemList();
            Launcher.setAllEquipments(allEquipments);
            //++
            Launcher.refreshPane();
        }
    }

    //1.34
    public static void onDragDetected(MouseEvent event, BasedEquipment equipment, ImageView imgView){
        Dragboard db = imgView.startDragAndDrop(TransferMode.ANY);
        db.setDragView(imgView.getImage());
        ClipboardContent content = new ClipboardContent();
        content.put(equipment.DATA_FORMAT, equipment);
        db.setContent(content);
        event.consume();
    }

    //Exercise(5)
    public static class unEquip implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            BasedCharacter character = Launcher.getMainCharacter();
            Launcher.setEquippedArmor(null);
            Launcher.setEquippedWeapon(null);
            ArrayList<BasedEquipment> allEquipments = GenItemList.setUpItemList();
            Launcher.setAllEquipments(allEquipments);
            character.unEquipArmor();
            character.unEquipWeapon();
            Launcher.refreshPane();
        }
    }

    //1.37
    public static void onDragOver(DragEvent event, String type){
        Dragboard dragboard = event.getDragboard();
        BasedEquipment retrievedEquipment = (BasedEquipment)dragboard.getContent(BasedEquipment.DATA_FORMAT);
        if(dragboard.hasContent(BasedEquipment.DATA_FORMAT) && retrievedEquipment.getClass().getSimpleName().equals(type))
            event.acceptTransferModes(TransferMode.MOVE);
    }

    public static void onDragDropped(DragEvent event, Label lbl, StackPane imgGroup){
        boolean dragComplete = false;
        Dragboard dragboard = event.getDragboard();
//1.41
        ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();
        
        if(dragboard.hasContent(BasedEquipment.DATA_FORMAT)){
            BasedEquipment retrievedEquipment = (BasedEquipment)dragboard.getContent(BasedEquipment.DATA_FORMAT);
            //1.39
            BasedCharacter character = Launcher.getMainCharacter();

            if(retrievedEquipment.getClass().getSimpleName().equals("Weapon")){

                Weapon weapon = (Weapon)retrievedEquipment;

                //Exercise(6)
                if(!character.getName().equals("BattleMageChar1")) {
                    if(weapon.getDamageType() != character.getType()) {
                        return;
                    }
                }

                //1.41
                if(Launcher.getEquippedWeapon() != null)
                    allEquipments.add(Launcher.getEquippedWeapon());
                
                Launcher.setEquippedWeapon((Weapon) retrievedEquipment);
                //1.39
                character.equipWeapon((Weapon) retrievedEquipment);
            } else {

                //Exercise(6)
                if(character.getName().equals("BattleMageChar1")) {
                    return;
                }

                //1.41
                if(Launcher.getEquippedArmor() != null)
                    allEquipments.add(Launcher.getEquippedArmor());
                
                Launcher.setEquippedArmor((Armor) retrievedEquipment);
                //1.39
                character.equipArmor((Armor) retrievedEquipment);
            }
            //1.39
            Launcher.setMainCharacter(character);
            
            Launcher.setAllEquipments(allEquipments);

            //1.39
            Launcher.refreshPane();
            ImageView imgView = new ImageView();
            if(imgGroup.getChildren().size() != 1){
                imgGroup.getChildren().remove(1);
                Launcher.refreshPane();
            }
            lbl.setText((retrievedEquipment.getClass().getSimpleName() + ":\n" + retrievedEquipment.getName()));
            imgView.setImage(new Image(Launcher.class.getResource(retrievedEquipment.getImagepath()).toString()));
            imgGroup.getChildren().add(imgView);
            dragComplete = true;
        }
        event.setDropCompleted(dragComplete);
        event.consume();
    }

    //1.41
    public static void onEquipDone(DragEvent event){
        Dragboard dragboard = event.getDragboard();
        ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();
        BasedEquipment retrievedEquipment = (BasedEquipment)dragboard.getContent(BasedEquipment.DATA_FORMAT);
        int pos = -1;

        //Exercise(3)
        if(event.getTransferMode() != TransferMode.MOVE){
            allEquipments.add(retrievedEquipment);
        }

        for(int i = 0; i < allEquipments.size(); i++){
            if(allEquipments.get(i).getName().equals(retrievedEquipment.getName())){
                pos = i;
            }
        }

        if(pos != -1){
            allEquipments.remove(pos);
        }
        Launcher.setAllEquipments(allEquipments);
        Launcher.refreshPane();
    }

}
