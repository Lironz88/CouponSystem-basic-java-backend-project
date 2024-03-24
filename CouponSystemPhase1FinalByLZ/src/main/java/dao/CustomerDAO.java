package dao;

import beans.Customer;

import java.sql.SQLException;

public interface CustomerDAO extends DAO<Customer, Integer> {
    void update(Customer customer) throws SQLException;

    boolean isExist(String email, String password) throws SQLException;

    public Customer getSingleByMail(String email) throws SQLException;

}
