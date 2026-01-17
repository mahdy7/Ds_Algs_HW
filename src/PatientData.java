public class PatientData {

    public String assignedDoctor;
    public Node<PatientData,String> nextPatient;
    public Node<PatientData,String> prevPatient;

    PatientData(String assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }

    public Node<PatientData,String> getNextPatient() {
        return nextPatient;
    }

    public Node<PatientData,String> getPrevPatient() {
        return prevPatient;
    }

    public void add(Node<PatientData,String> patient) {
        this.nextPatient = patient;
    }
}
