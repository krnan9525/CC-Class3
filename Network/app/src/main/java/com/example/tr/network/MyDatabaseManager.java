package com.example.tr.network;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by TR on 14/11/2015.
 */

public class MyDatabaseManager {
    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CONTACTS_TABLE_NAME = "task";
    //    public static final String CONTACTS_COLUMN_ID = "name";
//    public static final String CONTACTS_COLUMN_NAME = "check";
    private final Context context;
    private DBHelper dbHelper;
    public SQLiteDatabase db;
    private static class DBHelper extends SQLiteOpenHelper
    {

        private int idInUse = 0;

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, 1);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            //db = getWritableDatabase();
            db.execSQL(
                    "create table " + CONTACTS_TABLE_NAME +
                            " (_id integer primary key autoincrement, checked text, name text);"
            );
            //insertCountry("todo", 1);
            //insertCountry("to0do", 1);
            //insertCountry("to9do", 1);
            //insertCountry("ireland",R.drawable.ireland_grunge_flag_by_think0);
            //insertCountry("england", R.drawable.download);
            //insertCountry("japan", R.drawable.jjapan);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS "+CONTACTS_TABLE_NAME);
            onCreate(db);
            //insertCountry("ireland",R.drawable.ireland_grunge_flag_by_think0);
            //insertCountry("england", R.drawable.download);
            //insertCountry("japan", R.drawable.jjapan);
        }

        public boolean insertCountry(String name, String res_name) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("checked", res_name);
            //contentValues.put("_id",idInUse++);
            if(db.update(CONTACTS_TABLE_NAME,contentValues, "name = ? ", new String[]{name})==0)
                db.insert(CONTACTS_TABLE_NAME, null, contentValues);
            return true;
        }

        public Cursor getData(String name) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select * from "+CONTACTS_TABLE_NAME+" where name=" + name + "", null);
            return res;
        }

        public int numberOfRows() {
            SQLiteDatabase db = this.getReadableDatabase();
            int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
            return numRows;
        }

        public boolean updateContact(String name, int checked) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("checked", checked);

            db.update(CONTACTS_TABLE_NAME, contentValues, "id = ? ", new String[]{name});
            return true;
        }

        public Integer deleteContact(String name) {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.delete(CONTACTS_TABLE_NAME,
                    "id = ? ",
                    new String[]{name});
        }


        public ArrayList<String> getAllCotacts() {
            ArrayList<String> array_list = new ArrayList<String>();

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select * from "+CONTACTS_TABLE_NAME, null);
            res.moveToFirst();

            while (res.isAfterLast() == false) {

                array_list.add(res.getString(res.getColumnIndex("name")));
                array_list.add(res.getString(res.getColumnIndex("checked")));
                res.moveToNext();
            }
            return array_list;
        }
    }

    public MyDatabaseManager(Context ctx)
    {
        this.context=ctx;
        dbHelper= new DBHelper(context);
    }
    public MyDatabaseManager open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public boolean DBInsert(String name,String checked) throws SQLException
    {
        return dbHelper.insertCountry(name,checked);
    }
    public ArrayList<String> GetAllData()
    {
        return dbHelper.getAllCotacts();
    }
    public void close()
    {
        dbHelper.close();
    }
    public  boolean CreateDb()
    {
        db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE_NAME);
        db.execSQL(
                "create table " + CONTACTS_TABLE_NAME +
                        " (_id integer primary key autoincrement, checked text, name text);"
        );
        //dbHelper.insertCountry("todo", 1);
        //dbHelper.insertCountry("to0do", 1);
        //dbHelper.insertCountry("to9do", 1);
        return true;
    }
    public  Cursor GetCursor()
    {
        Cursor res = db.rawQuery("select * from "+CONTACTS_TABLE_NAME , null);
        return  res;
    }
}
