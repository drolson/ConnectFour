package com.dolson.connectfour;

public class ExpertStrategy implements Strategy
{
	Board board;
	Player p;
	
	public ExpertStrategy(Player p)
	{
		this.p = p;
		board = p.getBoard();
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
