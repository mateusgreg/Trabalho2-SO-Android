package br.ufrj.dcc.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import br.ufrj.dcc.adapter.CircularQueueAdapter;
import br.ufrj.dcc.controller.Controlador;
import br.ufrj.dcc.mensageiro.MensageiroAndroid;
import br.ufrj.dcc.util.Constantes;

public class GridActivity extends Activity{
	private Controlador controlador;
	private GridView gridView;
	private UICircularQueue uiCircularQueue;
	private MensageiroAndroid mensageiroAndroid = new MensageiroAndroid(new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			//Log.d("MAIN_ACTIVITY_HANDLER", "handling message : " + msg.obj.toString());
			
			if(msg.obj.toString().equals(Constantes.MensageiroComunicacao.PRODUZIU)){
				uiCircularQueue.enqueue();
			}
			else if(msg.obj.toString().equals(Constantes.MensageiroComunicacao.CONSUMIU)){
				uiCircularQueue.dequeue();
			}
			
			gridView.setAdapter(new CircularQueueAdapter(GridActivity.this, android.R.layout.simple_list_item_1, uiCircularQueue.getItems()));			
			
			//Log.d("MAIN_ACTIVITY_HANDLER", "message handled");
			return true;
		}
	}));
	
	private void inicializaDependencias(){
		this.controlador = Controlador.getInstance(mensageiroAndroid);
		this.gridView = (GridView)findViewById(R.id.gridView1);
		this.uiCircularQueue = new UICircularQueue();
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view);
        this.inicializaDependencias();
        this.gridView.setAdapter(new CircularQueueAdapter(this, android.R.layout.simple_list_item_1, this.uiCircularQueue.getItems()));
        
        Button iniciaProgramaBtn = (Button)findViewById(R.id.button1);
        
        iniciaProgramaBtn.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View v){
        		getControlador().iniciaPrograma();
        		Log.d("MAIN_ACTIVITY", "depois controlador.iniciaPRogrma");
        	}
        });
    }
    
    public Controlador getControlador(){
    	return this.controlador;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public class UICircularQueue{
    	private String[]items;
    	private int head;
    	private int tail;
    	private int capacidade;
    	
    	public UICircularQueue(){
    		this.head = 0;
    		this.tail = 0;
    		this.capacidade = Constantes.CAPACIDADE_BUFFER;
    		this.items = new String[this.capacidade];
    		for(String item : items){
    			item = Constantes.UICircularQueueRelated.SEM_ITEM;
    		}
    	}
    	
    	public void enqueue(){
    		this.items[this.tail % this.capacidade] = Constantes.UICircularQueueRelated.COM_ITEM;
    		this.tail++;
    	}
    	
    	public void dequeue(){
    		this.items[this.head % this.capacidade] = Constantes.UICircularQueueRelated.SEM_ITEM;
    		this.head++;
    	}

		public String[] getItems() {
			return items;
		}

		public int getHead() {
			return head;
		}

		public void setHead(int head) {
			this.head = head;
		}

		public int getTail() {
			return tail;
		}

		public void setTail(int tail) {
			this.tail = tail;
		}

		public int getCapacidade() {
			return capacidade;
		}
    	
    	
    }
    

}
