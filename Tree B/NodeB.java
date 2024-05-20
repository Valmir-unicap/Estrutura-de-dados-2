class NodeB<T extends Comparable<T>> { private int n;
  private Object[] infos;
  private NodeB[] filhos;
                                      
  NodeB (int m) {
    infos = new Object[m]; 
    filhos = new NodeB[m];
  }
                                      
  void setN(int n) { 
    this.n = n;
  }
                                      
  int getN() { 
    return this.n;
  }
                                      
  T getInfo(int i) {
  return (T)this.infos[i];
  }
                                      
  NodeB getFilho(int i) { 
    return this.filhos[i];
  }
                                      
  void setInfo(int i, Object value) { 
    this.infos[i] = value;
  }
                                      
  void setFilho(int i, NodeB f) { 
    this.filhos[i] = f;
  }
                                      
  void preencherRaiz(Object value, NodeB r, NodeB filhoDir) { 
    this.infos[0] = value;
    this.filhos[0] = r;
    this.filhos[1] = filhoDir;
    this.n = 1; 
  }

//Insere uma chave e o ponteiro para o filho da direita em um nó 
  void insereChave(T value, NodeB filhoDir) {
    int pos;
    int k = this.n;
    
//busca para obter a posição ideal para inserir a nova chave 
    pos = buscaBinaria(value);
    
//realiza o remanejamento para manter as chaves ordenadas 
    while (k > pos && value.compareTo((T)this.infos[k-1]) < 0) {
      this.filhos[k + 1] = this.filhos[k]; 
      this.infos[k] = this.infos[k-1]; 
      k--;
    }
    
//insere a chave na posição ideal 
    this.infos[pos] = value; 
    this.filhos[pos + 1] = filhoDir; 
    this.n++;
  }
                                      
  int buscaBinaria(T value) { 
    int meio, i, f, compara;
    i = 0;
    f = this.n - 1;
  
    while (i <= f) {
      meio = (i + f) / 2;
      compara = value.compareTo((T)this.infos[meio]); 
      
      if (compara == 0) {
        return meio; 
        
        // Encontrou. Retorna a posição em que a chave está. 
      } else if (compara < 0) {
        f = meio - 1; 
      
      } else {
        i = meio + 1; 
      }
  }

  return i; //Não encontrou. Retorna a posição do ponteiro para o filho. 
}
                                      
public void percorrerEmOrdem() { 
  int i;
  for (i = 0; i < this.n; i++) {
    
    if (this.filhos[i] != null) {
      this.filhos[i].percorrerEmOrdem(); 
    }
    
    System.out.println(this.infos[i]); 
  }
  
  if (this.filhos[i] != null) { 
      this.filhos[i].percorrerEmOrdem();
    }
  }
}
