package Controllers;

import Models.DoctorsListModel;
import Models.MedicineListModel;
import Models.ReciptModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;


public class NewReciptFormController
{
    @FXML
    private TextArea descriptionArea;

    @FXML
    private ChoiceBox<String> patientNameBox;

    @FXML
    private ChoiceBox<String> medicineNameBox;

    @FXML
    private TextField priceLabel;

    private MedicineListModel medicineListModel;
    private DoctorsListModel doctorsListModel;
    private ReciptModel reciptModel;
    int choosenMedicineIndex;
    int choosenPatientIndex;
    private int doctorId;

    @FXML
    public void init(int doctorID)
    {
        medicineListModel = new MedicineListModel();
        doctorsListModel = new DoctorsListModel();
        this.doctorId = doctorID;

        int patientsListSize = doctorsListModel.getPatientsListSize();
        int medicineListSize = medicineListModel.getListLenght();

        for(int i=0;i<patientsListSize;i++)
        {
            if(!patientNameBox.getItems().contains(doctorsListModel.patientsList.get(i)))
            {
                patientNameBox.getItems().add(doctorsListModel.patientsList.get(i));
            }
        }

        for(int i=0;i<medicineListSize;i++)
        {
            if(!medicineNameBox.getItems().contains(medicineListModel.getMedicine(i)))
            {
                medicineNameBox.getItems().add(medicineListModel.getMedicine(i));
            }
        }

        patientNameBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                choosenPatientIndex = t1.intValue();
            }
        });

        medicineNameBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                priceLabel.setText(medicineListModel.getMedicinePrice(t1.intValue())+" zł");
                choosenMedicineIndex=t1.intValue();
            }
        });
    }

    public int getDoctorId()
    {
        return doctorId;
    }

    public void saveRecipt(ActionEvent actionEvent)
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
            reciptModel = new ReciptModel(choosenPatientIndex,getDoctorId(),medicineListModel.medicineList.get(choosenMedicineIndex),descriptionArea.getText());
            if(reciptModel.saveRecipt())
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Wystawiono receptę");
                alert.setHeaderText("Wypisana recepta została dodana do bazy");
                alert.showAndWait();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Nie udało się wystawić recepty");
                alert.setHeaderText("Wystąpił problem podczas wysyłania do bazy");
                alert.setContentText(reciptModel.getErrorString());
                alert.showAndWait();
            }
        }
    }

    public void closeFrame(ActionEvent actionEvent)
    {
        ((Node)actionEvent.getSource()).getScene().getWindow().hide();
    }
}
