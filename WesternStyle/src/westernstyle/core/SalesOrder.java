package westernstyle.core;

import java.util.Date;

public class SalesOrder
{
    private String deliveryStatus;
    private Date date, deliveryDate;
    private int amount, id;
    
    public SalesOrder(int id)
    {
        this.id = id;
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
