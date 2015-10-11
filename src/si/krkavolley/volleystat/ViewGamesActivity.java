package si.krkavolley.volleystat;
import java.util.ArrayList;

import si.krkavolley.volleystat.Entity.Game;
import si.krkavolley.volleystat.Entity.Player;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Build;

public class ViewGamesActivity extends Activity {

	Button addNew, startStats, btnRemove;
	Context ctx;
	ListView lv;
	DatabaseHelper db;
	ArrayList<Game> games; 
	ArrayAdapter<String> arrayAdapter;
	ArrayAdapter<Game> adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_games);
		
		ctx = getApplicationContext();
	    db = new DatabaseHelper(ctx);
		games = new ArrayList<Game>();
		games = (ArrayList<Game>) db.getAllGames();
		
		lv = (ListView) findViewById(R.id.listview_viewGames);
		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		adapter = new ArrayAdapter<Game>(this, android.R.layout.simple_list_item_single_choice, games);
		//arrayAdapter = new ArrayAdapter<String>(ViewGamesActivity.this, android.R.layout.simple_list_item_single_choice, games);
		lv.setAdapter(adapter);
		
		addNew = (Button) findViewById(R.id.add_new_game);
		addNew.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i;
	        	i = new Intent(ctx, CreateGameActivity.class);
	        	startActivity(i);
				
			}
		});
		
		btnRemove = (Button) findViewById(R.id.button_game_remove);
		btnRemove.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				lv = (ListView) findViewById(R.id.listview_viewGames);
				Game g = (Game) lv.getAdapter().getItem(lv.getCheckedItemPosition());
				
				db.removeGame(g.getId());;
				Toast.makeText(getApplicationContext(), g.getId()+ " " +g.getName(), Toast.LENGTH_SHORT).show();
				games.clear();
				games = (ArrayList<Game>) db.getAllGames();
				notifyDataSetChanged();
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_games, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	protected void notifyDataSetChanged() { 
    	if (adapter!=null && adapter instanceof ArrayAdapter) {
    		((ArrayAdapter<?>)adapter).notifyDataSetChanged();
    		adapter = new ArrayAdapter<Game>(this,
    				android.R.layout.simple_list_item_single_choice, games);

    		lv.setAdapter(adapter);
    	} else { 
    		Log.w("adapter", "Could not update list Array Adapter");
    	} 
	} 
	

}