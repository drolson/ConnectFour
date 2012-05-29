package com.dolson.connectfour;

public class Player
{
	private String name = "";
	private int color = -1;
	private int id;
	
	public Player(int color, String playername, int id)
	{
		this.color = color;
		this.name = playername;
		this.id = id;
	}
	
	protected String getName()
	{
		return name;
	}
}
