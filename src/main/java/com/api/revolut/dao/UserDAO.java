package com.api.revolut.dao;

import com.api.revolut.exception.DAOException;
import com.api.revolut.objectModel.User;

import java.util.List;


public interface UserDAO {
    List<User> getAllUsers() throws DAOException;

    User getUserById(long userId) throws DAOException;

    User getUserByName(String userName) throws DAOException;

    /**
     * @param user: user to be created
     * @return userId generated from insertion. return -1 on error
     */
    long insertUser(User user) throws DAOException;

    int updateUser(Long userId, User user) throws DAOException;

    int deleteUser(long userId) throws DAOException;

}
