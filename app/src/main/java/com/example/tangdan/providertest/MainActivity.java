package com.example.tangdan.providertest;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{

    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addData=(Button)findViewById(R.id.add_data);
        addData.setOnClickListener(this);
        Button queryData=(Button)findViewById(R.id.query_data);
        queryData.setOnClickListener(this);
        Button updateData=(Button)findViewById(R.id.update_data);
        updateData.setOnClickListener(this);
        Button deleteData=(Button)findViewById(R.id.delete_data);
        deleteData.setOnClickListener(this);
        Button clearAll=(Button)findViewById(R.id.clear_all);
        clearAll.setOnClickListener(this);
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.add_data:
                Uri uri=Uri.parse("content://com.example.tangdan.databasetest.provider/book");
                ContentValues values = new ContentValues();
                values.put("name", "A Clash of Kings");
                values.put("author", "George Martin");
                values.put("pages", 1040);
                values.put("price", 22.85);
                Uri newUri = getContentResolver().insert(uri,values);
                newId=newUri.getPathSegments().get(1);
                break;
            case R.id.query_data:
                Uri uri1=Uri.parse("content://com.example.tangdan.databasetest.provider/book");
                Cursor cursor=getContentResolver().query(uri1,null,null,null,null);
                if(cursor!=null){
                    while(cursor.moveToNext()){
                        String name=cursor.getString(cursor.getColumnIndex("name"));
                        String author=cursor.getString(cursor.getColumnIndex("author"));
                        int pages=cursor.getInt(cursor.getColumnIndex("pages"));
                        double price=cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity",name+","+author+","+pages+","+price);
                    }
                }
                cursor.close();
                break;
            case R.id.update_data:
                Uri uri2=Uri.parse("content://com.example.tangdan.databasetest.provider/book/"+newId);
                ContentValues values1=new ContentValues();
                values1.put("name","A Storm of Sword");
                values1.put("pages",1216);
                values1.put("price",24.05);
                getContentResolver().update(uri2, values1, null, null);
                break;
            case R.id.delete_data:
                Uri uri3=Uri.parse("content://com.example.tangdan.databasetest.provider/book/"+newId);
                getContentResolver().delete(uri3,null,null);
                break;
            case R.id.clear_all:
                Uri uri4=Uri.parse("content://com.example.tangdan.databasetest.provider/book");
                getContentResolver().delete(uri4,null,null);
        }
    }
}
