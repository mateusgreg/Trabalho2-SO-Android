package br.ufrj.dcc.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import br.ufrj.dcc.controller.Controlador;

public class MainActivity extends Activity {
	private Controlador controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controlador = Controlador.getInstance();
        
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
    
}
