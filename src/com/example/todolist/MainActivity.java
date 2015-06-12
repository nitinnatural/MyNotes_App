package com.example.todolist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends OptionMenu {	
	public static ListView listView;
	CustomAdapter adapter;
	public static MyOpenHelper myopenhelper;
	List<Todolist> allToDos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listView = (ListView) findViewById(R.id.listView);
		
		
		/*
		 * Testing MyOpenHelper
		 */
		
		// creating object of open helper
		myopenhelper = new MyOpenHelper(this);		
        allToDos = myopenhelper.readAllTodo(); // getting all to-do list
		
		adapter = new CustomAdapter(this, R.layout.row, allToDos);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() { // onItemClick Listener

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (view.getId()) {
				case R.id.tvToDoContent:
					//Toast.makeText(getApplicationContext(), "you click on "+id, Toast.LENGTH_SHORT).show();
					break;

				case R.id.ibDel:
					//Toast.makeText(getApplicationContext(), "Del button", Toast.LENGTH_SHORT).show();
					break;
					
				case R.id.ibEdit:
					//Toast.makeText(getApplicationContext(), "Edit button", Toast.LENGTH_SHORT).show();
					break;
				
				case R.id.listView:
					//Toast.makeText(getApplicationContext(), "Edit button", Toast.LENGTH_SHORT).show();
					break;
				}
								
			}
		});		
	}
	
	public void refreshActivity(){
		finish();
		startActivity(getIntent());
	}
	
	@Override
	protected void onResume() {		
		super.onResume();
		//Log.d("todlist", "onResume executed");
		super.onResume();
		this.onCreate(null);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		//Log.d("todolist", "onPause() is executed");
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		//Log.d("todolist", "onStart is executed");
		allToDos = myopenhelper.readAllTodo(); // getting all to-do list
		adapter = new CustomAdapter(this, R.layout.row, allToDos);
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();	// of no use	
	}
	
	
	
	@Override
	protected void onStop() {
		super.onStop();
		//Log.d("todolist","onStop() get Executed");
	}
	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d("todolist", "onRestart() get executed");
		listView.invalidateViews();
	}
	
	
	
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.ibDel:
			//Toast.makeText(getApplicationContext(), "list deleted", Toast.LENGTH_SHORT).show();
			break;
		
		case R.id.btnAddNew:
			//Toast.makeText(getApplicationContext(), "AddNew is clicked", Toast.LENGTH_SHORT).show();
			Intent addIntent = new Intent(this, AddTodoActivity.class);
			startActivity(addIntent);
			finish();
			break;
		case R.id.ibEdit:
			String tag = adapter.btnEdit.getTag().toString();
			//Toast.makeText(getApplicationContext(), ""+tag, Toast.LENGTH_SHORT).show();
			//Intent editIntent = new Intent(this, EditTodoAcivity.class);
			//Button btn = (Button) findViewById(R.id.btnUpdate);
	
			//editIntent.putExtra("TODO_CONTENT", value)
			//startActivity(editIntent);
			//finish();
		default:
			break;
		}
	}	
	
	public interface MainInterface{
		
	}
	
} // End of main activity

class CustomAdapter extends ArrayAdapter<Todolist>{
	public TextView toDoContent;
	public Button btnEdit;
	public Button btnDel;
	Context ctx;
	List<Todolist> toDoList;
	String tvContent;

	public CustomAdapter(Context context, int resource, List<Todolist> objects) {
		super(context, resource, objects);
		ctx = context;
		toDoList = objects;		
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row, parent, false);
		toDoContent = (TextView) rowView.findViewById(R.id.tvToDoContent);
		toDoContent.setText(toDoList.get(position).getContent());
		btnEdit = (Button) rowView.findViewById(R.id.ibEdit);
		
		// setting tag() to the edit button
		try {
			tvContent = toDoList.get(position).getContent();
			btnEdit.setTag(R.id.key1,tvContent);
			btnEdit.setTag(R.id.key2, position);
		} catch (Exception e) {
			//Log.d("todolist",""+e);
		}
		
		// setting on click listener on button edit
		btnEdit.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				try {
					String textViewContent =  (String) v.getTag(R.id.key1);
					int rowIndex = (int) v.getTag(R.id.key2);
					Toast.makeText(getContext(), ""+textViewContent, Toast.LENGTH_SHORT).show();
					Log.d("todolist","position"+rowIndex);
					Log.d("todolist",""+MainActivity.myopenhelper.rowId(textViewContent));
					
					//  set intent and put content
					Intent editIntent = new Intent(getContext(), EditTodoAcivity.class);
					editIntent.putExtra("content",textViewContent);
					editIntent.putExtra("rowId", MainActivity.myopenhelper.rowId(textViewContent));
					ctx.startActivity(editIntent);
				} catch (Exception e) {
					//Log.d("todolist", ""+e);
				}							
			}
		});
		

		btnDel = (Button) rowView.findViewById(R.id.ibDel);
		btnDel.setTag(R.id.key1,tvContent);
		btnDel.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(final View v) {	
				
				try {
					AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
					builder.setTitle(R.string.dilaog_title);		
					
					builder.setPositiveButton(R.string.dialog_delBtn, new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   
				               // User clicked OK button
				        	   String textViewContent =  (String) v.getTag(R.id.key1);
				        	   int rowId = MainActivity.myopenhelper.rowId(textViewContent);
				        	   final long long_rowId = (long) (rowId);	
				        	   MainActivity.myopenhelper.deleteTodo(long_rowId);
				        	   
				        	   // refresh activity
	
				       				
				        	   Intent intent = ((Activity) ctx).getIntent();
				        	   ((Activity) ctx).overridePendingTransition(0, 0);
				        	   intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				        	   ((Activity) ctx).finish();
				        	   ((Activity) ctx).overridePendingTransition(0, 0);
				        	   ctx.startActivity(intent);
				        	   
				           }
				       });
					
					builder.setNegativeButton(R.string.dialog_cancelBtn, new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				               // User cancelled the dialog
				           }
				       });
					
					builder.show();
					
				} catch (Exception e) {
					//Log.d("todolist", ""+e);
				}			
			}
		});
		return rowView;
	}
	
}


/*
 * i created a custom list adapter which has 2 text view and 2 button. i want upon clicking the button i want to start
 * another activity which takes the data of that particular list to a new activity
 * */