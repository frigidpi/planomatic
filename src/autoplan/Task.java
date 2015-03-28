package autoplan;

import java.util.*;
import java.io.*;
import java.text.*;

public class Task extends Slot{
	//constructor
	public Task( String startTimeString, String endTimeString, String name, String text, int importance) throws ParseException {
		super( startTimeString, endTimeString );
		this.name = name;
		this.text = text;
		this.importance = importance;
	}
	public Task( String startTimeString, long duration, String name, String text, int importance ) throws ParseException {
		super( startTimeString, duration );
		this.name = name;
		this.text = text;
		this.importance = importance;		
	}	
	
	//private fields
	private String name;
	private String text;
	private int importance;
	
	//access
	public String getName(){
		return name;
	} 
	public String getText(){
		return text;
	}
	public int getImportance(){
		return importance;
	}
	
	//change
	public void setName( String newName ){
		name = newName;
	} 
	public void setText( String newText ){
		text = newText;
	} 	
	public void setImportance( int newImportance ){
		importance = newImportance;
	} 
}
