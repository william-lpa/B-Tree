import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


public class Arquivo 
{
	private String nomeArquivo;
	private long tamanhoArquivo;	
	RandomAccessFile arvore;
        

	public Arquivo(String nomeArquivo) throws IOException
	{ 
		this.nomeArquivo = nomeArquivo;
		arvore = new RandomAccessFile(this.nomeArquivo, "rw");
		this.tamanhoArquivo = arvore.length();
		if( tamanhoArquivo == 0)
			System.out.println("Arquivo vazio\n");
		else
		{
			
			System.out.println("Tamanho arquivo = "+tamanhoArquivo);
		}	
	}

        
        public long fileSize() throws IOException{
        return this.arvore.length();
        }
        
        public void gravar(NodoAuxiliar raiz) throws IOException{
            //gravar toda arvore
            NodoArvore n = raiz.getR();
            int i = 0;
		NodoArvore retorno = null;			
		         gravaArquivo(raiz, raiz.getPosicao());
		for (int k = 0; k < n.getNodosFilhos().length; k++) {
			if (n.getNodosFilhos()[k] != null){ //gravar quando for null
                            raiz=new NodoAuxiliar(n.getNodosFilhos()[k].getIDNodo(), n.getNodosFilhos()[k]);
				gravar(raiz);
                        }
		}
		return;          
            
        }
        
        public void closeFile() throws IOException{
            this.arvore.close();
        }
       
        
        public NodoArvore carregarRaiz(int ponteiro) throws IOException{
       
            
            NodoArvore raiz;//idNodo, nodosCriados, capacidade, indexChave,indexFilho,Filhos,chaves,pai
            arvore.seek(ponteiro);
            
          
            int id=arvore.readInt();
            int nodosCriados= arvore.readInt();
            int capacidade= arvore.readInt();
            int indexChave=arvore.readInt();
            int indexNodo=arvore.readInt();
            
            
            NodoArvore filhos[] = new NodoArvore[capacidade+1];
            
            	for (int k = 0; k < capacidade+1; k++) {
                    int posFiho=arvore.readInt();
                    System.out.println(arvore.getFilePointer());
                    //long posAnterior=arvore.getFilePointer();
                    //arvore.seek(posFiho*(4+4+4+4+4+4+(4*capacidade)+(4*capacidade+1))); //tamanho de cada nodo
                   if(posFiho!=-1){//verificar operações com null e nao null
                       long ter=arvore.getFilePointer();
                       filhos[k]=carregarRaiz(posFiho*(4+4+4+4+4+(4*capacidade)+(4*(capacidade+1))));                       
                       arvore.seek(ter);
                       
                   }
                   else
                       filhos[k]=null;
		}
                
                int chaves[]= new int[capacidade];
                for (int i=0; i< capacidade; i++)
			chaves[i]=arvore.readInt();
                
               /* NodoArvore pai;
                int posPai=arvore.readInt();
                if(posPai!=-1){
                pai=carregarRaiz(posPai*(4+4+4+4+4+4+(4*capacidade)+(4*(capacidade+1))));
                }else{
                    pai=null;//sem pai
                }*/
                
            raiz=new NodoArvore(capacidade/2, null, nodosCriados);
            raiz.setIndexChave(indexChave);
            raiz.setIndexNodo(indexNodo);
            raiz.setCapacidade(capacidade);
            raiz.setNodosFilhos(filhos);
            //raiz.setNodoPai(pai);
            raiz.setIDNodo(id);
            raiz.setItem(chaves);
            raiz=relacionarPaisEFilhos(raiz); // atribuir pai;
            return raiz;
            
        }
         public NodoArvore relacionarPaisEFilhos(NodoArvore raiz) {
        
             
		for (int k = 0; k < raiz.getNodosFilhos().length; k++) {
			if (raiz.getNodosFilhos()[k] != null){
			raiz.getNodosFilhos()[k].setNodoPai(raiz);
                        relacionarPaisEFilhos(raiz.getNodosFilhos()[k]);
                        }
		}
		return raiz;
    }
	
	private void gravaArquivo(NodoAuxiliar nodo, int ordem) throws IOException
	{
		int tamanho =(4+4+4+4+4+(4*nodo.getR().getCapacidade())+(4*(nodo.getR().getCapacidade()+1))); //idNodo, nodosCriados, capacidade, indexChave,indexFilho,Filhos,chaves,
		int p = ordem * tamanho;
		NodoArvore nodoArvoreB = nodo.getR();
		arvore.seek(p);
                arvore.writeInt(nodoArvoreB.getIDNodo());
                arvore.writeInt(nodoArvoreB.getNodosCriados());
                arvore.writeInt(nodoArvoreB.getCapacidade());
		//arvore.writeInt(nodoArvoreB.getCapacidade());
                arvore.writeInt(nodoArvoreB.getIndexChave());
		arvore.writeInt(nodoArvoreB.getIndexNodo());
                
		for (int i=0; i < nodoArvoreB.getCapacidade()+1; i++){  // numero de filho = capacidade + 1
                    if(nodoArvoreB.getNodosFilhos()[i]!=null){
                    arvore.writeInt(nodoArvoreB.getNodosFilhos()[i].getIDNodo());
                    }else{
                        arvore.writeInt(-1);//nulo
                    } 
                    }
		for (int i=0; i< nodoArvoreB.getCapacidade(); i++){
		arvore.writeInt(nodoArvoreB.getChave()[i]);
                }
              
                
             /*   if(nodoArvoreB.getNodoPai()!=null)
		arvore.writeInt(nodoArvoreB.getNodoPai().getIDNodo()); //segmento onde o pai está
                else
                    arvore.writeInt(-1);//sem pai*/
		
	}

   
	
}