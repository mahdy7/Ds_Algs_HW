public class Node <V> {

    public Node<V> left;
    public Node<V> middle;
    public Node<V> right;
    public Node<V> parent;
    public String key;
    public V value;

    Node (Node<V> left, Node<V> middle, Node<V> right, Node<V> parent, String key) {
        this.left = left;
        this.middle = middle;
        this.right = right;
        this.parent = parent;
        this.key = key;
    }

    Node (Node<V> left, Node<V> middle, Node<V> parent, String key) {
        this(left,middle,null,parent,key);
    }

    Node (String key) {
        this(null,null,null,key);
    }

    Node () {
        this(null);
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

    public void SetChildren( Node<V> l, Node<V> m, Node<V> r) {
        this.left = l;
        this.middle = m;
        this.right = r;
        left.parent = this;
        if (this.middle != null) {middle.parent = this;}
        if (this.right != null) {right.parent = this;}
        this.UpdateKey();
    }

    public Node<V> InsertAndSplit(Node<V> x, Node<V> z) {
        Node<V> l = x.left;
        Node<V> m = x.middle;
        Node<V> r = x.right;

        if (r == null ) {
            if (z.key.compareTo(l.key) < 0) {
                x.SetChildren(z,l,m);
            } else if (z.key.compareTo(m.key) < 0) {
                x.SetChildren(l,z,m);
            } else {
                x.SetChildren(l,m,z);
            }
            return null;
        }
        Node<V> y = new Node<>();
        if (z.key.compareTo(l.key) < 0) {
            x.SetChildren(z,l,null);
            y.SetChildren(m,r,null);
        } else if (z.key.compareTo(m.key) < 0) {
            x.SetChildren(l,z,null);
            y.SetChildren(m,r,null);
        } else if (z.key.compareTo(r.key) < 0) {
            x.SetChildren(l,m,null);
            y.SetChildren(z,r,null);
        } else {
            x.SetChildren(l,m,null);
            y.SetChildren(r,z,null);
        }
        return y;
    }
}

