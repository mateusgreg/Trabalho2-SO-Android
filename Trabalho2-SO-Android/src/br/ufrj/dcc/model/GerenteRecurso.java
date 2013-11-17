package br.ufrj.dcc.model;

import br.ufrj.dcc.util.Constantes;

public class GerenteRecurso {
	private int contadorRecursosProduzidos;
	private int contadorRecursosConsumidos;
	private int maxRecursos;
	
	private static GerenteRecurso instancia;
	
	public static GerenteRecurso getInstance(){
		if(instancia == null) instancia = new GerenteRecurso();
		return instancia;
	}
	
	private GerenteRecurso(){
		this.contadorRecursosConsumidos = 0;
		this.contadorRecursosProduzidos = 0;
		this.maxRecursos = Constantes.QTD_MAX_RECURSOS;
	}
	
	public boolean podeProduzir(){
		return this.contadorRecursosProduzidos <= this.maxRecursos;
	}
	
	public boolean podeConsumir(){
		return this.contadorRecursosConsumidos <= this.maxRecursos;
	}

}
