public class DoctorData {

    int load;
    Node<PatientData,String> head;
    Node<PatientData,String> tail;


    DoctorData() {
        this.load = 0;
        this.head = null;
        this.tail = null;
    }

    public void increaseLoad() {
        load++;
    }

    public void addPatient(Node<PatientData,String> patient) {
        if (this.head == null) {
            this.head = patient;
            this.tail = patient;
        } else {
            this.tail.value.nextPatient = patient;
            patient.value.prevPatient = this.tail;
            this.tail = patient;
        }
    }

    public void decreaseLoad() {
        load--;
    }

    public Node<PatientData,String> getHead() {
        return head;
    }

    public Node<PatientData,String> removeHead() {
        Node<PatientData,String> oldHead = this.head;
        if (this.head.value.nextPatient != null) {
            this.head.value.nextPatient.value.prevPatient = null;
            this.head = this.head.value.nextPatient;
            return oldHead;
        } else {
            this.head = null;
            this.tail = null;
            return oldHead;
        }
    }
}
