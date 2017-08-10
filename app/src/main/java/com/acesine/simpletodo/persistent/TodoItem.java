package com.acesine.simpletodo.persistent;

import android.os.Parcelable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.parceler.Parcel;

@Table(database = TodoItemDatabase.class)
@Parcel(analyze={TodoItem.class})
public class TodoItem extends BaseModel implements Parcelable {
    @Column
    @PrimaryKey
    private String itemId;

    @Column
    private String itemName;

    protected TodoItem(android.os.Parcel in) {
        itemId = in.readString();
        itemName = in.readString();
    }

    // For Parceler
    public TodoItem() { }

    public TodoItem(String itemId, String itemName) {
        this.itemId = itemId;
        this.itemName = itemName;
    }

    public static final Creator<TodoItem> CREATOR = new Creator<TodoItem>() {
        @Override
        public TodoItem createFromParcel(android.os.Parcel in) {
            return new TodoItem(in);
        }

        @Override
        public TodoItem[] newArray(int size) {
            return new TodoItem[size];
        }
    };

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeString(itemId);
        dest.writeString(itemName);
    }
}
