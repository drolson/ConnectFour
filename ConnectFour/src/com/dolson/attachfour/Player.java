package com.dolson.attachfour;



public class Player
{
	private String name = "";
	private int id;
	private Strategy strategy;
	int strat = -1;
	Board board;
	
	public Player(String playername, int id, int strat, Board board)
	{
		this.name = playername;
		this.id = id;
		this.strat = strat;
		this.board = board;
		this.setStrategy(strat);
	}
	
	public String getName()
	{
		return name;
	}
	
	protected void setName(String name)
	{
		this.name = name;
	}
	
	public int getID()
	{
		return id;
	}
	
	public void setID(int id)
	{
		this.id = id;
	}
	
	protected void notifyTurn()
	{
		//if its not human, then tell them to move
		if (strategy != null)
			new Thread(strategy).start();
	}
	
	protected int getStrat()
	{
		return strat;
	}
	
	protected void setStrat(int strat)
	{
		this.strat = strat;
		this.setStrategy(strat);
	}
	
	protected boolean isHuman()
	{
		if (strat == 0)
			return true;
		else
			return false;
	}
	
	protected void setStrategy(int strat)
	{
		if (strat == 0)
			strategy = null;
		else if (strat == 1)
			strategy = new BeginnerStrategy(this);
		else if (strat == 2)
			strategy = new IntermediateStrategy(this);
		else if (strat == 3)
			strategy = new ExpertStrategy(this);
	}
	
	public Board getBoard()
	{
		return board;
	}
}
