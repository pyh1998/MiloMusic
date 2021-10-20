package com.example.social_network_app.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//code in this class are based of  the code from CSDN
//url:https://cloud.tencent.com/developer/article/1394216
public class SQLiteHelper extends SQLiteOpenHelper {
        private static final String DB_NAME = "user.db";//
        private static final String UserInfoTBL_NAME = "User";//
    private static final String CREATE_UserInfoTBL = " create table "
            + " User(useNo integer primary key ,email text, password text) ";

        private SQLiteDatabase db;
        public SQLiteHelper(Context c) {
                  super(c, DB_NAME, null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            this.db = db;
            db.execSQL(CREATE_UserInfoTBL);
        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public void close() {
            if (db != null)
                db.close();
        }

        public long UserInfo_insert(ContentValues values) {
            long flag = -1;
            SQLiteDatabase db = getWritableDatabase();
            flag = db.insert(UserInfoTBL_NAME, null, values);
            db.close();
            return flag;
         }


        public Cursor UserInfo_query() {
            SQLiteDatabase db = getReadableDatabase();
            Cursor c = db.query(UserInfoTBL_NAME, null, null, null, null, null, null);
            return c;
        }


        public long UserInfo_del(int id) {
            long flag = -1;
            if (db == null)
                db = getWritableDatabase();
            flag = db.delete(UserInfoTBL_NAME, "id=?", new String[] { String.valueOf(id) });
            return flag;
        }

        public long UserInfo_update(int id, ContentValues values) {
            long flag = -1;
            if (db == null)
                db = getWritableDatabase();

            flag = db.update(UserInfoTBL_NAME, values,"id=?", new String[] { String.valueOf(id) });
            return flag;
        }


}
