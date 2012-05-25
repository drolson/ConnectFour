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
        

        
        board = new Board(this);
        
        
        LinearLayout main = (LinearLayout)findViewById(R.id.main_view);
        main.addView(board);
        board.setOnClickListener(board);

        startGame();
    }

    private void startGame()
    {
    	/*while (!board.gameOver())
    	{
    		
    	}*/
    }
}
