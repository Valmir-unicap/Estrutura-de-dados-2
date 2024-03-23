package br.c3.icam.unicap;

import java.util.LinkedList;

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

    public void inOrdem(){
        if(this.isEmpty()==true){
            System.out.println("Árvore vázia!");
        }else{
            percorrerInOrdem(raiz);
        }
    }

    private void percorrerInOrdem(AVLNode<T> r){
        if(r!=null){
            percorrerInOrdem(r.getEsquerda());
            System.out.println(r.getInformacao());
            percorrerInOrdem(r.getDireita());
        }
    }

    public void inNivel(){
        if(this.isEmpty() == true){
            System.out.println("Árvore Vázia!");
        }else{
            passeioPorNivel();
        }
    }

    public void inserirAVL(T valor){
        if(this.isEmpty() == true){
            this.raiz = new AVLNode(valor);
            this.statusDeBalanceamento = true;
            System.out.println("AVL vázia! Inserção efetuada!");
        }else{
            this.raiz = inserirNode(raiz,valor);
            System.out.println("Inserção efetuada!");
        }
    }

    public void removerAVL(T valor){
        if(this.isEmpty() == true){
            System.out.println("AVL vázia! Remoção não foi efetuada!");
        }else{
            this.raiz = removerNode(raiz,valor);
            System.out.println("Remoção efetuada!");
        }
    }

    private AVLNode<T> removerNode(AVLNode<T> atual, T valor) {
        if (atual == null) {
            this.statusDeBalanceamento = false;
            return null;
        }

        int comparacao = valor.compareTo(atual.getInformacao());

        if (comparacao < 0) {
            atual.setEsquerda(removerNode(atual.getEsquerda(), valor));

            if (this.statusDeBalanceamento) {
                atual = balancearNaRemocao(atual, -1);
            }

        } else if (comparacao > 0) {
            atual.setDireita(removerNode(atual.getDireita(), valor));

            if (this.statusDeBalanceamento) {
                atual = balancearNaRemocao(atual, 1);
            }

        } else {
            // Nó com o valor encontrado
            if (atual.getEsquerda() == null || atual.getDireita() == null) {
                // Nó com um filho ou nenhum
                atual = (atual.getEsquerda() != null) ? atual.getEsquerda() : atual.getDireita();
                this.statusDeBalanceamento = true;

            } else {
                // Nó com dois filhos
                AVLNode<T> substituto = encontrarMenor(atual.getDireita());
                atual.setInformacao(substituto.getInformacao());
                atual.setDireita(removerNode(atual.getDireita(), substituto.getInformacao()));

                if (this.statusDeBalanceamento) {
                    atual = balancearNaRemocao(atual, 1);
                }
            }
        }
        return atual;
    }

    private AVLNode<T> balancearNaRemocao(AVLNode<T> atual, int balancear) {
        atual.setFatorBalenceamento(atual.getFatorBalanceamento() + balancear);

        if (atual.getFatorBalanceamento() == -2) {

            if (altura(atual.getEsquerda().getDireita()) <= altura(atual.getEsquerda().getEsquerda())) {
                return rotacaoEsquerda(atual);

            } else {
                return rotacaoEsquerda(atual);
            }

        } else if (atual.getFatorBalanceamento() == 2) {

            if (altura(atual.getDireita().getEsquerda()) <= altura(atual.getDireita().getDireita())) {
                return rotacaoDireita(atual);

            } else {
                return rotacaoDireita(atual);
            }
        }
        return atual;
    }

    private AVLNode<T> encontrarMenor(AVLNode<T> atual) {
        while (atual.getEsquerda() != null) {
            atual = atual.getEsquerda();
        }
        return atual;
    }

    private AVLNode<T> encontrarMaior(AVLNode<T> atual) {
        while (atual.getDireita() != null) {
            atual = atual.getDireita();
        }
        return atual;
    }

    public int altura(AVLNode<T> atual) {
        if (atual == null) {
            // Se o nó atual é nulo, a altura é -1
            return -1;
        } else {
            int alturaEsquerda = altura(atual.getEsquerda());
            int alturaDireita = altura(atual.getDireita());

            // A altura do nó atual será o maior valor entre as alturas das subárvores + 1
            return Math.max(alturaEsquerda, alturaDireita) + 1;
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

        //Rotacao Simples

        if(b.getFatorBalanceamento() == 1){
            a.setDireita(b.getEsquerda());
            b.setEsquerda(a);
            a.setFatorBalenceamento(0);
            a = b;

            //Rotacao Dupla

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
        //Rotacao Simples

        b = a.getEsquerda();

        if(b.getFatorBalanceamento() == -1){
            a.setEsquerda(b.getDireita());
            b.setDireita(a);
            a.setFatorBalenceamento(0);
            a = b;

            // Rotacao Dupla

        }else{
            c = b.getDireita();
            b.setDireita(c.getEsquerda());
            c.setEsquerda(b);
            a.setEsquerda(c.getDireita());
            c.setDireita(a);

            if(c.getFatorBalanceamento() == -1){
                a.setFatorBalenceamento(1);
            }else{
                a.setFatorBalenceamento(0);
            }

            if (c.getFatorBalanceamento() == 1){
                b.setFatorBalenceamento(-1);
            }else{
                b.setFatorBalenceamento(0);
            }

            a = c;
        }
        a.setFatorBalenceamento(0);
        this.statusDeBalanceamento = false;
        return a;
    }

    public void passeioPorNivel() {
        LinkedList<AVLNode> fila;
        AVLNode aux;

        if (isEmpty() == false) {
            fila = new LinkedList<>();
            fila.add(raiz);

            while (fila.isEmpty()==false) {
                aux = fila.removeFirst();

                if (aux.getEsquerda() != null) {
                    fila.add(aux.getEsquerda());
                }

                if (aux.getDireita() != null) {
                    fila.add(aux.getDireita());
                }

                System.out.println(aux.getInformacao());
            }
        }
    }
}
