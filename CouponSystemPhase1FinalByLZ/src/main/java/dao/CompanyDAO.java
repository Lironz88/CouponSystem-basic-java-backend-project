package dao;

import beans.Company;

import java.sql.SQLException;

public interface CompanyDAO extends DAO<Company, Integer> {
    Boolean isExistByEmail(String email) throws SQLException;

    Boolean isExistByName(String name) throws SQLException;

    boolean isExist(String email, String password) throws SQLException;

    public void update(Company t) throws SQLException;

    public Company getSingleByMail(String email) throws SQLException;

}
