package si.krkavolley.volleystat;

import java.util.HashMap;
import java.util.List;

import si.krkavolley.volleystat.Entity.Stat;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.os.Build;

public class WebViewFullGameStatActivity extends Activity {

	WebView myWebView;
	long gameId;
	String gameName;
	DatabaseHelper db;
	Context ctx;
	HashMap playerMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view_full_game_stat);

		Bundle b = getIntent().getExtras();
		gameId = b.getLong("gameId");
		gameName = b.getString("gameName");

		ctx = getApplicationContext();
		db = new DatabaseHelper(ctx);
		playerMap = db.getAllPlayersHashMap();
		List<Stat> stats = db.getFullGameStats((int)gameId);
		
		myWebView = (WebView) findViewById(R.id.webViewFullGameStat);
		String htmlDocument = "<html><head><style>td{padding: 3px 7px; text-align:center;}</style></head><body><h1>"+gameName+"</h1>";
		
		htmlDocument+= "<table>"
				+"<tr style='font-weight: bold;'>"
				+"<td style='text-align:right;'>Player</td>"
				+"<td>Rec total</td>"
				+ "<td>W/A</td>"
				+"<td>Rec pos</td>"
				+"<td>Rec ideal</td>"
				+"<td>Att Total</td>"
				+"<td>Att points</td>"
				+"<td>Att %</td>"
				+"<td>Serve Total</td>"
				+"<td>W/A</td>"
				+"<td>Err</td>"
				+"<td>Total points</td>"
				+"</tr>";
		
		for(int i = 0; i < stats.size(); i++){
			int totalReceptions = stats.get(i).getReception_0()+stats.get(i).getReception_1()+stats.get(i).getReception_2()
					+stats.get(i).getReception_3()+stats.get(i).getReception_wa()+stats.get(i).getReception_over();
			int pos = 0, ideal = 0;
			if(totalReceptions>0){
				
			}
			int totalAttacks = stats.get(i).getAttack_0()+stats.get(i).getAttack_1()+stats.get(i).getAttack_2()+stats.get(i).getAttack_3()
					+stats.get(i).getAttack_e()+stats.get(i).getAttack_ee()+stats.get(i).getAttack_b()+stats.get(i).getAttack_bb();
			int attackEff = 0;
			if (totalAttacks>0){
				attackEff = (100*(stats.get(i).getAttack_3()+stats.get(i).getAttack_2())/totalAttacks);
			}
			int totalServe = stats.get(i).getServe_0()+stats.get(i).getServe_1()+stats.get(i).getServe_2()+stats.get(i).getServe_3()
					+stats.get(i).getServe_wa()+stats.get(i).getServe_over()+stats.get(i).getServe_e();
			htmlDocument+= "<tr>"
					+"<td style='text-align: right; font-weight: bold;'>"+playerMap.get(stats.get(i).getPlayer_id())+"</td>"
					+"<td>" + isZero(totalReceptions)+"</td>"
					+"<td>"+isZero(stats.get(i).getReception_wa())+"</td>"
					+"<td>"+isZero(pos)+"</td>"
					+"<td>"+isZero(ideal)+"</td>"
					+"<td>"+isZero(totalAttacks)+"</td>"
					+"<td>"+isZero(stats.get(i).getAttack_3()+stats.get(i).getAttack_2())+"</td>"
					+"<td>"+isZero(attackEff)+"</td>"
					+"<td>"+isZero(totalServe)+"</td>"
					+"<td>"+isZero(stats.get(i).getServe_wa())+"</td>"
					+"<td>"+isZero(stats.get(i).getServe_e())+"</td>"
					+"<td>"+isZero(stats.get(i).getServe_wa()+stats.get(i).getAttack_3()+stats.get(i).getAttack_2())+"</td>"
					+"</tr>";
		}
		
		Stat statsSum = db.getFullGameStatsSum((int)gameId);
		htmlDocument+="<tr style='font-weight: bold;'>"
				+"<td style='text-align: right; font-weight: bold;'>Totals</td>"
				+"<td>" + isZero(statsSum.getReceptionsTotal())+"</td>"
				+"<td>"+isZero(statsSum.getReception_wa())+"</td>"
				+"<td>"+isZero(statsSum.getReceptionsPositive())+"%</td>"
				+"<td>"+isZero(statsSum.getReceptionIdeal())+"%</td>"
				+"<td>"+isZero(statsSum.getAttacksTotal())+"</td>"
				+"<td>"+isZero(statsSum.getAttackPoints())+"</td>"
				+"<td>"+isZero(statsSum.getAttackEff())+"</td>"
				+"<td>"+isZero(statsSum.getServeTotal())+"</td>"
				+"<td>"+isZero(statsSum.getServe_wa())+"</td>"
				+"<td>"+isZero(statsSum.getServe_e())+"</td>"
				+"<td>"+isZero(statsSum.getTotalPoints())+"</td>"
				+"</tr>";
		htmlDocument+="</table>";
		
		htmlDocument += "</body></html>";
		myWebView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8",
				null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.web_view_full_game_stat, menu);
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
	
	public String isZero(int num) {
		if (num == 0)
			return "-";
		else {
			return "" + num;
		}
	}

}
