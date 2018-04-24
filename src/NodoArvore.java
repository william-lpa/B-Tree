
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author feijoada
 */
public class NodoArvore {

	private int[] chave;
	private int indexChave;
	private NodoArvore[] filhos;
	private int indexNodo;
	private NodoArvore nodoPai;
	private int capacidade;
        private int IDNodo;
        private static int nodosCriados;

    public  int getNodosCriados() {
        return nodosCriados;
    }

    public  void nodoCriado() {
        nodosCriados++;
    }

    public int getIDNodo() {
        return IDNodo;
    }

    public NodoArvore getNodoPai() {
        return nodoPai;
    }

    public int[] getChave() {
        return chave;
    }
    public int getCapacidade() {
        return capacidade;
    }
          
    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public void setNodoPai(NodoArvore nodoPai) {
        this.nodoPai = nodoPai;
    }

    public void setIDNodo(int IDNodo) {
        this.IDNodo = IDNodo;
    }
    
    
      
      
      

        
	/*
	
	 * Controi um novo NodoArvore nodo possui um vetor de chaves(valores) e um vetor de nodos descendentes.
	 * A quantidade de chaves e descendentes varia conforme a ordem m
         Filhos 
        * Quantidade mínima: m
	* Quantidade máxima: 2m+1
	 Chaves
	 * Quantidade mínima: m
	 * Quantidade m�xima: 2m
	 Excessão: A raiz da Árvore pode possuir entre 1(um) e 2m chaves.
	 * @param ordem Define a quantidade de chaves e descendentes deste nodo.
	 * @param pai -  O nodo pai deste nodo na estrura da Árvore B.
     * @param IDNodo
     * @param nodosCriados
	 * @see Arvore
	 */
	public NodoArvore(int ordem, NodoArvore pai, int nodosCriados) {
        	this.capacidade = ordem*2;	
                chave = new int[capacidade];
		filhos = new NodoArvore[capacidade + 1];
		indexChave = 0;
		indexNodo = 0;
		nodoPai = pai;
 		this.capacidade = ordem*2;
                this.IDNodo=nodosCriados;
                this.nodosCriados=nodosCriados;
	}

	public void setItem(int[] chave) {
		this.chave = chave;
	}

	public int[] getItem() {
		return chave;
	}
        public int getIndexNodo(){
            return this.indexNodo;
        }

        
	/*
	 * Adiciona um chave(chave) a lista de itens deste nodo. Os itens são
	 * inseridos de forma ordenada crescente. Caso a quantidade de chaves supere
	 * a quantidade maxima defida pela ordem ocorre a quebra do nodo, no qual:
	 
            O valor central do itens(incluindo o valor sendo inserido) é passado
	    para o nodo de nível superior S
	   Os valores menores do que o central são passados como descendentes menores do nodo S
	   Os valores maiores do que o central são passados como descendentes maiores do nodo S.
	 
	 * @param valor O valor do novo chave(chave) a ser adicionado.
	 */
	public void addChave(int valor) throws Exception {
		if (indexChave < chave.length	|| (indexChave == chave.length && indexNodo > 0)) {
			if (!possuiItem()) {
				adicionaChaveOrdenada(valor);
			} else {
				boolean adicionouValor = false;
				for (int i = 0; i < indexChave && !adicionouValor; i++) {
					if (chave[i] > valor) {
						adicionouValor = true;
						if (filhos[i] != null)
							filhos[i].addChave(valor);
						else {
							adicionaChaveOrdenada(valor);
						}
					}
				}
				if (!adicionouValor) { 
					if (filhos[indexChave] != null)
						filhos[indexChave].addChave(valor);
					else {
						adicionaChaveOrdenada(valor);
					}
				}
			}
		} else {// quebrou

			int vetorAux[] = new int[chave.length + 1];
			vetorAux = copiarVetorItemEAdicionaValor(valor);
			int posicao = (vetorAux.length / 2); //meio
			if (nodoPai == null) {
				// é raiz principal
				NodoArvore menoresValores = addFilho();
				for (int i = 0; i < posicao; i++) {
					menoresValores.addChave(vetorAux[i]);
				}
				NodoArvore maioresValores = addFilho();
				for (int i = posicao + 1; i < vetorAux.length; i++) {
					maioresValores.addChave(vetorAux[i]);
				}

				//instanciar um novo vetor e deixar apenas o valor mediano
				this.chave = new int[capacidade];
				indexChave = 0;
				addChave(vetorAux[posicao]);
			} else {
				// Qualquer nodo da arvore
				// Os valores menores continuaram neste nodo

				// Remove todos os itens e rafaz o vetor de itens
				chave = new int[capacidade];
				indexChave = 0;
				for (int i = 0; i < posicao; i++) {
					adicionaChaveOrdenada(vetorAux[i]);
				}

				// Os valores maiores serão irmao deste nodo, pois o valor do meu irão para o pai
                                nodoCriado();
				NodoArvore valoresMaiores = new NodoArvore(capacidade/2, nodoPai,nodosCriados);
				for (int i = posicao + 1; i < vetorAux.length; i++) {
					valoresMaiores.addChave(vetorAux[i]);
				}
				nodoPai.ocorreuQuebraNoFilho(valoresMaiores,
						vetorAux[posicao]);

			}
		}
	}

	private void ocorreuQuebraNoFilho(NodoArvore valoresMaioresNodo, int valor)
			throws Exception {
		// Este metodo é chamado quando o filho desta classe, ou seja, esse é o
		// pai, teve um estouro
		// Entao seu filho chama este metodo, passa o valor do meio "valor", que
		// deve ser adiciona como item no pai
		// Passa tamb�m o nodo com os maiores valores que ser� o filho do
		// pai(this)

		// Verifica se tem como adiciona o item ao pai
		if (indexChave < this.chave.length) {
			// Adiciona o valor e ordena
			adicionaChaveOrdenada(valor);

			valoresMaioresNodo.nodoPai = this;
			adicionaNodo(valor, valoresMaioresNodo);

		}
		// Se nao tiver, ou seja, ocorreu um estouro no pai
		else {
			// ESTOURO em cadeia, ou seja, deu estouro em um, mas ao passar o do meeio para cima o de cima tambem estorou
			int vetorTeste[] = new int[chave.length + 1];
			vetorTeste = copiarVetorItemEAdicionaValor(valor);
			int posicao = (vetorTeste.length / 2);

			// Verifica se foi estouro na raiz, pois se foi, deve criar uma nova
			// raiz na arvore
			if (nodoPai == null) {
				// CRIAR UMA NOVA RAIZ a PRINCIPAL
                            nodoCriado();
				NodoArvore raiz = new NodoArvore(capacidade/2, null,nodosCriados);

				// Adiciona o valor central, q ser� o unico chave da nova raiz
				raiz.addChave(vetorTeste[posicao]);

				// LEMBRANDO: raiz principal é nivel 1, os filhos dela sao nivel 2 e assim por diante
				// Cria o nodo filho da raiz, ou seja, filho do nivel 2.
                                nodoCriado();
				NodoArvore maioresNumero = new NodoArvore(capacidade/2, raiz,nodosCriados);
				for (int j = posicao + 1; j < vetorTeste.length; j++)
					maioresNumero.addChave(vetorTeste[j]);
                               // Adiciona os valor maiores valores ao nodo filho da raiz

				chave = new int[capacidade];
				indexChave = 0;
				for (int j = 0; j < posicao; j++) {
					// Precisa adicionar o valor na mao, não pode usar o metodo addChave
					chave[j] = vetorTeste[j];// Adiciona os menores valores, ao nodo filho da raiz
					indexChave++;
				}

				if (valor < vetorTeste[posicao]) {
					// Passa os nodos do nivel 1 para o nivel 3, e já seta para
					// null
					for (int i = posicao; i < filhos.length; i++) {
						maioresNumero.adicionaNodoOrdenado(filhos[i]);
						filhos[i] = null;
						indexNodo--;
					}
					this.adicionaNodoOrdenado(valoresMaioresNodo);
					valoresMaioresNodo.nodoPai = this;
				} else if (valor >= vetorTeste[posicao]) {
					// Passa os nodos do nivel 1 para o nivel 3, e já seta para
					// null
					for (int i = posicao + 1; i < filhos.length; i++) {
						maioresNumero.adicionaNodoOrdenado(filhos[i]);
						filhos[i] = null;
						indexNodo--;
					}

					// O filho da raiz com os maiores numeros, adiciona o filho
					// (nivel 3), este filho do nivel 3, foi onde ocorreu o
					// primeiro estouro
					maioresNumero.adicionaNodoOrdenado(valoresMaioresNodo);
					// maioresNumero.addNodo(valoresMaioresNodo);
					valoresMaioresNodo.nodoPai = maioresNumero;

				}

				// Adiciona os filhos na raiz principal da arvore
				raiz.addNodo(this);
				raiz.addNodo(maioresNumero);
				// Este nodo, ou seja o this, ser� filho da raiz, ou seja o nodo
				// pai dessa classe ser� a raiz
				this.nodoPai = raiz;
				// set na arvore, q a nova raiz agora � outra
				ArvoreB.getInstancia().setRaiz(raiz);

			}
			// Senao, foi um estouro num nodo qualquer
			else {
                            nodoCriado();
				NodoArvore maioresNumero = new NodoArvore(capacidade/2, null,nodosCriados);
				for (int j = posicao + 1; j < vetorTeste.length; j++) {
					maioresNumero.addChave(vetorTeste[j]);// Adiciona os valor
															// maiores, ao nodo
															// filho da raiz
				}
				chave = new int[capacidade];
				indexChave = 0;
				for (int j = 0; j < posicao; j++) {
					// Precisa adicionar o valor na mao, nao pode usar o metodo
					// addChave
					chave[j] = vetorTeste[j];// Adiciona os menores valores ao
											// nodo filho
					indexChave++;
				}

				if (valor < vetorTeste[posicao]) {
					for (int i = posicao; i < filhos.length; i++) {
						maioresNumero.addNodo(this.filhos[i]);
						this.filhos[i] = null;
						this.indexNodo--;
					}
					adicionaNodoOrdenado(valoresMaioresNodo);
				} else if (valor >= vetorTeste[posicao]) {
					for (int i = posicao + 1; i < filhos.length; i++) {
						maioresNumero.addNodo(this.filhos[i]);
						this.filhos[i] = null;
						this.indexNodo--;
					}
					maioresNumero.addNodo(valoresMaioresNodo);
				}

				this.nodoPai.ocorreuQuebraNoFilho(maioresNumero,
						vetorTeste[posicao]);
			}
		}
	}

	// Adiciona um novo Nodo, ordenando ele para que fique em ordem
	 //@param valoresMaioresNodo O nodo que será adicionado
	 
	private void adicionaNodoOrdenado(NodoArvore valoresMaioresNodo) throws Exception {
		try {
			boolean adicionou = true;
			int indexDoNodo = 0;
			int valor = 0;
			if (valoresMaioresNodo.indexChave > 0) {
				valor = valoresMaioresNodo.getItem()[0];
				adicionou = false;
			}
			for (int i = 0; i < indexChave && !adicionou; i++) {
				if (chave[i] > valor) {
					for (int j = indexNodo; j > indexDoNodo
							&& indexNodo < filhos.length; j--) {
						filhos[j] = filhos[j - 1];
					}
					filhos[indexDoNodo] = valoresMaioresNodo;
					valoresMaioresNodo.nodoPai = this;
					indexNodo++;
					adicionou = true;
				} else {
					indexDoNodo++;
				}
			}
			if (!adicionou) {
				addNodo(valoresMaioresNodo);
			}
		} catch (Exception e) {
			throw new Exception("Erro no método 'adicionaNodoOrdenado'. Erro: "
					+ e.getMessage());
		}
	}

	/** Adiciona um novo Nodo, apos o valor já adicionado
	 // @param valor O valor que já foi selecionado e que terá o novo nodo
	 // @param valoresMaioresNodo O nodo que ser� adicionado*/
	private void adicionaNodo(int valor, NodoArvore valoresMaioresNodo) {
		boolean adicionou = false;
		int indexDoNodo = 1;
		for (int i = 0; i < indexChave && !adicionou; i++) {
			if (chave[i] == valor) {
				for (int j = indexNodo; j > indexDoNodo
						&& indexNodo < filhos.length; j--) {
					filhos[j] = filhos[j - 1];
				}
				filhos[indexDoNodo] = valoresMaioresNodo; //atribuir filho
				valoresMaioresNodo.nodoPai = this;
				indexNodo++;
				adicionou = true;
			} else {
				indexDoNodo++;
			}
		}
	}

	private void addNodo(NodoArvore nodo) {
		// Adiciona o nodo do paramtero como filho desse nodo, o this.
		this.filhos[indexNodo] = nodo;
		// Seta o pai do nodo criado
		nodo.nodoPai = this;
		indexNodo++;
	}

	private void adicionaChaveOrdenada(int valor) {
		// Deixar o vetor item ordenado
		chave[indexChave] = valor;
		indexChave++;
		this.sortItem(chave, indexChave);
	}

	/*Cria um novo vetor ordenado de tamanho (ordem * 2 + 1) contendo os itens deste nodo, mais o valor indicado.
	 * @param valor O valor extra a ser adicionado
	 * @return Um vetor ordenado contendo o parametro valor mais as chaves deste
	 */
	private int[] copiarVetorItemEAdicionaValor(int valor) {
		// Copiar os itens do vetor chave para o vetor resultado
		int[] retorno = new int[indexChave + 1];
                
		System.arraycopy(chave, 0, retorno, 0, indexChave); //garantir que não seja ordenado o vetor orignal
		retorno[indexChave] = valor;
		this.sortItem(retorno, retorno.length);
		return retorno;
	}

	// Instancia e insere um nodo de mesma ordem aos descendentes do nodo atual.
	// @return O nodo que foi inserido.
	public NodoArvore addFilho() {
                nodoCriado();
		filhos[indexNodo] = new NodoArvore(capacidade/2, this,nodosCriados);
		indexNodo++;
 		return filhos[indexNodo - 1];
	}

	public void removeChave(int valor) {
		for (int i = 0; i < chave.length; i++) {
			if (chave[i] == valor) {
				for (int j = i; j < chave.length - 1; j++) {
					chave[j] = chave[j + 1];
				}
				indexChave--;
				i = chave.length;

				sortItem(chave, indexChave);
			}
		}
	}

	public void removeNodo(NodoArvore nodo) {
		for (int i = 0; i < filhos.length; i++) {
			if (filhos[i] == nodo) {
				if (i == filhos.length - 1)
					filhos[i] = null;
				for (int j = i; j < filhos.length - 1; j++) {
					filhos[j] = filhos[j + 1];
				}
				indexNodo--;
				i = filhos.length;

			}
		}

	}

	// Percorre todos os itens do nodo atual e seus descendentes.
	public NodoArvore buscar(int valor) {
		int i = 0;
		NodoArvore retorno = null;
		while (i < indexChave) {
			if (valor == chave[i]) {
				return this;
			} else {
				i++;
			}
		}
		for (int k = 0; k < filhos.length && retorno == null; k++) {
			if (filhos[k] != null)
				retorno = filhos[k].buscar(valor);
		}
		return retorno;
	}

	public NodoArvore retornaMenores(NodoArvore nodo) throws Exception {
		// serão passado um vetor como parâmetro e com base nele, é feita a
		// separão dos elementos retornando um nodoMenores[] e um nodoMaiores[]

		int itemCentral = nodo.getItem().length / 2;
		NodoArvore nodosMenores = this.addFilho();

		for (int i = 0; i < itemCentral; i++) {
			nodosMenores.addChave(nodo.getItem()[i]);

		}
		nodosMenores.setNodosFilhos(nodo.getNodosFilhos());
		return nodosMenores;
	}

	public void setNodosFilhos(NodoArvore[] filhos) {
		this.filhos = filhos;
	}

	public NodoArvore[] getNodosFilhos() {
		return this.filhos;
	}

	public boolean possuiItem() {
		return indexChave != 0;
	}

	public void setIndexNodo(int indexNodo) {
		this.indexNodo = indexNodo;
	}

	public int getIndexChave() {
		return indexChave;
	}

	public void setIndexChave(int indexChave) {
		this.indexChave = indexChave;
	}

	// retorna uma String contendo as chaves deste nodo.
        @Override
	public String toString() {
		String item = "";
		for (int i = 0; i < indexChave; i++) {
			item = item + this.chave[i] + " | ";
		}
		if (item.length() > 3)
			item = item.substring(0, item.length() - 3);
		return item;
	}

	
	private void sortItem(int[] vetor, int posicaoFinal) {
		java.util.Arrays.sort(vetor, 0, posicaoFinal);
	}

	public boolean buscaERemove(int valor) throws Exception {
		NodoArvore nodoComValor = buscar(valor);
		if (nodoComValor == null) //valor não encontrado na arvore
			return false;
		else {
			// remover chave
			if (nodoComValor.indexChave > nodoComValor.capacidade/2
					&& nodoComValor.indexNodo == 0) {
				// verificar remoção sem precisar balancear (folhas)
				nodoComValor.removeChave(valor);
                        }else {
				// remover e balancear
                            
				// tem filho para retornar
				if (nodoComValor.indexNodo > 0) {
					for (int i = 0; i < nodoComValor.indexChave; i++) {
						if (nodoComValor.chave[i] == valor) {
							// VERIFICAR SE TEM UM FILHO Q PODE SUBIR UM
							// ELEMENTO
							if (nodoComValor.filhos[i + 1].indexChave > nodoComValor.capacidade/2) {
								nodoComValor.chave[i] = nodoComValor.filhos[i + 1]
										.primeiroItemRemove();
								return true;
							} else if (nodoComValor.filhos[i].indexChave > nodoComValor.capacidade/2) {
								nodoComValor.chave[i] = nodoComValor.filhos[i]
										.ultimoItemRemove();
								return true;
							} else {
								for (int j = 0; j < nodoComValor.filhos[i + 1].indexChave; j++) {
									nodoComValor.filhos[i]
											.adicionaChaveOrdenada(nodoComValor.filhos[i + 1].chave[j]);
								}
								if (nodoComValor.nodoPai == null) {
									nodoComValor.filhos[i].nodoPai = null;
									ArvoreB.getInstancia().setRaiz(
											nodoComValor.filhos[i]);
								}
								nodoComValor.removeChave(valor);
								nodoComValor
										.removeNodo(nodoComValor.filhos[i + 1]);

								// Verificar nodo pai
								nodoComValor.nodoPai
										.verificaReorganizarNodo(nodoComValor.nodoPai);
							}
						}
					}
				} else {
					// folha
					NodoArvore irmaoMaior = nodoComValor.nodoPai
							.irmaoMaiorItens(nodoComValor);
					if (irmaoMaior != null) {
						// TEMOS UM IRMAO QUE PODE EMPRESTAR
						int itemSeparador = nodoComValor.nodoPai.itemSeparador(
								nodoComValor, irmaoMaior);
						nodoComValor.removeChave(valor);
						nodoComValor.adicionaChaveOrdenada(itemSeparador);
						int valorParaPai = irmaoMaior.primeiroUltimoItem(valor);
						nodoComValor.nodoPai.substituirItem(itemSeparador,
								valorParaPai);
						// Nao precisa verificar o pai
					} else {
						// NAO POSSUI IRMAO QUE POSSA EMPRESTAR
						// FAZER O COMBINE
						NodoArvore irmao = nodoComValor.nodoPai
								.irmaoItens(nodoComValor);
						int itemSeparador = nodoComValor.nodoPai.itemSeparador(
								nodoComValor, irmao);
						nodoComValor.removeChave(valor);

						// Adiciona os valores
						irmao.adicionaChaveOrdenada(itemSeparador);
						for (int i = 0; i < nodoComValor.indexChave; i++) {
							irmao.adicionaChaveOrdenada(nodoComValor.chave[i]);
						}

						nodoComValor.nodoPai.removeChave(itemSeparador);
						nodoComValor.nodoPai.removeNodo(irmao);
						nodoComValor.nodoPai.removeNodo(nodoComValor);
						nodoComValor.nodoPai.adicionaNodoOrdenado(irmao);

						// Precisa verificar o pai
						nodoComValor.nodoPai
								.verificaReorganizarNodo(nodoComValor.nodoPai);
					}
				}
			}
			return true;
		}
	}

	public void verificaReorganizarNodo(NodoArvore nodo) throws Exception {
		if (nodo.nodoPai == null) {
			// Raiz principal
			if (nodo.indexChave == 0) {
				if (indexNodo == 1) {
					nodo = nodo.filhos[indexNodo - 1];
					nodo.nodoPai = null;
					ArvoreB.getInstancia().setRaiz(nodo);
				}
			}
		} else if (nodo.indexChave < nodo.capacidade/2) {
			NodoArvore irmao = nodo.nodoPai.irmaoItens(nodo);
			int itemSeparador = nodo.nodoPai.itemSeparador(nodo, irmao);

			if (nodo.indexChave + irmao.indexChave + 1 > (nodo.capacidade)) {
				// empresta do irmao
				nodo.adicionaChaveOrdenada(itemSeparador);
				int valorParaPai = irmao.primeiroUltimoItem(itemSeparador);
				nodo.nodoPai.substituirItem(itemSeparador, valorParaPai);
				NodoArvore nodoIrmao = irmao.primeiroUltimoNodo(itemSeparador);
				nodo.adicionaNodoOrdenado(nodoIrmao);
				// Nao precisa verificar o pai
			} else {
				// Adiciona os valores
				irmao.adicionaChaveOrdenada(itemSeparador);

				for (int i = 0; i < nodo.indexChave; i++) {
					irmao.adicionaChaveOrdenada(nodo.chave[i]);
				}
				for (int i = 0; i < nodo.indexNodo; i++) {
					irmao.adicionaNodoOrdenado(nodo.filhos[i]);
				}

				nodo.nodoPai.removeChave(itemSeparador);
				nodo.nodoPai.removeNodo(irmao);
				nodo.nodoPai.removeNodo(nodo);
				nodo.nodoPai.adicionaNodoOrdenado(irmao);

				nodo.nodoPai.verificaReorganizarNodo(nodo.nodoPai);
			}
		}
	}

	private void substituirItem(int itemSeparador, int valorParaPai) {
		for (int i = 0; i < indexChave; i++) {
			if (chave[i] == itemSeparador) {
				chave[i] = valorParaPai;
				i = indexChave;
			}
		}
	}

	private NodoArvore primeiroUltimoNodo(int valor) {
		NodoArvore retorno = null;
		if (chave[0] > valor)
			retorno = filhos[0];
		else {
			retorno = filhos[indexNodo - 1];
		}
		removeNodo(retorno);
		return retorno;
	}

	private int primeiroUltimoItem(int valor) {
		int retorno = 0;
		if (chave[0] > valor)
			retorno = chave[0];
		else {
			retorno = chave[indexChave - 1];
		}
		removeChave(retorno);
		return retorno;
	}

	private int itemSeparador(NodoArvore nodoComValor, NodoArvore irmaoMaior) {
		int valor = 0;
		for (int i = 0; i < indexNodo; i++) {
			if (filhos[i] == nodoComValor || filhos[i] == irmaoMaior) {
				valor = chave[i];
				i = indexNodo;
			}
		}
		return valor;
	}

	private NodoArvore irmaoItens(NodoArvore irmao) {
		NodoArvore irmao2 = null;
		for (int i = 0; i < indexNodo; i++) {
			if (filhos[i] == irmao) {
				if (i == 0) {
					return filhos[i + 1];
				} else if (i == indexNodo - 1) {
					return filhos[i - 1];
				} else {
					return filhos[i - 1];
				}
			}
		}
		return irmao2;
	}

	private NodoArvore irmaoMaiorItens(NodoArvore irmao) {
		NodoArvore irmaoMaior = null;
		for (int i = 0; i < indexNodo; i++) {
			if (filhos[i] == irmao) {
				if (i == 0) {
					if (filhos[i + 1].indexChave > capacidade/2)
						irmaoMaior = filhos[i + 1];
				} else if (i == indexNodo - 1) {
					if (filhos[i - 1].indexChave > capacidade/2)
						irmaoMaior = filhos[i - 1];
				} else {
					if (filhos[i + 1].indexChave > filhos[i - 1].indexChave)
						irmaoMaior = filhos[i + 1];
					else
						irmaoMaior = filhos[i - 1];
					if (irmaoMaior.indexChave <= capacidade/2)
						irmaoMaior = null;
				}
			}
		}
		return irmaoMaior;
	}

	private int primeiroItemRemove() {
		int valor = 0;
		if (indexChave > 0) {
			valor = chave[0];
			removeChave(valor);
		}
		return valor;
	}

	private int ultimoItemRemove() {
		int valor = 0;
		if (indexChave > 0) {
			valor = chave[indexChave - 1];
			removeChave(valor);
		}
		return valor;
	}
    
    
    
}
