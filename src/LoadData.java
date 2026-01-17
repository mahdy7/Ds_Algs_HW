public class LoadData implements Comparable<LoadData> {
    int load;
    String doctorId;

    LoadData(String doctorId, int load) {
        this.load = load;
        this.doctorId = doctorId;
    }

    @Override
    public int compareTo(LoadData o) {
        int cmp = this.load - o.load;
        if (cmp == 0) {
            return this.doctorId.compareTo(o.doctorId);
        }
        return cmp;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof LoadData other) {
            return this.doctorId.equals(other.doctorId) && this.load == other.load;
        }
        return false;
    }
}
