package Pjt2CS365;

public abstract class Pack {
    private String id;
    private long creationTime;
    private long lastUpdateTime;

    protected Pack(){
        creationTime = System.currentTimeMillis();
        lastUpdateTime = -1;
    }
    protected String getId(){return id;}
    protected void setId(String name){id=name;}

    public long getCreationTime(){return creationTime;}
    public abstract void accept(Visitor v);         //visitor check
    public abstract String readAll(int tab);
    public abstract User isUser(String name);
    public UserGroup isGroup(String name){
        return null;
    }
    public long getLastUpdateTime(){return lastUpdateTime;}
    public void setLastUpdateTime(long updateTime){lastUpdateTime = updateTime;}
    public abstract Pack lastUpdated();
}
