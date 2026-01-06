public class TwoThreeTree <V> {

    public Node<V> root;

    TwoThreeTree () {
        Node<V> x = new Node<>();
        Node<V> l = new Node<>();
        Node<V> m = new Node<>();
        l.key = ClinicManager.MIN_ID;
        m.key = ClinicManager.MAX_ID;
        l.parent = x;
        m.parent = x;
        x.key = ClinicManager.MAX_ID;
        x.left = l;
        x.middle = m;
        root = x;
    }

    public Node<V> Search (Node<V> x, String key) {
        if (x.IsLeaf()) {
            if (x.key.equals(key)) {
                return x;
            } else {
                return null;
            }
        }
        if (key.compareTo(x.left.key) < 0) {
            return Search (x.left, key);
        } else if (key.compareTo(x.middle.key) < 0) {
            return Search(x.middle,key);
        } else {
            return Search(x.right, key);
        }
    }

    public void Insert(Node<V> z) {
        Node<V> y = this.root;
        while (!(y.IsLeaf())) {
            if (z.key.compareTo(y.left.key) < 0) {
                y = y.left;
            } else if (z.key.compareTo(y.middle.key) < 0) {
                y = y.middle;
            } else {
                y = y.right;
            }
        }
        Node<V> x = y.parent;
        z = z.InsertAndSplit(x,z);
        while (x != this.root) {
            x = x.parent;
            if (z != null) {
                z = z.InsertAndSplit(x,z);
            } else {
                x.UpdateKey();
            }
        }
        if (z != null) {
            Node<V> w = new Node<>();
            w.SetChildren(x,z,null);
            this.root = w;
        }
    }

    public Node<V> BorrowOrMerge(Node<V> y) {
        Node<V> z = y.parent;
        if (y == z.left) {
            Node<V> x = z.middle;
            if (x.right != null) {
                y.SetChildren(y.left,x.left,null);
                x.SetChildren(x.middle,x.right,null);
            } else {
                x.SetChildren(y.left,x.left,null);
                z.SetChildren(x,z.right,null);
            }
            return z;
        }
        if (y == z.middle) {
            Node<V> x = z.left;
            if (x.right != null) {
                y.SetChildren(x.right,y.left, null);
                x.SetChildren(x.left,x.middle,null);
            } else {
                x.SetChildren(x.left,x.middle,y.left);
                z.SetChildren(x,z.right,null);
            }
            return z;
        }
        Node<V> x = z.middle;
        if (x.right != null) {
            y.SetChildren(x.right,y.left, null);
            x.SetChildren(x.left,x.middle,null);
        } else {
            x.SetChildren(x.left,x.middle,y.left);
            z.SetChildren(z.left,x,null);
        }
        return z;
    }

    public void Delete(Node<V> x) {
        Node<V> y = x.parent;
        if (x == y.left) {
            y.SetChildren(y.middle,y.right,null);
        } else if (x == y.middle) {
            y.SetChildren(y.left,y.right,null);
        } else {
            y.SetChildren(y.left,y.middle,null);
        }
        while (y != null) {
            if (y.middle != null) {
                y.UpdateKey();
                y = y.parent;
            } else {
                if (y != this.root) {
                    y = BorrowOrMerge(y);
                } else {
                    this.root = y.left;
                    y.left.parent = null;
                    return;
                }
            }
        }
    }

}
