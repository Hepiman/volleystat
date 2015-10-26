package si.krkavolley.volleystat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import si.krkavolley.volleystat.Entity.Player;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.SparseBooleanArray;
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

public class AssignPlayersActivity extends Activity {

	ListView lv;
	Button btn;
	ArrayList players;
	ArrayAdapter adapter;
	DatabaseHelper db;
	ArrayList alreadyAssigned;
	int gameId;
	String gameName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_assign_players);

		Bundle b = getIntent().getExtras();
		gameId = b.getInt("gameId");
		gameName = b.getString("gameName");
		db = new DatabaseHelper(getApplicationContext());
		btn = (Button) findViewById(R.id.btn_assignPlayers);
		lv = (ListView) findViewById(R.id.lv_assignPlayers);
		lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		// TODO: mark already selected players

		players = (ArrayList) db.getAllPlayers();
		alreadyAssigned = (ArrayList) db.getAlreadyAssignedPlayers(gameId);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, players);

		lv.setAdapter(adapter);
		// lv.setItemChecked(1, true);
		for (int i = 0; i < players.size(); i++) {
			Player p = (Player) players.get(i);
			if (alreadyAssigned.contains(p.getId())) {
				lv.setItemChecked(i, true);
			}
		}
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO: get selected items and assign them to the game
				SparseBooleanArray checked = lv.getCheckedItemPositions();
				ArrayList<Player> toAssign = new ArrayList<Player>();

				for (int i = 0; i < lv.getAdapter().getCount(); i++) {
					if (checked.get(i)) {
						toAssign.add((Player) players.get(i));

					}
				}
				db.assignPlayersToGame(toAssign, gameId);
				Toast.makeText(getApplicationContext(),
						"Player selection for game: " + gameName + " saved.",
						Toast.LENGTH_SHORT).show();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.assign_players, menu);
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

}
