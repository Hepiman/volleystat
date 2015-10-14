package si.krkavolley.volleystat;

import java.util.ArrayList;

import si.krkavolley.volleystat.Entity.Player;
import si.krkavolley.volleystat.Entity.Stat;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class StatisticsActivity extends Activity implements OnClickListener {

	LinearLayout serveButtonsContainer, receptionButtonsContainer,
			attackButtonsContainer;
	DatabaseHelper db;
	ArrayList players;
	ListView lv_players, lv_actionTypes;
	ArrayAdapter<String> arrayAdapter, actionTypesAdapter;
	int gameId;
	String gameName, gameScore;
	TextView bottomText;
	// reception buttons
	Button btn_r0, btn_r1, btn_r2, btn_r3, btn_rwa, btn_rover;
	// attack buttons
	Button btn_a0, btn_a1, btn_a2, btn_a3, btn_ae, btn_aee, btn_ab, btn_abb;
	// serve buttons
	Button btn_s0, btn_s1, btn_s2, btn_s3, btn_swa, btn_sover, btn_se;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics);

		initializeButtons();

		Bundle b = getIntent().getExtras();
		gameId = b.getInt("gameId");
		gameName = b.getString("gameName");
		gameScore = b.getString("gameScore");

		bottomText = (TextView) findViewById(R.id.stats_bottom_text);
		bottomText.setText(gameName + " (" + gameScore + ")");

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

		lv_actionTypes = (ListView) findViewById(R.id.listview_action_type);
		lv_actionTypes.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		actionTypesAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, actionTypes);
		lv_actionTypes.setAdapter(actionTypesAdapter);

		lv_actionTypes.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// lv_actionTypes.getCheckedItemPosition()
				switch (position) {
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

	private void initializeButtons() {
		btn_r0 = (Button) findViewById(R.id.stats_btn_reception_0);
		btn_r0.setOnClickListener(this);
		btn_r1 = (Button) findViewById(R.id.stats_btn_reception_1);
		btn_r1.setOnClickListener(this);
		btn_r2 = (Button) findViewById(R.id.stats_btn_reception_2);
		btn_r2.setOnClickListener(this);
		btn_r3 = (Button) findViewById(R.id.stats_btn_reception_3);
		btn_r3.setOnClickListener(this);
		btn_rover = (Button) findViewById(R.id.stats_btn_reception_over);
		btn_rover.setOnClickListener(this);
		btn_rwa = (Button) findViewById(R.id.stats_btn_reception_wa);
		btn_rwa.setOnClickListener(this);

		btn_a0 = (Button) findViewById(R.id.stats_btn_attack_0);
		btn_a0.setOnClickListener(this);
		btn_a1 = (Button) findViewById(R.id.stats_btn_attack_1);
		btn_a1.setOnClickListener(this);
		btn_a2 = (Button) findViewById(R.id.stats_btn_attack_2);
		btn_a2.setOnClickListener(this);
		btn_a3 = (Button) findViewById(R.id.stats_btn_attack_3);
		btn_a3.setOnClickListener(this);
		btn_ae = (Button) findViewById(R.id.stats_btn_attack_e);
		btn_ae.setOnClickListener(this);
		btn_aee = (Button) findViewById(R.id.stats_btn_attack_ee);
		btn_aee.setOnClickListener(this);
		btn_ab = (Button) findViewById(R.id.stats_btn_attack_b);
		btn_ab.setOnClickListener(this);
		btn_abb = (Button) findViewById(R.id.stats_btn_attack_bb);
		btn_abb.setOnClickListener(this);

		btn_s0 = (Button) findViewById(R.id.stats_btn_serve_0);
		btn_s0.setOnClickListener(this);
		btn_s1 = (Button) findViewById(R.id.stats_btn_serve_1);
		btn_s1.setOnClickListener(this);
		btn_s2 = (Button) findViewById(R.id.stats_btn_serve_2);
		btn_s2.setOnClickListener(this);
		btn_s3 = (Button) findViewById(R.id.stats_btn_serve_3);
		btn_s3.setOnClickListener(this);
		btn_sover = (Button) findViewById(R.id.stats_btn_serve_over);
		btn_sover.setOnClickListener(this);
		btn_se = (Button) findViewById(R.id.stats_btn_serve_e);
		btn_se.setOnClickListener(this);
		btn_swa = (Button) findViewById(R.id.stats_btn_serve_wa);
		btn_swa.setOnClickListener(this);

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
	public void onClick(View v) {
		 //TODO: read playerId, setNumber and gameId
//		lv_players = (ListView) this.findViewById(R.id.listview_viewGames);
//		lv_actionTypes = (ListView) this.findViewById(R.id.listview_action_type);
//		Log.d("log", "" + lv_actionTypes.getSelectedItemPosition());
//		Log.d("log", "" + lv_players.getSelectedItemPosition());
		RadioGroup activeSet = (RadioGroup) this.findViewById(R.id.radioGroup_setNumbers);
		Log.d("log", "" + activeSet.getCheckedRadioButtonId());
		View selectedSetRadio = activeSet.findViewById(activeSet.getCheckedRadioButtonId());
		int setNumber = activeSet.indexOfChild(selectedSetRadio);
		Log.d("set", "active set: " + setNumber);
		//if(lv_actionTypes.getSelectedItemPosition() >= 0 && lv_players.getSelectedItemPosition() >= 0){
		switch (v.getId()) {

		// Serve
		case R.id.stats_btn_serve_0:
			Toast.makeText(getApplicationContext(), "servis 0",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_serve_1:
			Toast.makeText(getApplicationContext(), "servis 1",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_serve_2:
			Toast.makeText(getApplicationContext(), "servis 2",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_serve_3:
			Toast.makeText(getApplicationContext(), "servis 3",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_serve_wa:
			Toast.makeText(getApplicationContext(), "servis w/a",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_serve_over:
			Toast.makeText(getApplicationContext(), "servis over",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_serve_e:
			Toast.makeText(getApplicationContext(), "servis error",
					Toast.LENGTH_SHORT).show();
			break;

		// Reception
		case R.id.stats_btn_reception_0:
			Toast.makeText(getApplicationContext(), "reception 0",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_reception_1:
			Toast.makeText(getApplicationContext(), "reception 1",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_reception_2:
			Toast.makeText(getApplicationContext(), "reception 2",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_reception_3:
			Toast.makeText(getApplicationContext(), "reception 3",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_reception_wa:
			Toast.makeText(getApplicationContext(), "reception w/a",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_reception_over:
			Toast.makeText(getApplicationContext(), "reception over",
					Toast.LENGTH_SHORT).show();
			break;

		// Attack
		case R.id.stats_btn_attack_0:
			Toast.makeText(getApplicationContext(), "attack 0",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_attack_1:
			Toast.makeText(getApplicationContext(), "attack 1",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_attack_2:
			Toast.makeText(getApplicationContext(), "attack 2",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_attack_3:
			Toast.makeText(getApplicationContext(), "attack 3",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_attack_e:
			Toast.makeText(getApplicationContext(), "attack e",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_attack_ee:
			Toast.makeText(getApplicationContext(), "attack ee",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_attack_b:
			Toast.makeText(getApplicationContext(), "attack b",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_attack_bb:
			Toast.makeText(getApplicationContext(), "attack bb",
					Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
		//}else{
		//	Toast.makeText(getApplicationContext(), "Please select a player and/or action type", Toast.LENGTH_SHORT).show();
		//}
	}

}
