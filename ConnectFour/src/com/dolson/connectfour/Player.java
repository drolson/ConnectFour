package com.dolson.connectfour;


public class Player
{
	private String name = "";
	private int id;
	private Strategy strategy;
	int strat = -1;
	
	public Player(String playername, int id, int strat, Board board)
	{
		this.name = playername;
		this.id = id;
		this.strat = strat;
		
		if (strat == 1)
			strategy = new BeginnerStrategy(board);
		else if (strat == 2)
			strategy = new IntermediateStrategy(board);
		else if (strat == 3)
			strategy = new ExpertStrategy(board);
	}
	
	protected String getName()
	{
		return name;
	}
	
	public int getID()
	{
		return id;
	}
	
	protected void notifyPlayerTurn()
	{
		//if its not human, then tell them to move
		if (strategy != null)
			strategy.move();
	}
	
	protected boolean isHuman()
	{
		if (strat == 0)
			return true;
		else
			return false;
	}
}
