package Controllers;

import Models.PatientModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PatientController
{

    @FXML
    private Button newVisitBtn;

    @FXML
    private Button reciptBtn;

    @FXML
    private Button oldVisitBtn;

    @FXML
    private Button ilnessDbBtn;

    @FXML
    private Button yourDataBtn;

    @FXML
    private Label welcomeLabel;

    private String nickname;
    private PatientModel patient;


    void initVariables(String nickname, int id_user)
    {
        patient = new PatientModel(id_user);
        setNickname(nickname);
        updateWindow();
    }

    void updateWindow()
    {
        welcomeLabel.setText("Witaj, "+getNickname().toString());
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public void handleClicks(javafx.event.ActionEvent actionEvent)
    {
        if(actionEvent.getSource() == newVisitBtn)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Nowa wizyta!");
            alert.setHeaderText("AAAA!");
            alert.setContentText("BBBBBBBBB!");

            alert.showAndWait();
        }

        if(actionEvent.getSource() == reciptBtn)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Wystawione recepty");
            alert.setHeaderText("AAAA!");
            alert.setContentText("BBBBBBBBB!");

            alert.showAndWait();
        }

        if(actionEvent.getSource() == oldVisitBtn)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Historia wizyt");
            alert.setHeaderText("AAAA!");
            alert.setContentText("BBBBBBBBB!");

            alert.showAndWait();
        }

        if(actionEvent.getSource() == ilnessDbBtn)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Baza chorób i dolegliwości");
            alert.setHeaderText("AAAA!");
            alert.setContentText("BBBBBBBBB!");

            alert.showAndWait();
        }

        if(actionEvent.getSource() == yourDataBtn)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Twoje dane osobowe");
            alert.setHeaderText("AAAA!");
            alert.setContentText("BBBBBBBBB!");

            alert.showAndWait();
        }
    }

    public void closeApp(ActionEvent actionEvent)
    {
        System.exit(0);
    }
}
