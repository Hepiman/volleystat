package si.krkavolley.volleystat;

import java.util.ArrayList;
import java.util.Collections;

import si.krkavolley.volleystat.Entity.Player;
import si.krkavolley.volleystat.Entity.Stat;
import si.krkavolley.volleystat.util.CustomGrid;
import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class StatisticsActivity extends Activity implements OnClickListener {

	LinearLayout serveButtonsContainer, receptionButtonsContainer,
			attackButtonsContainer, otherButtonsContainer;
	DatabaseHelper db;
	ArrayList players_active, players_bench;
	ListView lv_players, lv_actionTypes;
	ArrayAdapter<String> arrayAdapterActive, arrayAdapterBench, actionTypesAdapter;
	int gameId, playerId, setNumber = 1;
	String gameName, gameScore;
	TextView bottomText;
	// reception buttons
	Button btn_r0, btn_r1, btn_r2, btn_r3, btn_rwa, btn_rover;
	// attack buttons
	Button btn_a0, btn_a1, btn_a2, btn_a3, btn_ae, btn_aee, btn_ab, btn_abb;
	// serve buttons
	Button btn_s0, btn_s1, btn_s2, btn_s3, btn_swa, btn_sover, btn_se;
	Button btn_err, btn_block, btn_opp_err, btn_opp_att_point, btn_substitution;

	RadioGroup setNumberGroup;
	int scoreMyTeam = 0, scoreOpponent = 0;
	TextView scoreDisplay;

	GridView gv_players;
	CustomGrid cAdapter;
	boolean ourServe = false;

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
		scoreDisplay = (TextView) findViewById(R.id.stats_score_text);

		serveButtonsContainer = (LinearLayout) findViewById(R.id.container_serve_buttons);
		attackButtonsContainer = (LinearLayout) findViewById(R.id.container_attack_buttons);
		receptionButtonsContainer = (LinearLayout) findViewById(R.id.container_reception_buttons);
		otherButtonsContainer = (LinearLayout) findViewById(R.id.container_other_buttons);

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

		players_active = new ArrayList<Player>();
		players_active = (ArrayList<Player>) db.getAllAssignedPlayers(gameId);
		
		players_bench = new ArrayList<Player>();
		if(players_active.size()> 6){
			for(int i = 6; i < players_active.size(); i=i)
			players_bench.add(players_active.remove(i));
		}
		arrayAdapterBench = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, players_bench);

		arrayAdapterActive = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, players_active);

			
		gv_players = (GridView) findViewById(R.id.gridview_player_list);

		cAdapter = new CustomGrid(StatisticsActivity.this, players_active);
		gv_players.setAdapter(cAdapter);
		gv_players.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
		gv_players
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// Toast.makeText(StatisticsActivity.this,
						// "You Clicked at " +players.get(position).toString()+
						// " ("+id+")", Toast.LENGTH_SHORT).show();
						playerId = (int) id;
					}
				});

		gv_players.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
		gv_players.setLongClickable(true);
		gv_players
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						if (position >= 0) {
							Player p = (Player) gv_players.getAdapter()
									.getItem(position);
							displayPlayerStats(p.getId(), p.getName());

						}
						return true;
					}
				});

		ArrayList<String> actionTypes = new ArrayList<String>();
		actionTypes.add("Serve");
		actionTypes.add("Reception");
		actionTypes.add("Attack");
		actionTypes.add("Other");

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
					otherButtonsContainer.setVisibility(View.GONE);
					break;

				case 1:
					serveButtonsContainer.setVisibility(View.GONE);
					receptionButtonsContainer.setVisibility(View.VISIBLE);
					attackButtonsContainer.setVisibility(View.GONE);
					otherButtonsContainer.setVisibility(View.GONE);
					break;

				case 2:
					serveButtonsContainer.setVisibility(View.GONE);
					receptionButtonsContainer.setVisibility(View.GONE);
					attackButtonsContainer.setVisibility(View.VISIBLE);
					otherButtonsContainer.setVisibility(View.GONE);
					break;
				case 3:
					otherButtonsContainer.setVisibility(View.VISIBLE);
					serveButtonsContainer.setVisibility(View.GONE);
					receptionButtonsContainer.setVisibility(View.GONE);
					attackButtonsContainer.setVisibility(View.GONE);

				default:
					break;
				}

			}
		});
		btn_opp_err = (Button) findViewById(R.id.stats_btn_opp_err);
		btn_opp_err.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				scoreMyTeam++;
				updateScoreDisplay();
				db.writeOppErr(gameId, setNumber);
				Toast.makeText(getApplicationContext(),
						"Opponent err counter increased", Toast.LENGTH_SHORT)
						.show();

			}
		});

		btn_block = (Button) findViewById(R.id.stats_btn_block);
		btn_block.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				scoreMyTeam++;
				updateScoreDisplay();
				db.writeBlock(gameId, playerId, setNumber);
				Toast.makeText(getApplicationContext(), "Block increased",
						Toast.LENGTH_SHORT).show();

			}
		});
		btn_opp_att_point = (Button) findViewById(R.id.stats_btn_opp_att_point);
		btn_opp_att_point.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ourServe = false;
				scoreOpponent++;
				updateScoreDisplay();
				Toast.makeText(getApplicationContext(),
						"Opponent attack point", Toast.LENGTH_SHORT).show();
			}
		});
		btn_err = (Button) findViewById(R.id.stats_btn_error);
		btn_err.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ourServe = false;
				db.writeOtherError(gameId, playerId, setNumber);
				Toast.makeText(getApplicationContext(), "Player error",
						Toast.LENGTH_SHORT).show();
				scoreOpponent++;
				updateScoreDisplay();

			}
		});
		btn_substitution = (Button) findViewById(R.id.stats_btn_player_substitution);
		btn_substitution.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				 final Dialog dialog = new Dialog(StatisticsActivity.this);
	                // Include dialog.xml file
	                dialog.setContentView(R.layout.player_substitution_dialog);
	                // Set dialog title
	                dialog.setTitle("Substitutions");
	 
	                // set values for custom dialog components - text, image and button
	                final ListView actives = (ListView) dialog.findViewById(R.id.players_active_list);
	                actives.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	                final ListView bench = (ListView) dialog.findViewById(R.id.players_bench_list);
	                bench.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	                
	                bench.setAdapter(arrayAdapterBench);
	                actives.setAdapter(arrayAdapterActive);
	 
	                dialog.show();
	                 
	                Button okButton = (Button) dialog.findViewById(R.id.players_substitution_dialog_ok);
	                
	                // if decline button is clicked, close the custom dialog
	                okButton.setOnClickListener(new OnClickListener() {
	                    @Override
	                    public void onClick(View v) {
	                        Player p1 = new Player();
	                        Player p2 = new Player();
	                        p2 = (Player) players_bench.get(bench.getCheckedItemPosition());
	                        p1 = (Player) players_active.get(actives.getCheckedItemPosition());;
	                        Log.d("sub", p1.getName());
	                        Log.d("sub", p2.getName());
	                        //Toast.makeText(getApplicationContext(), "Na igrišèe: " + p2.getName() + "Na klop: " + p1.getName(), Toast.LENGTH_SHORT).show();
	                        players_active.set(actives.getCheckedItemPosition(), p2);
	                        players_bench.set(bench.getCheckedItemPosition(), p1);
	                        arrayAdapterActive.notifyDataSetChanged();
	                        arrayAdapterBench.notifyDataSetChanged();
	                        cAdapter.notifyDataSetChanged();
	                        //Toast.makeText(getApplicationContext(), "Actives: " + players_active.size() + " Bench: " + players_bench.size(), Toast.LENGTH_SHORT).show();
	                        dialog.dismiss();
	                        refreshGrid();
	                    }
	                });
	                
	                
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
			scoreMyTeam++;
			updateScoreDisplay();
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
			scoreOpponent++;
			updateScoreDisplay();
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
			scoreOpponent++;
			updateScoreDisplay();
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
			scoreMyTeam++;
			updateScoreDisplay();
			Toast.makeText(getApplicationContext(), "attack 2",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_attack_3:
			db.writeAttack(gameId, playerId, setNumber, 3);
			servePingPong();
			arrayAdapterActive.notifyDataSetChanged();
			//gv_players.setAdapter(arrayAdapter);
			scoreMyTeam++;
			updateScoreDisplay();
			Toast.makeText(getApplicationContext(), "attack 3",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_attack_e:
			db.writeAttack(gameId, playerId, setNumber, 4);
			scoreOpponent++;
			updateScoreDisplay();
			Toast.makeText(getApplicationContext(), "attack e",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_attack_ee:
			db.writeAttack(gameId, playerId, setNumber, 5);
			scoreOpponent++;
			updateScoreDisplay();
			Toast.makeText(getApplicationContext(), "attack ee",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_attack_b:
			db.writeAttack(gameId, playerId, setNumber, 6);
			scoreOpponent++;
			updateScoreDisplay();
			Toast.makeText(getApplicationContext(), "attack b",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.stats_btn_attack_bb:
			db.writeAttack(gameId, playerId, setNumber, 7);
			scoreOpponent++;
			updateScoreDisplay();
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
	
	public void refreshGrid(){
//		gv_players.invalidate();
//		arrayAdapterActive = new ArrayAdapter<String>(this,
//		android.R.layout.simple_list_item_single_choice, players_active);
//		gv_players.setAdapter(arrayAdapterActive);
		arrayAdapterActive.notifyDataSetInvalidated();
		arrayAdapterActive.notifyDataSetChanged();
		CustomGrid ad = (CustomGrid)gv_players.getAdapter();
		ad.notifyDataSetChanged();
		gv_players.setAdapter(ad);
	}

	public void updateScoreDisplay() {
		scoreDisplay.setText("" + scoreMyTeam + " : " + scoreOpponent);
	}
	
	public void servePingPong(){
		if(ourServe){
			
		}else{
			ourServe=true;
			Collections.swap(players_active, 4, 5);
			Collections.swap(players_active, 3, 5);
			Collections.swap(players_active, 0, 5);
			Collections.swap(players_active, 1, 5);
			Collections.swap(players_active, 2, 5);
			CustomGrid ad = (CustomGrid)gv_players.getAdapter();
			ad.notifyDataSetChanged();
			gv_players.setAdapter(ad);
//			gv_players.setAdapter(new ArrayAdapter<String>(this,
//					android.R.layout.simple_list_item_single_choice, players_active));
		}
	}
}
