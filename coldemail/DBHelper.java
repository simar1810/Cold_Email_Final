//package com.example.coldemail;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//public class DBHelper extends SQLiteOpenHelper {
//    public static final String DBNAME="Login.db";
//
//    public DBHelper(Context context) {
//        super(context, "Login.db", null, 1);
//    }
//
//
//    @Override
//    public void onCreate(SQLiteDatabase mydb) {
//        mydb.execSQL("create Table users(username TEXT primary key , password TEXT, email TEXT)");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase mydb, int i, int i1) {
//        mydb.execSQL("drop Table if exists users");
//    }
//    public Boolean insertData(String username , String  password, String email){
//        SQLiteDatabase mydb = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("username" , username);
//        contentValues.put("password" , password);
//        contentValues.put("email",email);
//        long result = mydb.insert("users",null,contentValues);
//        return result != -1;
//
//    }
//    public Boolean checkusername(String username){
//        SQLiteDatabase mydb = this.getWritableDatabase();
//        Cursor cursor = mydb.rawQuery("Select * from users where username = ?", new String[]{username});
//        if (cursor.getCount() > 0) return true;
//        else {
//            return false;
//        }
//
//    }
//    public Boolean checkusernamepassword(String username , String password){
//        SQLiteDatabase mydb = this.getWritableDatabase();
//        Cursor cursor = mydb.rawQuery("Select * from users where username = ? and password = ? ",new String[]{username,password} );
//        if (cursor.getCount() > 0) {
//            return true;
//        }
//        else  {
//            return false;
//        }
//    }
//}
