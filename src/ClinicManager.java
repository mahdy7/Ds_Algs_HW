public class ClinicManager {
    public static final String MIN_ID = "";
    public static final String MAX_ID = "\uFFFF\uFFFF\uFFFF\uFFFF";


    public TwoThreeTree<DoctorData> D;
    public TwoThreeTree<PatientData> P;

    public ClinicManager() {
        D = new TwoThreeTree<>();
        P = new TwoThreeTree<>();
    }

    public void doctorEnter(String doctorId) {
        if (D.Search(D.root,doctorId) == null) {
            Node<DoctorData> d = new Node<DoctorData>(doctorId);
            d.value = new DoctorData();
            D.Insert(d);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void doctorLeave(String doctorId) {
        Node<DoctorData> d = D.Search(D.root,doctorId);
        if (d == null) {
            throw new IllegalArgumentException();
        } else if (d.value.load != 0) {
            throw new IllegalArgumentException();
        } else {
            D.Delete(d);
        }
    }

    public void patientEnter(String doctorId, String patientId) {
        Node<DoctorData> d = D.Search(D.root,doctorId);
        Node<PatientData> p = P.Search(P.root,patientId);
        if (d == null || p != null) {
            throw new IllegalArgumentException();
        }

        p = new Node<PatientData>(patientId);
        d.value.increaseLoad();
        d.value.addPatient(p);
        p.value = new PatientData(doctorId);
        P.Insert(p);
    }

    public String nextPatientLeave(String doctorId) {
        Node<DoctorData> d = D.Search(D.root,doctorId);
        if (d == null || d.value.load == 0) {
            throw new IllegalArgumentException();
        }
        Node<PatientData> p = d.value.removeHead();
        P.Delete(p);
        return p.key;
    }

    public void patientLeaveEarly(String patientId) {
        Node<PatientData> p = P.Search(P.root,patientId);
        if (p == null) {
            throw new IllegalArgumentException();
        }
        Node<DoctorData> d = D.Search(D.root,p.value.assignedDoctor);
        d.value.decreaseLoad();
        p.value.prevPatient.value.nextPatient = p.value.nextPatient;
        p.value.nextPatient.value.prevPatient = p.value.prevPatient;
        P.Delete(p);
    }

    public int numPatients(String doctorId) {
        Node<DoctorData> d = D.Search(D.root,doctorId);
        if (d == null) {
            throw new IllegalArgumentException();
        }
        return d.value.load;
    }

    public String nextPatient(String doctorId) {
        Node<DoctorData> d = D.Search(D.root,doctorId);
        if (d == null || d.value.load == 0) {
            throw new IllegalArgumentException();
        }
        Node<PatientData> p = d.value.getHead();
        return p.key;
    }

    public String waitingForDoctor(String patientId) {
        Node<PatientData> p = P.Search(P.root,patientId);
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return p.value.assignedDoctor;
    }

    public int numDoctorsWithLoadInRange(int low, int high) {
        return 0;
    }

    public int averageLoadWithinRange(int low, int high) {
        return 0;
    }
}