package com.dolson.connectfour;

public class IntermediateStrategy implements Strategy
{
	Board board;
	Player p;
	
	public IntermediateStrategy(Player p)
	{
		this.p = p;
		board = p.getBoard();
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
