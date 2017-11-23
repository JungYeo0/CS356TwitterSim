package Pjt2CS365;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class User extends Pack {
    //member variables
    private List<Observer> followers;
    private List<User> following;
    private List<String> myMessages;
    private JTextArea followingDisplay, newsFeedDisplay;

    //constructor
    public User(String name){
        setId(name);
        followers = new ArrayList<>();
        following = new ArrayList<>();
        myMessages = new ArrayList<>();
        followingDisplay = new JTextArea(10,15);
        followingDisplay.setText("List View (Currently Following\n");
        newsFeedDisplay = new JTextArea(10,15);
        newsFeedDisplay.setText("List View (News Feed)\n");
    }

    //getters and setters
    public List<Observer> getFollowers(){return followers;}
    public List<User> getFollowing(){return following;}
    public List<String> getMyMessages(){return myMessages;}
    public JTextArea getFollowingDisplay (){return followingDisplay;}
    public JTextArea getNewsFeedDisplay(){return newsFeedDisplay;}


    public void writeInNewsFeed(String message, String author){
        String send = "- "+ author + ": " + message + "\n";
        myMessages.add(send);
        newsFeedDisplay.append(send);
    }
    public void writeMyOwnMessage(String message){
        writeInNewsFeed(message + " Written at: " + this.getLastUpdateTime(), this.getId());
    }
    private void addFollowers(User u){
        followers.add(new UserObserver(u));
    }
    public void addFollowing(User u){
        if(u.getId().equals(this.getId()))
            return;
        for(User check : following)
            if(check.getId().equals(u.getId()))
                return;
        following.add(u);
        addToFollowingDisplay(u);
        u.addFollowers(this);
    }
    public void tweet(String s){
        this.setLastUpdateTime(System.currentTimeMillis());
        writeMyOwnMessage(s);
        for(Observer o : followers) {
            o.update(s, this);
        }
    }



    //helpers
    private void addToFollowingDisplay(User u){
        followingDisplay.append("- " + u.getId() + "\n");
    }


    @Override
    public User isUser(String name) {
        if(getId().equals(name))
            return this;
        else
            return null;
    }
    @Override
    public String readAll(int tab) {
        String s = "";
        for(int i=0; i<tab; i++)
            s+="  ";
        s +="- " + this.getId() + "\n";
        return s;
    }
    @Override
    public void accept(Visitor v) {
        v.atUser(this);
    }

    @Override
    public Pack lastUpdated() {
        return this;
    }
}
