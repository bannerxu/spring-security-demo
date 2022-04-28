package top.banner.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.banner.entity.User;

public interface UserDao extends JpaRepository<User, Long> {

    User findByUsernameAndDeletedIsFalse(String username);
}
