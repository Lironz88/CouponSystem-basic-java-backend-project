package facade;

import beans.Company;
import beans.Coupon;
import enums.Category;
import exceptions.CouponSystemException;
import exceptions.ErrMsg;

import java.sql.SQLException;
import java.util.ArrayList;

public class CompanyFacadeImpl extends ClientFacade implements CompanyFacade {
    private int companyId;

    @Override
    public boolean login(String email, String password) throws SQLException, CouponSystemException {
        if (companyDAO.isExist(email, password)) {
            companyId = companyDAO.getSingleByMail(email).getId();
            return true;
        } else throw new CouponSystemException(ErrMsg.LOGIN_FAILED);
    }

    @Override
    public void addCoupon(Coupon coupon) throws CouponSystemException, SQLException {
        for (Coupon co : couponDAO.getAll()) {
            if (coupon.getTitle().equals(co.getTitle()) &&
                    co.getCompanyId() == coupon.getCompanyId())
                throw new CouponSystemException(ErrMsg.COUPON_ALREADY_EXISTS);
        }
        couponDAO.add(coupon);
    }


    @Override
    public void updateCoupon(Coupon coupon, int id) throws SQLException, CouponSystemException {
        Coupon c1 = couponDAO.getSingle(id);
        if (c1 != null && c1.getCompanyId() == coupon.getCompanyId()) {
            coupon.setId(id);
            couponDAO.update(coupon);
        } else throw new CouponSystemException(ErrMsg.COMPANY_UPDATE_FAILED);
    }

    @Override
    public void deleteCoupon(int CouponID) throws CouponSystemException, SQLException {
        if (couponDAO.getSingle(CouponID) == null)
            throw new CouponSystemException(ErrMsg.COUPON_DELETE_FAILED);
        couponDAO.delete(CouponID);
        couponDAO.deletePurchaseCoupon(CouponID);
    }

    @Override
    public ArrayList<Coupon> getCompanyCoupons(Category category) throws SQLException {
        return (ArrayList<Coupon>) couponDAO.getAllByIdAndCategory(companyId, category);
    }


    public ArrayList<Coupon> getCompanyCoupons(int id) throws SQLException {
        return (ArrayList<Coupon>) couponDAO.getAllById(id);
    }

    @Override
    public ArrayList<Coupon> getCompanyCouponsByPrice(double maxPrice) throws SQLException {
        return (ArrayList<Coupon>) couponDAO.getAllByIdAndMaxPrice(companyId, maxPrice);
    }

    @Override
    public Company getCompanyDetails() throws SQLException {
        return companyDAO.getSingle(companyId);
    }

}
