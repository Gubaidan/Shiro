package dao.impl;

import dao.UserDao;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import po.User;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Component
public class UserDaoImpl implements UserDao {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Override
    public User getUserByName(String name) {
        String sql = "select username,password,password_salt from users where username = ?";
        List<User> list = jdbcTemplate.query(sql, new String[]{name}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassward(resultSet.getString("password"));
                user.setPassward_salt(resultSet.getString("password_salt"));
                return user;
            }
        });
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<String> getRolesByName(String username) {
        String sql = "select distinct role_name from user_roles where username = ?";
        List<String> list = jdbcTemplate.query(sql, new String[]{username}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {

                return resultSet.getString("role_name");
            }
        });
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list;
    }
}
