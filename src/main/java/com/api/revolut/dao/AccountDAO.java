package com.api.revolut.dao;

import com.api.revolut.exception.DAOException;
import com.api.revolut.objectModel.Account;
import com.api.revolut.objectModel.UserTransaction;

import java.math.BigDecimal;
import java.util.List;


public interface AccountDAO {

    List<Account> getAllAccounts() throws DAOException;

    Account getAccountById(long accountId) throws DAOException;

    long createAccount(Account account) throws DAOException;

    int deleteAccountById(long accountId) throws DAOException;


    int updateAccountBalance(long accountId, BigDecimal deltaAmount) throws DAOException;

    int transferAccountBalance(UserTransaction userTransaction) throws DAOException;
}
