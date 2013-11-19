package br.ufrj.dcc.model;

import java.util.concurrent.Semaphore;

import br.ufrj.dcc.mensageiro.Mensageiro;
import br.ufrj.dcc.util.CircularQueue;

public abstract class Ator extends Thread{
	protected CircularQueue buffer;
	protected Semaphore bufferCheioSemaforo;
	protected Semaphore temRecursoBufferSemaforo;
	protected Semaphore mutex;
	protected GerenteRecurso gerenteRecurso;
	protected Mensageiro mensageiro;
	
	public Ator(CircularQueue buffer, GerenteRecurso gerenteRecurso, Mensageiro mensageiro, Semaphore bufferCheioSemaforo, Semaphore temRecursoBufferSemaforo, Semaphore mutex) {
		this.buffer = buffer;
		this.bufferCheioSemaforo = bufferCheioSemaforo;
		this.temRecursoBufferSemaforo = temRecursoBufferSemaforo;
		this.mutex = mutex;
		this.gerenteRecurso = gerenteRecurso;
		this.mensageiro = mensageiro;
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
