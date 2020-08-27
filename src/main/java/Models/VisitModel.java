package Models;

import ConnectionPackage.Connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VisitModel
{
    private String doctorName, patientName;
    private int idDoctor,idPatient;
    private Date visitDate;
    private String description;

    public String getDoctorName()
    {
        return doctorName;
    }

    public void setDoctorName(String doctorName)
    {
        this.doctorName = doctorName;
    }

    public String getPatientName()
    {
        return patientName;
    }

    public void setPatientName(String patientName)
    {
        this.patientName = patientName;
    }

    public Date getVisitDate()
    {
        return visitDate;
    }

    public void setVisitDate(Date visitDate)
    {
        this.visitDate = visitDate;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getIdDoctor()
    {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor)
    {
        this.idDoctor = idDoctor;
    }

    public int getIdPatient()
    {
        return idPatient;
    }

    public void setIdPatient(int idPatient)
    {
        this.idPatient = idPatient;
    }

    public VisitModel(String doctorName, String patientName, Date visitDate, String description)
    {
        setDoctorName(doctorName);
        setPatientName(patientName);
        setVisitDate(visitDate);
        setDescription(description);
    }

    public boolean saveVisitToDatabase()
    {
        String[] patientNameParts = getPatientName().split(" ");
        String patientName = patientNameParts[0];
        String patientSurname = patientNameParts[1];

        String[] doctorNameParts = getDoctorName().split(" ");
        String doctorName = doctorNameParts[0];
        String doctorSurname = doctorNameParts[1];

        String idPatientQuery = "SELECT user_id FROM private_data WHERE name='"+patientName+"' AND surname='"+patientSurname+"';";
        String idDoctorQuery = "SELECT user_id FROM private_data WHERE name='"+doctorName+"' AND surname='"+doctorSurname+"';";

        boolean executed = false;


        try
        {
            PreparedStatement selectPatientID = Connector.getConnection().prepareStatement(idPatientQuery);
            PreparedStatement selectDoctorID = Connector.getConnection().prepareStatement(idDoctorQuery);
            ResultSet resultPatient = selectPatientID.executeQuery();
            ResultSet resultDoctor = selectDoctorID.executeQuery();
            if (resultPatient.next() && resultDoctor.next())
            {
                setIdPatient(resultPatient.getInt(1));
                setIdDoctor(resultDoctor.getInt(1));

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                String dateString = format.format( getVisitDate());

                String insertQuery = "INSERT INTO visits(patient_id,doctor_id,description, visit_date) VALUES ("+getIdPatient()+","+getIdDoctor()+",'"+getDescription()+"','"+dateString+"');";
                PreparedStatement insertVisitQuery = Connector.getConnection().prepareStatement(insertQuery);
                int resultInsert = insertVisitQuery.executeUpdate();
                if(resultInsert==1)
                {
                    executed = true;
                }
                else
                    executed = false;
            }
            else executed = false;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return executed;
    }
}
