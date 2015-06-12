package com.example.todolist;

import android.app.Activity;
import android.app.PendingIntent.OnFinished;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTodoActivity extends OptionMenu {
	EditText etAdd;
	String todoContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_todo);		
	}
	
	public void onClick(View view){
		MyOpenHelper myopenhelper = new MyOpenHelper(this); // object of SQLiteOpenHelper
		etAdd = (EditText) findViewById(R.id.etUpdate);
		todoContent = etAdd.getText().toString();
		//Log.d("todolist", ""+todoContent);
		myopenhelper.addNewTodo(new Todolist(todoContent));  // create / insert into database
		
		// code to kill current activity and restarting main activity.
		Intent mainActivityIntent = new Intent(this, MainActivity.class);
		startActivity(mainActivityIntent);
		finish();
	}

}
