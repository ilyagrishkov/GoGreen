package server.envy.me.userfood;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userfood")
public class UserFoodClass {

    @Id
    @Column(name = "foodid")
    private int foodId;

    @Column(name = "userid")
    private int userId;

    public UserFoodClass(int foodId, int userId) {
        this.foodId = foodId;
        this.userId = userId;
    }

    /**
     * For Deserialization purposes.
     */
    public UserFoodClass() {
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

