package Pjt2CS365;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

public class Execute extends JFrame {
    private UserGroup root;
    private JTextArea treeView;
    private TotalMemberVisitor memTot;
    private TotalMessageVisitor msgTot;

    // begin one itteration of the program
    private static Execute singularity= null;                   //singularity check
    private Execute(){
        root = new UserGroup("Root");
        memTot = new TotalMemberVisitor();
        msgTot = new TotalMessageVisitor();
        this.makeWindow();
    }
    public static void Run(){
        if(singularity == null)
            synchronized (Execute.class){
            if(singularity== null)
                singularity= new Execute();
            }
    }


    /////////////////////////////////////////////
    private void makeWindow(){
        this.setSize(600,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setName("Admin Page");

        this.windowComponents();

        this.setVisible(true);
    }
    private void windowComponents(){
        makeBasePanels();
    }

    private void makeBasePanels(){
        JPanel  main  = new JPanel(),
                memberList = makeMemberListPan(),
                dataList = new JPanel(),
                newMember = makeNewMemberPan(),
                memberWindow = makeMemberWindowPan(),
                adminControl = makeAdminControlPan();

        main.setLayout(new BorderLayout());
        main.add(memberList, BorderLayout.LINE_START);
        main.add(dataList, BorderLayout.CENTER);

        dataList.setLayout(new BoxLayout(dataList, BoxLayout.Y_AXIS));
        dataList.add(newMember); dataList.add(memberWindow); dataList.add(adminControl);
        this.add(main);
    }
    private JPanel makeMemberListPan(){
        JPanel memberList = new JPanel();
        treeView = new JTextArea(25,20);
        treeView.setEditable(false);
        treeView.setWrapStyleWord(true);
        treeView.setLineWrap(true);
        updateTreeView();
        memberList.add(new JScrollPane(treeView));
        return memberList;
    }
    private JPanel makeNewMemberPan(){
        //make base panels and layout
        JPanel  main = new JPanel(),
                addUserPan = new JPanel(),
                addGroupPan = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        addUserPan.setLayout(new FlowLayout());
        addGroupPan.setLayout(new FlowLayout());

        //create input GUIs
        JTextField userName = new JTextField(10),
                    groupName = new JTextField(10),
                    userJoinGroup = new JTextField (10),
                    groupJoinGroup = new JTextField(10);
        JLabel joinUserLab = new JLabel("User's Group"),
                joinGroupLab = new JLabel(("Group's Group"));

        JButton userButton = new JButton("add new user"),
                groupButton = new JButton("add new group");
        class UserButtonListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                User u = new User(userName.getText());
                if (root.userJoinGroup(userJoinGroup.getText(), u))
                    u.accept(memTot);
                userName.setText("");
                userJoinGroup.setText("");
                updateTreeView();
            }
        }
        class GroupButtonListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                UserGroup g = new UserGroup(groupName.getText());
                if(root.groupJoinGroup(groupJoinGroup.getText(), g))
                    g.accept(memTot);
                groupName.setText("");
                groupJoinGroup.setText("");
                updateTreeView();
            }
        }
        userButton.addActionListener(new UserButtonListener());
        groupButton.addActionListener(new GroupButtonListener());

        //add everything
        addUserPan.add(userName); addUserPan.add(userButton); addUserPan.add(userJoinGroup); addUserPan.add(joinUserLab);
        addGroupPan.add(groupName); addGroupPan.add(groupButton); addGroupPan.add(groupJoinGroup); addGroupPan.add(joinGroupLab);
        main.add(addUserPan); main.add(addGroupPan);

        return main;
    }
    private JPanel makeMemberWindowPan(){
        JPanel main = new JPanel();
        JTextField userNameSelect = new JTextField(10);
        JButton userSelect = new JButton("User"),
        validIDs = new JButton("valid ID check");

        class UserSelectListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                User selected = root.findUser(userNameSelect.getText());
                userNameSelect.setText("");
                if(selected == null)
                    return;
                newUserPopup(selected);
            }
        }
        userSelect.addActionListener(new UserSelectListener());
        main.add(userNameSelect); main.add(userSelect);

        class validIDsListener implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                String completeList = root.readAll(0);
                boolean checkCurr=false;
                Set<String> uniqueNameCheck = new HashSet<>();
                int startWord=0;
                for(int i=0; i< completeList.length(); i++){
                    if(checkCurr){
                        if(completeList.charAt(i)== ' '){
                            System.out.println("invalid ID found");
                            break;
                        }
                        else if(completeList.charAt(i)=='\n'){
                            checkCurr=false;
                            if (!uniqueNameCheck.add(completeList.substring(startWord, i))){
                                System.out.println("non Unique ID found");
                                break;
                            }

                        }
                    }
                    else {
                        if (completeList.charAt(i) == '[') {
                            i += 9;
                            startWord = i+1;
                            checkCurr = true;
                        } else if (completeList.charAt(i) == '-') {
                            i++;
                            startWord = i+1;
                            checkCurr = true;
                        }
                    }
                }
            }
        }
        validIDs.addActionListener(new validIDsListener());
        main.add(validIDs);

        return main;
    }
    private JPanel makeAdminControlPan(){
        JPanel  main = new JPanel(),
                row1 = new JPanel(),
                row2 = new JPanel(),
                row3 = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        row1.setLayout(new FlowLayout());
        row2.setLayout(new FlowLayout());
        row3.setLayout(new FlowLayout());

        JButton userTotal = new JButton("Show User Total"),
                groupTotal  = new JButton("Show Group Total"),
                msgTotal = new JButton("Show Message Total"),
                posPercentTotal = new JButton("Show Positive Percentage"),
                lastUpdatedUser = new JButton("Show Last Updated User");

        class UserTotalListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                adminPopUps("User Total", "Total Users: " + memTot.getTotalUser());
            }
        }
        class GroupTotalListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                adminPopUps("Group Total", "Group Total: " + memTot.getTotalGroup());
            }
        }
        class MsgTotalListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                adminPopUps("Message Total", "Message Total: " + msgTot.getTotalMsg());
            }
        }
        class PosPercentTotalListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                adminPopUps("Positive Vibes", "Positive Messages: " + msgTot.getTotalPositiveMsg());
            }
        }
        class LastUpdatedUserListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                Pack lastUpdated = root.lastUpdated();
                System.out.println(lastUpdated.getId() + ": " + lastUpdated.getLastUpdateTime());
            }
        }

        userTotal.addActionListener(new UserTotalListener());
        groupTotal.addActionListener(new GroupTotalListener());
        msgTotal.addActionListener(new MsgTotalListener());
        posPercentTotal.addActionListener(new PosPercentTotalListener());
        lastUpdatedUser.addActionListener(new LastUpdatedUserListener());

        row1.add(userTotal); row1.add(groupTotal);
        row2.add(msgTotal); row2.add(posPercentTotal);
        row3.add(lastUpdatedUser);

        main.add(row1); main.add(row2); main.add(row3);
        return main;
    }

    //helpers
    //----makeNewMemberPan---
    private void updateTreeView(){
        treeView.setText("Tree View\n" + root.readAll(0));
    }

    //----makeAdminControls---
    private void adminPopUps(String title, String data){
        JFrame popup = new JFrame(title);
        popup.setLocationRelativeTo(null);
        popup.setSize(200,200);

        JLabel info = new JLabel(data);
        popup.add(info);
        popup.setVisible(true);
    }

    ////////////////////////////////////////////
    //user window
    private void newUserPopup(User u){
        JFrame userWindow = new JFrame("User: " + u.getId() + "  Created at:" + u.getCreationTime());
        userWindow.setName(u.getId());
        userWindow.setLocationRelativeTo(null);
        userWindow.setSize(500,500);

        userWindow.add(setupUserWindow(u));

        userWindow.setVisible(true);
    }           //makes a new JFrame named the User's ID
    private JPanel setupUserWindow(User u){
        JPanel main = new JPanel(),
                followUser = makeFollowUser(u),
                currentFollowing = makeCurrentFollowing(u),
                tweetArea = makeTweetArea(u),
                newsFeed = makeNewsFeed(u);
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(followUser);main.add(currentFollowing);
        main.add(tweetArea); main.add(newsFeed);
        return main;
    }      //sets up the inside of the JFrame

    private JPanel makeFollowUser(User u){
        JPanel main = new JPanel();
        main.setLayout(new FlowLayout());

        JTextField userIDName = new JTextField(10);
        JButton followUserBut = new JButton("Follow User");
        main.add(userIDName); main.add(followUserBut);

        class FollowUserButtonListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = root.findUser(userIDName.getText());
                userIDName.setText("");
                if(user == null) {
                    return;
                }
                u.addFollowing(user);
            }
        }

        followUserBut.addActionListener(new FollowUserButtonListener());
        return main;
    }
    private JPanel makeCurrentFollowing(User u){
        JPanel main = new JPanel();
        main.add(u.getFollowingDisplay());
        return main;
    }
    private JPanel makeTweetArea(User u){
        JPanel main = new JPanel();
        main.setLayout(new FlowLayout());
        JTextField tweetMsg = new JTextField(10);
        JButton tweetBut = new JButton("Tweet");

        class TweetButListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tweetMsg.getText().equals(""))
                    return;
                String msg = tweetMsg.getText();
                tweetMsg.setText("");
                u.tweet(msg);
                msgTot.atUser(u);
            }
        }
        tweetBut.addActionListener(new TweetButListener());
        main.add(tweetMsg); main.add(tweetBut);
        return main;
    }
    private JPanel makeNewsFeed(User u){
        JPanel main= new JPanel();
        main.add(u.getNewsFeedDisplay());
        return main;
    }
}
