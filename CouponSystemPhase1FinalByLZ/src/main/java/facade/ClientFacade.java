package facade;

import dao.*;
import exceptions.CouponSystemException;

import java.sql.SQLException;

public abstract class ClientFacade {
    protected CustomerDAO customerDAO = new CustomerDAOImpl();
    protected CompanyDAO companyDAO = new CompanyDAOImpl();
    protected CouponDAO couponDAO = new CouponDAOImpl();

    public abstract boolean login(String email, String password) throws SQLException, CouponSystemException;

}
