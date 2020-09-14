package Controllers;

import ConnectionPackage.Connector;
import Models.Illness;
import Models.IllnessClassification;
import Models.MedicineModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IllnessBaseController
{
    private List<Illness> illnessList;
    int chosenCategoryIndex;
    String[] alphabet = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    @FXML
    private ChoiceBox classificationBox;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private ComboBox symptomsBox;

    @FXML
    private TextField nameField;

    @FXML
    private TableView table;

    @FXML
    private TableColumn idColumn;

    @FXML
    private TableColumn classificationColumn;

    @FXML
    private TableColumn nameColumn;

    @FXML
    private TableColumn symptomsColumn;

    @FXML
    private TableColumn treatmentColumn;


    public List<Illness> getIllnessList()
    {
        return illnessList;
    }

    public void loadList()
    {
        String query = "SELECT * from illnesses";
        try
        {
            PreparedStatement selectIllnesses = Connector.getConnection().prepareStatement(query);
            ResultSet result = selectIllnesses.executeQuery();
            while(result.next())
            {
                int id = result.getInt(1);
                String categorySymbol = result.getString(2);
                String name = result.getString(3);
                String symptoms = result.getString(4);
                String treatment = result.getString(5);
                getIllnessList().add(new Illness(id,categorySymbol,name,symptoms,treatment));
            }

        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage()+"\n"+e.getErrorCode()+"\n"+e.getCause());
        }
    }

    public void search()
    {
        if(classificationBox.getValue()==null && symptomsBox.getValue()==null && nameField.getText().trim()==null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd!");
            alert.setHeaderText("Nie ustawiono żadnych filtrów");
            alert.setContentText("Wybierz jakikolwiek filtr i spróbuj ponownie");
            alert.showAndWait();
        }
        else
        {
            boolean isClassificationSet = false;
            boolean isSymptomsSelected = false;
            boolean isNameSet = false;
            String classyfication = "";
            String selectedSymptom = "";
            String illnessName = "";
            String query = "";

            if(!nameField.getText().trim().isEmpty())
            {
                isNameSet = true;
                illnessName = nameField.getText();
            }
            else
            {
                isNameSet = false;
            }

            if(symptomsBox.getValue() !=null)
            {
                isSymptomsSelected = true;
                selectedSymptom = String.valueOf(symptomsBox.getValue());
            }
            else
            {
                isSymptomsSelected = false;
            }

            if(!(classificationBox.getValue() ==null))
            {
                isClassificationSet = true;
                classyfication = String.valueOf(classificationBox.getValue());
            }
            else
            {
                isClassificationSet = false;
            }

            System.out.println("Odczytane wartości\nNazwa:"+illnessName+"\nObjawy: "+selectedSymptom+"\nKlasyfikacja: "+classyfication);

            if(isNameSet && isClassificationSet && isSymptomsSelected)
            {
                query = "SELECT * FROM illnesses WHERE categorySymbol ='"+classyfication+"' AND name='"+illnessName+"' AND symptoms LIKE '%"+selectedSymptom+"%';";
            }
            else if(isNameSet && isClassificationSet && !isSymptomsSelected)
            {
                query = "SELECT * FROM illnesses WHERE categorySymbol ='"+classyfication+"' AND name = '"+illnessName+"';";
            }
            else if(isNameSet && !isClassificationSet && isSymptomsSelected)
            {
                query = "SELECT * FROM illnesses WHERE name='"+illnessName+"' AND symptoms LIKE '%"+selectedSymptom+"%';";
            }
            else if(!isNameSet && isClassificationSet && isSymptomsSelected)
            {
                query = "SELECT * FROM illnesses WHERE categorySymbol ='"+classyfication+"' AND symptoms LIKE '%"+selectedSymptom+"%';";
            }
            else if(!isNameSet && !isClassificationSet && isSymptomsSelected)
            {
                query = "SELECT * FROM illnesses WHERE symptoms LIKE '%"+selectedSymptom+"%';";
            }
            else if(isNameSet && !isClassificationSet && !isSymptomsSelected)
            {
                query = "SELECT * FROM illnesses WHERE name='"+illnessName+"';";
            }
            else if(!isNameSet && isClassificationSet && !isSymptomsSelected)
            {
                query = "SELECT * FROM illnesses WHERE categorySymbol ='"+classyfication+"';";
            }

            System.out.println(query);
            clearLists();

            try
            {
                PreparedStatement selectFilteredIlness = Connector.getConnection().prepareStatement(query);
                ResultSet resultFilteredIllness = selectFilteredIlness.executeQuery();
                while(resultFilteredIllness.next())
                {
                    int id = resultFilteredIllness.getInt(1);
                    String categorySymbol = resultFilteredIllness.getString(2);
                    String name = resultFilteredIllness.getString(3);
                    String symptoms = resultFilteredIllness.getString(4);
                    String treatment = resultFilteredIllness.getString(5);
                    illnessList.add(new Illness(id,categorySymbol,name,symptoms,treatment));
                }
                System.out.println("Ilość końcowa nowej listy "+illnessList.size());
                for(int i=0;i<illnessList.size();i++)
                {
                    table.getItems().add(illnessList.get(i));
                }
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage()+"\n"+e.getErrorCode()+"\n"+e.getCause());
            }
        }
    }

    public void clearLists()
    {
        table.getItems().clear();
        illnessList.clear();
    }

    public void clearFilters()
    {
        classificationBox.getSelectionModel().clearSelection();
        descriptionArea.clear();
        symptomsBox.getSelectionModel().clearSelection();
        nameField.clear();
        clearLists();
        init();
    }

    public void init()
    {
        illnessList = new ArrayList<>();
        loadList();
        setValueForClassificationBox();


        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        classificationColumn.setCellValueFactory(new PropertyValueFactory<>("illnessClassification"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        symptomsColumn.setCellValueFactory(new PropertyValueFactory<>("symptoms"));
        treatmentColumn.setCellValueFactory(new PropertyValueFactory<>("treatment"));

        for(int i=0; i<getIllnessList().size();i++)
        {
            table.getItems().add(getIllnessList().get(i));
        }

        classificationBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                if(t1.intValue()==-1)
                {
                    descriptionArea.clear();
                    chosenCategoryIndex=0;
                }
                else
                {
                    chosenCategoryIndex=t1.intValue();
                    descriptionArea.setText(new IllnessClassification(alphabet[t1.intValue()]).getDescription());
                }

            }
        });

        symptomsBox.getItems().addAll("biegunka","wodnisty stolec","ogólne osłabienie","gorączka","katar","ból brzucha","wymioty","ból głowy","zaparcia","opuchlizna","zawroty głowy", "nieżyt nosa", "zapalenie spojówek", "kaszel", "zapalenie krtani"
                , "zapalenie gardła", "senność", "apatia", "przyspieszone tętno");
    }

    public void closeFrame(ActionEvent actionEvent)
    {
        ((Node)actionEvent.getSource()).getScene().getWindow().hide();
    }

    void setValueForClassificationBox()
    {
        for(int i=0;i<26;i++)
        {
            classificationBox.getItems().add(alphabet[i]);
        }
    }
}
