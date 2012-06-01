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
		board.setSpace(1);
	}

}
