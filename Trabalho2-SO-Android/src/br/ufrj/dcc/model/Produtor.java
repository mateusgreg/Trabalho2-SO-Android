package br.ufrj.dcc.model;

import java.util.concurrent.Semaphore;

import br.ufrj.dcc.util.CircularArrayList;

public class Produtor extends Ator {
	
	public Produtor(CircularArrayList<Recurso> buffer, Semaphore bufferCheioSemaforo, Semaphore temRecursoBufferSemaforo, Semaphore mutex){
		super(buffer, bufferCheioSemaforo, temRecursoBufferSemaforo, mutex);
	}
	
	@Override
	public void runAtor() throws InterruptedException{
		
	}
	
	

}
