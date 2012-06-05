package com.dolson.connectfour;

public class IntermediateStrategy implements Strategy
{
	Board board;
	public IntermediateStrategy(Board board)
	{
		this.board = board;
	}
	
	@Override
	public void move()
	{
		int move = 2;
		
		do 
		{
			move = (move+1)%7;
			
		} while (board.setSpace(move) == 1);
	}

}
