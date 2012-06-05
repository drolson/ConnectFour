package com.dolson.connectfour;

public class ExpertStrategy implements Strategy
{
	Board board;
	public ExpertStrategy(Board board)
	{
		this.board = board;
	}
	
	@Override
	public void move()
	{
		// TODO Auto-generated method stub
		int move = 4;
		
		do 
		{
			move = (move+1)%7;
			
		} while (board.setSpace(move) == 1);
	}

}
