package Models;

import ConnectionPackage.Connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorsListModel
{
    public List<String> doctorsList;
    public List<String> patientsList;
    public List<PatientModel> patientEntitiesList;
    private int doctorsListSize;
    private int patientsListSize;

    public DoctorsListModel()
    {
        getListDoctor();
        getListPatients();
        doctorsListSize = doctorsList.size();
        patientsListSize = patientsList.size();
    }

    public int getDoctorsListSize()
    {
        return doctorsListSize;
    }

    public int getPatientsListSize()
    {
        return patientsListSize;
    }


    public void getListDoctor()
    {
        doctorsList = new ArrayList<String>();
        patientEntitiesList = new ArrayList<PatientModel>();

        String selectDoctors = "SELECT  private_data.name, private_data.surname FROM private_data INNER JOIN account_type WHERE account_type.account_type='doctor' and account_type.id_account=private_data.user_id;";
        try
        {
            PreparedStatement selectDoctorsStatement = Connector.getConnection().prepareStatement(selectDoctors);
            ResultSet result = selectDoctorsStatement.executeQuery();
            while (result.next())
            {
                String name = result.getString(1);
                String surname = result.getString(2);

                String doctorFullName = name+" "+surname;
                doctorsList.add(doctorFullName);

            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void getListPatients()
    {
        patientsList = new ArrayList<String>();

        String selectPatients = "SELECT  private_data.user_id,private_data.name, private_data.surname FROM private_data INNER JOIN account_type WHERE account_type.account_type='patient' and account_type.id_account=private_data.user_id;";
        try
        {
            PreparedStatement selectPatientsStatement = Connector.getConnection().prepareStatement(selectPatients);
            ResultSet result = selectPatientsStatement.executeQuery();
            while (result.next())
            {
                int id = result.getInt(1);
                String name = result.getString(2);
                String surname = result.getString(3);

                patientEntitiesList.add(new PatientModel(id));
                String patientFullName = name+" "+surname;
                patientsList.add(patientFullName);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
