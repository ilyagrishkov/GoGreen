package server.envy.me.userfriends;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserFriendsRepository extends CrudRepository<UserFriendsClass, Integer> {

    UserFriendsClass findByUserId1AndUserId2(int userId1, int userId2);

    List<UserFriendsClass> findAll();
}