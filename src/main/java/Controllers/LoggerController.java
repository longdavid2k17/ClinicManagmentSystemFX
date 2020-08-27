package Controllers;

import ConnectionPackage.Connector;
import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoggerController
{
    @FXML
    private Button adminBtn;

    @FXML
    private Button registerBtn;

    @FXML
    private Button logInBtn;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;



    public void openAdminPanel(ActionEvent actionEvent)
    {
        try
        {
            Desktop.getDesktop().browse(URI.create("http://localhost/ClinicAdmin/"));
            new java.util.Timer().schedule
                    (
                            new java.util.TimerTask()
                            {
                                @Override
                                public void run()
                                {
                                    System.exit(0);
                                }
                            },
                            2000
                    );
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
    }

    public void openRegisterForm(ActionEvent actionEvent)
    {
        try
        {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Views/RegistrationFormView.fxml"));
            Parent sceneMain = loader.load();
            RegistrationFormController controller = loader.<RegistrationFormController>getController();
            controller.initialize();

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

    public void logOn(ActionEvent actionEvent)
    {
        String login, password, accountType;
        login = loginField.getText();
        password = passwordField.getText();

        if(login!="" && password!="")
        {
            String checkForExistingAccountStatement = "Select * from users where login='"+login+"' and password='"+password+"';";
            try
            {
                PreparedStatement checkForUser = Connector.getConnection().prepareStatement(checkForExistingAccountStatement);
                ResultSet checkForExistingAccountResult = checkForUser.executeQuery();
                if(checkForExistingAccountResult.next())
                {
                    int idUser = checkForExistingAccountResult.getInt(1);
                    boolean isActive = checkForExistingAccountResult.getBoolean(4);

                    if (isActive)
                    {
                        PreparedStatement getAccountTypeQuery = Connector.getConnection().prepareStatement("Select account_type from account_type where id_account = '" + idUser + "';");
                        ResultSet resultAccountTypeQuery = getAccountTypeQuery.executeQuery();
                        if (resultAccountTypeQuery.next()) {
                            accountType = resultAccountTypeQuery.getString(1);

                            if (accountType.contains("patient"))
                            {
                                //Patient patientInstance = new Patient(databaseConnectionInstance, getInsertedLogin(), idUser);
                                /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Zalogowano poprawnie!");
                                alert.setHeaderText("Typ konta - pacjent");
                                alert.setContentText("Nazwa użytkownika - "+login);

                                alert.showAndWait();*/

                                try
                                {
                                    ((Node)actionEvent.getSource()).getScene().getWindow().hide();
                                    Stage stage = new Stage();
                                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Views/PatientView.fxml"));
                                    Parent sceneMain = loader.load();
                                    PatientController controller = loader.<PatientController>getController();
                                    controller.initVariables(login, idUser);

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
                            else if (accountType.contains("doctor"))
                            {
                                try
                                {
                                    ((Node)actionEvent.getSource()).getScene().getWindow().hide();
                                    Stage stage = new Stage();
                                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Views/DoctorView.fxml"));
                                    Parent sceneMain = loader.load();
                                    DoctorController controller = loader.<DoctorController>getController();
                                    controller.initVariables(login, idUser);

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
                            else if (accountType.contains("admin"))
                            {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Logowanie nie powiodło się!");
                                alert.setHeaderText("Twoje konto to konto administracyjne");
                                alert.setContentText("Aby zalogować się na konto administracyjne, użyj do tego swojego panelu administracyjnego, do którego odnośnik znajduje się na dole formularza logowania.");

                                alert.showAndWait();
                            }
                        }
                    }
                    else
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Logowanie nie powiodło się!");
                        alert.setHeaderText("Twoje konto nie jest aktywne!");
                        alert.setContentText("Skontaktuj się z administratorem aplikacji.");

                        alert.showAndWait();
                    }
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Logowanie nie powiodło się!");
                    alert.setHeaderText("Nie znaleziono takiego użytkownika");
                    alert.setContentText("Sprawdź poprawność wprowadzonych danych i spróbuj ponownie.");

                    alert.showAndWait();
                }
            }
            catch (SQLException e)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Logowanie nie powiodło się!");
                alert.setHeaderText("Błąd połączenia z bazą danych.");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                //e.printStackTrace();
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pola danych są puste!");
            alert.setHeaderText("Nie podałeś żadnych danych!");
            alert.setContentText("Wprowadź poprawne dane i spróbuj ponownie.");

            alert.showAndWait();
        }
    }

    public void closeApp(ActionEvent actionEvent)
    {
        System.exit(0);
    }
}
