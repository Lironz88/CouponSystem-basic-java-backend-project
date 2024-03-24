package jobs;

import enums.ClientType;
import exceptions.CouponSystemException;
import facade.AdminFacadeImpl;
import facade.ClientFacade;
import facade.CompanyFacadeImpl;
import facade.CustomerFacadeImpl;

import java.sql.SQLException;

public class LoginManager {
    private static LoginManager loginManager;

    // TODO: 21/12/2023 :upgrade to private 
    public LoginManager() {
    }

    public static LoginManager getInstance() {
        if (loginManager == null) {
            loginManager = new LoginManager();
        }
        return loginManager;
    }

    public ClientFacade login(String email, String password, ClientType clientType) throws SQLException, CouponSystemException {
        ClientFacade facade = null;
        switch (clientType) {
            case Administrator:
                facade = new AdminFacadeImpl();
                break;
            case Company:
                facade = new CompanyFacadeImpl();
                break;
            case Customer:
                facade = new CustomerFacadeImpl();
                break;
            default:
                return null;
        }
        if (facade.login(email, password))
            return facade;
        return null;
    }
}
