package facade;

import beans.Company;
import beans.Coupon;
import enums.Category;
import exceptions.CouponSystemException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CompanyFacade {
	boolean login(String email, String password) throws SQLException, CouponSystemException;
	void addCoupon(Coupon coupon) throws CouponSystemException, SQLException;
	void updateCoupon(Coupon coupon, int id) throws SQLException, CouponSystemException;
	void deleteCoupon(int CouponID) throws CouponSystemException, SQLException;
	ArrayList<Coupon> getCompanyCoupons( Category category) throws SQLException;
	ArrayList<Coupon> getCompanyCoupons(int id) throws SQLException;
	ArrayList<Coupon> getCompanyCouponsByPrice( double maxPrice) throws SQLException;
	Company getCompanyDetails() throws SQLException;
}
