package Controllers;

import Models.DoctorsListModel;
import Models.VisitModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class NewVisitFormController
{
    @FXML
    private TextArea descriptionArea;

    @FXML
    private ChoiceBox<String> patientNameBox;

    @FXML
    private ChoiceBox<String> doctorNameBox;

    @FXML
    private DatePicker datePicker;

    private DoctorsListModel doctorsListModel;
    private VisitModel visitModel;

    @FXML
    public void init()
    {
        doctorsListModel = new DoctorsListModel();

        int doctorsListSize = doctorsListModel.getDoctorsListSize();
        int patientsListSize = doctorsListModel.getPatientsListSize();

        for(int i=0;i<doctorsListSize;i++)
        {
            if(!doctorNameBox.getItems().contains(doctorsListModel.doctorsList.get(i)))
            {
                doctorNameBox.getItems().add(doctorsListModel.doctorsList.get(i));
            }
        }

        for(int i=0;i<patientsListSize;i++)
        {
            if(!patientNameBox.getItems().contains(doctorsListModel.patientsList.get(i)))
            {
                patientNameBox.getItems().add(doctorsListModel.patientsList.get(i));
            }
        }
    }

    public void saveVisit(ActionEvent actionEvent)
    {
        if(descriptionArea.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nie wypełniono formularza poprawnie");
            alert.setHeaderText("Pole opisu nie może pozostać puste.");
            alert.setContentText("Wypełnij formularz poprawnie i spróbuj ponownie.");
            alert.showAndWait();
        }
        else
        {
            LocalDate localDate = datePicker.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);

            visitModel = new VisitModel((String)doctorNameBox.getValue(),(String)patientNameBox.getValue(),date,descriptionArea.getText());
            if(visitModel.saveVisitToDatabase())
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Zapisano wizytę!");
                alert.setHeaderText("Wizyta została umieszczona w harmonogramie lekarza obsługującego danego pacjenta.");
                alert.setContentText("Wszystkie informacje na temat tej wizyty będą dostępne z poziomu harmonogramu lekarza!");
                alert.showAndWait();

                descriptionArea.setText("");
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Wizyta nie została zapisana!");
                alert.setHeaderText("Wystąpił problem przy zapisie do bazy danych");
                alert.setContentText("Skontaktuj się z administratorem!");
                alert.showAndWait();
            }
        }
    }

    public void closeFrame(ActionEvent actionEvent)
    {
        ((Node)actionEvent.getSource()).getScene().getWindow().hide();
    }
}
