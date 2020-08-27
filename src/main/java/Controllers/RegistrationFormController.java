package Controllers;

import ConnectionPackage.Connector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class RegistrationFormController
{
    @FXML
    private ChoiceBox accountTypeBox;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField rptPasswordField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField peselField;

    @FXML
    private TextField phoneNbrField;

    @FXML
    private TextField streetField;

    @FXML
    private TextField zipCodeField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField growthField;

    @FXML
    private TextField weightField;

    @FXML
    private RadioButton maleRadio;

    @FXML
    private RadioButton femaleRadio;


    final ToggleGroup group = new ToggleGroup();

    @FXML
    public void initialize()
    {
        maleRadio.setToggleGroup(group);
        femaleRadio.setToggleGroup(group);

        if(!accountTypeBox.getItems().contains("pacjent"))
        {
            accountTypeBox.getItems().add("pacjent");
        }
        if(!accountTypeBox.getItems().contains("lekarz"))
        {
            accountTypeBox.getItems().add("lekarz");
        }
        if(!accountTypeBox.getItems().contains("administrator"))
        {
            accountTypeBox.getItems().add("administrator");
        }
    }

    public void checkInsertedData(ActionEvent actionEvent)
    {
        if(!maleRadio.isSelected()&&!femaleRadio.isSelected())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nie zaznaczono wszystkich pól!");
            alert.setHeaderText("Nie zaznaczono płci");
            alert.setContentText("Zaznacz swoją płeć i spróbuj ponownie");

            alert.showAndWait();
        }
        else
        {
            String login,password,confPassword,accountType,name,surname,peselNumber,phoneNmbr, street, zipCode,city,growth,weight,sex;
            boolean isCorrect = true;
            StringBuilder errorString = new StringBuilder("Formularz jest wypełniony niepoprawnie!\n");
            accountType = "patient";
            sex="male";
            login = loginField.getText();
            password = passwordField.getText();
            confPassword = rptPasswordField.getText();
            if((String)accountTypeBox.getValue()=="pacjent")
            {
                accountType = "patient";
            }
            else if((String)accountTypeBox.getValue()=="doktor")
            {
                accountType = "doctor";
            }
            else if((String)accountTypeBox.getValue()=="administrator")
            {
                accountType = "admin";
            }

            name = nameField.getText();

            surname = surnameField.getText();
            peselNumber = peselField.getText();
            phoneNmbr = phoneNbrField.getText();
            street = streetField.getText();
            zipCode = zipCodeField.getText();
            city = cityField.getText();
            growth = growthField.getText();
            weight = weightField.getText();

            if(name.length()<=2)
            {
                errorString.append("- Twoje imie jest za krótkie!\n");
                isCorrect=false;
            }
            if(password.length()<5)
            {
                errorString.append("- Twoje hasło jest za krótkie! Minimalna długość hasła to 5 znaków.\n");
                isCorrect=false;
            }
            if(password!=confPassword)
            {
                errorString.append("- Twoje hasła nie są takie same!\n");
                isCorrect=false;
            }
            if(surname.length()<=2)
            {
                errorString.append("- Twoje nazwisko jest za krótkie!\n");
                isCorrect=false;
            }
            if(peselNumber.length()!=11)
            {
                errorString.append("- Twój numer pesel ma niepoprawną długość!\n");
                isCorrect=false;
            }
            if(phoneNmbr.length()!=9)
            {
                errorString.append("- Twój numer telefonu ma niepoprawną długość!\n");
                isCorrect=false;
            }
            if(zipCode.length()!=6)
            {
                errorString.append("- Twój kod pocztowy ma niepoprawną długość!\n");
                isCorrect=false;
            }
            if(Integer.parseInt(growth)>240 || Integer.parseInt(growth)<60)
            {
                errorString.append("- Twój wzrost wydaje się nieprawdopodobny!\n");
                isCorrect=false;
            }
            if(Double.parseDouble(growth)>200 || Double.parseDouble(growth)<20)
            {
                errorString.append("- Twoja waga wydaje się nieprawdopodobna!\n");
                isCorrect=false;
            }

            if(maleRadio.isSelected())
            {
                sex="male";
            }
            else if(femaleRadio.isSelected())
            {
                sex="female";
            }


            if(isCorrect)
            {
                try
                {
                    PreparedStatement insertIntoUsersStatement = Connector.getConnection().prepareStatement("INSERT INTO users (login,password,is_active) VALUES ('"+login+"','"+password+"',0);" );

                    int resultQuery1 = insertIntoUsersStatement.executeUpdate();
                    if(resultQuery1==1)
                    {
                        PreparedStatement getIdQuery = Connector.getConnection().prepareStatement("Select id_user from users where login='"+login+"';");
                        ResultSet resultIdQuery = getIdQuery.executeQuery();
                        if(resultIdQuery.next())
                        {
                            int idUser = Integer.parseInt(resultIdQuery.getString(1));
                            PreparedStatement insertIntoAccountTypeStatement = Connector.getConnection().
                                    prepareStatement("INSERT INTO account_type (id_account,account_type) VALUES ("+idUser+",'"+accountType+"');" );

                            PreparedStatement insertIntoPrivateDataStatement = Connector.getConnection().prepareStatement("INSERT INTO private_data (user_id,name,surname,pesel_number,address_street," +
                                    "address_zipcode,address_city,phone_number) " +
                                    "VALUES ("+idUser+",'"+name+"','"+surname+"',"+peselNumber+",'"+street+"','"+zipCode+"','"+
                                    city+"','"+phoneNmbr+"');" );

                            int resultQuery2 = insertIntoAccountTypeStatement.executeUpdate();
                            int resultQuery3 = insertIntoPrivateDataStatement.executeUpdate();

                            if(accountType=="patient")
                            {
                                PreparedStatement insertIntoPatientsDim = Connector.getConnection().
                                        prepareStatement("INSERT INTO patients_dim (user_id,growth,weight,sex) VALUES ("+idUser+","+Integer.parseInt(growth)+","+Double.parseDouble(weight)+",'"+sex+"');" );
                                int resultQuery4 = insertIntoPatientsDim.executeUpdate();

                            }

                            if(resultQuery2==1 && resultQuery3==1)
                            {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Konto zostało założone!");
                                alert.setHeaderText("Operacja zakończyła się powodzeniem.");
                                alert.setContentText("Twoje konto zostało założone, ale musi zostać aktywowane.\nProsimy o cierpliwość i przelogowanie się do systemu za kilka minut!\n");
                                Optional<ButtonType> result = alert.showAndWait();
                                if(result.get()==ButtonType.OK)
                                {
                                    System.exit(0);
                                }
                            }
                        }

                    }
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Formularz jest niepoprawnie wypełniony!");
                alert.setHeaderText("Jedno lub kilka pól jest niepoprawnie wypełnionych.");
                alert.setContentText(errorString.toString());
                alert.showAndWait();
            }
        }

    }

    public void closeFrame(ActionEvent actionEvent)
    {
        ((Node)actionEvent.getSource()).getScene().getWindow().hide();
    }
}
