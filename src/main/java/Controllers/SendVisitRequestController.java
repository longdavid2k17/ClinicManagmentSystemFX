package Controllers;

import ConnectionPackage.Connector;
import Models.DoctorsListModel;
import Models.PatientModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class SendVisitRequestController
{
    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField patientNameField;

    @FXML
    private ChoiceBox doctorBox;

    @FXML
    private TextArea messeageArea;

    private PatientModel patient;
    private DoctorsListModel doctorsListModel;

    public PatientModel getPatient()
    {
        return patient;
    }

    public void setPatient(PatientModel patient)
    {
        this.patient = patient;
    }

    public void init(PatientModel patient)
    {
        setPatient(patient);
        doctorsListModel = new DoctorsListModel();
        patientNameField.setText(getPatient().getName()+" "+getPatient().getSurname());

        for(int i=0;i<doctorsListModel.getDoctorsListSize();i++)
        {
            if(!doctorBox.getItems().contains(doctorsListModel.doctorsList.get(i)))
            {
                doctorBox.getItems().add(doctorsListModel.doctorsList.get(i));
            }
        }
    }

    public void closeFrame(ActionEvent actionEvent)
    {
        ((Node)actionEvent.getSource()).getScene().getWindow().hide();
    }

    public void send(ActionEvent actionEvent)
    {
        if(messeageArea.getText().trim().isEmpty() || doctorBox.getSelectionModel().getSelectedItem() == null || datePicker.getValue() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd!");
            alert.setHeaderText("Nie wypełniono wymaganych pól");
            alert.setContentText("Wypełnij poprawnie formularz i spróbuj ponownie");
            alert.showAndWait();
        }
        else
        {
            String[] doctorNameParts = doctorBox.getValue().toString().split(" ");
            String doctorName = doctorNameParts[0];
            String doctorSurname = doctorNameParts[1];
            int id_doctor;
            boolean executed = false;


            String idDoctorQuery = "SELECT user_id FROM private_data WHERE name='"+doctorName+"' AND surname='"+doctorSurname+"';";

            try
            {
                PreparedStatement selectDoctorID = Connector.getConnection().prepareStatement(idDoctorQuery);
                ResultSet resultDoctor = selectDoctorID.executeQuery();
                if (resultDoctor.next())
                {
                    id_doctor = resultDoctor.getInt(1);

                    LocalDate localDate = datePicker.getValue();
                    Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                    Date date = Date.from(instant);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String dateString = format.format(date);

                    String query = "INSERT INTO messages (doctor_id, patient_id, message, preffered_date, readed) VALUES ("+id_doctor+","+getPatient().getPatient_id()+",'"+messeageArea.getText()+"','"+dateString+"',0)";
                    PreparedStatement insertVisitQuery = Connector.getConnection().prepareStatement(query);
                    int resultInsert = insertVisitQuery.executeUpdate();
                    if(resultInsert==1)
                    {
                        /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Wysłano wiadomość!");
                        alert.setHeaderText("Wysłano wiadomość do wskazanego lekarza");
                        alert.showAndWait();*/
                        ((Node)actionEvent.getSource()).getScene().getWindow().hide();
                    }
                    else
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Błąd!");
                        alert.setHeaderText("Nie udało się wysłać wiadomości!");
                        alert.setContentText("Skontaktuj się z administratorem albo poczekaj i spróbuj ponownie");
                        alert.showAndWait();
                    }
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd!");
                    alert.setHeaderText("Nie udało się pobrać danych wybranego lekarza!");
                    alert.setContentText("Skontaktuj się z administratorem albo poczekaj i spróbuj ponownie");
                    alert.showAndWait();
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }

        }
    }
}
