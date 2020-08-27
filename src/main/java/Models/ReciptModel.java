package Models;
import ConnectionPackage.Connector;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ReciptModel
{
    private int patient_id;
    private int doctor_id;
    private MedicineModel medicine;
    private String description;
    private String errorString;

    public ReciptModel(int patient_id, int doctor_id, MedicineModel medicine, String description)
    {
        this.patient_id = patient_id;
        this.doctor_id = doctor_id;
        this.medicine = medicine;
        this.description = description;
    }

    public int getPatient_id()
    {
        return patient_id;
    }

    public int getDoctor_id()
    {
        return doctor_id;
    }

    public MedicineModel getMedicine()
    {
        return medicine;
    }

    public String getDescription()
    {
        return description;
    }

    public String getErrorString()
    {
        return errorString;
    }

    public boolean saveRecipt()
    {
        boolean executed = false;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();

        String query = "INSERT INTO recipts(user_id,doctor_id,medicine_id,description,creation_date) VALUES ('"+getPatient_id()+"','"+getDoctor_id()+"','"+getMedicine().getId()+"','"+getDescription()+"','"+dtf.format(now)+"');";
        try
        {
            PreparedStatement insertRecipt = Connector.getConnection().prepareStatement(query);
            int result = insertRecipt.executeUpdate();
            if (result==1) {
                executed = true;
            }
            else
                executed = false;

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            errorString = e.getMessage();
        }

        return executed;
    }
}
