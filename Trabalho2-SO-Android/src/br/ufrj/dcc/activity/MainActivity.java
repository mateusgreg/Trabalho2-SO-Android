package br.ufrj.dcc.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import br.ufrj.dcc.controller.Controlador;
import br.ufrj.dcc.mensageiro.MensageiroAndroid;

public class MainActivity extends Activity {
	private Controlador controlador;
	private TextView textView;
	private MensageiroAndroid mensageiroAndroid = new MensageiroAndroid(new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			Log.d("MAIN_ACTIVITY_HANDLER", "handling message : " + msg.obj.toString());
			
			textView.setText(msg.obj.toString());
			
			Log.d("MAIN_ACTIVITY_HANDLER", "message handled");
			return true;
		}
	}));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controlador = Controlador.getInstance(mensageiroAndroid);
        
        Button iniciaProgramaBtn = (Button)findViewById(R.id.button1);
        this.textView = (TextView)findViewById(R.id.textView1);
        
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
    
}
