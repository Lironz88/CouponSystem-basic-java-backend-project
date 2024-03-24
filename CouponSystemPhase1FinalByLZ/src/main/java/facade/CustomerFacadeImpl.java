package facade;

import beans.Coupon;
import beans.Customer;
import enums.Category;
import exceptions.CouponSystemException;
import exceptions.ErrMsg;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class CustomerFacadeImpl extends ClientFacade implements CustomerFacade {
    private int customer_id;

    @Override
    public boolean login(String email, String password) throws SQLException, CouponSystemException {
        if (customerDAO.isExist(email, password)) {
            customer_id = customerDAO.getSingleByMail(email).getId();
            return true;
        } else throw new CouponSystemException(ErrMsg.LOGIN_FAILED);
    }


    @Override
    public void purchaseCoupon(Coupon coupon) throws SQLException, CouponSystemException {
        Date currentDate = new Date(); // Get the current date

        if (couponDAO.getPurchasedCoupons(customer_id).contains(coupon)) {
            throw new CouponSystemException(ErrMsg.COUPON_PURCHASE_FAILED_ONLY_ONCE);
        }
        if (coupon.getEndDate().before(currentDate)) { // Check if the coupon has expired
            throw new CouponSystemException(ErrMsg.COUPON_PURCHASE_FAILED_EXPIRED);
        }
        if (couponDAO.getSingle(coupon.getId()).getAmount() <= 0) {
            throw new CouponSystemException(ErrMsg.COUPON_PURCHASE_FAILED_NOT_EXISTING);
        }
        couponDAO.addPurchaseCoupon(customer_id, coupon.getId());
        ArrayList<Coupon> coupons = (ArrayList<Coupon>) couponDAO.getAllById(customer_id);
        coupons.add(coupon);
        Customer customer = customerDAO.getSingle(customer_id);
        customer.setCoupons(coupons);
        customerDAO.update(customer);
        couponDAO.decreaseCouponAmount(coupon.getId());
    }

    @Override
    public ArrayList<Coupon> getCustomerCoupons() throws SQLException {
        return (ArrayList<Coupon>) couponDAO.getAllById(customer_id);
    }

    @Override
    public ArrayList<Coupon> getCustomerCoupons(Category category) throws SQLException {
        return (ArrayList<Coupon>) couponDAO.getAllByIdAndCategory(customer_id, category);
    }

    @Override
    public ArrayList<Coupon> getCustomerCoupons(double maxPrice) throws SQLException {
        return (ArrayList<Coupon>) couponDAO.getAllByIdAndMaxPrice(customer_id, maxPrice);
    }

    @Override
    public Customer getCustomerDetails() throws SQLException {
        return customerDAO.getSingle(customer_id);
    }
}
