package Models;

import ConnectionPackage.Connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicineListModel
{
    public List<MedicineModel> medicineList = new ArrayList<MedicineModel>();
    private int listLenght;


    public MedicineListModel()
    {
        getMedicineList();
        listLenght = medicineList.size();
    }


    void getMedicineList()
    {
        String selectDoctors = "SELECT  * FROM medicines;";
        try
        {
            PreparedStatement selectMedicines = Connector.getConnection().prepareStatement(selectDoctors);
            ResultSet result = selectMedicines.executeQuery();
            while (result.next())
            {
                int id = result.getInt(1);
                String name = result.getString(2);
                double price = result.getDouble(3);
                String capacity = result.getString(4);

                medicineList.add(new MedicineModel(id,name,price,capacity));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public int getListLenght()
    {
        return listLenght;
    }

    public String getMedicine(int id)
    {
        return medicineList.get(id).toString();
    }

    public double getMedicinePrice(int id)
    {
        return medicineList.get(id).getPrice();
    }
}
