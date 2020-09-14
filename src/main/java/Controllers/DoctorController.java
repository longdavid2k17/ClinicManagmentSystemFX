package Controllers;

import ConnectionPackage.Connector;
import Models.DoctorModel;
import Models.MedicineModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorController
{
    @FXML
    private Button newVisitBtn;

    @FXML
    private ImageView messageImage;

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
        // TODO wrzucić metodę areNewMessages do wątku który będzie ją wykonywał co 30 sekund
        areNewMessages();
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

        if(actionEvent.getSource() == medicinesBtn)
        {
            try
            {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Views/MedicinesBaseView.fxml"));
                Parent sceneMain = loader.load();
                MedicinesBaseController controller = loader.<MedicinesBaseController>getController();
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

        if(actionEvent.getSource() == patientsDataBtn)
        {
            try
            {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Views/PatientsBaseView.fxml"));
                Parent sceneMain = loader.load();
                PatientBaseController controller = loader.<PatientBaseController>getController();
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
    }

    public void areNewMessages()
    {
        String query = "SELECT * FROM messages WHERE doctor_id="+doctor.getDoctor_id()+" AND readed=0";
        File file = null;

        try
        {
            PreparedStatement selectMessages = Connector.getConnection().prepareStatement(query);
            ResultSet result = selectMessages.executeQuery();
            if(result.next())
            {
                file = new File("src/main/resources/Icons/icons8_new_message_24px.png");
                Image image = new Image(file.toURI().toString());
                messageImage.setImage(image);
            }
            else
            {
                if(result.next())
                {
                    file = new File("src/main/resources/Icons/icons8_group_message_24px.png");
                    Image image = new Image(file.toURI().toString());
                    messageImage.setImage(image);
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage()+"\n"+e.getErrorCode()+"\n"+e.getCause());
        }
    }

    public void closeApp(ActionEvent actionEvent)
    {
        System.exit(0);
    }
}
