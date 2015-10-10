package si.krkavolley.volleystat;

import java.util.ArrayList;
import si.krkavolley.volleystat.Entity.Player;
import android.content.Context;
import android.content.res.AssetManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class PlayerAdapter extends ArrayAdapter<Player> {
	ArrayList<Player> list;
	Context context;
	
	public PlayerAdapter(Context context, ArrayList<Player> players) {
	       super(context, 0, players);
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Player getItem(int i) {
		// TODO Auto-generated method stub
		return list.get(i);
	}

	@Override
	public long getItemId(int i) {
		// TODO Auto-generated method stub
		return i;
	}
	
	public long getPlayerId(int i){
		return list.get(i).getId();
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Get the data item for this position
       Player player = getItem(position);    
       // Check if an existing view is being reused, otherwise inflate the view
       MyViewHolder viewHolder; // view lookup cache stored in tag
       if (convertView == null) {
          viewHolder = new MyViewHolder(convertView);
          LayoutInflater inflater = LayoutInflater.from(getContext());
          convertView = inflater.inflate(R.layout.item_player, parent, false);
          viewHolder.name = (TextView) convertView.findViewById(R.id.item_player_name);
          viewHolder.desc = (TextView) convertView.findViewById(R.id.item_player_desc);
          convertView.setTag(viewHolder);
       } else {
           viewHolder = (MyViewHolder) convertView.getTag();
       }
       // Populate the data into the template view using the data object
       viewHolder.name.setText(player.getName());
       viewHolder.desc.setText(player.getDescription());
       // Return the completed view to render on screen
       return convertView;
   }
	
	class MyViewHolder {
		TextView name;
		TextView desc;

		MyViewHolder(View v) {
			name = (TextView) v.findViewById(R.id.item_player_name);
			desc = (TextView) v.findViewById(R.id.item_player_desc);
		}
	}

}
