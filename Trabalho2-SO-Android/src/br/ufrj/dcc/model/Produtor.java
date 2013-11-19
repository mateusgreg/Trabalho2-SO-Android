package br.ufrj.dcc.model;

import java.util.concurrent.Semaphore;

import android.util.Log;
import br.ufrj.dcc.mensageiro.Mensageiro;
import br.ufrj.dcc.util.CircularQueue;
import br.ufrj.dcc.util.Constantes;

public class Produtor extends Ator {
	
	public Produtor(CircularQueue buffer, GerenteRecurso gerenteRecurso, Mensageiro mensageiro, Semaphore bufferCheioSemaforo, Semaphore temRecursoBufferSemaforo, Semaphore mutex){
		super(buffer, gerenteRecurso, mensageiro, bufferCheioSemaforo, temRecursoBufferSemaforo, mutex);
	}
	
	@Override
	public void runAtor() throws InterruptedException{
		while(true){
			super.bufferCheioSemaforo.acquire();
			
			super.mutex.acquire();
			if(super.gerenteRecurso.podeProduzir()){
				
				buffer.enqueue(new Recurso(super.gerenteRecurso.getIdRecurso()));
				this.mensageiro.send(new String(Constantes.MensageiroComunicacao.PRODUZIU));
				Log.d("PRODUTOR" + getId(), "" + super.gerenteRecurso.getIdRecurso());
				super.gerenteRecurso.atualizaProducao();
				super.mutex.release();
				
				super.temRecursoBufferSemaforo.release();
			}
			else{
				super.mutex.release();
				for(int i = 0; i < Constantes.N_CONSUMIDORES; i++)
					super.temRecursoBufferSemaforo.release(); //pode ocorrer de a producao ter alcancado o max mas este produtor nao sabia e tem um consumidor preso esperando 
				break;
			}	
		}
	}
	
}
