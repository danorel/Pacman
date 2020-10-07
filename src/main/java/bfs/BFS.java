package src.main.java.bfs;

public class BFS {
    public Node root;

    public BFS() {
        root = null;
    }

    // Class representing tree nodes
    static class Node {
        int v;
        Node l, r;

        Node(int v) {
            this.v = v;
            l = null;
            r = null;
        }
    }

    public void insert(int v) {
        root = insert(root, v);
    }

    private Node insert(Node node, int v) {
        if (node == null)
            return new Node(v);

        if (v < node.v)
            node.l = insert(node.l, v);
        else if (v > node.v)
            node.r = insert(node.r, v);

        return node;
    }

    public void levelOrder() {
        int height = calculateTreeHeight(root);

        for(int i = 0; i < height; i++){
            levelOrderTraversal(root, i);
        }
    }

    private void levelOrderTraversal(Node node, int level) {
        if (node == null)
            return;

        if (level == 0)
            System.out.print(node.v + " ");
        else {
            levelOrderTraversal(node.l, level - 1);
            levelOrderTraversal(node.r, level - 1);
        }
    }

    private int calculateTreeHeight(Node root){
        if (root == null)
            return 0;
        else {
            int lsh = calculateTreeHeight(root.l);
            int rsh = calculateTreeHeight(root.r);

            return Math.max(lsh, rsh) + 1;
        }
    }
}
