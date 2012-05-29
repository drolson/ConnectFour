package com.dolson.connectfour;

import android.content.Context;

public class Game
{
	Player p1;
	Player p2;
	int turn = -1;
	Board board;
	
	public Game(Context context, Player p1, Player p2)
	{
		board = new Board(context);
		this.p1 = p1;
		this.p2 = p2;
	}
	
	protected Board getBoard()
	{
		return board;
	}
}
