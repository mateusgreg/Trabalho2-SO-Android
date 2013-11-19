package br.ufrj.dcc.mensageiro;

import br.ufrj.dcc.util.Constantes;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class MensageiroAndroid implements Mensageiro{
	private Handler handler;
	private int delay;
	
	public MensageiroAndroid(Handler handler) {
		this(handler, Constantes.MensageiroComunicacao.BAIXA_VELOCIDADE);
	}
	
	public MensageiroAndroid(Handler handler, int delay){
		this.handler = handler;
		this.delay = delay;
	}


	@Override
	public void send(Object msg){
		Message message = new Message();
		message.obj = msg;
		try {
			Thread.sleep(this.delay);
		} catch (InterruptedException e) {
			Log.e("MENSAGEIRO_ANDROID_EXCEPTION", e.getMessage());
			e.printStackTrace();
		}
		this.handler.sendMessage(message);
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	
}
