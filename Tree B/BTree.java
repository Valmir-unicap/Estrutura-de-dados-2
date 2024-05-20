public class BTree <T extends Comparable<T>> { 
  private NodeB<T> root;
  private final int ordem;
  private final int nChaves;
  private final int minimo;
  
  public BTree(int m) { 
    this.ordem = m; 
    this.nChaves = m - 1; 
    this.minimo = m / 2;
  }

  public boolean isEmpty() { 
    return root == null;
  }
  
  public void insert(T info) { 
    T infoRetorno;
    
    if (this.isEmpty() == true) {
      this.root = new NodeB(this.ordem); 
      this.root.preencherRaiz(info, null, null);
      
    } else {
      
      Retorno result = new Retorno(); 
      insertB(this.root, info, result);
      boolean h = result.getH();
      
      if (h) { // Aumetará a altura da árvore
        NodeB filhoDir = result.getFilhoDir();
        infoRetorno = (T)result.getInfo();
        NodeB novaRaiz = new NodeB(this.ordem); 
        novaRaiz.preencherRaiz(infoRetorno, this.root, filhoDir); 
        this.root = novaRaiz;
      }   
    }
  }

private void insertB(NodeB raiz, T info, Retorno result) { 
  int i, pos;
  T infoMediano; 
  //auxiliar para armazenar a chave que irá subir para o pai // NodeB filhoDir; //referência para o filho à direita da chave
  
  if (raiz == null) { //O nó anterior (que é um nó folha) é o ideal para inserir a nova chave 
    result.setH(true);
    result.setInfo(info);
    
  } else {
    
    pos = raiz.buscaBinaria(info);
    if (pos < raiz.getN() && raiz.getInfo(pos) == info) {
      System.out.println("Chave já contida na árvore");
      result.setH(false);
      
    } else { //desce na árvore até encontrar o nó folha para inserir a chave.
      
      this.insertB(raiz.getFilho(pos), info, result);
    
      if (result.getH()) { //Se true, deve inserir a info_retorno no nó.
      
      if (raiz.getN() < this.nChaves) { //Tem espaço na página 
        raiz.insereChave(result.getInfo(), result.getFilhoDir()); 
        result.setH(false);
      
      } else { //Overflow. Precisa subdividir a página

        NodeB temp = new NodeB(this.ordem); //elemento mediano que vai subir para o pai 
        infoMediano = (T)raiz.getInfo(this.minimo + 1); //insere metade do nó raiz no temp (efetua subdivisão) // temp.setFilho(0, raiz.getFilho(this.minimo + 1));
        
        for (i = this.minimo + 1; i < this.nChaves; i++) { 
          temp.insereChave(raiz.getInfo(i), raiz.getFilho(i + 1));
        }

        //atualiza nó raiz.
        for (i = this.minimo + 1; i < this.nChaves; i++) {
          raiz.setInfo(i, null);
          raiz.setFilho(i + 1, null); }
          raiz.setN(this.minimo);

          //Verifica em qual nó será inserida a nova chave 
          if (pos <= this.minimo) {
            raiz.insereChave(result.getInfo(), result.getFilhoDir());
          } else {
            temp.insereChave(result.getInfo(), result.getFilhoDir());
          }
        
//retorna o mediano para inserí-lo no nó pai e o temp como filho direito do mediano.
        result.setInfo(infoMediano);
        result.setFilhoDir(temp);
        }
      } 
    }
  } 
}
