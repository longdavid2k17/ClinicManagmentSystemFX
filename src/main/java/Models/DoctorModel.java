package Models;

public class DoctorModel
{
    private int doctor_id;


    public DoctorModel(int id_user)
    {
        setDoctor_id(id_user);
    }

    public int getDoctor_id()
    {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id)
    {
        this.doctor_id = doctor_id;
    }


}
