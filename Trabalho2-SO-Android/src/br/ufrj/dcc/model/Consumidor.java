package br.ufrj.dcc.model;

import java.util.concurrent.Semaphore;

import android.util.Log;
import br.ufrj.dcc.mensageiro.Mensageiro;
import br.ufrj.dcc.util.CircularQueue;
import br.ufrj.dcc.util.Constantes;

public class Consumidor extends Ator{
	
	public Consumidor(CircularQueue buffer, GerenteRecurso gerenteRecurso, Mensageiro mensageiro, Semaphore bufferCheioSemaforo, Semaphore temRecursoBufferSemaforo, Semaphore mutex){
		super(buffer, gerenteRecurso, mensageiro, bufferCheioSemaforo, temRecursoBufferSemaforo, mutex);
	}
	
	@Override
	public void runAtor() throws InterruptedException{
		while(true){
			super.temRecursoBufferSemaforo.acquire();
			
			super.mutex.acquire();
			if(super.gerenteRecurso.podeConsumir()){
				
				Recurso recurso = (Recurso)buffer.dequeue();
				super.gerenteRecurso.atualizaConsumo();
				Thread.sleep(500);
				this.mensageiro.send(new String("CONSUMIDOR" + getId() + " : " + recurso.getItem()));
				Log.d("CONSUMIDOR" + getId(), "" + recurso.getItem());
				super.mutex.release();
				
				super.bufferCheioSemaforo.release();
			}
			else{
				super.mutex.release();
				for(int i = 0; i < Constantes.N_CONSUMIDORES; i++)
					super.bufferCheioSemaforo.release();
				break;
			}
		}
	}
}
