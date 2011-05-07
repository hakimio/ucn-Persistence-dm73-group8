package westernstyle.core;

public class Purchase
{
    private int id;
    private Product product;
    private SalesOrder salesOrder;
    
    public Purchase(int id)
    {
        this.id = id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public void setSalesOrder(SalesOrder salesOrder)
    {
        this.salesOrder = salesOrder;
    }

    public int getId()
    {
        return id;
    }

    public Product getProduct()
    {
        return product;
    }

    public SalesOrder getSalesOrder()
    {
        return salesOrder;
    }
}
