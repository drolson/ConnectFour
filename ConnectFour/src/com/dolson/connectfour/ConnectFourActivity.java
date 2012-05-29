package com.dolson.connectfour;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ConnectFourActivity extends Activity
{
	Game game;
	Board board;
	Player p1 = new Player(0, "test", 0);
	Player p2 = new Player(1, "test2", 1);
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        
        //board = new Board(this);
        game = new Game(this, p1, p2);
        board = game.getBoard();
        
        //set the players names along with the colors that they are
        TextView tv1 = (TextView)findViewById(R.id.player1);
        tv1.setText(p1.getName());
        tv1 = (TextView)findViewById(R.id.player2);
        tv1.setText(p2.getName());
        
        LinearLayout main = (LinearLayout)findViewById(R.id.board_view);
        main.addView(board);
        board.setOnClickListener(board);
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
            //System.out.println("WE FINALLY GOT TO HERE BITCH");
            return true;
        case R.id.settings:
        	//System.out.println("Settings bitch");
        	return true;
    	}
    	return false;
    }
    
    
}
