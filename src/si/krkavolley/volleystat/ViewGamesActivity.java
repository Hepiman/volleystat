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
import android.widget.AdapterView;
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
		
		lv.setLongClickable(true);
		lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						if (position >= 0) {
							Game g = (Game) lv.getAdapter()
									.getItem(position);
							displayGameStats(g.getId(),g.getName());
							
						}
						return true;
					}
				});
		
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
				if (lv.getCheckedItemPosition() >= 0){
					Game g = (Game) lv.getAdapter().getItem(lv.getCheckedItemPosition());
					db.removeGame(g.getId());;
					Toast.makeText(getApplicationContext(), g.getId()+ " " +g.getName(), Toast.LENGTH_SHORT).show();
					games.clear();
					games = (ArrayList<Game>) db.getAllGames();
					notifyDataSetChanged();
				}else{
					Toast.makeText(getApplicationContext(), "Please select a game",  Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		startStats = (Button) findViewById(R.id.button_game_start_stats);
		startStats.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i;
				lv = (ListView) findViewById(R.id.listview_viewGames);
				
				if (lv.getCheckedItemPosition() >= 0){
					Game g = (Game) lv.getAdapter().getItem(lv.getCheckedItemPosition());
					Bundle b = new Bundle();
					b.putInt("gameId", g.getId()); //Your id
					b.putString("gameName", g.getName());
					b.putString("gameScore", g.getScore());
					//Put your id to your next Intent
		        	i = new Intent(ctx, StatisticsActivity.class);
		        	i.putExtras(b); 
		        	startActivity(i);
				}else{
					Toast.makeText(getApplicationContext(), "Please select a game",  Toast.LENGTH_SHORT).show();
				}
				
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
	
	private void displayGameStats(long gameId, String gameName) {

		Intent i;
		i = new Intent(getApplicationContext(), WebViewFullGameStatActivity.class);
		Bundle b = new Bundle(); 
		b.putLong("gameId", gameId);
		b.putString("gameName", gameName);

		i.putExtras(b);
		startActivity(i);

	}
	

}
