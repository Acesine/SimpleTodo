package com.acesine.simpletodo.persistent;

import android.os.Parcelable;

import com.acesine.simpletodo.Constants;
import com.acesine.simpletodo.Constants.Priority;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.parceler.Parcel;

import java.util.Date;

@Table(database = TodoItemDatabase.class)
@Parcel(analyze={TodoItem.class})
public class TodoItem extends BaseModel implements Parcelable {
    @Column
    @PrimaryKey
    private String itemId;

    @Column
    private String itemName;

    @Column
    private Priority itemPriority;

    @Column
    private String itemDueDate;

    @Column
    private String itemCreationDate;

    protected TodoItem(android.os.Parcel in) {
        itemId = in.readString();
        itemName = in.readString();
        itemPriority = Priority.valueOf(in.readString());
        itemDueDate = in.readString();
        itemCreationDate = in.readString();
    }

    // For Parceler
    public TodoItem() { }

    public TodoItem(String itemId, String itemName, Priority itemPriority, String itemDueDate, String itemCreationDate) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPriority = itemPriority;
        this.itemDueDate = itemDueDate;
        this.itemCreationDate = itemCreationDate;
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

    public void setItemPriority(Priority itemPriority) {
        this.itemPriority = itemPriority;
    }

    public void setItemDueDate(String itemDueDate) {
        this.itemDueDate = itemDueDate;
    }

    public void setItemCreationDate(String itemCreationDate) {
        this.itemCreationDate = itemCreationDate;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public Priority getItemPriority() {
        return itemPriority;
    }

    public String getItemDueDate() {
        return itemDueDate;
    }

    public String getItemCreationDate() {
        return itemCreationDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeString(itemId);
        dest.writeString(itemName);
        dest.writeString(itemPriority.name());
        dest.writeString(itemDueDate);
        dest.writeString(itemCreationDate);
    }
}
