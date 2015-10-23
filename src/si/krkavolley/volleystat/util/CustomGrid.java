package si.krkavolley.volleystat.util;
import java.util.List;

import si.krkavolley.volleystat.R;
import si.krkavolley.volleystat.Entity.Player;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class CustomGrid extends BaseAdapter{
	private Context mContext;
	private List<Player> pList;
	
	public CustomGrid(Context c, List<Player> playerList){
		mContext = c;
		pList = playerList;
	}

	@Override
	public int getCount() {
		return pList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return pList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return pList.get(arg0).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View grid;
		LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(convertView == null){
			grid = new View(mContext);
			
            grid = inflater.inflate(R.layout.grid_single, null);
            CheckedTextView textName = (CheckedTextView) grid.findViewById(R.id.grid_single_checked_text_view);
            textName.setText(pList.get(position).getName());
		}else{
			grid = (View) convertView;
		}
		return grid;
	}
	
	
}
