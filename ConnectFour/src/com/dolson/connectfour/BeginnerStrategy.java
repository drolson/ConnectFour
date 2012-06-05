package com.dolson.connectfour;

import android.view.MotionEvent;


public class BeginnerStrategy implements Strategy
{

	Board board;
	public BeginnerStrategy(Board board)
	{
		this.board = board;
	}
	
	@Override
	public void move()
	{
		int move = -1;
		
		do 
		{
			move = (move+1)%7;
			
		} while (board.setSpace(move) == 1);
	}

}
