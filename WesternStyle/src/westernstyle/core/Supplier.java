package westernstyle.core;

public class Supplier
{
    private String name, address, country, phoneno, email;
    private int id;

    public Supplier(int id)
    {
        this.id = id;
    }

    public String getAddress()
    {
        return address;
    }

    public String getCountry()
    {
        return country;
    }

    public String getEmail()
    {
        return email;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getPhoneno()
    {
        return phoneno;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPhoneno(String phoneno)
    {
        this.phoneno = phoneno;
    }
}
