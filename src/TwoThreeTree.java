public class TwoThreeTree<V, K extends Comparable<K>> {

    public Node<V, K> root;

    TwoThreeTree(K maximum, K minimum) {
        Node<V, K> x = new Node<>();
        Node<V, K> l = new Node<>();
        Node<V, K> m = new Node<>();
        l.key = minimum;
        l.sentinel = true;
        l.nodeCount = 0;
        l.loadSum = 0;
        m.key = maximum;
        m.sentinel = true;
        m.nodeCount = 0;
        m.loadSum = 0;
        l.parent = x;
        m.parent = x;
        x.key = maximum;
        x.left = l;
        x.middle = m;
        root = x;
    }

    public Node<V, K> Search(Node<V, K> x, K key) {
        if (x == null) {
            return null;
        }
        if (x.IsLeaf()) {
            if (x.key.equals(key)) {
                return x;
            } else {
                return null;
            }
        }
        if (key.compareTo(x.left.key) <= 0) {
            return Search(x.left, key);
        } else if (key.compareTo(x.middle.key) <= 0) {// there was a (x.right == null ||
            return Search(x.middle, key);
        } else {
            return Search(x.right, key);
        }
    }

    public Node<LoadData, LoadData> PredNodeSearch(Node<LoadData, LoadData> x, LoadData key) {
        if (x.IsLeaf()) {
            return x;
        }
        if (key.compareTo(x.left.key) <= 0) {
            return PredNodeSearch(x.left, key);
        } else if (x.right == null || key.compareTo(x.middle.key) <= 0) {
            return PredNodeSearch(x.middle, key);
        } else {
            return PredNodeSearch(x.right, key);
        }
    }

    public void Insert(Node<V, K> z) {
        Node<V, K> y = this.root;
        while (y != null && !(y.IsLeaf())) {
            if (z.key.compareTo(y.left.key) < 0) {
                y = y.left;
            } else if (z.key.compareTo(y.middle.key) < 0) {
                y = y.middle;
            } else {
                y = y.right;
            }
        }

        Node<V, K> x = y.parent;
        z = x.InsertAndSplit(z);
        while (x != this.root) {
            x = x.parent;
            if (z != null) {
                z = x.InsertAndSplit(z);
            } else {
                x.UpdateKey();
                x.UpdateValues();
            }
        }
        if (z != null) {
            Node<V, K> w = new Node<>();
            w.SetChildren(x, z, null);
            w.UpdateValues();
            this.root = w;
        }
    }

    public Node<V, K> BorrowOrMerge(Node<V, K> y) {
        Node<V, K> z = y.parent;
        if (y == z.left) {
            Node<V, K> x = z.middle;
            if (x.right != null) {
                y.SetChildren(y.left, x.left, null);
                x.SetChildren(x.middle, x.right, null);
            } else {
                x.SetChildren(y.left, x.left, x.middle);
                z.SetChildren(x, z.right, null);
            }
            y.UpdateValues();
            x.UpdateValues();
            z.UpdateValues();
            z.UpdateKey();

            if (z.parent != null) z.parent.UpdateKey();
            return z;
        }
        if (y == z.middle) {
            Node<V, K> x = z.left;
            if (x.right != null) {
                y.SetChildren(x.right, y.left, null);
                x.SetChildren(x.left, x.middle, null);
            } else {
                x.SetChildren(x.left, x.middle, y.left);
                z.SetChildren(x, z.right, null);
            }
            y.UpdateValues();
            x.UpdateValues();
            z.UpdateValues();
            return z;
        }
        Node<V, K> x = z.middle;
        if (x.right != null) {
            y.SetChildren(x.right, y.left, null);
            x.SetChildren(x.left, x.middle, null);
        } else {
            x.SetChildren(x.left, x.middle, y.left);
            z.SetChildren(z.left, x, null);
        }
        y.UpdateValues();
        x.UpdateValues();
        z.UpdateValues();
        return z;
    }

    public void Delete(Node<V, K> x) {
        Node<V, K> y = x.parent;
        if (x == y.left) {
            y.SetChildren(y.middle, y.right, null);
        } else if (x == y.middle) {
            y.SetChildren(y.left, y.right, null);
        } else {
            y.SetChildren(y.left, y.middle, null);
        }
        y.UpdateValues();

        while (y != null) {
            if (y.middle != null) {
                y.UpdateValues();
                y.UpdateKey();
                y = y.parent;
            } else {
                if (y != this.root) {
                    y = BorrowOrMerge(y);
                } else {
                    this.root = y.left;
                    this.root.UpdateValues();
                    this.root.parent = null;
                    return;
                }
            }
        }
    }

    public int Rank(Node<LoadData, LoadData> x) {
        int rank = 1;
        Node<LoadData, LoadData> y = x.parent;
        while (y != null) {
            if (x == y.middle) {
                rank = rank + y.left.nodeCount;
            } else if (x == y.right) {
                rank = rank + y.left.nodeCount + y.middle.nodeCount;
            }
            x = y;
            y = y.parent;
        }
        return rank;
    }

    public int SumOfSmallerRec(Node<LoadData,LoadData> x, int k) {
        LoadData bigLoad = new LoadData(ClinicManager.MAX_ID,k);
        if (x.IsLeaf()) {
            if (x.key.compareTo(bigLoad) <= 0) {
                return x.loadSum;
            } else {
                return 0;
            }
        }
        if (bigLoad.compareTo(x.left.key) <= 0) {
            return SumOfSmallerRec(x.left, k);
        } else if (bigLoad.compareTo(x.middle.key) <= 0) {
            return x.left.loadSum + SumOfSmallerRec(x.middle, k);
        } else {
            return x.left.loadSum + x.middle.loadSum + SumOfSmallerRec(x.right, k);
        }
    }

}
