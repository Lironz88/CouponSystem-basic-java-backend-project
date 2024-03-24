package facade;

import beans.Company;
import beans.Customer;
import exceptions.CouponSystemException;

import java.sql.SQLException;
import java.util.List;

public interface AdminFacade {
    boolean login(String email, String password) throws CouponSystemException;

    void addCompany(Company comapny) throws CouponSystemException, SQLException;

    void updateCompany(Company comapny, int id) throws SQLException, CouponSystemException;

    void deleteCompany(int companyId) throws SQLException, CouponSystemException;

    List<Company> getAllCompanies() throws SQLException;

    Company getSingleCompany(int companyId) throws SQLException, CouponSystemException;

    void addCustomer(Customer customer) throws CouponSystemException, SQLException;

    void updateCustomer(Customer customer, int id) throws SQLException, CouponSystemException;

    void deleteCustomer(int customerId) throws SQLException, CouponSystemException;

    List<Customer> getAllCustomers() throws SQLException;

    Customer getSingleCustomer(int customerId) throws SQLException, CouponSystemException;


}
