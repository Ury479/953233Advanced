package se233.chapter1.view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import se233.chapter1.model.item.BasedEquipment;
import se233.chapter1.Launcher;

import java.awt.*;
import java.util.ArrayList;

import static se233.chapter1.controller.AllCustomHandler.onDragDetected;
import static se233.chapter1.controller.AllCustomHandler.onEquipDone;

//1.22
public class InventoryPane extends ScrollPane {
//   1.32
//   private BasedEquipment[] equipmentsArray;
    private ArrayList<BasedEquipment> equipmentArray; //1.31
    public InventoryPane(){

    }
    private Pane getDetailsPane(){
        Pane inventoryInfoPane = new HBox(10);
        inventoryInfoPane.setBorder(null);
        inventoryInfoPane.setPadding(new Insets(25,25,25,25));
        if(equipmentArray != null){


//1.32            ImageView[] imageViewList = new ImageView[equipmentsArray.length];
            ImageView[] imageViewList = new ImageView[equipmentArray.size()];

//1.32            for(int i = 0; i < equipmentsArray.length; i++){
            for(int i = 0; i < equipmentArray.size(); i++){

                imageViewList[i] = new ImageView();
//1.32                imageViewList[i].setImage(new Image(Launcher.class.getResource(equipmentsArray[i].getImagegpath()).toString()));
                imageViewList[i].setImage(new Image(Launcher.class.getResource(equipmentArray.get(i).getImagepath()).toString()));

                //1.35
                int finalI = i;

                //1.42
                imageViewList[i].setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        onDragDetected(event, equipmentArray.get(finalI),imageViewList[finalI]);
                    }
                });
                //
                    imageViewList[i].setOnDragDone(new EventHandler<DragEvent>() {
                        @Override
                        public void handle(DragEvent event) {
                            onEquipDone(event);
                        }
                    });
            }
            inventoryInfoPane.getChildren().addAll(imageViewList);
        }
        return inventoryInfoPane;
    }

//1.32    public void drawPane(BasedEquipment[] equipmentsArray){
    public void drawPane(ArrayList<BasedEquipment> equipmentsArray){
        this.equipmentArray = equipmentsArray;
        Pane inventoryInfo = getDetailsPane();
        this.setStyle("-fx-background-color:Red;");
        this.setContent(inventoryInfo);
    }
}
