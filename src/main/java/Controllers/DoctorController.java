package Controllers;

import Models.DoctorModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class DoctorController
{
    @FXML
    private Button newVisitBtn;

    @FXML
    private Button reciptBtn;

    @FXML
    private Button scheduleBtn;

    @FXML
    private Button ilnessDbBtn;

    @FXML
    private Button patientsDataBtn;

    @FXML
    private Button medicinesBtn;

    @FXML
    private Label welcomeLabel;

    private String nickname;

    private DoctorModel doctor;


    void initVariables(String nickname, int id_user)
    {
        doctor = new DoctorModel(id_user);
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
            //((Node)actionEvent.getSource()).getScene().getWindow().hide();
            try
            {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Views/NewVisitFormView.fxml"));
                Parent sceneMain = loader.load();
                NewVisitFormController controller = loader.<NewVisitFormController>getController();
                controller.init();

                Image icon = new Image(getClass().getResourceAsStream("/Icons/app_icon.png"));

                Scene scene = new Scene(sceneMain);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.getIcons().add(icon);
                stage.show();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }

        if(actionEvent.getSource() == reciptBtn)
        {
            try
            {
                System.out.println("DoctorID: "+doctor.getDoctor_id());
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Views/NewReciptFormView.fxml"));
                Parent sceneMain = loader.load();
                NewReciptFormController controller = loader.<NewReciptFormController>getController();
                controller.init(doctor.getDoctor_id());

                Image icon = new Image(getClass().getResourceAsStream("/Icons/app_icon.png"));

                Scene scene = new Scene(sceneMain);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.getIcons().add(icon);
                stage.show();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }

        if(actionEvent.getSource() == scheduleBtn)
        {
            try
            {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Views/ScheduleView.fxml"));
                Parent sceneMain = loader.load();
                ScheduleController controller = loader.<ScheduleController>getController();
                controller.init(nickname,doctor.getDoctor_id());

                Image icon = new Image(getClass().getResourceAsStream("/Icons/app_icon.png"));

                Scene scene = new Scene(sceneMain);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.getIcons().add(icon);
                stage.show();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        if(actionEvent.getSource() == ilnessDbBtn)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Baza chorób i dolegliwości");
            alert.setHeaderText("AAAA!");
            alert.setContentText("BBBBBBBBB!");

            alert.showAndWait();
        }

        if(actionEvent.getSource() == medicinesBtn)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Baza leków");
            alert.setHeaderText("AAAA!");
            alert.setContentText("BBBBBBBBB!");

            alert.showAndWait();
        }

        if(actionEvent.getSource() == patientsDataBtn)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Dane pacjentów");
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
