package Controllers;

import ConnectionPackage.Connector;
import Models.SimpleVisit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

public class PatientScheduleController
{
    @FXML
    private DatePicker datePickerFrom;

    @FXML
    private DatePicker datePickerTo;

    @FXML
    private TableView table;

    @FXML
    private TableColumn visitDate;

    @FXML
    private TableColumn doctorName;

    @FXML
    private TableColumn description;

    private List<SimpleVisit> visitList;

    private int patientId;

    public List<SimpleVisit> getVisitList()
    {
        return visitList;
    }

    public int getPatientId()
    {
        return patientId;
    }

    public void setPatientId(int patientId)
    {
        this.patientId = patientId;
    }

    void init(int patientId)
    {
        visitList = new ArrayList<>();
        setPatientId(patientId);
        getPatientVisits();

        visitDate.setCellValueFactory(new PropertyValueFactory<>("visitDate"));
        doctorName.setCellValueFactory(new PropertyValueFactory<>("patient"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));

        int size = getVisitList().size();

        for(int i=0;i<size;i++)
        {
            table.getItems().add(getVisitList().get(i));
        }
    }

    public void closeApp(ActionEvent actionEvent)
    {
        ((Node)actionEvent.getSource()).getScene().getWindow().hide();
    }

    public void clearLists()
    {
        table.getItems().clear();
        getVisitList().clear();
    }

    public void search(ActionEvent actionEvent)
    {
        if(datePickerFrom.getValue()==null && datePickerTo.getValue()==null)
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
            String query = "";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String dateFromString="";
            String dateToString="";

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
                    query = "SELECT private_data.name, private_data.surname, visits.visit_date,visits.description FROM private_data \n" +
                            "INNER JOIN visits ON visits.patient_id = private_data.user_id WHERE visits.patient_id=private_data.user_id \n" +
                            "AND visits.visit_date >= '"+dateFromString+"' AND visits.visit_date <='"+dateToString+"' AND visits.patient_id="+getPatientId()+";";

            }
            else if(isSetFrom && isSetTo==false)
            {
                    query = "SELECT private_data.name, private_data.surname, visits.visit_date,visits.description FROM private_data \n" +
                            "INNER JOIN visits ON visits.patient_id = private_data.user_id WHERE visits.patient_id=private_data.user_id \n" +
                            "AND visits.visit_date >= '"+dateFromString+"' AND visits.patient_id="+getPatientId()+";";
            }
            else if(isSetFrom==false && isSetTo)
            {

                    query = "SELECT private_data.name, private_data.surname, visits.visit_date,visits.description FROM private_data \n" +
                            "INNER JOIN visits ON visits.patient_id = private_data.user_id WHERE visits.patient_id=private_data.user_id \n" +
                            "AND visits.visit_date <='"+dateToString+"' AND visits.patient_id="+getPatientId()+";";
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
                    getVisitList().add(new SimpleVisit(fullName,tempDate,desc));
                }
                System.out.println("Ilość końcowa nowej listy "+getVisitList().size());
                for(int i=0;i<getVisitList().size();i++)
                {
                    table.getItems().add(getVisitList().get(i));
                }
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage()+"\n"+e.getErrorCode()+"\n"+e.getCause());
            }
        }
    }

    public void clearFilters(ActionEvent actionEvent)
    {
        datePickerFrom.setValue(null);
        datePickerTo.setValue(null);
        init(getPatientId());
    }

    void getPatientVisits()
    {
        clearLists();
        String visitsQuery = "SELECT private_data.name, private_data.surname, visits.visit_date,visits.description FROM private_data INNER JOIN visits ON visits.doctor_id = private_data.user_id WHERE visits.doctor_id=private_data.user_id AND visits.patient_id="+getPatientId()+"; ";
        try
        {
            PreparedStatement selectVisits = Connector.getConnection().prepareStatement(visitsQuery);
            ResultSet resultVisits = selectVisits.executeQuery();
            while (resultVisits.next())
            {
                String fullName = resultVisits.getString(1)+" "+resultVisits.getString(2);
                Date tempDate = resultVisits.getDate(3);
                String desc = resultVisits.getString(4);
                visitList.add(new SimpleVisit(fullName,tempDate,desc));

            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
