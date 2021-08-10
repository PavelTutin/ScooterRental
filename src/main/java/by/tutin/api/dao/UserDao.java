package by.tutin.api.dao;

import by.tutin.model.User;

public interface UserDao extends GenericDao<User>{

    User getByUsername(String username);
}
