package Models;

public class PatientModel
{
    private int patient_id;

    public PatientModel(int patient_id)
    {
        setPatient_id(patient_id);
    }

    public int getPatient_id()
    {
        return patient_id;
    }

    public void setPatient_id(int patient_id)
    {
        this.patient_id = patient_id;
    }
}
