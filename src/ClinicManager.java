public class ClinicManager {
    public static final String MIN_ID = "";
    public static final String MAX_ID = "\uFFFF\uFFFF\uFFFF\uFFFF";

    public TwoThreeTree<DoctorData, String> D;
    public TwoThreeTree<PatientData, String> P;
    public TwoThreeTree<LoadData, LoadData> L;

    public ClinicManager() {
        D = new TwoThreeTree<>(MAX_ID, MIN_ID);
        P = new TwoThreeTree<>(MAX_ID, MIN_ID);
        LoadData loadMax = new LoadData(MAX_ID, Integer.MAX_VALUE);
        LoadData loadMin = new LoadData(MIN_ID, Integer.MIN_VALUE);
        L = new TwoThreeTree<>(loadMax, loadMin);
    }

    public void doctorEnter(String doctorId) {
        if (D.Search(D.root, doctorId) == null) {
            Node<DoctorData, String> d = new Node<>(doctorId);
            d.value = new DoctorData();
            D.Insert(d);
            Node<LoadData, LoadData> dLoad = new Node<>(new LoadData(doctorId, 0));
            L.Insert(dLoad);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void doctorLeave(String doctorId) {
        Node<DoctorData, String> d = D.Search(D.root, doctorId);
        if (d == null) {
            throw new IllegalArgumentException();
        } else if (d.value.load != 0) {
            throw new IllegalArgumentException();
        } else {
            L.Delete(L.Search(L.root, new LoadData(doctorId, 0)));
            D.Delete(d);
        }
    }

    public void patientEnter(String doctorId, String patientId) {
        Node<DoctorData, String> d = D.Search(D.root, doctorId);
        Node<PatientData, String> p = P.Search(P.root, patientId);
        if (d == null || p != null) {
            throw new IllegalArgumentException();
        }

        p = new Node<>(patientId);
        p.value = new PatientData(doctorId);

        L.Delete(L.Search(L.root, new LoadData(doctorId, d.value.load)));

        d.value.addPatient(p);
        d.value.increaseLoad();

        L.Insert(new Node<>(new LoadData(doctorId, d.value.load)));

        P.Insert(p);
    }


    public String nextPatientLeave(String doctorId) {
        Node<DoctorData, String> d = D.Search(D.root, doctorId);
        if (d == null || d.value.load == 0) {
            throw new IllegalArgumentException();
        }
        Node<PatientData, String> p = d.value.removeHead();
        L.Delete(L.Search(L.root, new LoadData(doctorId, d.value.load)));
        d.value.decreaseLoad();
        L.Insert(new Node<>(new LoadData(doctorId, d.value.load)));
        P.Delete(p);
        return p.key;
    }

    public void patientLeaveEarly(String patientId) {
        Node<PatientData, String> p = P.Search(P.root, patientId);
        if (p == null) {
            throw new IllegalArgumentException();
        }
        Node<DoctorData, String> d = D.Search(D.root, p.value.assignedDoctor);
        if (p.value.prevPatient != null) {
            p.value.prevPatient.value.nextPatient = p.value.nextPatient;
        } else {
            if (d.value.head == p) {
                if (p.value.nextPatient != null) {
                    d.value.head = p.value.nextPatient;
                } else {
                    d.value.head = null;
                    d.value.tail = null;
                }
            }
        }
        if (p.value.nextPatient != null) {
            p.value.nextPatient.value.prevPatient = p.value.prevPatient;
        } else {
            if (d.value.tail == p) {
                if (p.value.prevPatient != null) {
                    d.value.tail = p.value.prevPatient;
                } else {
                    d.value.head = null;
                    d.value.tail = null;
                }
            }
        }
        p.value.prevPatient = null;
        p.value.nextPatient = null;
        L.Delete(L.Search(L.root, new LoadData(d.key, d.value.load)));
        d.value.decreaseLoad();
        L.Insert(new Node<>(new LoadData(d.key, d.value.load)));
        P.Delete(p);
    }

    public int numPatients(String doctorId) {
        Node<DoctorData, String> d = D.Search(D.root, doctorId);
        if (d == null) {
            throw new IllegalArgumentException();
        }
        return d.value.load;
    }

    public String nextPatient(String doctorId) {
        Node<DoctorData, String> d = D.Search(D.root, doctorId);
        if (d == null || d.value.load == 0) {
            throw new IllegalArgumentException();
        }
        Node<PatientData, String> p = d.value.getHead();
        return p.key;
    }

    public String waitingForDoctor(String patientId) {
        Node<PatientData, String> p = P.Search(P.root, patientId);
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return p.value.assignedDoctor;
    }

    public int numDoctorsWithLoadInRange(int low, int high) {
        if (high < low) return 0;
        LoadData highLoad = new LoadData(MAX_ID, high);
        LoadData lowLoad = new LoadData(MIN_ID, low);
        Node<LoadData, LoadData> highestOne = L.PredNodeSearch(L.root, highLoad);
        Node<LoadData, LoadData> lowestOne = L.PredNodeSearch(L.root, lowLoad);
        if (highestOne.key.load != high) {
            Node<LoadData, LoadData> tmp = highestOne.predecessor(highestOne);
            if (highestOne == lowestOne && highestOne.sentinel) {
                return 0;
            }
            if (tmp != null) {
                highestOne = tmp;
            } else {
                return 0;
            }
        }
        return L.Rank(highestOne) - L.Rank(lowestOne) + 1;
    }

    public int averageLoadWithinRange(int low, int high) {
        int num = numDoctorsWithLoadInRange(low, high);
        if (num == 0) {
            return 0;
        }
        return (L.SumOfSmallerRec(L.root, high) - L.SumOfSmallerRec(L.root, low - 1)) / num;
    }
}