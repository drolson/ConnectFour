package com.dolson.connectfour;

public class ExpertStrategy implements Strategy
{
	Board board;
	Player p;
	private final int MAX = Integer.MAX_VALUE;
	private final int MIN = Integer.MIN_VALUE;
	//int [][] tb = new int[6][7];
	int [][] b = new int[6][7];
	
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
			move = minimax(board.getBoard(), 5);
			System.out.println("trying expert move " + move);
			
		} while (board.setSpace(move) == 1);
	}

	private void addPiece(int col, int player)
	{
		for (int i = b.length-1; i >= 0; i--)
		{
			if (b[i][col] == -1)
			{
				b[i][col] = player;
				break;
			}
		}
	}
	
	private void removePiece(int col)
	{
		for (int i = 0; i < b.length; i++)
		{
			if (b[i][col] != -1)
			{
				b[i][col] = -1;
				break;
			}
		}
	}
	
	private int minimax(int [][] tb, int depth)
	{
		//low level copy of the board, so we dont actually edit the board
		for (int i = 0; i < tb.length; i++)
		{
			for (int j = 0; j < tb[i].length; j++)
			{
				if (tb[i][j] == -1)
					b[i][j] = -1;
				else if (tb[i][j] == 0)
					b[i][j] = 0;
				else if (tb[i][j] == 1)
					b[i][j] = 1;
			}
		}
		
		if (depth <= 0) //invalid call to the function
		{
			return -1;
		}
		
		int max = MIN;
		int index = 3; //start at center column
		
		for (int i = 0; i < b[0].length; i++)
		{
			if (b[0][i] == -1) //then not full
			{
				addPiece(i, p.getID());
				int strength = minNode(depth-1, max);
				if (strength > max)
				{
					index = i;
					max = strength;
				}
				removePiece(i);
			}
		}
		
		return index;
	}
	
	private int minNode(int depth, int parentMin)
	{
		//System.out.println("max");
		if (depth <= 0)
		{
			return winner();
		}
		int max = MIN;
		
		for (int i = 0; i < b[0].length; i++)
		{
			if (b[0][i] == -1) //then not full
			{
				addPiece(i, (p.getID()+1)%2);
				int strength = maxNode(depth-1, max);
				if (strength > parentMin)
				{
					removePiece(i);
					return strength;
				}
				if(strength > max)
                {
                    max = strength;
                }
				removePiece(i);
			}
		}
		return max;
	}
	
	private int maxNode(int depth, int parentMax)
	{
		//System.out.println("min");
		if (depth <= 0)
		{
			return winner();
		}
		int min = MAX;
		
		for (int i = 0; i < b[0].length; i++)
		{
			if (b[0][i] == -1) //then not full
			{
				addPiece(i, p.getID());
				int strength = minNode(depth-1, min);
				if (strength < parentMax)
				{
					removePiece(i);
					return strength;
				}
				if(strength < min)
                {
                    min = strength;
                }
				removePiece(i);
			}
		}
		return min;
	}
	
	private int winner()
	{
		for (int i = 0; i < b.length; i++)
		{
			for (int j = 0; j < b[i].length; j++)
			{
				if (i == 0 || j == 0)
				{
					if (this.p.getID() == board.checkWinner(i, j, 4, b))
					{
						//System.out.println("possible winner");
						return MAX;
					}
					else if (((this.p.getID()+1)%2) == board.checkWinner(i, j, 4, b))
					{
						//System.out.println("possible winner1");
						return MIN;
					}
				}
			}
		}
		return 0;
	}
}
