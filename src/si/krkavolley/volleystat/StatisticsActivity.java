package si.krkavolley.volleystat;

import java.util.ArrayList;

import si.krkavolley.volleystat.Entity.Player;
import si.krkavolley.volleystat.Entity.Stat;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Build;

public class StatisticsActivity extends Activity implements OnClickListener {

	LinearLayout serveButtonsContainer, receptionButtonsContainer, attackButtonsContainer;
	DatabaseHelper db;
	ArrayList players;
	ListView lv_players, lv_actionTypes;
	ArrayAdapter<String> arrayAdapter, actionTypesAdapter;
	// reception buttons
	
	// attack buttons
	
	// serve buttons

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics);

		serveButtonsContainer = (LinearLayout) findViewById(R.id.container_serve_buttons);
		attackButtonsContainer = (LinearLayout) findViewById(R.id.container_attack_buttons);
		receptionButtonsContainer = (LinearLayout) findViewById(R.id.container_reception_buttons);
		
		db = new DatabaseHelper(getApplicationContext());
		
		players = new ArrayList<Player>();
		players = (ArrayList<Player>) db.getAllPlayers();
		lv_players = (ListView) findViewById(R.id.listview_player_list);
		lv_players.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, players);

		lv_players.setAdapter(arrayAdapter);
		
		ArrayList<String> actionTypes = new ArrayList<String>();
		actionTypes.add("Serve");
		actionTypes.add("Reception");
		actionTypes.add("Attack");
		
		lv_actionTypes = (ListView)findViewById(R.id.listview_action_type);
		lv_actionTypes.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		actionTypesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, actionTypes);
		lv_actionTypes.setAdapter(actionTypesAdapter);
		
		lv_actionTypes.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (lv_actionTypes.getCheckedItemPosition()) {
				case 0:
					serveButtonsContainer.setVisibility(View.VISIBLE);
					receptionButtonsContainer.setVisibility(View.GONE);
					attackButtonsContainer.setVisibility(View.GONE);
					break;

				case 1:
					serveButtonsContainer.setVisibility(View.GONE);
					receptionButtonsContainer.setVisibility(View.VISIBLE);
					attackButtonsContainer.setVisibility(View.GONE);
					break;
				
				case 2:
					serveButtonsContainer.setVisibility(View.GONE);
					receptionButtonsContainer.setVisibility(View.GONE);
					attackButtonsContainer.setVisibility(View.VISIBLE);
					break;
					
				default:
					break;
				}
				
			}
		});
	}

	public void onClickStats_serve_0(View v) {
		db.writeServe(1, 1, 1, 1);
		Stat stat = db.getPlayerStats(1, 1);
		Toast.makeText(getApplicationContext(),
				"Serve 0: " + stat.getServe_0(), Toast.LENGTH_SHORT).show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.statistics, menu);
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

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
//		switch (v.getId()) {
//
//	    case R.id.oneButton:
//	        // do your code
//	        break;
//
//	    case R.id.twoButton:
//	        // do your code
//	        break;
//
//	    case R.id.threeButton:
//	        // do your code
//	        break;
//
//	    default:
//	        break;
//		}
	}

}
