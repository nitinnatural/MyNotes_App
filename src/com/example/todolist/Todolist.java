package com.example.todolist;
/*
 * MODEL CLASS
 * Getter and Setter for all the columns + extraVariable
 * in our table.
 */

public class Todolist {
	private long id;
	private String content;
	private int rowId;
	
	// constructor
	public Todolist() {
	}
	
	public Todolist(String value){
		this.content = value;
	}
	
	// setter methods
	public void setId(long value){
		this.id = value;
	}
	
	public void setContent(String value){
		this.content = value;
	}
	
	public void setRowId(int value){
		this.rowId = value;
	}
	
	// getter methods
	public long getId(){
		return id;
	}
	
	public String getContent(){
		return content;
	}
	
	public int getRowId(){
		return rowId;
	}
	
}
