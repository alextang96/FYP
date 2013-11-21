package com.example.maptest;

public class ListModel {
    
    private  String butterflyName="";
    private  String Image="";
     
    /*********** Set Methods ******************/
     
    public void setButterflyName(String butterflyName)
    {
        this.butterflyName = butterflyName;
    }
     
    public void setImage(String Image)
    {
        this.Image = Image;
    }
     
    /*********** Get Methods ****************/
     
    public String getButterflyName()
    {
        return this.butterflyName;
    }
     
    public String getImage()
    {
        return this.Image;
    }  
}

