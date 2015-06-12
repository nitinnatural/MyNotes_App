package com.example.todolist;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyOpenHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "todo.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_NAME = "todolist" ;
	public static final String COLUMN_ID = "_id" ;
	public static final String COLUMN_CONTENT = "content" ;
	
	// create query to create table
	private static final String SQL_QUERY = "create table "
		      + TABLE_NAME + "(" + COLUMN_ID
		      + " integer primary key autoincrement, " + COLUMN_CONTENT + " text not null);"; 
	
	
	public MyOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// code to execute SQL_QUERY to create table
		db.execSQL(SQL_QUERY);		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// code to upgrade database
		Log.d(MyOpenHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);		
	}
	
	// method to create a TO-DO
	public long addNewTodo(Todolist todolist){
	    SQLiteDatabase dbWriteAccess = this.getWritableDatabase();	    
	    ContentValues values = new ContentValues();
	    values.put(COLUMN_CONTENT, todolist.getContent());
	    long rowId = dbWriteAccess.insert(TABLE_NAME, null, values); // if row not added rowId equal -1
	    if(rowId != -1){
	    	Log.d("todolist", "row inserted successfully "+rowId);
	    }
	    return rowId;
	}
	
	// method to list all TO-DO
	public List<Todolist> readAllTodo(){
		List<Todolist> todos = new ArrayList<Todolist>();
	    String selectQuery = "SELECT  * FROM " + TABLE_NAME;	 
	    Log.d("todolist", selectQuery);	 
	    SQLiteDatabase dbReadAccess = this.getReadableDatabase();
	    Cursor c = dbReadAccess.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
	        	Todolist todolistObj = new Todolist();
	        	todolistObj.setId(c.getInt((c.getColumnIndex(COLUMN_ID))));
	        	todolistObj.setContent((c.getString(c.getColumnIndex(COLUMN_CONTENT))));
	 
	            // adding to todo list
	        	todos.add(todolistObj);
	        } while (c.moveToNext());
	    }	 
	    return todos;
	}
	
	/*
	 * Updating a TO-DO list
	 */
	public int updateTodo(Todolist todolist) {
	    SQLiteDatabase dbWriteAccess = this.getWritableDatabase();	 
	    ContentValues values = new ContentValues();
	    values.put(COLUMN_CONTENT, todolist.getContent());	 
	    // updating row
	    return dbWriteAccess.update(TABLE_NAME, values, COLUMN_ID + " = ?",
	            new String[] { String.valueOf(todolist.getId()) });
	}
	
	
	/*
	 * Deleting a TO-DO
	 */
	public void deleteTodo(long todo_id) {
	    SQLiteDatabase dbWriteAccess = this.getWritableDatabase();
	    dbWriteAccess.delete(TABLE_NAME, COLUMN_ID + " = ?",
	            new String[] { String.valueOf(todo_id) });
	}	
	
	// closing database
    public void closeDB() {
        SQLiteDatabase dbReadAccess = this.getReadableDatabase(); 
        if (dbReadAccess != null && dbReadAccess.isOpen())
        	dbReadAccess.close();
    }
    
    // getting the id of a row in query return of WHERE clause
    public int rowId(String where){
    	String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_CONTENT + "=" +"'"+ where+"'";
    	Log.d("todolist", "query: "+query);
    	SQLiteDatabase dbReadable = this.getReadableDatabase();
    	Cursor c = dbReadable.rawQuery(query, null);
    	Todolist todolistObj = new Todolist();
    	if(c.moveToFirst()){
    		todolistObj.setRowId(c.getInt((c.getColumnIndex(COLUMN_ID))));    		
    	}    	
    	return todolistObj.getRowId();
    	
    }

} // END of class MyOpenHelper