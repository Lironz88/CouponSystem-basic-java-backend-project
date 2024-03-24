package dao;

import enums.Category;
import beans.Coupon;

import java.sql.SQLException;
import java.util.List;

public interface CouponDAO extends DAO<Coupon, Integer> {
    void addPurchaseCoupon(int customerId, int couponId) throws SQLException;

    void deletePurchaseCoupon(int customerId) throws SQLException;

    List<Coupon> getAllByIdAndCategory(int companyId, Category category) throws SQLException;

    List<Coupon> getAllByIdAndMaxPrice(int customerId, double maxPrice) throws SQLException;

    List<Coupon> getAllById(int customerId) throws SQLException;

    List<Coupon> getPurchasedCoupons(int customerId) throws SQLException;

    void decreaseCouponAmount(int id) throws SQLException;
}
