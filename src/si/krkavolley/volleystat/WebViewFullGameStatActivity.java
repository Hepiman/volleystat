package si.krkavolley.volleystat;

import java.util.HashMap;
import java.util.List;

import si.krkavolley.volleystat.Entity.OpponentError;
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
				+"<td>Srv Total</td>"
				+"<td>W/A</td>"
				+"<td>Err</td>"
				+"<td>Tot. Err</td>"
				+"<td>Blcks</td>"
				+"<td>Total points</td>"
				+"</tr>";
		
		for(int i = 0; i < stats.size(); i++){
			htmlDocument+= "<tr>"
					+"<td style='text-align: right; font-weight: bold;'>"+playerMap.get(stats.get(i).getPlayer_id())+"</td>"
					+"<td>" + isZero(stats.get(i).getReceptionsTotal())+"</td>"
					+"<td>"+isZero(stats.get(i).getReception_wa())+"</td>"
					+"<td>"+isZero(stats.get(i).getReceptionsPositive())+"%</td>"
					+"<td>"+isZero(stats.get(i).getReceptionIdeal())+"%</td>"
					+"<td>"+isZero(stats.get(i).getAttacksTotal())+"</td>"
					+"<td>"+isZero(stats.get(i).getAttackPoints())+"</td>"
					+"<td>"+isZero(stats.get(i).getAttackEff())+"%</td>"
					+"<td>"+isZero(stats.get(i).getServeTotal())+"</td>"
					+"<td>"+isZero(stats.get(i).getServe_wa())+"</td>"
					+"<td>"+isZero(stats.get(i).getServe_e())+"</td>"
					+"<td>"+isZero(stats.get(i).getTotalErrors())+"</td>"
					+"<td>"+isZero(stats.get(i).getBlock())+"</td>"
					+"<td>"+isZero(stats.get(i).getTotalPoints())+"</td>"
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
				+"<td>"+isZero(statsSum.getAttackEff())+"%</td>"
				+"<td>"+isZero(statsSum.getServeTotal())+"</td>"
				+"<td>"+isZero(statsSum.getServe_wa())+"</td>"
				+"<td>"+isZero(statsSum.getServe_e())+"</td>"
				+"<td>"+isZero(statsSum.getTotalErrors())+"</td>"
				+"<td>"+isZero(statsSum.getBlock())+"</td>"
				+"<td style='font-weight: bold;'>"+isZero(statsSum.getTotalPoints())+"</td>"
				+"</tr>";
		htmlDocument+="</table>";
		OpponentError oppErr = db.getOppErrByGameId((int)gameId);
		if(oppErr != null){
		htmlDocument += "<h3>Opponent errors</h3>";
		
		htmlDocument += "<p>1.set: <strong>"+oppErr.getErr_1()+"</strong> 2.set: <strong>"+oppErr.getErr_2()+"</strong> 3.set: <strong>"
		+oppErr.getErr_3()+"</strong> 4.set: <strong>"+oppErr.getErr_4()+"</strong> 5.set: <strong>"+oppErr.getErr_1()+"</strong></p>";
		}
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
