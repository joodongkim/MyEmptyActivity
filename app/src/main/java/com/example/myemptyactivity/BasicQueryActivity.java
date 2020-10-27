package com.example.myemptyactivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class BasicQueryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_query);

        SchemaHelper sch = new SchemaHelper(this);
        SQLiteDatabase sqdb = sch.getWritableDatabase();

        /*
         * SELECT 쿼리
         */

        Log.d("SCHEMA", "METHOD 1");

        //방법 #1 - SQLITEDATABASE RAWQUERY()
        Cursor c = sqdb.rawQuery("SELECT * from " + StudentTable.TABLE_NAME, null);
        while(c.moveToNext()) {
            int colid = c.getColumnIndex(StudentTable.NAME);
            String name = c.getString(colid);
            Log.d("SCHEMA", "GOT STUDENT " + name);
        }

        Log.d("SCHEMA", "METHOD 2");

        //방법 #2 - SQLITEDATABASE QUERY()
        c = sqdb.query(StudentTable.TABLE_NAME, null, null, null, null, null, null, null);
        while(c.moveToNext()) {
            int colid = c.getColumnIndex(StudentTable.NAME);
            String name = c.getString(colid);
            Log.d("SCHEMA", "GOT STUDENT " + name);
        }

        Log.d("SCHEMA", "METHOD 3");

        //방법 3# - SQLITEQUERYBUILDER
        String query = SQLiteQueryBuilder.buildQueryString(false,
                StudentTable.TABLE_NAME, null, null, null, null, null, null);
        Log.d("SCHEMA", query);
        c = sqdb.rawQuery(query, null);
        while(c.moveToNext()) {
            int colid = c.getColumnIndex(StudentTable.NAME);
            String name = c.getString(colid);
            Log.d("SCHEMA", "GOT STUDENT " + name);
            System.out.println("GOT STUDENT " + name);
        }

        /*
         * SELECT 컬럼 쿼리
         */

        System.out.println("METHOD 1");

        //방법 #1 - SQLITEDATABASE RAWQUERY()
        c = sqdb.rawQuery( "SELECT " +
                StudentTable.NAME + "," +
                StudentTable.STATE +
                " from " + StudentTable.TABLE_NAME, null);
        resultPrint("SELECT 컬럼 쿼리", c);

        System.out.println("METHOD 2");

        //방법 #2 - SQLITEDATABASE QUERY()
        c = sqdb.query(StudentTable.TABLE_NAME,
                new String[] {
                        StudentTable.NAME,
                        StudentTable.STATE
                },
                null, null, null, null, null);
        resultPrint("SELECT 컬럼 쿼리", c);

        System.out.println("METHOD 3");

        //방법 #3 - SQLITEQUERYBUILDER
        query = SQLiteQueryBuilder.buildQueryString(false,
                StudentTable.TABLE_NAME,
                new String[] {
                        StudentTable.NAME,
                        StudentTable.STATE
                }, null, null, null, null, null);
        System.out.println(query);
        c = sqdb.rawQuery(query, null);
        resultPrint("SELECT 컬럼 쿼리", c);

        /*
         * AND/OR 절
         */

        System.out.println("METHOD  1");

        //방법 #1 - SQLITEDATABSE RAWQUERY()
        c = sqdb.rawQuery("SELECT * from " + StudentTable.TABLE_NAME +
                " WHERE " + StudentTable.STATE + "= ?  OR " +
                StudentTable.STATE + "= ? OR " +
                StudentTable.STATE + "= ?", new String[] {
                        "IL", "AR" });
        resultPrint("AND/OR 절", c);

        System.out.println("METHOD 2");

        //방법 #2 - SQLITEDATABASE QUERY()
        c = sqdb.query(StudentTable.TABLE_NAME, null, StudentTable.STATE +
                "= ? OR " + StudentTable.STATE + "= ?",
                new String[] { "IL", "AR" }, null, null, null, null);
        resultPrint("AND/OR 절", c);

        System.out.println("METHOD 3");

        //방법 #3 - SQLITEQUERYBUILDER
        query = SQLiteQueryBuilder.buildQueryString(false,
                StudentTable.TABLE_NAME, null, StudentTable.STATE +
                        "='IL' OR " + StudentTable.STATE + "='AR'", null, null, null, null);

        c = sqdb.rawQuery(query, null);
        resultPrint("AND/OR 절", c);

        /**
         * DISTINCT 절
         */

        System.out.println("METHOD 1");

        //방법 #1 - SQLDATABASE RAWQUERY()
        c = sqdb.rawQuery("SELECT DISTINCT " + StudentTable.STATE + " from " +
                StudentTable.TABLE_NAME, null);
        resultPrint("DISTINCT 절", c);

        System.out.println("METHOD 2");
        //방법 #2 - SQLITEDATABASE QUERY()
        //좀 더 일반적인 QUERY() 메소드로 바꾼다.
        c = sqdb.query(true, StudentTable.TABLE_NAME, new String[] {
                StudentTable.STATE }, null, null, null, null, null, null);
        resultPrint("DISTINCT 절", c);

        System.out.println("METHOD 3");

        //방법 #3 - SQLITEQUERYBUILDER
        query = SQLiteQueryBuilder.buildQueryString(true,
                StudentTable.TABLE_NAME, new String[]{StudentTable.STATE},
                null, null, null, null, null);
        System.out.println(query);
        c = sqdb.rawQuery(query, null);
        resultPrint("DISTINCT 절", c);
    }

    public void resultPrint(String TAG, Cursor c) {
        while (c.moveToNext()) {
            int colid = c.getColumnIndex(StudentTable.NAME);
            int colid2 = c.getColumnIndex(StudentTable.STATE);
            String name = c.getString(colid);
            String state = c.getString(colid2);
            System.out.println(TAG + " GOT STUDENT " + name + " FROM " + state);
        }
    }
}