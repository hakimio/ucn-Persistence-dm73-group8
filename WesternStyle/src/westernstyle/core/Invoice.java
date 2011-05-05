package westernstyle.core;

import java.util.Date;

public class Invoice
{
    private int invoiceNo, amount, id;
    private Date paymentDate;
    
    public Invoice(int id)
    {
        this.id = id;
    }

    public int getAmount()
    {
        return amount;
    }

    public int getId()
    {
        return id;
    }

    public int getInvoiceNo()
    {
        return invoiceNo;
    }

    public Date getPaymentDate()
    {
        return paymentDate;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setInvoiceNo(int invoiceNo)
    {
        this.invoiceNo = invoiceNo;
    }

    public void setPaymentDate(Date paymentDate)
    {
        this.paymentDate = paymentDate;
    }
    
    
}
