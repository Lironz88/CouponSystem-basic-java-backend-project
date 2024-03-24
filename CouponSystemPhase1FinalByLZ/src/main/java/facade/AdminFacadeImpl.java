package facade;

import beans.Company;
import beans.Coupon;
import beans.Customer;
import exceptions.CouponSystemException;
import exceptions.ErrMsg;

import java.sql.SQLException;
import java.util.List;

public class AdminFacadeImpl extends ClientFacade implements AdminFacade {
    private String email = "admin@admin.com";
    private String password = "admin";

    @Override
    public boolean login(String email, String password) throws CouponSystemException {
        if (this.email.equals(email) && this.password.equals(password)) {
            return true;
        } else {
            throw new CouponSystemException(ErrMsg.LOGIN_FAILED);
        }
    }

    @Override
    public void addCompany(Company comapny) throws CouponSystemException, SQLException {
        if (this.companyDAO.isExistByName(comapny.getName())) {
            throw new CouponSystemException(ErrMsg.COMPANY_NAME_EXISTS);
        }
        if (this.companyDAO.isExistByEmail(comapny.getEmail())) {
            throw new CouponSystemException(ErrMsg.COMPANY_EMAIL_EXISTS);
        }
        this.companyDAO.add(comapny);
    }

    public void updateCompany(Company company, int id) throws SQLException, CouponSystemException {
        Company c = companyDAO.getSingle(id);
        if (c != null && c.getName().equals(company.getName())) {
            company.setId(id);
            companyDAO.update(company);
        } else throw new CouponSystemException(ErrMsg.COMPANY_UPDATE_FAILED);
    }

    @Override
    public void deleteCompany(int companyId) throws SQLException, CouponSystemException {
        Company c = companyDAO.getSingle(companyId);
        if {
            c == null
        }throw new CouponSystemException(ErrMsg.COMPANY_IS_NOT_FOUND);
        List<Coupon> companyCoupons = c.getCoupons();
        for (Coupon coupon : companyCoupons) {
            couponDAO.deletePurchaseCoupon(coupon.getId());
            couponDAO.delete(coupon.getId());
        }
        companyDAO.delete(companyId);
    }

    @Override
    public List<Company> getAllCompanies() throws SQLException {
        return companyDAO.getAll();

    }

    @Override
    public Company getSingleCompany(int companyId) throws SQLException, CouponSystemException {
        Company company = companyDAO.getSingle(companyId);
        if (company == null) {
            throw new CouponSystemException(ErrMsg.COMPANY_NULL);
        }
        return company;
    }


    @Override
    public void addCustomer(Customer customer) throws CouponSystemException, SQLException {
        if (this.companyDAO.isExistByEmail(customer.getEmail())) {
            throw new CouponSystemException(ErrMsg.CUSTOMER_EMAIL_EXISTS);
        }
        this.customerDAO.add(customer);

    }


    public void updateCustomer(Customer customer, int id) throws SQLException, CouponSystemException {
        Customer c = customerDAO.getSingle(id);
        if (c != null) {
            customer.setId(id);
            customerDAO.update(customer);
        } else throw new CouponSystemException(ErrMsg.CUSTOMER_NOT_FOUND_UPDATE);
    }

    public void deleteCustomer(int customerId) throws SQLException, CouponSystemException {
        Customer cus = customerDAO.getSingle(customerId);
        if (cus == null) {
            throw new CouponSystemException(ErrMsg.CUSTOMER_NOT_FOUND_DELETE);
        }
        customerDAO.delete(customerId);
        for (Coupon c : cus.getCoupons()) {
            couponDAO.delete(customerId);
        }
    }

    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        return customerDAO.getAll();

    }

    @Override
    public Customer getSingleCustomer(int customerId) throws SQLException, CouponSystemException {
        Customer customer = customerDAO.getSingle(customerId);
        if (customer == null) {
            throw new CouponSystemException(ErrMsg.CUSTOMER_NULL);
        }
        return customer;
    }


}
