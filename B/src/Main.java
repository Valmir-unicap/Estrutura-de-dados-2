package b.src;

public class Main {

    public static void main(String[] args) {
        BTree btree = new BTree(4); // Ordem 4

        // Insira alguns valores
        btree.insert(10);
        btree.insert(20);
        btree.insert(5);

        System.out.println("Insercao efetuada");

        // Exibir a árvore por nível
        System.out.println("Por nível:");
        btree.levelOrderTraversal();

        System.out.println("\nPré-ordem:");
        btree.preOrderTraversal(btree.getRoot());
        
        // Remover uma chave
        System.out.println("\n\nRemovendo a chave 6:");
        btree.remove(5);
        
        // Exibir novamente após a remoção
        System.out.println("Traversal por nível após remoção:");
        btree.levelOrderTraversal();
    }
}
