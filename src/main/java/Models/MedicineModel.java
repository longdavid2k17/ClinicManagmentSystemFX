package Models;

public class MedicineModel
{
    private int id;
    private String name;
    private double price;
    private String capacity;

    public MedicineModel(int id,String name, double price, String capacity)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.capacity = capacity;
    }

    public String getName()
    {
        return name;
    }

    public double getPrice()
    {
        return price;
    }

    public String getCapacity()
    {
        return capacity;
    }

    public int getId()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return getName()+" "+getCapacity();
    }
}
