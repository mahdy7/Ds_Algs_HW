public class Node<V, K extends Comparable<K>> {

    public Node<V, K> left;
    public Node<V, K> middle;
    public Node<V, K> right;
    public Node<V, K> parent;
    public int nodeCount;
    public int loadSum;
    public K key;
    public V value;

    Node(Node<V, K> left, Node<V, K> middle, Node<V, K> right, Node<V, K> parent, K key) {
        this.left = left;
        this.middle = middle;
        this.right = right;
        this.parent = parent;
        this.key = key;
        this.nodeCount = 1;
        this.loadSum = extractLoad(key);
    }

    Node(Node<V, K> left, Node<V, K> middle, Node<V, K> parent, K key) {
        this(left, middle, null, parent, key);
    }

    Node(K key) {
        this(null, null, null, key);
    }

    Node() {
        this(null);
    }

    private int extractLoad(K key) {
        if (key instanceof LoadData) {
            return ((LoadData) key).load;
        }
        return 0;
    }

    public boolean IsLeaf() {
        return this.left == null && this.middle == null && this.right == null;
    }

    public void UpdateKey() {
        this.key = this.left.key;
        if (this.middle != null) {
            this.key = this.middle.key;
        }
        if (this.right != null) {
            this.key = this.right.key;
        }
    }

    public void UpdateValues() {
        if (this.IsLeaf()) {
            return;
        }
        nodeCount = 0;
        loadSum = 0;
        if (this.left != null) {
            nodeCount += this.left.nodeCount;
            loadSum += this.left.loadSum;
        }
        if (this.middle != null) {
            nodeCount += this.middle.nodeCount;
            loadSum += this.middle.loadSum;
        }
        if (this.right != null) {
            nodeCount += this.right.nodeCount;
            loadSum += this.right.loadSum;
        }
    }

    public void SetChildren(Node<V, K> l, Node<V, K> m, Node<V, K> r) {
        this.left = l;
        this.middle = m;
        this.right = r;
        l.parent = this;
        if (m != null) {
            m.parent = this;
        }
        if (r != null) {
            r.parent = this;
        }
        this.UpdateKey();
        this.UpdateValues();
    }

    public Node<V, K> InsertAndSplit(Node<V, K> z) {
        Node<V, K> l = this.left;
        Node<V, K> m = this.middle;
        Node<V, K> r = this.right;

        if (r == null) {
            if (z.key.compareTo(l.key) < 0) {
                this.SetChildren(z, l, m);
            } else if (z.key.compareTo(m.key) < 0) {
                this.SetChildren(l, z, m);
            } else {
                this.SetChildren(l, m, z);
            }
            this.UpdateValues();
            return null;
        }

        Node<V, K> y = new Node<>();
        if (z.key.compareTo(l.key) < 0) {
            this.SetChildren(z, l, null);
            y.SetChildren(m, r, null);
        } else if (z.key.compareTo(m.key) < 0) {
            this.SetChildren(l, z, null);
            y.SetChildren(m, r, null);
        } else if (z.key.compareTo(r.key) < 0) {
            this.SetChildren(l, m, null);
            y.SetChildren(z, r, null);
        } else {
            this.SetChildren(l, m, null);
            y.SetChildren(r, z, null);
        }
        this.UpdateValues();
        y.UpdateValues();
        return y;
    }

    public Node<V, K> RightMostLeaf(Node<V, K> z) {
        if (z == null) {
            return null;
        }
        while (!(z.IsLeaf())) {
            if (z.right != null) {
                z = z.right;
            } else {
                z = z.middle;
            }
        }
        return z;
    }

    public Node<V, K> predecessor(Node<V, K> x) {
        Node<V, K> y = x;
        Node<V, K> z = y.parent;

        while (z != null) {
            if (z.middle == y) {
                return RightMostLeaf(z.left);
            }
            if (z.right == y) {
                return RightMostLeaf(z.middle);
            }
            y = z;
            z = z.parent;
        }
        return null;
    }
}

