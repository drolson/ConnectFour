package com.dolson.attachfour;

import java.util.Random;

/*
 * THis is a beginner strategy that is board aware. It will try and make a winning move, but it will not try to block the other player from making the winning move
 * 
 */
public class BeginnerStrategy implements Strategy
{
	Random random = new Random();
	int lastMove;
	int move;
	int count = 0;
	Player p;
	Board board;
	int yValue;
	
	public BeginnerStrategy(Player p)
	{
		this.p = p;
		board = p.getBoard();
	}
	
	@Override
	public void move()
	{
		lastMove = board.getSpace();
		boolean set = false;
		
		//see if we have 3 in a row from the last move
		int type = board.checkWinner(yValue, move, 3, board.getBoard(), false)[0];
		if (type > 0) //then we have a potential winning area
		{		
			set = true;
			if (type == 1) //horizontal
			{
				int start = 0;
				int count = 0;
				int [][] b = board.getBoard();
				for (int i = 0; i < b[yValue].length; i++)
				{
					if (b[yValue][i] == p.getID())
					{
						if (count == 0)
							start = i;
						count++;
					}
					else
					{
						start = 0;
						count = 0;
					}
					
					if (count == 3)
					{
						if (start-1 >= 0)
							move = start-1;
						else if (start+3 < b[yValue][i])
							move = start+3;
						System.out.println("horz move has been set " + move);
						if (board.getYValue(move) > yValue) //someone already placed a marker where we cant win there
						{
							set = false;
							System.out.println("cant win here");
						}
						
						break;
					}
				}
				if (count < 3) //then someone already filled in the space to win
				{
					set = false;
					System.out.println("changed out move");
				}
			}
			else if (type == 2) //vertical
			{
				//dont change where we placed it last unless that spot is filled now
				if (board.getBoard()[0][move] != -1)
					set = false;
				
			}
			else if (type == 3) //backslash
			{
				set = false;
			}
			else if (type == 4) //frontslash
			{
				set = false;
			}
		}
		
		do 
		{
			if (!set)
			{
				move = lastMove+random.nextInt(3)-1;
				System.out.println(move);
				count++;
				
				// 10 random tries before choosing the next available space
				if (count > 10)
				{
					for (int i = 0; i < board.getBoard()[0].length; i++)
					{
						if (board.getBoard()[0][i] == -1)
						{
							move = i;
							break;
						}
					}
				}
			}
			yValue = board.getYValue(move);
		} while (board.setSpace(move) == 1);
		count = 0;
	}

	@Override
	public void run()
	{
		lastMove = board.getSpace();
		boolean set = false;
		
		//see if we have 3 in a row from the last move
		int type = board.checkWinner(yValue, move, 3, board.getBoard(), false)[0];
		if (type > 0) //then we have a potential winning area
		{		
			set = true;
			if (type == 1) //horizontal
			{
				int start = 0;
				int count = 0;
				int [][] b = board.getBoard();
				for (int i = 0; i < b[yValue].length; i++)
				{
					if (b[yValue][i] == p.getID())
					{
						if (count == 0)
							start = i;
						count++;
					}
					else
					{
						start = 0;
						count = 0;
					}
					
					if (count == 3)
					{
						if (start-1 >= 0)
							move = start-1;
						else if (start+3 < b[yValue][i])
							move = start+3;
						System.out.println("horz move has been set " + move);
						if (board.getYValue(move) > yValue) //someone already placed a marker where we cant win there
						{
							set = false;
							System.out.println("cant win here");
						}
						
						break;
					}
				}
				if (count < 3) //then someone already filled in the space to win
				{
					set = false;
					System.out.println("changed out move");
				}
			}
			else if (type == 2) //vertical
			{
				//dont change where we placed it last unless that spot is filled now
				if (board.getBoard()[0][move] != -1)
					set = false;
				
			}
			else if (type == 3) //backslash
			{
				set = false;
			}
			else if (type == 4) //frontslash
			{
				set = false;
			}
		}
		
		do 
		{
			if (!set)
			{
				move = lastMove+random.nextInt(3)-1;
				System.out.println(move);
				count++;
				
				// 10 random tries before choosing the next available space
				if (count > 10)
				{
					for (int i = 0; i < board.getBoard()[0].length; i++)
					{
						if (board.getBoard()[0][i] == -1)
						{
							move = i;
							break;
						}
					}
				}
			}
			yValue = board.getYValue(move);
		} while (board.setSpace(move) == 1);
		System.out.println("beginner set move");
		count = 0;
		
	}
}
