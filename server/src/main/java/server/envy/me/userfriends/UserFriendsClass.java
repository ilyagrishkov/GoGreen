package server.envy.me.userfriends;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(UserFriendsMapping.class)
@Table(name = "userfriends")
public class UserFriendsClass {

    @Id
    @Column(name = "userid1")
    private int userId1;

    @Id
    @Column(name = "userid2")
    private int userId2;

    public UserFriendsClass(){
    }

    public UserFriendsClass(int userId1, int userId2) {
        this.userId1 = userId1;
        this.userId2 = userId2;
    }

    public int getUserId1() {
        return userId1;
    }

    public void setUserId1(int userId1) {
        this.userId1 = userId1;
    }

    public int getUserId2() {
        return userId2;
    }

    public void setUserId2(int userId2) {
        this.userId2 = userId2;
    }
}
