package Pjt2CS365;

import java.util.ArrayList;
import java.util.List;

public class UserGroup extends Pack{
    private List<Pack> membership;          //composit check
    public UserGroup(String name){
        setId(name);
        membership = new ArrayList<>();
    }

    //settters and getters
    public boolean addGroup(UserGroup g){
        boolean addMe =isUniqueGroup(g.getId());
        if(addMe)
            membership.add(g);
        return addMe;
    }


    public void gen(){
        User a,b,c,d,e,f,g,h,i,j,k;
        UserGroup w,x,y,z;
        a = new User("a");
        b = new User("b");
        c = new User("c");
        d = new User("d");
        e = new User("e");
        f = new User("f");
        g = new User("g");
        h = new User("h");
        i = new User("i");
        j = new User("j");
        k = new User("k");
        w = new UserGroup("w");
        x = new UserGroup("x");
        y = new UserGroup("y");
        z = new UserGroup("z");
        this.membership.add(w);
        w.membership.add(a);w.membership.add(a);w.membership.add(a);
        w.membership.add(b);w.membership.add(c);w.membership.add(x);w.membership.add(d);

        x.membership.add(e);x.membership.add(e);x.membership.add(y);
        x.membership.add(z);
        z.membership.add(f);
        z.membership.add(g);
        z.membership.add(h);
        z.membership.add(i);
        z.membership.add(j);
        z.membership.add(k);
    }
    private List<Pack> getMembership(){return membership;}
    public User findUser(String name) {
        for(Pack p : membership){
            User checker = p.isUser(name);
            if(checker != null)
                return checker;
        }
        return null;
    }
    public boolean userJoinGroup(String name, User member){
        boolean addMe =isUniqueUser(member.getId());
        if(addMe){
            UserGroup group = isGroup(name);
            if(group==null)
                this.membership.add(member);
            else
                group.membership.add(member);
        }
        return addMe;
    }
    public boolean groupJoinGroup(String name, UserGroup group){
        boolean addMe =isUniqueGroup(group.getId());
        if(addMe){
            UserGroup parent = isGroup(name);
            if(parent==null)
                membership.add(group);
            else
                parent.membership.add(group);
        }
        return addMe;
    }


    private boolean isUniqueUser(String name){
        User u = findUser(name);
        if(u ==null)
            return true;
        return false;
    }
    private boolean isUniqueGroup(String name){
        UserGroup g = isGroup(name);
        if(g == null)
            return true;
        return false;
    }
    @Override
    public UserGroup isGroup(String name) {
        if(this.getId().equals(name))
            return this;
        for(Pack p : membership){
            UserGroup g = p.isGroup(name);
            if(g != null)
                return g;
        }
        return null;
    }
    @Override
    public User isUser(String name) {
        return this.findUser(name);
    }
    @Override
    public String readAll(int tab) {
        String output ="";
        for(int i=0; i<tab; i++) {
            output += "  ";
        }
        output += "[Group]-- " + this.getId() + "\n";
        for( Pack u : membership){
            output+= u.readAll(tab+1);
        }
        return output;
    }
    @Override
    public void accept(Visitor v) {
        v.atGroup(this);
    }


}

