package com.dolson.connectfour;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

public class Board extends View implements OnClickListener
{
	private int[][] board;
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private int placeChecker = -1;
	private float diameter;
	private int space;
	
	public Board(Context context)
	{
		super(context);
		board = new int[6][7];
			for (int i = 0; i < board.length; i++)
				for (int j = 0; j < board[0].length; j++)
					board[i][j] = 0;
	}

	@Override
	protected void onDraw(Canvas canvas) {
	    super.onDraw(canvas);
	 
	    diameter = this.getWidth()/7;
	    //System.out.println(this.getWidth());
	    float radius = diameter/2;
	    //System.out.println(this.getHeight());
	    float spacing = (this.getWidth() - (diameter)*(float)7)/6;
	    System.out.println(spacing);
	    
	    for (int row = 1; row <= 6; row++)
	    {
	    	for (int i = 0; i < 7; i++)
	    	{
	    		// if board = 1 > red
	    		//if board = 2 > black
	    		//if board = 0 >white
	    		if (board[6-row][i] == 0)
	    			paint.setColor(Color.WHITE);
	    		else if (board[6-row][i] == 1)
	    			paint.setColor(Color.RED);
	    		else if (board[6-row][i] == 2)
	    			paint.setColor(Color.BLACK);
	    		
	    		canvas.drawCircle((i*diameter)+(radius)+spacing, (this.getHeight()-(diameter*row))+radius, radius, paint);
	    		
	    		    		
	    	}
	    }
	}
	

	protected void insert(int col, int player)
	{
		for (int i = board.length-1; i >= 0; i++)
		{
			if (board[i][col] == 0) //then empty space
			{
				board[i][col] = player;
				break;
			}
		}
	}
	
	/*
	 * 0 - Nobody won yet
	 * 1 - Player one won
	 * 2 - Player 2 won
	 */
	protected int checkWinner()
	{
		//check horizontal
		//check vertical
		//check forwardslash
		//check backslash
		
		return 0;
	}
	
	@Override
	public String toString()
	{
		String s = "";
		for (int row = 0; row < board.length; row++)
		{
			for (int col = 0; col < board[0].length; col++)
			{
				s += "|" + board[row][col];
			}
			
			s+="|/n";
		}
		return s;
	}

	@Override
	public void onClick(View v)
	{
		
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent me)
	{
		int xloc = (int)me.getX();
		space = (int)(xloc/diameter);
		System.out.println(space);
		
		
		return true;
	}
	
	
}