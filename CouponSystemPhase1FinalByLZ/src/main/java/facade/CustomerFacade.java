package facade;

import beans.Coupon;
import beans.Customer;
import enums.Category;
import exceptions.CouponSystemException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerFacade {
    boolean login(String email, String password) throws SQLException, CouponSystemException;

    void purchaseCoupon(Coupon coupon) throws SQLException, CouponSystemException;

    ArrayList<Coupon> getCustomerCoupons() throws SQLException;

    ArrayList<Coupon> getCustomerCoupons(Category category) throws SQLException;

    ArrayList<Coupon> getCustomerCoupons(double maxPrice) throws SQLException;

    Customer getCustomerDetails() throws SQLException;
}
