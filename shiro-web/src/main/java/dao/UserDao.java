package dao;

import org.springframework.stereotype.Component;
import po.User;

import java.util.List;

/**
 *
 */
@Component
public interface UserDao {
    User getUserByName(String name);

    List<String> getRolesByName(String username);

    List<String> getPermissionByName(String username);
}
