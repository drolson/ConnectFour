package com.dolson.connectfour;

public class ExpertStrategy implements Strategy
{
	Board board;
	Player p;
	private final int MAX = Integer.MAX_VALUE;
	private final int MIN = -1*MAX;
	//int [][] tb = new int[6][7];
	int [][] b = new int[6][7];
	int myID;
	int otherID;
	int main;
	
	public ExpertStrategy(Player p)
	{
		this.p = p;
		board = p.getBoard();
		myID = p.getID();
		otherID = (myID+1)%2;
	}
	
	@Override
	public void move()
	{
		
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
		int index = 0; //start at center column
		
		for (int i = 0; i < b[0].length; i++)
		{
			if (b[0][i] == -1) //then not full
			{
				addPiece(i, myID);
				int strength = maxNode(depth-1, max);
				System.out.println("strength for " + i + " is: " + strength);
				if (strength > max)
				{
					index = i;
					max = strength;
					System.out.println("chaning the index value");
				}
				removePiece(i);
			}
		}
		
		return index;
	}
	
	private int maxNode(int depth, int parentMax)
	{
		//System.out.println("min");
		if (depth <= 0)
		{
			return winner(otherID);
		}
		//int min = MIN;
		int max = MIN;
		
		for (int i = 0; i < b[0].length; i++)
		{
			if (b[0][i] == -1) //then not full
			{
				addPiece(i, otherID);
				int strength = minNode(depth-1, max);
				if (strength > parentMax)
				{
					removePiece(i);
					max = strength;
				}
				/*if(strength > max)
                {
                    max = strength;
                }*/
				removePiece(i);
			}
		}
		return max;
	}
	
	private int minNode(int depth, int parentMin)
	{
		//System.out.println("max");
		if (depth <= 0)
		{
			return winner(myID);
		}
		//int max = MAX;
		int min = MAX;
		
		for (int i = 0; i < b[0].length; i++)
		{
			if (b[0][i] == -1) //then not full
			{
				addPiece(i, myID);
				int strength = maxNode(depth-1, min);
				if (strength < parentMin)
				{
					removePiece(i);
					min = strength;
				}
				/*if(strength < min)
                {
                    min = strength;
                }*/
				removePiece(i);
			}
		}
		return min;
	}
	
	
	
	private int winner(int player)
	{
		for (int i = 0; i < b.length; i++)
		{
			for (int j = 0; j < b[i].length; j++)
			{
				if (i == 0 || j == 0)
				{
					//System.out.print("("+i+","+j+")");
					int [] winner = board.checkWinner(i, j, 4, b, false);
					if (winner[0] > 0) //make sure that there was a 4 in a row
					{
						if (myID != player && player == winner[1]) //block the win
						{
							System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
							return MIN;
						}
						else if (myID == player && player == winner[1])
						{
							System.out.println("possible winner*************************************");
							return MAX;
						}
					}
				}
			}
		}
		//System.out.println();
		return MIN;
	}

	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		int move = 4;
		
		do 
		{
			move = minimax(board.getBoard(), 2);
			System.out.println("trying expert move " + move);
			
		} while (board.setSpace(move) == 1);
		
		System.out.println("exiting out of settings a piece");
	}
}
