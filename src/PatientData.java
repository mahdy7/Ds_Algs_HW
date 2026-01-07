public class PatientData {

    public String assignedDoctor;
    public Node<PatientData> nextPatient;
    public Node<PatientData> prevPatient;

    PatientData(String assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }

    public Node<PatientData> getNextPatient() {
        return nextPatient;
    }

    public Node<PatientData> getPrevPatient() {
        return prevPatient;
    }

    public void add(Node<PatientData> patient) {
        this.nextPatient = patient;

    }
}
