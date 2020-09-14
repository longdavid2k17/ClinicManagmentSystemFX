package Controllers;

import ConnectionPackage.Connector;
import Models.PatientModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientBaseController
{
    private List<PatientModel> patientList;
    private List<String> citiesList;

    @FXML
    private TableView table;

    @FXML
    private TableColumn idColumn;

    @FXML
    private TableColumn loginColumn;

    @FXML
    private TableColumn nameColumn;

    @FXML
    private TableColumn surnameColumn;

    @FXML
    private TableColumn sexColumn;

    @FXML
    private TableColumn peselColumn;

    @FXML
    private TableColumn cityColumn;

    @FXML
    private TableColumn phoneNumberColumn;

    @FXML
    private TextField nameField;

    @FXML
    private ChoiceBox cityBox;

    public void init()
    {
        patientList = new ArrayList<>();
        citiesList = new ArrayList<>();
        getPatients();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        sexColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));
        peselColumn.setCellValueFactory(new PropertyValueFactory<>("peselNumber"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        table.setRowFactory( tv -> {
            TableRow<PatientModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) )
                {
                    PatientModel rowData = row.getItem();
                    try
                    {
                        Stage stage = new Stage();
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Views/PatientPreviewView.fxml"));
                        Parent sceneMain = loader.load();
                        PatientPreviewController controller = loader.<PatientPreviewController>getController();
                        controller.init(rowData);

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
            });
            return row ;
        });

        for(int i=0;i<getPatientList().size();i++)
        {
            table.getItems().add(getPatientList().get(i));
        }

        for(int i=0;i<getCitiesList().size();i++)
        {
            cityBox.getItems().add(getCitiesList().get(i));
        }
    }

    public List<PatientModel> getPatientList()
    {
        return patientList;
    }

    public void clearLists()
    {
        table.getItems().clear();
        citiesList.clear();
        getPatientList().clear();
    }

    public void clearFilters(ActionEvent actionEvent)
    {
        nameField.clear();
        cityBox.getSelectionModel().clearSelection();
        cityBox.getItems().clear();
        init();
    }

    public void closeFrame(ActionEvent actionEvent)
    {
        ((Node)actionEvent.getSource()).getScene().getWindow().hide();
    }

    public void search(ActionEvent actionEvent)
    {
        if(nameField.getText().trim().isEmpty() && cityBox.getSelectionModel().getSelectedItem()==null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd!");
            alert.setHeaderText("Nie ustawiono żadnych filtrów");
            alert.setContentText("Wybierz jakikolwiek filtr i spróbuj ponownie");
            alert.showAndWait();
        }
        else
        {
            clearLists();
            String fullName = "";
            String patientName = "";
            String patientSurname = "";
            String city = "";
            String query = "";
            boolean isNameSet = false;
            boolean isCitySet = false;
            if(!nameField.getText().trim().isEmpty())
            {
                System.out.println(nameField.getText());
                fullName = nameField.getText();
                isNameSet = true;
                String[] patientNameParts = fullName.split(" ");
                patientName = patientNameParts[0];
                patientSurname = patientNameParts[1];
            }
            if(cityBox.getValue()!=null)
            {
                System.out.println(cityBox.getValue());
                city = cityBox.getSelectionModel().getSelectedItem().toString();
                isCitySet = true;
            }
            if(isNameSet&&isCitySet)
            {
                query = "SELECT users.id_user, users.login, private_data.name,private_data.surname,private_data.pesel_number,private_data.phone_number, private_data.address_street,private_data.address_zipcode,private_data.address_city, " +
                        " patients_dim.growth, patients_dim.weight, patients_dim.sex FROM users INNER JOIN account_type ON users.id_user = account_type.id_account INNER JOIN private_data ON users.id_user = private_data.user_id INNER JOIN patients_dim ON users.id_user = patients_dim.user_id " +
                        "WHERE account_type.account_type = 'patient' AND private_data.address_city='"+city+"' AND private_data.name='"+patientName+"' AND private_data.surname='"+patientSurname+"';";
            }
            else if(isNameSet&&!isCitySet)
            {
                query = "SELECT users.id_user, users.login, private_data.name,private_data.surname,private_data.pesel_number,private_data.phone_number, private_data.address_street,private_data.address_zipcode,private_data.address_city, " +
                        " patients_dim.growth, patients_dim.weight, patients_dim.sex FROM users INNER JOIN account_type ON users.id_user = account_type.id_account INNER JOIN private_data ON users.id_user = private_data.user_id INNER JOIN patients_dim ON users.id_user = patients_dim.user_id " +
                        "WHERE account_type.account_type = 'patient' AND private_data.name='"+patientName+"' AND private_data.surname='"+patientSurname+"';";
            }
            else if(!isNameSet&&isCitySet)
            {
                query = "SELECT users.id_user, users.login, private_data.name,private_data.surname,private_data.pesel_number,private_data.phone_number, private_data.address_street,private_data.address_zipcode,private_data.address_city, " +
                        " patients_dim.growth, patients_dim.weight, patients_dim.sex FROM users INNER JOIN account_type ON users.id_user = account_type.id_account INNER JOIN private_data ON users.id_user = private_data.user_id INNER JOIN patients_dim ON users.id_user = patients_dim.user_id " +
                        "WHERE account_type.account_type = 'patient' AND private_data.address_city='"+city+"';";
            }

            try
            {
                PreparedStatement selectFilteredPatients = Connector.getConnection().prepareStatement(query);
                ResultSet resultFilteredPatients = selectFilteredPatients.executeQuery();
                while (resultFilteredPatients.next())
                {
                    int id = resultFilteredPatients.getInt(1);
                    String login = resultFilteredPatients.getString(2);
                    String name = resultFilteredPatients.getString(3);
                    String surname = resultFilteredPatients.getString(4);
                    String pesel_nmbr = resultFilteredPatients.getString(5);
                    String phone_nmbr = resultFilteredPatients.getString(6);
                    String address_street = resultFilteredPatients.getString(7);
                    String address_zipcode = resultFilteredPatients.getString(8);
                    String address_city = resultFilteredPatients.getString(9);
                    String growth = resultFilteredPatients.getString(10);
                    String weight = resultFilteredPatients.getString(11);
                    String sex = resultFilteredPatients.getString(12);
                    patientList.add(new PatientModel(id,login,name,surname,pesel_nmbr,phone_nmbr,address_street,address_zipcode,address_city,growth,weight,sex));
                    if(getCitiesList().size()>0)
                    {
                        boolean hasList=true;

                        for(int i=0;i<getCitiesList().size();i++)
                        {
                            if(getCitiesList().get(i).equals(address_city))
                            {
                                hasList=true;
                            }
                            else
                            {
                                hasList=false;
                            }
                        }
                        if(!hasList)
                        {
                            getCitiesList().add(address_city);
                        }
                    }
                    else
                        getCitiesList().add(address_city);
                }
                for(int i=0;i<getPatientList().size();i++)
                {
                    table.getItems().add(getPatientList().get(i));
                }
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    public List<String> getCitiesList()
    {
        return citiesList;
    }

    public void getPatients()
    {
        clearLists();
        String patientsQuery = "SELECT users.id_user, users.login, private_data.name,private_data.surname,private_data.pesel_number,private_data.phone_number, private_data.address_street,private_data.address_zipcode,private_data.address_city, " +
                " patients_dim.growth, patients_dim.weight, patients_dim.sex FROM users INNER JOIN account_type ON users.id_user = account_type.id_account INNER JOIN private_data ON users.id_user = private_data.user_id INNER JOIN patients_dim ON users.id_user = patients_dim.user_id " +
                "WHERE account_type.account_type = 'patient' ;";
        try
        {
            PreparedStatement selectPatients = Connector.getConnection().prepareStatement(patientsQuery);
            ResultSet resultPatients = selectPatients.executeQuery();
            while (resultPatients.next())
            {
                int id = resultPatients.getInt(1);
                String login = resultPatients.getString(2);
                String name = resultPatients.getString(3);
                String surname = resultPatients.getString(4);
                String pesel_nmbr = resultPatients.getString(5);
                String phone_nmbr = resultPatients.getString(6);
                String address_street = resultPatients.getString(7);
                String address_zipcode = resultPatients.getString(8);
                String address_city = resultPatients.getString(9);
                String growth = resultPatients.getString(10);
                String weight = resultPatients.getString(11);
                String sex = resultPatients.getString(12);
                patientList.add(new PatientModel(id,login,name,surname,pesel_nmbr,phone_nmbr,address_street,address_zipcode,address_city,growth,weight,sex));
                if(getCitiesList().size()>0)
                {
                    boolean hasList=true;

                    for(int i=0;i<getCitiesList().size();i++)
                    {
                        if(getCitiesList().get(i).equals(address_city))
                        {
                            hasList=true;
                        }
                        else
                        {
                            hasList=false;
                        }
                    }
                    if(!hasList)
                    {
                        getCitiesList().add(address_city);
                    }
                }
                else
                    getCitiesList().add(address_city);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
