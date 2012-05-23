package com.dolson.connectfour;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class ConnectFourActivity extends Activity
{
	Board board;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        //Display display = getWindowManager().getDefaultDisplay(); 
        //int width = display.getWidth();
        //int height = display.getHeight();
        //System.out.println(width);
        //System.out.println(height);
        
        board = new Board(this);
        
        
        //GridView gridview = (GridView) this.findViewById(R.id.boardview);
        LinearLayout main = (LinearLayout)findViewById(R.id.main_view);
        main.addView(board);
        board.setOnClickListener(board);
        
        
        //System.out.println(board.toString());
    }

}
