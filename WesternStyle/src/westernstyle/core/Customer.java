package westernstyle.core;

public class Customer
{
    private String name, address, city, phoneno;
    private int id, orderZipCode;
    
    public Customer(int id)
    {
        this.id = id;
    }

    public String getAddress()
    {
        return address;
    }

    public String getCity()
    {
        return city;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public int getOrderZipCode()
    {
        return orderZipCode;
    }

    public String getPhoneno()
    {
        return phoneno;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setOrderZipCode(int orderZipCode)
    {
        this.orderZipCode = orderZipCode;
    }

    public void setPhoneno(String phoneno)
    {
        this.phoneno = phoneno;
    }
}
