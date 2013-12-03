package br.ufrj.dcc.util;

import android.graphics.Color;

public class Constantes {
	public final static int QTD_MAX_RECURSOS = 200;
	public final static int N_PRODUTORES = 2;
	public final static int N_CONSUMIDORES = 3;
	public final static int CAPACIDADE_BUFFER = 50;
	
	public static class UICircularQueueRelated{
		public final static String COM_ITEM = "#FF0000";
		public final static String SEM_ITEM = "#F0D3D3D3";
		public final static String TAIL = "#D0A67D3D";
		public final static String HEAD = "#D0D19275";
		
		public final static int COR_COM_ITEM = Color.RED;
		public final static int COR_SEM_ITEM = Color.parseColor("#F0D3D3D3");
		public final static int COR_TAIL = Color.parseColor("#D0A67D3D");
		public final static int COR_HEAD = Color.parseColor("#D0D19275");
	}
	
	public static class MensageiroComunicacao{
		public final static String PRODUZIU = "PRODUZIU";
		public final static String CONSUMIU = "CONSUMIU";
		
		public final static int ALTA_VELOCIDADE = 50;
		public final static int MEDIA_VELOCIDADE = 200;
		public final static int BAIXA_VELOCIDADE = 500;
	}
}
