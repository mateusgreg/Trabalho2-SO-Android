package br.ufrj.dcc.model;

import java.util.concurrent.Semaphore;

import br.ufrj.dcc.util.CircularArrayList;

public class Consumidor extends Ator{
	
	public Consumidor(CircularArrayList<Recurso> buffer, Semaphore bufferCheioSemaforo, Semaphore temRecursoBufferSemaforo, Semaphore mutex){
		super(buffer, bufferCheioSemaforo, temRecursoBufferSemaforo, mutex);
	}
	
	@Override
	public void runAtor() throws InterruptedException{
		while(true){
			super.temRecursoBufferSemaforo.acquire();
			
			super.mutex.acquire();
			if(super.gerenteRecurso.podeConsumir()){
				
				Recurso recurso = buffer.dequeue();
				super.gerenteRecurso.atualizaConsumo();
				super.mutex.release();
				
				super.bufferCheioSemaforo.release();
			}
			else{
				super.mutex.release();
				super.bufferCheioSemaforo.release();
				break;
			}
		}
	}
}
