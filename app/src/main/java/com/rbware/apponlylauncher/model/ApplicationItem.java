package com.rbware.apponlylauncher.model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class ApplicationItem implements Comparable, Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(packageName);

        Bitmap bitmap = (Bitmap)((BitmapDrawable) icon).getBitmap();
        dest.writeParcelable(bitmap, flags);
    }

    private ApplicationItem(Parcel in) {
        name = in.readString();
        packageName = in.readString();

        // Deserialize Parcelable and cast to Bitmap first:
        Bitmap bitmap = (Bitmap)in.readParcelable(getClass().getClassLoader());
        // Convert Bitmap to Drawable:
        icon = new BitmapDrawable(bitmap);
    }

    public static final Creator<ApplicationItem> CREATOR = new Creator<ApplicationItem>() {
        @Override
        public ApplicationItem createFromParcel(Parcel in) {
            return new ApplicationItem(in);
        }

        @Override
        public ApplicationItem[] newArray(int size) {
            return new ApplicationItem[size];
        }
    };



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