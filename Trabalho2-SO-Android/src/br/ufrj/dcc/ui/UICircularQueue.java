package br.ufrj.dcc.ui;

import br.ufrj.dcc.util.Constantes;

public class UICircularQueue{
	private String[]items;
	private int head;
	private int tail;
	private int capacidade;
	
	public UICircularQueue(){
		this.head = 0;
		this.tail = 0;
		this.capacidade = Constantes.CAPACIDADE_BUFFER;
		this.items = new String[this.capacidade];
		for(String item : items){
			item = Constantes.UICircularQueueRelated.SEM_ITEM;
		}
	}
	
	public void enqueue(){
		this.items[this.tail % this.capacidade] = Constantes.UICircularQueueRelated.COM_ITEM;
		this.tail++;
	}
	
	public void dequeue(){
		this.items[this.head % this.capacidade] = Constantes.UICircularQueueRelated.SEM_ITEM;
		this.head++;
	}

	public String[] getItems() {
		return items;
	}

	public int getHead() {
		return head;
	}

	public void setHead(int head) {
		this.head = head;
	}

	public int getTail() {
		return tail;
	}

	public void setTail(int tail) {
		this.tail = tail;
	}

	public int getCapacidade() {
		return capacidade;
	}
	
	
}