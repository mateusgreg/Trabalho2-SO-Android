package br.ufrj.dcc.controller;

import java.util.concurrent.Semaphore;

import android.util.Log;
import br.ufrj.dcc.mensageiro.Mensageiro;
import br.ufrj.dcc.model.Ator;
import br.ufrj.dcc.model.Consumidor;
import br.ufrj.dcc.model.GerenteRecurso;
import br.ufrj.dcc.model.Produtor;
import br.ufrj.dcc.util.CircularQueue;
import br.ufrj.dcc.util.Constantes;

public class Controlador {
	private CircularQueue buffer;
	private Semaphore bufferCheioSemaforo;
	private Semaphore temRecursoBufferSemaforo;
	private Semaphore mutex;
	private GerenteRecurso gerenteRecurso;
	private Mensageiro mensageiro;
	
	private static Controlador instancia;
	
	public static Controlador getInstance(Mensageiro mensageiro){
		if(instancia == null) instancia = new Controlador(mensageiro);
		return instancia;
	}
	private Controlador(Mensageiro mensageiro){
		this.reiniciaDependencias(mensageiro);
	}
	
	private void reiniciaDependencias(Mensageiro mensageiro){
		this.buffer = new CircularQueue(Constantes.CAPACIDADE_BUFFER);
		this.bufferCheioSemaforo = new Semaphore(Constantes.CAPACIDADE_BUFFER);
		this.temRecursoBufferSemaforo = new Semaphore(0);
		this.mutex = new Semaphore(1);
		this.gerenteRecurso = new GerenteRecurso();
		this.mensageiro = mensageiro;
	}
	
	public void iniciaPrograma(){
		this.reiniciaDependencias(this.mensageiro);
		
		Ator[] atores = new Ator[Constantes.N_PRODUTORES + Constantes.N_CONSUMIDORES];
		
		for(int i = 0; i < Constantes.N_PRODUTORES; i++){
			atores[i] = new Produtor(this.buffer, this.gerenteRecurso, this.mensageiro, this.bufferCheioSemaforo, this.temRecursoBufferSemaforo, this.mutex);
		}
		
		for(int i = Constantes.N_PRODUTORES; i < Constantes.N_PRODUTORES + Constantes.N_CONSUMIDORES; i++){
			atores[i] = new Consumidor(this.buffer, this.gerenteRecurso, this.mensageiro, this.bufferCheioSemaforo, this.temRecursoBufferSemaforo, this.mutex);
		}
		
		for(Ator ator : atores){
			ator.start();
		}
		
//		for(Ator ator : atores){
//			try {
//				ator.join();
//				Log.d("CONTROLADOR", "ATOR" + ator.getId() + " ACABOU");
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		
		Log.d("CONTROLADOR", "Acabou");
	}
}
