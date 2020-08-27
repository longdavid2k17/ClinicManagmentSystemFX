package Models;

import java.util.Date;

public class SimpleVisit
{
    private String patient;
    private Date visitDate;
    private String description;

    public String getPatient()
    {
        return patient;
    }

    public void setPatient(String patient)
    {
        this.patient = patient;
    }

    public Date getVisitDate()
    {
        return visitDate;
    }

    public void setVisitDate(Date visitDate)
    {
        this.visitDate = visitDate;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public SimpleVisit(String patientName, Date visitDate, String description)
    {
        setPatient(patientName);
        setVisitDate(visitDate);
        setDescription(description);
    }

    @Override
    public String toString()
    {
        return getPatient()+" "+getVisitDate()+" "+getDescription();
    }
}
