package Pjt2CS365;

public class TotalMemberVisitor implements Visitor {
    private int totalUser, totalGroup;
    public TotalMemberVisitor(){
        totalGroup=1;
        totalUser=0;
    }

    public int getTotalUser(){return totalUser;}
    public int getTotalGroup(){return totalGroup;}

    @Override
    public void atUser(User u) {
        totalUser++;
    }

    @Override
    public void atGroup(UserGroup g) {
        totalGroup++;
    }
}
