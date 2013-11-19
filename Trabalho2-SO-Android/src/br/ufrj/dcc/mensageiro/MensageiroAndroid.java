package br.ufrj.dcc.mensageiro;

import android.os.Handler;
import android.os.Message;

public class MensageiroAndroid implements Mensageiro{
	private Handler handler;
	
	public MensageiroAndroid(Handler handler) {
		this.handler = handler;
	}


	@Override
	public void send(Object msg){
		Message message = new Message();
		message.obj = msg;
		this.handler.sendMessage(message);
	}
}
