package b.src;

import java.util.ArrayList;
import java.util.List;

public class BTree {

    private BTreeNode root; // Raiz da árvore
    private final int order; // Ordem da árvore B

    public BTree(int order) {
        if (order < 4) {
            throw new IllegalArgumentException("A ordem da árvore B deve ser pelo menos 4.");
        }
        this.order = order; // A ordem da árvore (mínimo 4)
        this.root = new BTreeNode(true); // Raiz é uma folha inicialmente
    }

public void insert(int key) {
    BTreeNode root = this.root;
    if (root.keys.size() == order - 1) {
        // Se a raiz está cheia, precisa ser dividida
        BTreeNode newRoot = new BTreeNode(false);
        newRoot.children.add(root); // A nova raiz aponta para a raiz antiga como filho
        this.root = newRoot;

        // Divide o filho cheio
        splitChild(newRoot, 0);

        // Insira a chave no novo nó raiz
        insertNonFull(newRoot, key);
    } else {
        insertNonFull(root, key);
    }
}

    private void insertNonFull(BTreeNode x, int key) {
        if (x.isLeaf) {
            int i = x.keys.size() - 1;
            while (i >= 0 && x.keys.get(i) > key) {
                i--;
            }
            x.keys.add(i + 1, key); // Inserir na posição correta
        } else {
            int i = x.keys.size() - 1;
            while (i >= 0 && x.keys.get(i) > key) {
                i--;
            }
            int childIndex = i + 1;
            BTreeNode child = x.children.get(childIndex);
            if (child.keys.size() == order - 1) { // Se o filho está cheio, dividir
                splitChild(x, childIndex);
                if (key > x.keys.get(childIndex)) {
                    childIndex++;
                }
            }
            insertNonFull(x.children.get(childIndex), key);
        }
    }

    private void splitChild(BTreeNode parent, int childIndex) {
        // Obter o filho a ser dividido
        BTreeNode child = parent.children.get(childIndex);
    
        // Criar um novo nó para armazenar parte das chaves e filhos
        BTreeNode newChild = new BTreeNode(child.isLeaf);
    
        // Índice do meio para dividir
        int midIndex = (order - 1) / 2;
    
        // Chaves e filhos do filho original que serão movidas para o novo nó
        newChild.keys.addAll(child.keys.subList(midIndex + 1, child.keys.size()));
        child.keys.subList(midIndex, child.keys.size()).clear();
    
        if (!child.isLeaf) {
            newChild.children.addAll(child.children.subList(midIndex + 1, child.children.size()));
            child.children.subList(midIndex + 1, child.children.size()).clear();
        }
    
        // A chave do meio sobe para o pai
        parent.keys.add(childIndex, child.keys.remove(midIndex));
    
        // Adiciona o novo filho ao pai
        parent.children.add(childIndex + 1, newChild);
    }
    

    public void remove(int key) {
        // Função para remover um valor da árvore B
        if (root.keys.isEmpty()) {
            throw new IllegalStateException("Árvore vazia.");
        }

        removeInternal(root, key);

        if (root.keys.isEmpty() && !root.isLeaf) {
            root = root.children.get(0); // Ajustar a raiz se estiver vazia
        }
    }

    private void removeInternal(BTreeNode node, int key) {
        int i = 0;
        while (i < node.keys.size() && node.keys.get(i) < key) {
            i++;
        }

        if (i < node.keys.size() && node.keys.get(i) == key) {
            if (node.isLeaf) {
                node.keys.remove(i); // Se for uma folha, apenas remover
            } else {
                // Remover de um nó interno
                BTreeNode leftChild = node.children.get(i);
                BTreeNode rightChild = node.children.get(i + 1);

                if (leftChild.keys.size() > (order - 1) / 2) {
                    // Encontra o predecessor e o move para a chave removida
                    int predecessor = getPredecessor(leftChild);
                    node.keys.set(i, predecessor);
                    removeInternal(leftChild, predecessor);
                } else if (rightChild.keys.size() > (order - 1) / 2) {
                    // Encontra o sucessor e o move para a chave removida
                    int successor = getSuccessor(rightChild);
                    node.keys.set(i, successor);
                    removeInternal(rightChild, successor);
                } else {
                    // Mesclar os filhos e remover a chave
                    mergeChildren(node, i);
                    removeInternal(leftChild, key);
                }
            }
        } else {
            if (node.isLeaf) {
                throw new IllegalArgumentException("Chave não encontrada na árvore B.");
            }

            boolean lastChild = (i == node.keys.size());
            BTreeNode child = node.children.get(i);

            if (child.keys.size() == (order - 1) / 2) {
                // Reequilibrar se o filho tiver poucas chaves
                if (i > 0 && node.children.get(i - 1).keys.size() > (order - 1) / 2) {
                    // Mover uma chave do irmão esquerdo
                    BTreeNode leftSibling = node.children.get(i - 1);
                    child.keys.add(0, node.keys.get(i - 1));
                    node.keys.set(i - 1, leftSibling.keys.remove(leftSibling.keys.size() - 1));
                    if (!child.isLeaf) {
                        child.children.add(0, leftSibling.children.remove(leftSibling.children.size() - 1));
                    }
                } else if (i < node.children.size() - 1 && node.children.get(i + 1).keys.size() > (order - 1) / 2) {
                    // Mover uma chave do irmão direito
                    BTreeNode rightSibling = node.children.get(i + 1);
                    child.keys.add(node.keys.get(i));
                    node.keys.set(i, rightSibling.keys.remove(0));
                    if (!child.isLeaf) {
                        child.children.add(rightSibling.children.remove(0));
                    }
                } else {
                    if (i > 0) {
                        mergeChildren(node, i - 1);
                    } else {
                        mergeChildren(node, i);
                    }
                }
            }

            if (lastChild) {
                removeInternal(node.children.get(i - 1), key);
            } else {
                removeInternal(node.children.get(i), key);
            }
        }
    }

    private void mergeChildren(BTreeNode node, int i) {
        BTreeNode leftChild = node.children.get(i);
        BTreeNode rightChild = node.children.get(i + 1);
        int key = node.keys.remove(i);

        leftChild.keys.add(key); // Chave intermediária
        leftChild.keys.addAll(rightChild.keys); // Adiciona as chaves do irmão direito
        if (!leftChild.isLeaf) {
            leftChild.children.addAll(rightChild.children); // Adiciona os filhos do irmão direito
        }

        node.children.remove(i + 1); // Remove o irmão direito do pai
    }

    private int getPredecessor(BTreeNode node) {
        while (!node.isLeaf) {
            node = node.children.get(node.children.size() - 1);
        }
        return node.keys.get(node.keys.size() - 1);
    }

    private int getSuccessor(BTreeNode node) {
        while (!node.isLeaf) {
            node = node.children.get(0);
        }
        return node.keys.get(0);
    }

    public int height() {
        int height = 0;
        BTreeNode node = root;
        while (!node.isLeaf) {
            node = node.children.get(0);
            height++;
        }
        return height;
    }

    public void levelOrderTraversal() {
        List<BTreeNode> currentLevel = new ArrayList<>();
        List<BTreeNode> nextLevel = new ArrayList<>();

        currentLevel.add(root);

        while (!currentLevel.isEmpty()) {
            nextLevel.clear();

            for (BTreeNode node : currentLevel) {
                System.out.print(node.keys + " ");

                if (!node.isLeaf) {
                    nextLevel.addAll(node.children);
                }
            }

            System.out.println(); // Nova linha para cada nível
            currentLevel = new ArrayList<>(nextLevel);
        }
    }

    public void preOrderTraversal(BTreeNode node) {
        if (node == null) {
            return;
        }

        System.out.print(node.keys + " ");

        if (!node.isLeaf) {
            for (BTreeNode child : node.children) {
                preOrderTraversal(child);
            }
        }
    }

    public BTreeNode getRoot() {
        return root;
    }
}
