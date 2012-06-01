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
		board.setSpace(0);
	}

}
