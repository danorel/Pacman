package src.main.java.bfs;

public class BFS {
    private Node root;

    BFS() {
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

    public Node insert(Node node, int v) {
        if (node == null)
            return new Node(v);

        if (v < node.v)
            node.l = insert(node.l, v);
        else if (v > node.v)
            node.r = insert(node.r, v);

        return node;
    }

    public void levelOrderTraversal(Node node, int level) {
        if (node == null)
            return;

        if (level == 0)
            System.out.print(node.v + " ");
        else {
            levelOrderTraversal(node.l, level - 1);
            levelOrderTraversal(node.r, level - 1);
        }
    }
}
