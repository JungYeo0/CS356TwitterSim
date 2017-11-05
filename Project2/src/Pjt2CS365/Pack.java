package Pjt2CS365;

public abstract class Pack {
    private String id;
    protected String getId(){return id;}
    protected void setId(String name){id=name;}

    public abstract void accept(Visitor v);         //visitor check
    public abstract String readAll(int tab);
    public abstract User isUser(String name);
    public UserGroup isGroup(String name){
        return null;
    }
}
