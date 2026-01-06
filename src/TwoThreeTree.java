public class TwoThreeTree <V> {

    public static class Node <V> {
        public Node<V> left;
        public Node<V> middle;
        public Node<V> right;
        public Node<V> parent;
        public String key;
        public V value;

        Node (Node<V> left,Node<V> middle,Node<V> right, Node<V> parent,String key) {
            this.left = left;
            this.middle = middle;
            this.right = right;
            this.parent = parent;
            this.key = key;
        }

        Node (Node<V> left,Node<V> middle,Node<V> parent, String key) {
            this(left,middle,null,parent,key);
        }

        Node () {
            this(null,null,null,null);
        }

        public boolean IsLeaf() {
            return this.left == null && this.middle == null && this.right == null;
        }

        public void UpdateKey(Node<V> x) {
            x.key = x.left.key;
            if (x.middle != null) {
                x.key = x.middle.key;
            }
            if (x.right != null) {
                x.key = x.right.key;
            }
        }

        public void SetChildren(Node<V> x,Node<V> l,Node<V> m,Node<V> r) {
            x.left = l;
            x.middle = m;
            x.right = r;
            left.parent = x;
            if (x.middle != null) {middle.parent = x;}
            if (x.right != null) {right.parent = x;}
            UpdateKey(x);
        }

        public Node<V> InsertAndSplit(Node<V> x,Node<V> z) {
            Node<V> l = x.left;
            Node<V> m = x.middle;
            Node<V> r = x.right;

            if (r == null ) {
                if (z.key.compareTo(l.key) < 0) {
                    SetChildren(x,z,l,m);
                } else if (z.key.compareTo(m.key) < 0) {
                    SetChildren(x,l,z,m);
                } else {
                    SetChildren(x,l,m,z);
                }
                return null;
            }
            Node<V> y = new Node<>();
            if (z.key.compareTo(l.key) < 0) {
                SetChildren(x,z,l,null);
                SetChildren(y,m,r,null);
            } else if (z.key.compareTo(m.key) < 0) {
                SetChildren(x,l,z,null);
                SetChildren(y,m,r,null);
            } else if (z.key.compareTo(r.key) < 0) {
                SetChildren(x,l,m,null);
                SetChildren(y,z,r,null);
            } else {
                SetChildren(x,l,m,null);
                SetChildren(y,r,z,null);
            }
            return y;
        }

    }

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

    public void Insert(TwoThreeTree<V> T,Node<V> z) {
        Node<V> y = T.root;
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
        while (x != T.root) {
            x = x.parent;
            if (z != null) {
                z = z.InsertAndSplit(x,z);
            } else {
                x.UpdateKey(x);
            }
        }
        if (z != null) {
            Node<V> w = new Node<>();
            Set_Children(w,x,z,null);
            T.root = 
        }
    }


}
