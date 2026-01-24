public class PatientData {

    public String assignedDoctor;
    public Node<PatientData,String> nextPatient;
    public Node<PatientData,String> prevPatient;

    PatientData(String assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }

    public void add(Node<PatientData,String> patient) {
        this.nextPatient = patient;
    }
}
