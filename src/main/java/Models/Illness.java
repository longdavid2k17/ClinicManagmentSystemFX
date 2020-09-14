package Models;

public class Illness
{
    private int id;
    private IllnessClassification illnessClassification;
    private String name;
    private String symptoms;
    private String treatment;

    public Illness(int id, String illnessClassificationSymbol, String name, String symptoms, String treatment)
    {
        setId(id);
        setIllnessClassification(illnessClassificationSymbol);
        setName(name);
        setSymptoms(symptoms);
        setTreatment(treatment);
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public IllnessClassification getIllnessClassification()
    {
        return illnessClassification;
    }

    public void setIllnessClassification(String illnessClassificationSymbol)
    {
        this.illnessClassification = new IllnessClassification(illnessClassificationSymbol);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSymptoms()
    {
        return symptoms;
    }

    public void setSymptoms(String symptoms)
    {
        this.symptoms = symptoms;
    }

    public String getTreatment()
    {
        return treatment;
    }

    public void setTreatment(String treatment)
    {
        this.treatment = treatment;
    }
}
