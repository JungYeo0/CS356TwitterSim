package Pjt2CS365;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bees {
    public static void main(String[] args){
        Execute.Run();
        //new Bees().doMe();
    }

    private void doMe(){
        UserGroup root = new UserGroup("root");
        root.gen();
        JFrame main = new JFrame();
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setSize(500,500);
        JPanel pan = new JPanel();

        JTextField userNameSelect = new JTextField(10);
        JButton userSelect = new JButton("User");

        class UserSelectListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                String find = userNameSelect.getText();
                System.out.println(find + "--------");
                User selected = root.findUser(find);
                //System.out.println(selected.getId() + "=====");
                if(selected == null)
                    return;
                System.out.println("found");
            }
        }

        userSelect.addActionListener(new UserSelectListener());
        pan.add(userNameSelect); pan.add(userSelect);
        main.add(pan);
        main.setVisible(true);
    }
}
