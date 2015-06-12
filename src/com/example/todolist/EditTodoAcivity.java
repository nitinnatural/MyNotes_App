package com.example.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditTodoAcivity extends OptionMenu {
	EditText etUpdate;
	Button btnUpdate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_todo);
		
		etUpdate = (EditText) findViewById(R.id.etUpdate);
		btnUpdate = (Button) findViewById(R.id.btnUpdate);
		
		// intent carrying id and content of a todo that need to be udated
		Intent intent = getIntent();
		String content = intent.getStringExtra("content");	
		int rowId = intent.getIntExtra("rowId", -1);
		final long long_rowId = (long) (rowId);		
		etUpdate.setText(content);
		
		btnUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String contentData  = etUpdate.getText().toString();
				MyOpenHelper openHelper = new MyOpenHelper(getApplicationContext());
				//Log.d("todolist", ""+contentData);
				//Log.d("todolist", ""+long_rowId);
				
				Todolist todoModel = new Todolist(contentData);	
				todoModel.setId(long_rowId);
				openHelper.updateTodo(todoModel);
				finish();
				
			}
		});
	}
	

}
