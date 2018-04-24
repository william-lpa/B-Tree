
import com.sun.org.apache.xerces.internal.util.FeatureState;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author feijoada
 */
public class ArvoreB {
        private int ordem;
	private NodoArvore raiz;
	private static ArvoreB arvore;
	private PrintWriter gravarArq;
        private Arquivo arvoreArq;
        private static File arq;
                
	private ArvoreB(int ordem, File arq) {
           this.arq=arq;
            try {
                arvoreArq = new Arquivo(arq.getAbsolutePath());  
                if(arq.exists() && arvoreArq.fileSize()>0){
                 raiz=arvoreArq.carregarRaiz(0);//id da raiz
                 ordem=raiz.getCapacidade()/2;
                }else{
                 setOrdem(ordem);
                 raiz = new NodoArvore(ordem, null,0);
                }
            } catch (IOException ex) {
                Logger.getLogger(ArvoreB.class.getName()).log(Level.SEVERE, null, ex);
            }
              
	}

	private ArvoreB() {
	}

	public static ArvoreB getInstancia() {
		if (arvore == null)
			arvore = new ArvoreB();
		return arvore;
	}

	public static ArvoreB getInstancia(int ordem,File arq) {
		if (arvore == null)
			arvore = new ArvoreB(ordem,arq);
		return arvore;
	}

	public void inserir(int valor) throws Exception {
		if (pesquisar(valor) == null){
			raiz.addChave(valor);
                       NodoAuxiliar n = new NodoAuxiliar(raiz.getIDNodo(), raiz);
                        String dir =arq.getAbsolutePath();
                        arvoreArq.closeFile();
                        arq.delete();
                        arq= new File(dir);                        
                        arvoreArq = new Arquivo(arq.getAbsolutePath()); 
                        arvoreArq.gravar(n);     
                       
                }
		else
			throw new Exception("Não é permitido inserir números duplicados" +"("+valor+")");
	}

	public void remover(int valor) throws Exception {
		if (!raiz.buscaERemove(valor))
			throw new Exception("O número " + valor + " não foi removido.");
                else{
                    NodoAuxiliar n = new NodoAuxiliar(raiz.getIDNodo(), raiz);
                        String dir =arq.getAbsolutePath();
                        arvoreArq.closeFile();
                        arq.delete();
                        arq= new File(dir);                        
                        arvoreArq = new Arquivo(arq.getAbsolutePath()); 
                     
                        arvoreArq.gravar(n);                        
                       
                }
	}

	public NodoArvore pesquisar(int valor) {
		// Vai retornar um nodo pois no jtree o nodo q contem o valor ficara
		// selecionado, entao o q eu quero � o nodo
		return raiz.buscar(valor);
	}

        public NodoArvore getRaiz() {
		return this.raiz;
	}

	public void setRaiz(NodoArvore r) {
		this.raiz = r;
	}
	public void setOrdem(int o) {
		this.ordem = o;
	}
	public int getOrdem() {
		return ordem;
	}
    
}
