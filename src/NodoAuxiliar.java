/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author feijoada
 */
public class NodoAuxiliar 
{
     private int posicao; // posicao no arquivo
     private NodoArvore r;
     
     public NodoAuxiliar(int p, NodoArvore r)
     {
    	 posicao = p;
    	 this.r = r;
     }


	public int getPosicao() {
		return posicao;
	}

	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}

	public NodoArvore getR() {
		return r;
	}

	public void setR(NodoArvore r) 
	{
		this.r = r;
	}
}