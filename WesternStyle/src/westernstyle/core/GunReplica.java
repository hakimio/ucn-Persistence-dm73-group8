package westernstyle.core;

public class GunReplica extends Product
{
    private String fabric, calibre;
    
    public GunReplica(int id)
    {
        super(id);
    }

    public String getCalibre()
    {
        return calibre;
    }

    public String getFabric()
    {
        return fabric;
    }

    public void setCalibre(String calibre)
    {
        this.calibre = calibre;
    }

    public void setFabric(String fabric)
    {
        this.fabric = fabric;
    }
    
}
