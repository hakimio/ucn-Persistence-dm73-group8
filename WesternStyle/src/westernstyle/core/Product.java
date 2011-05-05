package westernstyle.core;

public class Product
{
    private String name, countryOfOrigin;
    private double purchasePrice, salesPrice, rentPrice;
    private int minStock;
    private  int id;
    
    public Product(int id)
    {
        this.id = id;
    }

    public void setCountryOfOrigin(String countryOfOrigin)
    {
        this.countryOfOrigin = countryOfOrigin;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setMinStock(int minStock)
    {
        this.minStock = minStock;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPurchasePrice(double purchasePrice)
    {
        this.purchasePrice = purchasePrice;
    }

    public void setRentPrice(double rentPrice)
    {
        this.rentPrice = rentPrice;
    }

    public void setSalesPrice(double salesPrice)
    {
        this.salesPrice = salesPrice;
    }

    public String getCountryOfOrigin()
    {
        return countryOfOrigin;
    }

    public int getId()
    {
        return id;
    }

    public int getMinStock()
    {
        return minStock;
    }

    public String getName()
    {
        return name;
    }

    public double getPurchasePrice()
    {
        return purchasePrice;
    }

    public double getRentPrice()
    {
        return rentPrice;
    }

    public double getSalesPrice()
    {
        return salesPrice;
    }
    
}
