package Controllers;

import ConnectionPackage.Connector;
import Models.MedicineListModel;
import Models.MedicineModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MedicinesBaseController
{
    private MedicineListModel medicineList;

    @FXML
    private TextField priceFromField;

    @FXML
    private TextField priceToField;

    @FXML
    private TableView table;

    @FXML
    private TableColumn idColumn;

    @FXML
    private TableColumn nameColumn;

    @FXML
    private TableColumn capacityColumn;

    @FXML
    private TableColumn priceColumn;

    public void init()
    {
        medicineList = new MedicineListModel();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        for(int i=0;i<medicineList.getListLenght();i++)
        {
            table.getItems().add(medicineList.getList().get(i));
        }
    }

    public void closeFrame(ActionEvent actionEvent)
    {
        ((Node)actionEvent.getSource()).getScene().getWindow().hide();
    }

    public void search()
    {
        if(priceToField.getText().trim().isEmpty() && priceFromField.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd!");
            alert.setHeaderText("Nie ustawiono żadnych filtrów");
            alert.setContentText("Wybierz jakikolwiek filtr i spróbuj ponownie");
            alert.showAndWait();
        }
        else
        {
            boolean isSetFrom = false;
            boolean isSetTo = false;
            String fromValue = "";
            String toValue = "";
            String query = "";

            if(!priceFromField.getText().trim().isEmpty())
            {
                isSetFrom = true;
                fromValue = priceFromField.getText();
            }
            else
            {
                isSetFrom = false;
            }

            if(!priceToField.getText().trim().isEmpty())
            {
                isSetTo = true;
                toValue = priceToField.getText();
            }
            else
            {
                isSetTo = false;
            }

            System.out.println("Wartość od: "+fromValue+"\nWartość do: "+toValue);

            if(isSetFrom && isSetTo==false)
            {
                query = "SELECT * FROM medicines WHERE price > "+fromValue+";";
            }
            else if(isSetFrom==false && isSetTo)
            {
                query = "SELECT * FROM medicines WHERE price < "+toValue+";";
            }
            else if(isSetFrom && isSetTo)
            {
                query = "SELECT * FROM medicines WHERE price > "+fromValue+" AND price < "+toValue+";";
            }

            clearLists();

            try
            {
                PreparedStatement selectFilteredVisits = Connector.getConnection().prepareStatement(query);
                ResultSet resultFilteredMedicines = selectFilteredVisits.executeQuery();
                while(resultFilteredMedicines.next())
                {
                    int id = resultFilteredMedicines.getInt(1);
                    String name = resultFilteredMedicines.getString(2);
                    double price = resultFilteredMedicines.getDouble(3);
                    String capacity = resultFilteredMedicines.getString(4);
                    medicineList.getList().add(new MedicineModel(id,name,price,capacity));
                }
                System.out.println("Ilość końcowa nowej listy "+medicineList.getList().size());
                for(int i=0;i<medicineList.getList().size();i++)
                {
                    table.getItems().add(medicineList.getList().get(i));
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
        medicineList.getList().clear();
    }

    public void clearFilters()
    {
        priceToField.clear();
        priceFromField.clear();
        clearLists();
        init();
    }

}
