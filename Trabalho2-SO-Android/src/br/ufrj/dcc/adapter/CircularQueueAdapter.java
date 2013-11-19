package br.ufrj.dcc.adapter;

import br.ufrj.dcc.util.Constantes;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CircularQueueAdapter extends ArrayAdapter<String>{
	private Context ctx;
	
	public CircularQueueAdapter(Context context, int resource, String[] items) {
		super(context, resource, items);
		this.ctx = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		TextView textView = (TextView)convertView;
		if(convertView == null){
			convertView = new TextView(this.ctx);
			textView = (TextView)convertView;
		}
		
		if(this.getItem(position) != null){
			if(this.getItem(position).toString().equals(Constantes.UICircularQueueRelated.COM_ITEM)){
				textView.setBackgroundColor(Constantes.UICircularQueueRelated.COR_COM_ITEM);
				
			}
			else if(this.getItem(position).toString().equals(Constantes.UICircularQueueRelated.SEM_ITEM)){
				textView.setBackgroundColor(Constantes.UICircularQueueRelated.COR_SEM_ITEM);
				
			}
		}
		else{
			textView.setBackgroundColor(Constantes.UICircularQueueRelated.COR_SEM_ITEM);
			
		}
		
		return convertView;
	}

}
