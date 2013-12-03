package br.ufrj.dcc.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import br.ufrj.dcc.adapter.CircularQueueAdapter;
import br.ufrj.dcc.controller.Controlador;
import br.ufrj.dcc.mensageiro.MensageiroAndroid;
import br.ufrj.dcc.ui.UICircularQueue;
import br.ufrj.dcc.util.Constantes;

public class GridActivity extends Activity{
	private Controlador controlador;
	private GridView gridView;
	private UICircularQueue uiCircularQueue;
	private Button iniciaProgramaBtn;
	private RadioGroup radioGroup;
	private TextView prodCountUI;
	private MensageiroAndroid mensageiroAndroid = new MensageiroAndroid(new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			
			if(msg.obj.toString().equals(Constantes.MensageiroComunicacao.PRODUZIU)){
				uiCircularQueue.enqueue();
				GridActivity.this.prodCountUI.setText(String.valueOf(Integer.parseInt(GridActivity.this.prodCountUI.getText().toString()) + 1));
				
			}
			else if(msg.obj.toString().equals(Constantes.MensageiroComunicacao.CONSUMIU)){
				uiCircularQueue.dequeue();
			}
			
			gridView.setAdapter(new CircularQueueAdapter(GridActivity.this, android.R.layout.simple_list_item_1, uiCircularQueue.getItems()));			
			
			return true;
		}
	}), Constantes.MensageiroComunicacao.MEDIA_VELOCIDADE);
	
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
        
        this.iniciaProgramaBtn = (Button)findViewById(R.id.button1);
        this.radioGroup = (RadioGroup)findViewById(R.id.radioGroup1);
        this.prodCountUI = (TextView)findViewById(R.id.prodCount);
        
        
        iniciaProgramaBtn.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View v){
        		new ControladorTask(getControlador()).execute("");
        		Log.d("MAIN_ACTIVITY", "depois controlador.iniciaPRogrma");
        	}
        });
        
        for(int i = 0; i < radioGroup.getChildCount(); i++){
			radioGroup.getChildAt(i).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					onRadioButtonClicked(v);
				}
			});
		}
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
    
    public void onRadioButtonClicked(View view) {
    	Log.d("RADIO_BUTTON", "CLICKED!!!");
        boolean checked = ((RadioButton) view).isChecked();
        int velocidade = Constantes.MensageiroComunicacao.MEDIA_VELOCIDADE;
        
        switch(view.getId()) {
            case R.id.radioAlta:
                if (checked)
                    velocidade = Constantes.MensageiroComunicacao.ALTA_VELOCIDADE;
                break;
            case R.id.radioMedia:
                if (checked)
                	velocidade = Constantes.MensageiroComunicacao.MEDIA_VELOCIDADE;
                break;
            case R.id.radioBaixa:
            	if (checked)
            		velocidade = Constantes.MensageiroComunicacao.BAIXA_VELOCIDADE;
            	break;
            default:
            	velocidade = Constantes.MensageiroComunicacao.MEDIA_VELOCIDADE;
            	break;
        }
        
        this.mensageiroAndroid.setDelay(velocidade);
    }
    
    private class ControladorTask extends AsyncTask<String, Integer, String>{
    	private Controlador controlador;
    	
    	public ControladorTask(Controlador controlador) {
			super();
			this.controlador = controlador;
		}
    	
    	@Override
    	protected void onPreExecute(){
    		GridActivity.this.prodCountUI.setText("0");
    		Log.d("ASY_TASK", "desabilitando btn");
			iniciaProgramaBtn.setEnabled(false);
			iniciaProgramaBtn.setText("Rodando...");
			for(int i = 0; i < radioGroup.getChildCount(); i++){
				radioGroup.getChildAt(i).setEnabled(false);
			}
			Log.d("ASY_TASK", "btn desabilitado");
    	}

		@Override
    	protected String doInBackground(String ... args){
			Log.d("ASY_TASK", "antes controlador.inicaPRograma");
			controlador.iniciaPrograma();
			Log.d("ASY_TASK", "dps controlador.inicaPRograma");
    		return "";
    	}
		
		@Override
		protected void onPostExecute(String arg){
			iniciaProgramaBtn.setEnabled(true);
			iniciaProgramaBtn.setText("Iniciar");
			for(int i = 0; i < radioGroup.getChildCount(); i++){
				radioGroup.getChildAt(i).setEnabled(true);
			}
			
			GridActivity.this.inicializaDependencias();
		}
    }
    

}
