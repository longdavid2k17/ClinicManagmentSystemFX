package Controllers;

import ConnectionPackage.Connector;
import Models.PatientModel;
import Models.ReciptModel;
import Models.SimpleRecipt;
import Models.SimpleVisit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientReciptsController
{
    @FXML
    private TableView table;

    @FXML
    private TableColumn date;

    @FXML
    private TableColumn doctor;

    @FXML
    private TableColumn medicineName;

    @FXML
    private TableColumn description;

    private List<SimpleRecipt> reciptList;
    private PatientModel patient;

    public PatientModel getPatient()
    {
        return patient;
    }

    public void setPatient(PatientModel patient)
    {
        this.patient = patient;
    }

    public List<SimpleRecipt> getReciptList()
    {
        return reciptList;
    }

    public void init(PatientModel patient)
    {
        setPatient(patient);
        getPatientRecipts();

        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        doctor.setCellValueFactory(new PropertyValueFactory<>("doctor"));
        medicineName.setCellValueFactory(new PropertyValueFactory<>("medicine_name"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));

        for(int i=0;i<getReciptList().size();i++)
        {
            table.getItems().add(getReciptList().get(i));
        }
    }

    public void closeApp(ActionEvent actionEvent)
    {
        ((Node)actionEvent.getSource()).getScene().getWindow().hide();
    }

    public void getPatientRecipts()
    {
        reciptList = new ArrayList<>();

        String query = "SELECT private_data.name, private_data.surname, recipts.creation_date,medicines.name, recipts.description FROM private_data INNER JOIN recipts ON private_data.user_id = recipts.doctor_id INNER JOIN medicines ON recipts.medicine_id = medicines.id" +
                " WHERE  recipts.user_id="+patient.getPatient_id()+";";

        System.out.println(query);
        try
        {
            PreparedStatement selectRecipts = Connector.getConnection().prepareStatement(query);
            ResultSet resultRecipts = selectRecipts.executeQuery();
            while(resultRecipts.next())
            {
                String fullName = resultRecipts.getString(1)+" "+resultRecipts.getString(2);
                Date tempDate = resultRecipts.getDate(3);
                String medicineName = resultRecipts.getString(4);
                String desc = resultRecipts.getString(5);
                reciptList.add(new SimpleRecipt(fullName,tempDate,desc,medicineName));
            }
            System.out.println("Ilość końcowa nowej listy "+getReciptList().size());
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage()+"\n"+e.getErrorCode()+"\n"+e.getCause());
        }
    }
}
