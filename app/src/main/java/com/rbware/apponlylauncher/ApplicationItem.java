package com.rbware.apponlylauncher;

import android.graphics.drawable.Drawable;

public class ApplicationItem implements Comparable{
    private String name;
    private String packageName;
    private Drawable icon;

    public ApplicationItem(){ }

    public ApplicationItem(String name, String packageName, Drawable icon){
        this.name = name;
        this.packageName = packageName;
        this.icon = icon;
    }

    @Override
    public int compareTo(Object item) {
        return getName().compareTo(((ApplicationItem)item).getName());
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName(){
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcon(){
        return this.icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}