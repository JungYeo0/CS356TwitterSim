package Pjt2CS365;

public class UserObserver implements Observer {
    private User self;
    public UserObserver(User me){self =me;}

    @Override
    public void update(String msg, User sender) {
        self.writeInNewsFeed(msg, sender.getId());
    }
}
