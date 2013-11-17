package br.ufrj.dcc.model;

import java.util.concurrent.Semaphore;

import br.ufrj.dcc.util.CircularArrayList;

public class Produtor extends Ator {
	
	public Produtor(CircularArrayList<Recurso> buffer, Semaphore bufferCheioSemaforo, Semaphore temRecursoBufferSemaforo, Semaphore mutex){
		super(buffer, bufferCheioSemaforo, temRecursoBufferSemaforo, mutex);
	}
	
	@Override
	public void runAtor() throws InterruptedException{
		while(true){
			super.bufferCheioSemaforo.acquire();
			
			super.mutex.acquire();
			if(super.gerenteRecurso.podeProduzir()){
				
				buffer.enqueue(new Recurso(super.gerenteRecurso.getIdRecurso()));
				super.gerenteRecurso.atualizaProducao();
				super.mutex.release();
				
				super.temRecursoBufferSemaforo.release();
			}
			else{
				super.mutex.release();
				super.temRecursoBufferSemaforo.release(); //pode ocorrer de a producao ter alcancado o max mas este produtor nao sabia e tem um consumidor preso esperando 
				break;
			}	
		}
	}
	
}
