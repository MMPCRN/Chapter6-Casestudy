package se233.chapter1.model.item;

import javafx.scene.input.DataFormat;

import java.io.Serializable;

public class BasedEquipment implements Serializable
{
    public static final DataFormat DATA_FORMAT = new DataFormat("src.main.java.se233.chapter1.model.item.BasedEquipment");
    protected String name;
    protected String imgPath;
    public String getName()
    {
        return name;
    }
    public String getImagePath()
    {
        return imgPath;
    }
    public void setImagePath(String imgPath)
    {
        this.imgPath = imgPath;
    }
}
