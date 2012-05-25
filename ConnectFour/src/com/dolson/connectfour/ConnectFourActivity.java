package com.dolson.connectfour;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	MenuInflater mi = this.getMenuInflater();
    	mi.inflate(R.menu.menu, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch (item.getItemId()) 
    	{
        case R.id.new_game:
            	System.out.println("WE FINALLY GOT TO HERE BITCH");
            	return true;	 
    	}
    	return true;
    }
    
    
}
