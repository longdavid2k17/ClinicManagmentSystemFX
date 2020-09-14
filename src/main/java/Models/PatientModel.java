package Models;

import ConnectionPackage.Connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientModel
{
    private int patient_id;
    private String login,name,surname,peselNumber,phoneNumber, street, zipCode,city,growth,weight,sex;

    public PatientModel(int patient_id) throws Exception
    {
        setPatient_id(patient_id);
        if(!getDataFromDatabase())
        {
            throw new Exception("Błąd podczas pobierania danych z bazy danych!");
        }
    }

    public PatientModel(int patient_id, String login, String name, String surname, String peselNumber, String phoneNumber, String street, String zipCode, String city, String growth, String weight, String sex)
    {
        setPatient_id(patient_id);
        setLogin(login);
        setName(name);
        setSurname(surname);
        setPeselNumber(peselNumber);
        setPhoneNumber(phoneNumber);
        setStreet(street);
        setZipCode(zipCode);
        setCity(city);
        setGrowth(growth);
        setWeight(weight);
        setSex(sex);
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getPeselNumber()
    {
        return peselNumber;
    }

    public void setPeselNumber(String peselNumber)
    {
        this.peselNumber = peselNumber;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getGrowth()
    {
        return growth;
    }

    public void setGrowth(String growth)
    {
        this.growth = growth;
    }

    public String getWeight()
    {
        return weight;
    }

    public void setWeight(String weight)
    {
        this.weight = weight;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex)
    {
        if(sex.equals("male"))
        {
            this.sex="Mężczyzna";
        }
        else if(sex.equals("female"))
        {
            this.sex="Kobieta";
        }
    }

    public int getPatient_id()
    {
        return patient_id;
    }

    public void setPatient_id(int patient_id)
    {
        this.patient_id = patient_id;
    }

    public boolean getDataFromDatabase()
    {
        String patientsQuery = "SELECT users.id_user, users.login, private_data.name,private_data.surname,private_data.pesel_number,private_data.phone_number, private_data.address_street,private_data.address_zipcode,private_data.address_city, " +
                " patients_dim.growth, patients_dim.weight, patients_dim.sex FROM users INNER JOIN account_type ON users.id_user = account_type.id_account INNER JOIN private_data ON users.id_user = private_data.user_id INNER JOIN patients_dim ON users.id_user = patients_dim.user_id " +
                "WHERE account_type.account_type = 'patient' AND users.id_user='"+getPatient_id()+"';";
        boolean dataSetted = false;
        try
        {
            PreparedStatement selectPatient = Connector.getConnection().prepareStatement(patientsQuery);
            ResultSet resultPatients = selectPatient.executeQuery();
            if (resultPatients.next())
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
                setLogin(login);
                setName(name);
                setSurname(surname);
                setPeselNumber(pesel_nmbr);
                setPhoneNumber(phone_nmbr);
                setStreet(address_street);
                setZipCode(address_zipcode);
                setCity(address_city);
                setGrowth(growth);
                setWeight(weight);
                setSex(sex);
                dataSetted = true;
            }
        }
        catch (SQLException e)
        {
            dataSetted=false;
            System.out.println(e.getMessage());
        }
        return dataSetted;
    }

    @Override
    public String toString() {
        return "PatientModel{" +
                "patient_id=" + patient_id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", peselNumber='" + peselNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", street='" + street + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", growth='" + growth + '\'' +
                ", weight='" + weight + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
