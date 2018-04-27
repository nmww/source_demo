package com.example.android.provider02;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class TestProvider extends ContentProvider {
	//SQLiteOpenHelper-建立資料庫PhoneContentDB和Table:Users
	private static class DatabaseHelper extends SQLiteOpenHelper {
		private static final String DATABASE_NAME = "PhoneContentDB";
		private static final int DATABASE_VERSION = 1;
		//建立PhoneContentDB資料庫
		private DatabaseHelper(Context ctx) {
			super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		}
		//建立Users表單
		@Override
		public void onCreate(SQLiteDatabase db) {
			String sql = "CREATE TABLE " + UserSchema.TABLE_NAME + " (" 
			+ UserSchema.ID  + " INTEGER primary key autoincrement, " 
			+ UserSchema.USER_NAME + " text not null, " 
			+ UserSchema.TELEPHONE + " text not null, " 
			+ UserSchema.ADDRESS + " text not null, "
			+ UserSchema.MAIL_ADDRESS + " text not null "+ ");";
			db.execSQL(sql);	
		}
		//更新新版本
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS test");
            onCreate(db);
		}
	}
	//定義DatabaseHelper類別變數 databaseHelper
    static DatabaseHelper databaseHelper;
    //實作Content Providers的onCreate()
    @Override
    public boolean onCreate() {
    	databaseHelper = new DatabaseHelper(getContext());
        return true;
    }
    public interface UserSchema {
		String TABLE_NAME = "Users";           //Table Name
		String ID = "_id";                    //ID
		String USER_NAME = "user_name";       //User Name
		String ADDRESS = "address";           //Address
		String TELEPHONE = "telephone";       //Phone Number
		String MAIL_ADDRESS = "mail_address"; //Mail Address
	}
    //實作Content Providers的insert()
    @Override
    public Uri insert(Uri uri, ContentValues values) {
    	SQLiteDatabase db = databaseHelper.getWritableDatabase();
		db.insert(UserSchema.TABLE_NAME, null, values);
		db.close();
		return null;
	}
    //實作Content Providers的query()
    @Override
    public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
    	SQLiteDatabase db = databaseHelper.getWritableDatabase();
		db.update(UserSchema.TABLE_NAME, values, selection ,null);
		db.close();
		return 0;
	}
    //實作Content Providers的delete()
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
    	SQLiteDatabase db = databaseHelper.getWritableDatabase();
		db.delete(UserSchema.TABLE_NAME, selection ,null);
		db.close();
		return 0;
	}
    //實作Content Providers的update()
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(UserSchema.TABLE_NAME);
        Cursor c = qb.query(db, projection, selection, selectionArgs, null,
                null, null);
        return c;
    }
    //實作Content Providers的getType()
    @Override
    public String getType(Uri uri) {
        // TODO Auto-generated method stub
        return null;
    }
}
