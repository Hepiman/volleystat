package si.krkavolley.volleystat;

import java.util.ArrayList;

import si.krkavolley.volleystat.Entity.Stat;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.os.Build;

public class WebViewSingleStatActivity extends Activity {

	WebView myWebView;
	int gameId;
	long playerId;
	String playerName;
	DatabaseHelper db;
	Context ctx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view_single_stat);

		Bundle b = getIntent().getExtras();
		gameId = b.getInt("gameId");
		playerId = b.getLong("playerId");
		playerName = b.getString("playerName");

		ctx = getApplicationContext();
		db = new DatabaseHelper(ctx);

		// Stat stat1 = db.getPlayerStats((int)playerId, gameId);
		ArrayList<Stat> stats = (ArrayList<Stat>) db.getPlayerStats(
				(int) playerId, gameId);
		Stat playerTotals = db.getPlayerFullGameStatsSum(gameId, (int)playerId);
		myWebView = (WebView) findViewById(R.id.webViewSingleStat);
		String htmlDocument = "<html><head><style>td{padding: 3px 7px;}</style></head><body><h1>"+playerName+"</h1>";
		if(stats.size() > 0){
			htmlDocument += "<p style='text-decoration: underline;' ><strong>SUMMARY: </strong><p/>"
					+"<p><strong>"+playerTotals.getTotalPoints()
					+" points</strong> (aces:"+playerTotals.getServe_wa()+", blocks:" + playerTotals.getBlock()+")";
			if(playerTotals.getReceptionsTotal()>0){
				htmlDocument+= ", receptions: "+playerTotals.getReceptionsTotal() + " ("+playerTotals.getReceptionsPositive()+"% / "
						+ playerTotals.getReceptionIdeal()+"%)";
			}
			if(playerTotals.getAttacksTotal()>0){
				htmlDocument+= ", attacks: "+playerTotals.getAttackPoints()+"/"+playerTotals.getAttacksTotal() + " ("+playerTotals.getAttackEff()+"%)";
			}
			htmlDocument +="</p>";
			htmlDocument += "<h3>Sprejem</h3>"
					+ "<table style='border: 1px solid gray; text-align: center; padding-left: 8px; padding-right: 8px; padding-top:5px;'>"
					+ "<tr style='font-weight: bold'>"
					+ "<td style='text-align: right;'>set</td>" + "<td>3</td>"
					+ "<td>2</td>" + "<td>1</td>" + "<td>0</td>" + "<td>w/a</td>"
					+ "<td>over</td>" + "<td>ideal</td>" + "<td>positive</td>"
					+ "<td>efficiency</td>" + "</tr>";
			for (int i = 0; i < stats.size(); i++) {
				
				htmlDocument += "<tr>" + "<td style='text-align: right;'><strong>"
						+ stats.get(i).getGame_set() + "</strong></td>" + "<td>"
						+ isZero(stats.get(i).getReception_3()) + "</td>" + "<td>"
						+ isZero(stats.get(i).getReception_2()) + "</td>" + "<td>"
						+ isZero(stats.get(i).getReception_1()) + "</td>" + "<td>"
						+ isZero(stats.get(i).getReception_0()) + "</td>" + "<td>"
						+ isZero(stats.get(i).getReception_wa()) + "</td>" + "<td>"
						+ isZero(stats.get(i).getReception_over()) + "</td>"
						+ "<td>" + stats.get(i).getReceptionIdeal() + "%</td>" + "<td>" + stats.get(i).getReceptionsPositive() + "%</td>"
						+ "<td>" + stats.get(i).getReceptionEff() + "%</td>" + "</tr>";
			}
			
			htmlDocument += "<tr style='font-weight: bold;'>"
					+ "<td style='text-align: right;'><strong>total</strong/td>"
					+ "<td>"
					+ isZero(playerTotals.getReception_3())
					+ "</td>"
					+ "<td>"
					+ isZero(playerTotals.getReception_2())
					+ "</td>"
					+ "<td>"
					+ isZero(playerTotals.getReception_1())
					+ "</td>"
					+ "<td>"
					+ isZero(playerTotals.getReception_0())
					+ "</td>"
					+ "<td>"
					+ isZero(playerTotals.getReception_wa())
					+ "</td>"
					+ "<td>"
					+ isZero(playerTotals.getReception_over())
					+ "</td>"
					+ "<td>"
					+ isZero(playerTotals.getReceptionIdeal())
					+ "%</td>"
					+ "<td>"
					+ isZero(playerTotals.getReceptionsPositive())
					+ "%</td>"
					+ "<td>" + isZero(playerTotals.getReceptionEff()) + "%</td>" + "</tr>";
			htmlDocument += "</table>";
	
			htmlDocument += "<h3>Napad</h3>"
					+ "<table style='border: 1px solid gray; text-align: center; padding-left: 8px; padding-right: 8px; padding-top:5px;'>"
					+ "<tr style='font-weight: bold'>" + "<td style='text-align: right;'>set</td>"
					+ "<td>3</td>" + "<td>2</td>" + "<td>1</td>" + "<td>0</td>"
					+ "<td>SO err</td>" + "<td>K2 err</td>" + "<td>SO blck</td>" + "<td>K2 blck</td>" + "<td>Att. eff%</td>" + "<td>Points</td>"
					+ "</tr>";
			for (int i = 0; i < stats.size(); i++) {
				
				htmlDocument += "<tr>" + "<td style='text-align: right;'><strong>"
						+ stats.get(i).getGame_set() + "</strong></td>" + "<td>"
						+ isZero(stats.get(i).getAttack_3()) + "</td>" + "<td>"
						+ isZero(stats.get(i).getAttack_2()) + "</td>" + "<td>"
						+ isZero(stats.get(i).getAttack_1()) + "</td>" + "<td>"
						+ isZero(stats.get(i).getAttack_0()) + "</td>" + "<td>"
						+ isZero(stats.get(i).getAttack_e()) + "</td>" + "<td>"
						+ isZero(stats.get(i).getAttack_ee()) + "</td>" + "<td>"
						+ isZero(stats.get(i).getAttack_b()) + "</td>" + "<td>"
						+ isZero(stats.get(i).getAttack_bb()) + "</td>" + "<td>"
						+ isZero(stats.get(i).getAttackEff()) + "%</td>" + "<td style='font-weight:bold;'>"
						+ isZero(stats.get(i).getAttackPoints())+"</td>"
						+ "</tr>";
			}
	
			htmlDocument += "<tr style='font-weight: bold;'>"
					+ "<td style='text-align: right;'><strong>total</strong/td>"
					+ "<td>"
					+ isZero(playerTotals.getAttack_3())
					+ "</td>"
					+ "<td>"
					+ isZero(playerTotals.getAttack_2())
					+ "</td>"
					+ "<td>"
					+ isZero(playerTotals.getAttack_1())
					+ "</td>"
					+ "<td>"
					+ isZero(playerTotals.getAttack_0())
					+ "</td>"
					+ "<td>"
					+ isZero(playerTotals.getAttack_e())
					+ "</td>"
					+ "<td>"
					+ isZero(playerTotals.getAttack_ee())
					+ "</td>"
					+ "<td>"
					+ isZero(playerTotals.getAttack_b())
					+ "</td>"
					+ "<td>"
					+ isZero(playerTotals.getAttack_bb())
					+ "</td>" 
					+ "<td>"+isZero(playerTotals.getAttackEff())+"%</td>"
					+ "<td style='font-weight: bold;'>"+isZero(playerTotals.getAttackPoints())+"</td>"
					+"</tr>";
	
			htmlDocument += "</table>";
	
			htmlDocument += "<h3>Servis</h3>"
					+ "<table style='border: 1px solid gray; text-align: center; padding-left: 8px; padding-right: 8px; padding-top:5px;'>"
					+ "<tr style='font-weight: bold'>" + "<td>set</td>"
					+ "<td>3</td>" + "<td>2</td>" + "<td>1</td>" + "<td>0</td>"
					+ "<td>w/a</td>" + "<td>over</td>" + "<td>err</td>" + "</tr>";
			for (int i = 0; i < stats.size(); i++) {
				htmlDocument += "<tr>" + "<td><strong>"
						+ stats.get(i).getGame_set() + "</strong></td>" + "<td>"
						+ isZero(stats.get(i).getServe_3()) + "</td>" + "<td>"
						+ isZero(stats.get(i).getServe_2()) + "</td>" + "<td>"
						+ isZero(stats.get(i).getServe_1()) + "</td>" + "<td>"
						+ isZero(stats.get(i).getServe_0()) + "</td>" + "<td>"
						+ isZero(stats.get(i).getServe_wa()) + "</td>" + "<td>"
						+ isZero(stats.get(i).getServe_over()) + "</td>" + "<td>"
						+ isZero(stats.get(i).getServe_e()) + "</td>" + "</tr>";
			}
			htmlDocument += "<tr style='font-weight: bold;'>"
					+ "<td style='text-align: right;'><strong>total</strong/td>"
					+ "<td>"
					+ isZero(playerTotals.getServe_3())
					+ "</td>"
					+ "<td>"
					+ isZero(playerTotals.getServe_2())
					+ "</td>"
					+ "<td>"
					+ isZero(playerTotals.getServe_1())
					+ "</td>"
					+ "<td>"
					+ isZero(playerTotals.getServe_0())
					+ "</td>"
					+ "<td>"
					+ isZero(playerTotals.getServe_wa())
					+ "</td>"
					+ "<td>"
					+ isZero(playerTotals.getServe_over())
					+ "</td>"
					+ "<td>"
					+ isZero(playerTotals.getServe_e())
					+ "</td>"
					+ "</tr>";
			
			htmlDocument += "</table>";
		}else{
			htmlDocument +="Igralec se ni vpisal v statistiko.";
		}
		
		htmlDocument += "</body></html>";

		myWebView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8",
				null);

	}

	public String isZero(int num) {
		if (num == 0)
			return "-";
		else {
			return "" + num;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.web_view_single_stat, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar willy
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
