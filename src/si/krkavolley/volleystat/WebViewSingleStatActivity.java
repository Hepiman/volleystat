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
	    
	    //Stat stat1 = db.getPlayerStats((int)playerId, gameId);
	    ArrayList<Stat> stats = (ArrayList<Stat>) db.getPlayerStats((int) playerId, gameId);
		myWebView = (WebView) findViewById(R.id.webViewSingleStat);
		
		String htmlDocument = "<html><body><h1>"+playerName+"</h1>"
				+ "<h3>Sprejem</h3>"
				+ "<table style='border: 1px solid gray; text-align: center; padding-left: 4px; padding-right: 4px; padding-top:5px;'>"
					+ "<tr style='font-weight: bold'>"
						+ "<td>set</td>"
						+ "<td>3</td>"
						+ "<td>2</td>"
						+ "<td>1</td>"
						+ "<td>0</td>"
						+ "<td>w/a</td>"
						+ "<td>over</td>"
					+ "</tr>";
				for(int i = 0; i <stats.size(); i++){
					htmlDocument+= "<tr>"
						+ "<td><strong>" + stats.get(i).getGame_set() +"</strong></td>"
						+ "<td>" + stats.get(i).getReception_3() + "</td>" 
						+ "<td>" + stats.get(i).getReception_2() + "</td>" 
						+ "<td>" + stats.get(i).getReception_1() + "</td>" 
						+ "<td>" + stats.get(i).getReception_0() + "</td>"
						+ "<td>" + stats.get(i).getReception_wa() + "</td>"
						+ "<td>" + stats.get(i).getReception_over() + "</td>"
					+ "</tr>";
				}
				htmlDocument += "</table>";
				
				htmlDocument += "<br /><h3>Napad</h3>"
				+ "<table style='border: 1px solid gray; text-align: center; padding-left: 4px; padding-right: 4px; padding-top:5px;'>"
					+ "<tr style='font-weight: bold'>"
						+ "<td>set</td>"
						+ "<td>3</td>"
						+ "<td>2</td>"
						+ "<td>1</td>"
						+ "<td>0</td>"
						+ "<td>e</td>"
						+ "<td>ee</td>"
						+ "<td>b</td>"
						+ "<td>bb</td>"
					+ "</tr>";
				for(int i = 0; i <stats.size(); i++){
					htmlDocument+= "<tr>"
						+ "<td><strong>" + stats.get(i).getGame_set() +"</strong></td>"
						+ "<td>" + stats.get(i).getAttack_3() + "</td>" 
						+ "<td>" + stats.get(i).getAttack_2() + "</td>" 
						+ "<td>" + stats.get(i).getAttack_1() + "</td>" 
						+ "<td>" + stats.get(i).getAttack_0() + "</td>"
						+ "<td>" + stats.get(i).getAttack_e() + "</td>"
						+ "<td>" + stats.get(i).getAttack_ee() + "</td>"
						+ "<td>" + stats.get(i).getAttack_b() + "</td>"
						+ "<td>" + stats.get(i).getAttack_bb() + "</td>"
					+ "</tr>";
				}
				htmlDocument += "</table>";
				
				htmlDocument += "</body></html>";

		myWebView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8",
				null);

		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.web_view_single_stat, menu);
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
