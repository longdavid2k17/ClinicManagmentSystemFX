package Controllers;

import Models.PatientModel;
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
        try
        {
            patient = new PatientModel(id_user);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        setNickname(nickname);
        updateWindow();
    }

    public PatientModel getPatient()
    {
        return patient;
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
            try
            {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Views/SendMeetingRequestView.fxml"));
                Parent sceneMain = loader.load();
                SendVisitRequestController controller = loader.<SendVisitRequestController>getController();
                controller.init(patient);

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
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Views/PatientReciptsView.fxml"));
                Parent sceneMain = loader.load();
                PatientReciptsController controller = loader.<PatientReciptsController>getController();
                controller.init(patient);

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

        if(actionEvent.getSource() == oldVisitBtn)
        {
            try
            {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Views/PatientScheduleView.fxml"));
                Parent sceneMain = loader.load();
                PatientScheduleController controller = loader.<PatientScheduleController>getController();
                controller.init(patient.getPatient_id());

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
            try
            {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Views/IllnessBaseView.fxml"));
                Parent sceneMain = loader.load();
                IllnessBaseController controller = loader.<IllnessBaseController>getController();
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

        if(actionEvent.getSource() == yourDataBtn)
        {
            System.out.println(getPatient().toString());
            try
            {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Views/PatientPreviewView.fxml"));
                Parent sceneMain = loader.load();
                PatientPreviewController controller = loader.<PatientPreviewController>getController();
                controller.init(getPatient());

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
    }

    public void closeApp(ActionEvent actionEvent)
    {
        System.exit(0);
    }
}
