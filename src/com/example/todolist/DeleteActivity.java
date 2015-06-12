package com.example.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DeleteActivity extends Activity {
	Button btnCancel;
	Button btnDelete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete);
		
		// initialize variable
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnDelete = (Button) findViewById(R.id.btnDelete);		
		
		btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		btnDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				
				Intent intent = getIntent();
				//String content = intent.getStringExtra("content");	
				int rowId = intent.getIntExtra("rowId", -1);
				final long long_rowId = (long) (rowId);	
				
				MyOpenHelper openHelper =new MyOpenHelper(getApplicationContext());
				openHelper.deleteTodo(long_rowId);
				finish();
				
			}
		});
		
	}

}
