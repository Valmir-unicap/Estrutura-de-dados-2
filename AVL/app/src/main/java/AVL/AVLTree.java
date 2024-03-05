package AVL;
/**
 *
 * @author aluno
 */
public class AVLTree <T extends Comparable <T>> {
    private AVLNode<T> raiz;
    private boolean statusDeBalanceamento;
    
    private boolean isEmpty(){
        if(raiz == null){
            return true;
        }else{
            return false;
        }
    }
    
    public void inserirAVL(T valor){
        if(this.isEmpty() == true){
            this.raiz = new AVLNode(valor);
            this.statusDeBalanceamento = true;
        }else{
            this.raiz = inserirNode(raiz,valor);
        }
    }
    
     private AVLNode<T> inserirNode(AVLNode<T> r, T valor){
        if(r == null){
            r = new AVLNode(valor);
            this.statusDeBalanceamento = true;
            
        }else if(r.getInformacao().compareTo(valor) == 0){
            return r;
            
        }else if(r.getInformacao().compareTo(valor)>0){
            r.setEsquerda(inserirNode(r.getEsquerda(),valor));
            
            if (this.statusDeBalanceamento == true){
                
                switch(r.getFatorBalanceamento()){
                    
                    case 1: 
                        r.setFatorBalenceamento(0);
                        this.statusDeBalanceamento = false;
                        break;
                    
                    case 0:
                        r.setFatorBalenceamento(-1);
                        break;
                        
                    case -1:
                        r = this.rotacaoDireita(r);
                        break;
                }
            }
        }else{
            r.setDireita(inserirNode(r.getDireita(),valor));
            
            if(this.statusDeBalanceamento == true){
                
                switch (r.getFatorBalanceamento()){
                    
                    case -1:
                        r.setFatorBalenceamento(0);
                        this.statusDeBalanceamento = false;
                        break;
                        
                    case 0:
                        r.setFatorBalenceamento(1);
                        break;
                        
                    case 1:
                        r = this.rotacaoEsquerda(r);
                        break;
                }
            }
        }
        return r;
    }
     
     private AVLNode<T> rotacaoEsquerda(AVLNode<T> a){
         AVLNode<T> b, c;
         b= a.getDireita();
         
         //Rotação Simples
         
         if(b.getFatorBalanceamento() == 1){
             a.setDireita(b.getEsquerda());
             b.setEsquerda(a);
             a.setFatorBalenceamento(0);
             a = b;
             
             //Rotação dupla
             
         }else{
             c = b.getEsquerda();
             b.setEsquerda(c.getDireita());
             c.setDireita(b);
             a.setDireita(c.getEsquerda());
             c.setEsquerda(a);
             
             if (c.getFatorBalanceamento() == 1){
                 a.setFatorBalenceamento(-1);
                 
             }else{
                 a.setFatorBalenceamento(0);
         }
             if(c.getFatorBalanceamento() == -1){
                 b.setFatorBalenceamento(1);
             }else{
                 b.setFatorBalenceamento(0);
             }
             a=c;
         }
         a.setFatorBalenceamento(0);
         this.statusDeBalanceamento = false;
         return a;
     }
     
     private AVLNode<T> rotacaoDireita(AVLNode<T> a){
         AVLNode<T> b,c;
         //Rotação Simples
         
         b = a.getEsquerda();
         
         if(b.getFatorBalanceamento() == -1){
             a.setEsquerda(b.getDireita());
             b.setDireita(a);
             a.setFatorBalenceamento(0);
             a = b;
         }
         
         // Rotação dupla
     }
}
