package westernstyle.core;

public class Clothing extends Product
{
    private int size;
    private String colour;
    
    public Clothing(int id)
    {
        super(id);
    }

    public String getColour()
    {
        return colour;
    }

    public int getSize()
    {
        return size;
    }

    public void setColour(String colour)
    {
        this.colour = colour;
    }

    public void setSize(int size)
    {
        this.size = size;
    }
    
    
}
