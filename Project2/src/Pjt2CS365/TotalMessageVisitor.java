package Pjt2CS365;


public class TotalMessageVisitor implements Visitor{
    private int totalMsg, totalPositiveMsg;
    public TotalMessageVisitor(){
        totalMsg=0;
        totalPositiveMsg=0;
    }

    public int getTotalMsg(){return totalMsg;}
    public double getTotalPositiveMsg(){return totalPositiveMsg==0 ?0:((1.0 * totalPositiveMsg)/totalMsg) *100.0;}

    private boolean goodVibrations(User u){
        String lastMsg = u.getMyMessages().get(u.getMyMessages().size()-1);
        System.out.println(lastMsg);
        return lastMsg.contains("cool")|| lastMsg.contains("great")|| lastMsg.contains("sup");
    }
    @Override
    public void atUser(User u) {
        totalMsg += u.getFollowers().size() + 1;
        if(goodVibrations(u))
            totalPositiveMsg += u.getFollowers().size() + 1;
    }

    @Override
    public void atGroup(UserGroup g) {

    }
}
