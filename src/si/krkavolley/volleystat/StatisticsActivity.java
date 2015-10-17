package si.krkavolley.volleystat;

import java.util.ArrayList;

import si.krkavolley.volleystat.Entity.Player;
import si.krkavolley.volleystat.Entity.Stat;
import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.RadioGroup.OnCheckedChangeListener;
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
	int gameId, playerId, setNumber = 1;
	String gameName, gameScore;
	TextView bottomText;
	// reception buttons
	Button btn_r0, btn_r1, btn_r2, btn_r3, btn_rwa, btn_rover;
	// attack buttons
	Button btn_a0, btn_a1, btn_a2, btn_a3, btn_ae, btn_aee, btn_ab, btn_abb;
	// serve buttons
	Button btn_s0, btn_s1, btn_s2, btn_s3, btn_swa, btn_sover, btn_se;
	Button btn_block, btn_opp_err;

	RadioGroup setNumberGroup;

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

		setNumberGroup = (RadioGroup) findViewById(R.id.radioGroup_setNumbers);
		setNumberGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						View rButton = group.findViewById(checkedId);
						int index = group.indexOfChild(rButton);
						setNumber = index + 1;
						Toast.makeText(getApplicationContext(),
								"setNumber: " + setNumber, Toast.LENGTH_SHORT)
								.show();
					}
				});

		db = new DatabaseHelper(getApplicationContext());

		players = new ArrayList<Player>();
		players = (ArrayList<Player>) db.getAllPlayers();
		lv_players = (ListView) findViewById(R.id.listview_player_list);
		lv_players.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, players);

		lv_players.setAdapter(arrayAdapter);

		lv_players.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				if (position >= 0) {
					Player p = (Player) lv_players.getAdapter().getItem(
							position);
					playerId = p.getId();
					// Log.d("debug", "playerId =" + playerId + " gameId = " +
					// gameId + " setNumber = " + setNumber + "position: " +
					// position + "  long: " + arg3);
				}

			}
		});
		lv_players.setLongClickable(true);
		lv_players
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						if (position >= 0) {
							Player p = (Player) lv_players.getAdapter()
									.getItem(position);
							displayPlayerStats(p.getId(),p.getName());
							
						}
						return true;
					}
				});

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
		btn_opp_err = (Button) findViewById(R.id.stats_btn_opp_err);
		btn_opp_err.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				db.writeOppErr(gameId, setNumber);
				Toast.makeText(getApplicationContext(), "Opponent err counter increased", Toast.LENGTH_SHORT).show();
				
			}
		});
		
		btn_block = (Button) findViewById(R.id.stats_btn_block);
		btn_block.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				db.writeBlock(gameId, playerId, setNumber);
				Toast.makeText(getApplicationContext(), "Block increased", Toast.LENGTH_SHORT).show();
				
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
		switch (v.getId()) {

		// Serve
		case R.id.stats_btn_serve_0:
			db.writeServe(gameId, playerId, setNumber, 0);
			Toast.makeText(getApplicationContext(), "servis 0",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_serve_1:
			db.writeServe(gameId, playerId, setNumber, 1);
			Toast.makeText(getApplicationContext(), "servis 1",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_serve_2:
			db.writeServe(gameId, playerId, setNumber, 2);
			Toast.makeText(getApplicationContext(), "servis 2",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_serve_3:
			db.writeServe(gameId, playerId, setNumber, 3);
			Toast.makeText(getApplicationContext(), "servis 3",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_serve_wa:
			db.writeServe(gameId, playerId, setNumber, 4);
			Toast.makeText(getApplicationContext(), "servis w/a",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_serve_over:
			db.writeServe(gameId, playerId, setNumber, 5);
			Toast.makeText(getApplicationContext(), "servis over",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_serve_e:
			db.writeServe(gameId, playerId, setNumber, 6);
			Toast.makeText(getApplicationContext(), "servis error",
					Toast.LENGTH_SHORT).show();
			break;

		// Reception
		case R.id.stats_btn_reception_0:
			db.writeReception(gameId, playerId, setNumber, 0);
			Toast.makeText(getApplicationContext(), "reception 0",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_reception_1:
			db.writeReception(gameId, playerId, setNumber, 1);
			Toast.makeText(getApplicationContext(), "reception 1",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_reception_2:
			db.writeReception(gameId, playerId, setNumber, 2);
			Toast.makeText(getApplicationContext(), "reception 2",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_reception_3:
			db.writeReception(gameId, playerId, setNumber, 3);
			Toast.makeText(getApplicationContext(), "reception 3",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_reception_wa:
			db.writeReception(gameId, playerId, setNumber, 4);
			Toast.makeText(getApplicationContext(), "reception w/a",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_reception_over:
			db.writeReception(gameId, playerId, setNumber, 5);
			Toast.makeText(getApplicationContext(), "reception over",
					Toast.LENGTH_SHORT).show();
			break;

		// Attack
		case R.id.stats_btn_attack_0:
			db.writeAttack(gameId, playerId, setNumber, 0);
			Toast.makeText(getApplicationContext(), "attack 0",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_attack_1:

			db.writeAttack(gameId, playerId, setNumber, 1);
			Toast.makeText(getApplicationContext(), "attack 1",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_attack_2:
			db.writeAttack(gameId, playerId, setNumber, 2);
			Toast.makeText(getApplicationContext(), "attack 2",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_attack_3:
			db.writeAttack(gameId, playerId, setNumber, 3);
			Toast.makeText(getApplicationContext(), "attack 3",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_attack_e:
			db.writeAttack(gameId, playerId, setNumber, 4);
			Toast.makeText(getApplicationContext(), "attack e",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_attack_ee:
			db.writeAttack(gameId, playerId, setNumber, 5);
			Toast.makeText(getApplicationContext(), "attack ee",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_attack_b:
			db.writeAttack(gameId, playerId, setNumber, 6);
			Toast.makeText(getApplicationContext(), "attack b",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_attack_bb:
			db.writeAttack(gameId, playerId, setNumber, 7);
			Toast.makeText(getApplicationContext(), "attack bb",
					Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}

	}

	private void displayPlayerStats(long playerId, String name) {

		Intent i;
		i = new Intent(getApplicationContext(), WebViewSingleStatActivity.class);
		Bundle b = new Bundle();
		b.putInt("gameId", gameId); 
		b.putLong("playerId", playerId);
		b.putString("playerName", name);

		i.putExtras(b);
		startActivity(i);

	}
}
