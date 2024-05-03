package b.src;

import java.util.ArrayList;
import java.util.List;

public class BTreeNode {
    boolean isLeaf;
    List<Integer> keys; // As chaves contidas neste nó
    List<BTreeNode> children; // Filhos do nó

    BTreeNode(boolean isLeaf) {
        this.isLeaf = isLeaf;
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
    }

}
