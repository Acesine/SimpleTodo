package com.acesine.simpletodo.persistent;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = TodoItemDatabase.DATABASE_NAME, version = TodoItemDatabase.DATABASE_VERSION)
public class TodoItemDatabase {
    public final static String DATABASE_NAME = "TodoItems";
    public final static int DATABASE_VERSION = 1;
}