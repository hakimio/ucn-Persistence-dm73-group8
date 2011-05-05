package westernstyle.core;

public class Equipment extends Product
{
    private String type, description;

    public Equipment(int id)
    {
        super(id);
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getDescription()
    {
        return description;
    }

    public String getType()
    {
        return type;
    }
    
}
