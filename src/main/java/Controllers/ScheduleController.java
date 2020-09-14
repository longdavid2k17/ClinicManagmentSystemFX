package Controllers;

import ConnectionPackage.Connector;
import Models.DoctorModel;
import Models.DoctorsListModel;
import Models.SimpleVisit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleController
{
    @FXML
    private Label welcomeLabel;

    @FXML
    private DatePicker datePickerFrom;

    @FXML
    private DatePicker datePickerTo;

    @FXML
    private ChoiceBox<String> patientNameBox;

    @FXML
    private TableView table;

    @FXML
    private TableColumn visitDate;

    @FXML
    private TableColumn patient;

    @FXML
    private TableColumn description;

    private int doctor_id;
    private String nickname;
    private List<SimpleVisit> visitsList;
    private DoctorsListModel doctorsListModel;


    public void init(String nickname,int doctor_id)
    {
        datePickerFrom.setValue(null);
        datePickerTo.setValue(null);
        setNickname(nickname);
        setDoctor_id(doctor_id);
        welcomeLabel.setText("Terminarz dla: "+getNickname());
        visitsList = new ArrayList<SimpleVisit>();
        getVisits();
        doctorsListModel = new DoctorsListModel();

        int patientsListSize = doctorsListModel.getPatientsListSize();

        visitDate.setCellValueFactory(new PropertyValueFactory<>("visitDate"));
        patient.setCellValueFactory(new PropertyValueFactory<>("patient"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));

        for(int i=0;i<patientsListSize;i++)
        {
            if(!patientNameBox.getItems().contains(doctorsListModel.patientsList.get(i)))
            {
                patientNameBox.getItems().add(doctorsListModel.patientsList.get(i));
            }
        }

        for(int i=0;i<getVisitsList().size();i++)
        {
            table.getItems().add(getVisitsList().get(i));
        }
    }

    public void setDoctor_id(int doctor_id)
    {
        this.doctor_id = doctor_id;
    }

    public int getDoctor_id()
    {
        return doctor_id;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public List<SimpleVisit> getVisitsList()
    {
        return visitsList;
    }

    public void getVisits()
    {
        clearLists();
        String visitsQuery = "SELECT private_data.name, private_data.surname, visits.visit_date,visits.description FROM private_data INNER JOIN visits ON visits.patient_id = private_data.user_id WHERE visits.patient_id=private_data.user_id AND visits.doctor_id="+getDoctor_id()+"; ";
        try
        {
            PreparedStatement selectVisits = Connector.getConnection().prepareStatement(visitsQuery);
            ResultSet resultVisits = selectVisits.executeQuery();
            while (resultVisits.next())
            {
                String fullName = resultVisits.getString(1)+" "+resultVisits.getString(2);
                Date tempDate = resultVisits.getDate(3);
                String desc = resultVisits.getString(4);
                visitsList.add(new SimpleVisit(fullName,tempDate,desc));

            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }

    public void closeApp(ActionEvent actionEvent)
    {
        ((Node)actionEvent.getSource()).getScene().getWindow().hide();
    }

    public void search(ActionEvent actionEvent)
    {
        if(patientNameBox.getValue() == null && datePickerFrom.getValue()==null && datePickerTo.getValue()==null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd!");
            alert.setHeaderText("Nie ustawiono żadnych filtrów");
            alert.setContentText("Wybierz jakikolwiek filtr i spróbuj ponownie");
            alert.showAndWait();
        }
        else
        {
            boolean isSetFrom = false;
            boolean isSetTo = false;
            boolean isSetPatient = false;
            String patientName="";
            String patientSurname="";
            String query = "";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String dateFromString="";
            String dateToString="";

            if(patientNameBox.getValue() != null)
            {
                String[] patientNameParts = patientNameBox.getValue().split(" ");
                patientName = patientNameParts[0];
                patientSurname = patientNameParts[1];
                isSetPatient = true;
                System.out.println("WARTOŚĆ PACJENTA "+patientNameBox.getValue());
            }

            if(datePickerFrom.getValue() != null)
            {
                LocalDate localDateFrom = datePickerFrom.getValue();
                Instant instantFrom = Instant.from(localDateFrom.atStartOfDay(ZoneId.systemDefault()));
                Date dateFrom = Date.from(instantFrom);
                dateFromString = format.format( dateFrom);
                isSetFrom = true;
            }
            if(datePickerTo.getValue() != null)
            {
                LocalDate localDateTo = datePickerTo.getValue();
                Instant instantTo = Instant.from(localDateTo.atStartOfDay(ZoneId.systemDefault()));
                Date dateTo = Date.from(instantTo);
                dateToString = format.format( dateTo);
                isSetTo = true;
            }

            if(isSetFrom && isSetTo)
            {
                if(isSetPatient)
                {
                    query = "SELECT private_data.name, private_data.surname, visits.visit_date,visits.description FROM private_data INNER JOIN visits ON visits.patient_id = private_data.user_id " +
                            "WHERE visits.patient_id=private_data.user_id AND private_data.name='"+patientName+"' AND private_data.surname='"+patientSurname+"' AND visits.visit_date >= '"+dateFromString+"' AND visits.visit_date <='"+dateToString+"' AND visits.doctor_id="+getDoctor_id()+";";
                }
                else
                {
                    query = "SELECT private_data.name, private_data.surname, visits.visit_date,visits.description FROM private_data INNER JOIN visits ON visits.patient_id = private_data.user_id " +
                            "WHERE visits.patient_id=private_data.user_id  AND visits.visit_date >= '"+dateFromString+"' AND visits.visit_date <='"+dateToString+"' AND visits.doctor_id="+getDoctor_id()+";";
                }
            }
            else if(isSetFrom && isSetTo==false)
            {
                if(isSetPatient)
                {
                    query = "SELECT private_data.name, private_data.surname, visits.visit_date,visits.description FROM private_data INNER JOIN visits ON visits.patient_id = private_data.user_id " +
                            "WHERE visits.patient_id=private_data.user_id AND private_data.name='"+patientName+"' AND private_data.surname='"+patientSurname+"' AND visits.visit_date >= '"+dateFromString+"' AND visits.doctor_id="+getDoctor_id()+";";
                }
                else
                {
                    query = "SELECT private_data.name, private_data.surname, visits.visit_date,visits.description FROM private_data INNER JOIN visits ON visits.patient_id = private_data.user_id " +
                            "WHERE visits.patient_id=private_data.user_id  AND visits.visit_date >= '"+dateFromString+"' AND visits.doctor_id="+getDoctor_id()+";";
                }
            }
            else if(isSetFrom==false && isSetTo)
            {
                if(isSetPatient)
                {
                    query = "SELECT private_data.name, private_data.surname, visits.visit_date,visits.description FROM private_data INNER JOIN visits ON visits.patient_id = private_data.user_id " +
                            "WHERE visits.patient_id=private_data.user_id AND private_data.name='"+patientName+"' AND private_data.surname='"+patientSurname+"' AND visits.visit_date <='"+dateToString+"' AND visits.doctor_id="+getDoctor_id()+";";
                }
                else
                {
                    query = "SELECT private_data.name, private_data.surname, visits.visit_date,visits.description FROM private_data INNER JOIN visits ON visits.patient_id = private_data.user_id " +
                            "WHERE visits.patient_id=private_data.user_id AND visits.visit_date <='"+dateToString+"' AND visits.doctor_id="+getDoctor_id()+";";
                }
            }
            else if(isSetFrom==false && isSetTo==false)
            {
                if(isSetPatient)
                {
                    query = "SELECT private_data.name, private_data.surname, visits.visit_date,visits.description FROM private_data INNER JOIN visits ON visits.patient_id = private_data.user_id WHERE visits.patient_id=private_data.user_id AND private_data.name='"+patientName+"'" +
                            " AND private_data.surname='"+patientSurname+"' AND visits.doctor_id="+getDoctor_id()+";";
                }
            }

            clearLists();
            System.out.println(query);
            try
            {
                PreparedStatement selectFilteredVisits = Connector.getConnection().prepareStatement(query);
                ResultSet resultFilteredVisits = selectFilteredVisits.executeQuery();
                while(resultFilteredVisits.next())
                {
                    String fullName = resultFilteredVisits.getString(1)+" "+resultFilteredVisits.getString(2);
                    Date tempDate = resultFilteredVisits.getDate(3);
                    String desc = resultFilteredVisits.getString(4);
                    visitsList.add(new SimpleVisit(fullName,tempDate,desc));
                }
                System.out.println("Ilość końcowa nowej listy "+getVisitsList().size());
                for(int i=0;i<getVisitsList().size();i++)
                {
                    table.getItems().add(getVisitsList().get(i));
                }
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage()+"\n"+e.getErrorCode()+"\n"+e.getCause());
            }
        }


    }

    public void clearLists()
    {
        table.getItems().clear();
        getVisitsList().clear();
    }

    public void clearFilters(ActionEvent actionEvent)
    {
        patientNameBox.getSelectionModel().clearSelection();
        datePickerFrom.setValue(null);
        datePickerTo.setValue(null);
        init(getNickname(),getDoctor_id());
    }
}
