package com.amador.pruebaexamen;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

/**
 * Created by amador on 11/12/16.
 */

public class Sport implements Parcelable {

    private String name;
    private int icon;
    private boolean like;
    public static final Comparator<Sport> ORDER_BY_ALF = new Comparator<Sport>() {
        @Override
        public int compare(Sport sport, Sport t1) {
            return sport.getName().compareToIgnoreCase(t1.getName());
        }
    };

    public Sport(String name, int icon, boolean like) {
        this.name = name;
        this.icon = icon;
        this.like = like;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.icon);
        dest.writeByte(this.like ? (byte) 1 : (byte) 0);
    }

    protected Sport(Parcel in) {
        this.name = in.readString();
        this.icon = in.readInt();
        this.like = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Sport> CREATOR = new Parcelable.Creator<Sport>() {
        @Override
        public Sport createFromParcel(Parcel source) {
            return new Sport(source);
        }

        @Override
        public Sport[] newArray(int size) {
            return new Sport[size];
        }
    };

    @Override
    public boolean equals(Object obj) {

        boolean result = false;

        if(obj != null){

            if(obj instanceof Sport){


                result = ((Sport) obj).getName().equalsIgnoreCase(this.name);
            }
        }

        return result;

    }
}
