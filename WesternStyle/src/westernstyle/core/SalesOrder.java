package westernstyle.core;

import java.util.Date;

public class SalesOrder
{
    private String deliveryStatus;
    private Date date, deliveryDate;
    private int amount, id;
    private Customer customer;
    private Invoice invoice;
    
    public SalesOrder(int id)
    {
        this.id = id;
        customer = null;
        invoice = null;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    public void setInvoice(Invoice invoice)
    {
        this.invoice = invoice;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public Invoice getInvoice()
    {
        return invoice;
    }

    public int getAmount()
    {
        return amount;
    }

    public Date getDate()
    {
        return date;
    }

    public Date getDeliveryDate()
    {
        return deliveryDate;
    }

    public String getDeliveryStatus()
    {
        return deliveryStatus;
    }

    public int getId()
    {
        return id;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public void setDeliveryDate(Date deliveryDate)
    {
        this.deliveryDate = deliveryDate;
    }

    public void setDeliveryStatus(String deliveryStatus)
    {
        this.deliveryStatus = deliveryStatus;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
