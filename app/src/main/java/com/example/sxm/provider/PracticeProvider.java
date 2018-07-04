package com.example.sxm.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.sxm.utils.LogUtils;

import java.util.List;

public class PracticeProvider extends ContentProvider {
    private static final String TAG = "PracticeProvider";
    private static final int VERSION = 1;//数据库版本
    private static final String DATABASE_NAME = "practice.db";
    private static final String TABLE_ONE = "table1";
    private static final String TABLE_TWO = "table2";
    private static final int MATCH_1 = 1;
    private static final int MATCH_2 = 2;
    public static final String URI = "com.example.sxm.provider.PracticeProvider";
    private DataBaseHelper mDB = null;
    private UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    {
        sURIMatcher.addURI(URI, "table1", MATCH_1);
        sURIMatcher.addURI(URI, "table2", MATCH_2);
    }

    @Override
    public boolean onCreate() {
        mDB = new DataBaseHelper(getContext(), DATABASE_NAME, null, VERSION);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Uri result = null;
        int matchCode = sURIMatcher.match(uri);
        LogUtils.d(TAG, "insert matchCode:" + matchCode);
        if (matchCode == MATCH_2) {
            SQLiteDatabase db = mDB.getWritableDatabase();
            long rowId = db.insert(TABLE_TWO, null, values);
            if (rowId > 0) {
                result = Uri.parse("content://practice/" + TABLE_TWO + "/" + rowId);
            }
        }
        return result;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    private class DataBaseHelper extends SQLiteOpenHelper {
        private Context mContext = null;

        public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_ONE
                    + "(_id INTERGER PRIMARY KEY ," +
                    "type TEXT ," +
                    "click_count INTERGER);");
            db.execSQL("CREATE TABLE " + TABLE_TWO
                    + "(_id INTERGER PRIMARY KEY ," +
                    "type TEXT ," +
                    "size INTERGER);");
            initDataBaseTables();
        }

        private void initDataBaseTables() {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
