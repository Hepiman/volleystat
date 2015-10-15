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
		myWebView = (WebView) findViewById(R.id.webViewSingleStat);
		Stat totals = new Stat();
		String htmlDocument = "<html><head><style>td{padding: 3px 7px;}</style></head><body><h1>"
				+ playerName
				+ "</h1>"
				+ "<h3>Sprejem</h3>"
				+ "<table style='border: 1px solid gray; text-align: center; padding-left: 8px; padding-right: 8px; padding-top:5px;'>"
				+ "<tr style='font-weight: bold'>"
				+ "<td style='text-align: right;'>set</td>" + "<td>3</td>"
				+ "<td>2</td>" + "<td>1</td>" + "<td>0</td>" + "<td>w/a</td>"
				+ "<td>over</td>" + "<td>ideal</td>" + "<td>positive</td>"
				+ "<td>efficiency</td>" + "</tr>";
		for (int i = 0; i < stats.size(); i++) {
			totals.setReception_0(totals.getReception_0()
					+ stats.get(i).getReception_0());
			totals.setReception_1(totals.getReception_1()
					+ stats.get(i).getReception_1());
			totals.setReception_2(totals.getReception_2()
					+ stats.get(i).getReception_2());
			totals.setReception_3(totals.getReception_3()
					+ stats.get(i).getReception_3());
			totals.setReception_wa(totals.getReception_wa()
					+ stats.get(i).getReception_wa());
			totals.setReception_over(totals.getReception_over()
					+ stats.get(i).getReception_over());
			int allReceptions = stats.get(i).getReception_3()
					+ stats.get(i).getReception_2()
					+ stats.get(i).getReception_1()
					+ stats.get(i).getReception_0()
					+ stats.get(i).getReception_wa()
					+ stats.get(i).getReception_over();
			int eff = 0, pos = 0, ideal = 0;
			if (allReceptions > 0) {
				ideal = (100 * stats.get(i).getReception_3()) / (allReceptions);
				pos = ((stats.get(i).getReception_3() + stats.get(i)
						.getReception_2()) * 100) / (allReceptions);
				eff = (((stats.get(i).getReception_3()
						+ stats.get(i).getReception_2() + stats.get(i)
						.getReception_1()) - (stats.get(i).getReception_0()
						+ stats.get(i).getReception_wa() + stats.get(i)
						.getReception_over())) * 100)
						/ (allReceptions);
			}
			htmlDocument += "<tr>" + "<td style='text-align: right;'><strong>"
					+ stats.get(i).getGame_set() + "</strong></td>" + "<td>"
					+ isZero(stats.get(i).getReception_3()) + "</td>" + "<td>"
					+ isZero(stats.get(i).getReception_2()) + "</td>" + "<td>"
					+ isZero(stats.get(i).getReception_1()) + "</td>" + "<td>"
					+ isZero(stats.get(i).getReception_0()) + "</td>" + "<td>"
					+ isZero(stats.get(i).getReception_wa()) + "</td>" + "<td>"
					+ isZero(stats.get(i).getReception_over()) + "</td>"
					+ "<td>" + ideal + "%</td>" + "<td>" + pos + "%</td>"
					+ "<td>" + eff + "%</td>" + "</tr>";
		}
		int allReceptions = totals.getReception_0() + totals.getReception_1()
				+ totals.getReception_2() + totals.getReception_3()
				+ totals.getReception_wa() + totals.getReception_over();
		int eff = 0, pos = 0, ideal = 0;
		if (allReceptions > 0) {
			ideal = (100 * totals.getReception_3()) / (allReceptions);
			pos = ((totals.getReception_3() + totals.getReception_2()) * 100)
					/ (allReceptions);
			eff = (((totals.getReception_3() + totals.getReception_2() + totals
					.getReception_1()) - (totals.getReception_0()
					+ totals.getReception_wa() + totals.getReception_over())) * 100)
					/ (allReceptions);
		}
		htmlDocument += "<tr style='font-weight: bold;'>"
				+ "<td style='text-align: right;'><strong>total</strong/td>"
				+ "<td>"
				+ isZero(totals.getReception_3())
				+ "</td>"
				+ "<td>"
				+ isZero(totals.getReception_2())
				+ "</td>"
				+ "<td>"
				+ isZero(totals.getReception_1())
				+ "</td>"
				+ "<td>"
				+ isZero(totals.getReception_0())
				+ "</td>"
				+ "<td>"
				+ isZero(totals.getReception_wa())
				+ "</td>"
				+ "<td>"
				+ isZero(totals.getReception_over())
				+ "</td>"
				+ "<td>"
				+ isZero(ideal)
				+ "%</td>"
				+ "<td>"
				+ isZero(pos)
				+ "%</td>"
				+ "<td>" + isZero(eff) + "%</td>" + "</tr>";
		htmlDocument += "</table>";

		htmlDocument += "<h3>Napad</h3>"
				+ "<table style='border: 1px solid gray; text-align: center; padding-left: 8px; padding-right: 8px; padding-top:5px;'>"
				+ "<tr style='font-weight: bold'>" + "<td style='text-align: right;'>set</td>"
				+ "<td>3</td>" + "<td>2</td>" + "<td>1</td>" + "<td>0</td>"
				+ "<td>SO err</td>" + "<td>K2 err</td>" + "<td>SO blck</td>" + "<td>K2 blck</td>"
				+ "</tr>";
		for (int i = 0; i < stats.size(); i++) {
			totals.setAttack_0(totals.getAttack_0()
					+ stats.get(i).getAttack_0());
			totals.setAttack_1(totals.getAttack_1()
					+ stats.get(i).getAttack_1());
			totals.setAttack_2(totals.getAttack_2()
					+ stats.get(i).getAttack_2());
			totals.setAttack_3(totals.getAttack_3()
					+ stats.get(i).getAttack_3());
			totals.setAttack_e(totals.getAttack_e()
					+ stats.get(i).getAttack_e());
			totals.setAttack_ee(totals.getAttack_ee()
					+ stats.get(i).getAttack_ee());
			totals.setAttack_b(totals.getAttack_b()
					+ stats.get(i).getAttack_b());
			totals.setAttack_bb(totals.getAttack_bb()
					+ stats.get(i).getAttack_bb());
			htmlDocument += "<tr>" + "<td style='text-align: right;'><strong>"
					+ stats.get(i).getGame_set() + "</strong></td>" + "<td>"
					+ isZero(stats.get(i).getAttack_3()) + "</td>" + "<td>"
					+ isZero(stats.get(i).getAttack_2()) + "</td>" + "<td>"
					+ isZero(stats.get(i).getAttack_1()) + "</td>" + "<td>"
					+ isZero(stats.get(i).getAttack_0()) + "</td>" + "<td>"
					+ isZero(stats.get(i).getAttack_e()) + "</td>" + "<td>"
					+ isZero(stats.get(i).getAttack_ee()) + "</td>" + "<td>"
					+ isZero(stats.get(i).getAttack_b()) + "</td>" + "<td>"
					+ isZero(stats.get(i).getAttack_bb()) + "</td>" + "</tr>";
		}

		htmlDocument += "<tr style='font-weight: bold;'>"
				+ "<td style='text-align: right;'><strong>total</strong/td>"
				+ "<td>"
				+ isZero(totals.getAttack_3())
				+ "</td>"
				+ "<td>"
				+ isZero(totals.getAttack_2())
				+ "</td>"
				+ "<td>"
				+ isZero(totals.getAttack_1())
				+ "</td>"
				+ "<td>"
				+ isZero(totals.getAttack_0())
				+ "</td>"
				+ "<td>"
				+ isZero(totals.getAttack_e())
				+ "</td>"
				+ "<td>"
				+ isZero(totals.getAttack_ee())
				+ "</td>"
				+ "<td>"
				+ isZero(totals.getAttack_b())
				+ "</td>"
				+ "<td>"
				+ isZero(totals.getAttack_bb())
				+ "</td>" + "</tr>";

		htmlDocument += "</table>";

		htmlDocument += "<h3>Servis</h3>"
				+ "<table style='border: 1px solid gray; text-align: center; padding-left: 8px; padding-right: 8px; padding-top:5px;'>"
				+ "<tr style='font-weight: bold'>" + "<td>set</td>"
				+ "<td>3</td>" + "<td>2</td>" + "<td>1</td>" + "<td>0</td>"
				+ "<td>w/a</td>" + "<td>over</td>" + "<td>err</td>" + "</tr>";
		for (int i = 0; i < stats.size(); i++) {
			totals.setServe_0(totals.getServe_0()
					+ stats.get(i).getServe_0());
			totals.setServe_1(totals.getServe_1()
					+ stats.get(i).getServe_1());
			totals.setServe_2(totals.getServe_2()
					+ stats.get(i).getServe_2());
			totals.setServe_3(totals.getServe_3()
					+ stats.get(i).getServe_3());
			totals.setServe_wa(totals.getServe_wa()
					+ stats.get(i).getServe_wa());
			totals.setServe_over(totals.getServe_over()
					+ stats.get(i).getServe_over());
			totals.setServe_e(totals.getServe_e()
					+ stats.get(i).getServe_e());
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
				+ isZero(totals.getServe_3())
				+ "</td>"
				+ "<td>"
				+ isZero(totals.getServe_2())
				+ "</td>"
				+ "<td>"
				+ isZero(totals.getServe_1())
				+ "</td>"
				+ "<td>"
				+ isZero(totals.getServe_0())
				+ "</td>"
				+ "<td>"
				+ isZero(totals.getServe_wa())
				+ "</td>"
				+ "<td>"
				+ isZero(totals.getServe_over())
				+ "</td>"
				+ "<td>"
				+ isZero(totals.getServe_e())
				+ "</td>"
				+ "</tr>";
		
		htmlDocument += "</table>";

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
