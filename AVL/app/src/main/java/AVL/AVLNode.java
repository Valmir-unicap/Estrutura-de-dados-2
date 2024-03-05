package AVL;

/**
 *
 * @author aluno
 * @param <T>
 */
public class AVLNode<T extends Comparable <T>> {
    private AVLNode<T> esquerda;
    private AVLNode<T> direita;
    private T informacao;
    private int fatorBalenceamento;
    
    AVLNode(T informacao){
        this.informacao = informacao;
    }
    
    void setInformacao(T informacao){
        this.informacao = informacao;
    }
    
    T getInformacao(){
        return this.informacao;
    }
    
    void setEsquerda(AVLNode esquerda){
        this.esquerda= esquerda;
    }
    
    AVLNode getEsquerda(){
        return this.esquerda;
    }
    
    void setDireita(AVLNode direita){
        this.direita = direita;
    }
    
    AVLNode getDireita(){
        return this.direita;
    }
    
    void setFatorBalenceamento(int fatorBalenceamento){
        this.fatorBalenceamento = fatorBalenceamento;
    }
    
    int getFatorBalanceamento(){
        return this.fatorBalenceamento;
    }
    

}
