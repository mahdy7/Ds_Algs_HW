public class DoctorData {

    int load;
    Node<PatientData> head;
    Node<PatientData> tail;


    DoctorData() {
        this.load = 0;
        this.head = null;
        this.tail = null;
    }

    public void increaseLoad() {
        load++;
    }

    public void addPatient(Node<PatientData> patient) {
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

    public Node<PatientData> getHead() {
        return head;
    }

    public void setHead(Node<PatientData> head) {
        this.head = head;
    }

    public Node<PatientData> getTail() {
        return tail;
    }

    public void setTail(Node<PatientData> tail) {
        this.tail = tail;
    }

    public Node<PatientData> removeHead() {
        Node<PatientData> oldHead = this.head;
        this.head.value.nextPatient.value.prevPatient = null;
        this.head = oldHead.value.nextPatient;
        return oldHead;
    }
}
