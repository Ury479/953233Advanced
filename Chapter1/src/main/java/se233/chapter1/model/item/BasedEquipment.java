package se233.chapter1.model.item;

import javafx.scene.input.DataFormat;

import java.io.Serializable;
import java.util.zip.DataFormatException;

//1.19
//public class BasedEquipment {

//1.36
public class BasedEquipment implements Serializable {
    public static final DataFormat DATA_FORMAT = new DataFormat("src.main.java.se233.chapter1.model.item.BasedEquipment");

    protected String name;
    protected String imgpath;

    public String getName(){
        return name;
    }
    public String getImagepath(){
        return imgpath;
    }
    public void setImagePath(String imgpath){
        this.imgpath = imgpath;
    }
}