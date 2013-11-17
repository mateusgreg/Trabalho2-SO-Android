package br.ufrj.dcc.model;

import java.util.concurrent.Semaphore;

import br.ufrj.dcc.util.CircularArrayList;

public abstract class Ator extends Thread{
	protected CircularArrayList<Recurso> buffer;
	protected Semaphore bufferCheioSemaforo;
	protected Semaphore temRecursoBufferSemaforo;
	protected Semaphore mutex;
	protected GerenteRecurso gerenteRecurso;
	
	public Ator(CircularArrayList<Recurso> buffer, Semaphore bufferCheioSemaforo, Semaphore temRecursoBufferSemaforo, Semaphore mutex) {
		this.buffer = buffer;
		this.bufferCheioSemaforo = bufferCheioSemaforo;
		this.temRecursoBufferSemaforo = temRecursoBufferSemaforo;
		this.mutex = mutex;
		this.gerenteRecurso = GerenteRecurso.getInstance();
	}
	
	@Override
	public void run(){
		try {
			this.runAtor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public abstract void runAtor() throws InterruptedException;
}
