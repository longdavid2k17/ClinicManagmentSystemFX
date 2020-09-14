package Models;

import java.util.Date;

public class SimpleRecipt
{
    private String doctor;
    private Date date;
    private String description;
    private String medicine_name;

    public SimpleRecipt(String doctor, Date date, String description, String medicine_name)
    {
        setDoctor(doctor);
        setDate(date);
        setDescription(description);
        setMedicine_name(medicine_name);
    }

    public String getDoctor()
    {
        return doctor;
    }

    public void setDoctor(String doctor)
    {
        this.doctor = doctor;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getMedicine_name()
    {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name)
    {
        this.medicine_name = medicine_name;
    }
}
