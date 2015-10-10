package si.krkavolley.volleystat;

import java.util.ArrayList;
import java.util.List;

import si.krkavolley.volleystat.Entity.Player;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class PlayersActivity extends Activity {
	ListView lv;
	Button btnAdd, btnRemove;
	EditText textName, textDesc;
	DatabaseHelper db;
	ArrayList players;
	ArrayAdapter<String> arrayAdapter;
	ArrayList<String> playerList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_players);

		db = new DatabaseHelper(getApplicationContext());
		players = new ArrayList<Player>();
		players = (ArrayList<Player>) db.getAllPlayers();
		
		lv = (ListView) findViewById(R.id.listview_players);
		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, players);

		lv.setAdapter(arrayAdapter);
		
		textName = (EditText) findViewById(R.id.players_input_name);
		textDesc = (EditText) findViewById(R.id.players_input_desc);
		btnAdd = (Button) findViewById(R.id.button_add_player);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	//Toast.makeText(context, "klik shraniIgralca", Toast.LENGTH_SHORT).show();
            	String playerName = textName.getText().toString();
            	String playerDesc = textDesc.getText().toString();
            	if(playerName.length()>0){
            		Player p = new Player();
            		p.setName(playerName);
            		p.setDescription(playerDesc);
            		db.createPlayer(p);
            		Log.d("addPlayer", playerName + ", " + playerDesc);
            		textName.setText("");
            		textDesc.setText("");
            		
            		players = (ArrayList<Player>) db.getAllPlayers();
            		Toast.makeText(getApplicationContext(), "Igralci: "+players.size(), Toast.LENGTH_LONG).show();
            		
            		notifyDataSetChanged(); 
            		
            	}
            	else 
            		Log.d("choice", "Vnesi ime igralca!");
            	
            }
        });
        btnRemove = (Button) findViewById(R.id.button_remove_player);
        btnRemove.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				lv = (ListView) findViewById(R.id.listview_players);
				Player p = (Player) lv.getAdapter().getItem(lv.getCheckedItemPosition());
				Toast.makeText(getApplicationContext(), p.getId()+ " " +p.getName(), Toast.LENGTH_SHORT).show();
				db.removePlayer(p.getId());
				players.clear();
				players = (ArrayList<Player>) db.getAllPlayers();

        		notifyDataSetChanged(); 
				Log.d("players size", ""+players.size());
				
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.players, menu);
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
    	if (arrayAdapter!=null && arrayAdapter instanceof ArrayAdapter) {
    		((ArrayAdapter<?>)arrayAdapter).notifyDataSetChanged();
    		arrayAdapter = new ArrayAdapter<String>(this,
    				android.R.layout.simple_list_item_single_choice, players);

    		lv.setAdapter(arrayAdapter);
    	} else { 
    		Log.w("adapter", "Could not update list Array Adapter");
    	} 
	} 
	

}
