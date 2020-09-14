package Controllers;

import ConnectionPackage.Connector;
import Models.PatientModel;
import Models.SimpleVisit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientPreviewController
{
    private List<SimpleVisit> patientVisitsList;
    private PatientModel patient;

    @FXML
    private Label titleLabel;

    @FXML
    private TextField loginField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField peselField;

    @FXML
    private TextField phoneNbrField;

    @FXML
    private TextField streetField;

    @FXML
    private TextField zipCodeField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField growthField;

    @FXML
    private TextField weightField;

    @FXML
    private TextField sexField;

    @FXML
    private TextField bmiField;

    @FXML
    private TableView table;

    @FXML
    private TableColumn fullNameDoctorColumn;

    @FXML
    private TableColumn visitDateColumn;

    @FXML
    private TableColumn descriptionColumn;

    public void init(PatientModel patientModel)
    {
        patient = patientModel;
        patientVisitsList = new ArrayList<>();

        fullNameDoctorColumn.setCellValueFactory(new PropertyValueFactory<>("patient"));
        visitDateColumn.setCellValueFactory(new PropertyValueFactory<>("visitDate"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        getPatientVisits();

        titleLabel.setText("Pacjent o id "+patientModel.getPatient_id());
        loginField.setText(patientModel.getLogin());
        nameField.setText(patientModel.getName());
        surnameField.setText(patientModel.getSurname());
        peselField.setText(patientModel.getPeselNumber());
        phoneNbrField.setText(patientModel.getPhoneNumber());
        streetField.setText(patientModel.getStreet());
        zipCodeField.setText(patientModel.getZipCode());
        cityField.setText(patientModel.getCity());
        growthField.setText(patientModel.getGrowth());
        sexField.setText(patientModel.getSex());
        weightField.setText(patientModel.getWeight());
        bmiField.setText(getBMIStatus(Double.parseDouble(patientModel.getWeight()), Integer.parseInt(patientModel.getGrowth())));
    }

    String getBMIStatus(double weight,int growth)
    {
        double correctedGrowth = growth*0.01;
        double bmiValue = weight/(Math.pow(correctedGrowth,2));
        String status="";
        if(bmiValue<18.5)
        {
            status = "Niedowaga";
        }
        else if(bmiValue>=18.5&&bmiValue<=24.9)
        {
            status = "Waga prawidłowa";
        }
        else if(bmiValue>=25&&bmiValue<=29.9)
        {
            status = "Nadwaga";
        }
        if(bmiValue>30)
        {
            status = "Otyłość";
        }
        return status;
    }

    public List<SimpleVisit> getPatientVisitsList()
    {
        return patientVisitsList;
    }

    void getPatientVisits()
    {
        String query = "SELECT private_data.name, private_data.surname, visits.visit_date, visits.description FROM visits INNER JOIN private_data ON visits.doctor_id = private_data.user_id WHERE patient_id="+patient.getPatient_id();
        try
        {
            PreparedStatement selectFilteredVisits = Connector.getConnection().prepareStatement(query);
            ResultSet resultFilteredVisits = selectFilteredVisits.executeQuery();
            while(resultFilteredVisits.next())
            {
                String fullName = resultFilteredVisits.getString(1)+" "+resultFilteredVisits.getString(2);
                Date tempDate = resultFilteredVisits.getDate(3);
                String desc = resultFilteredVisits.getString(4);
                patientVisitsList.add(new SimpleVisit(fullName,tempDate,desc));
            }
            System.out.println("Ilość końcowa nowej listy "+getPatientVisitsList().size());
            for(int i=0;i<getPatientVisitsList().size();i++)
            {
                table.getItems().add(getPatientVisitsList().get(i));
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage()+"\n"+e.getErrorCode()+"\n"+e.getCause());
        }
    }

    public void closeFrame(ActionEvent actionEvent)
    {
        ((Node)actionEvent.getSource()).getScene().getWindow().hide();
    }
}
