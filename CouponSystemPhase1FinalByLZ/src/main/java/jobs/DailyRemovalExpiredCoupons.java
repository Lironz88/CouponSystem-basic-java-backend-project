package jobs;

import beans.Coupon;
import dao.CouponDAO;
import dao.CouponDAOImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DailyRemovalExpiredCoupons implements Runnable{
    private CouponDAO couponDAO;
    private boolean stop;


    public DailyRemovalExpiredCoupons() {
       couponDAO= new CouponDAOImpl();
       stop=false;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                List<Coupon> coupons = couponDAO.getAll();
                if (coupons != null && !coupons.isEmpty()) {
                    for (Coupon coupon : coupons) {
                        if (coupon != null) {
                            LocalDate sqlDate = coupon.getEndDate().toLocalDate();
                            if (sqlDate.isBefore(LocalDate.now())) {
                                try {
                                    couponDAO.delete(coupon.getId());
                                } catch (Exception a) {
                                    System.out.println(a);
                                }
                                couponDAO.deletePurchaseCoupon(coupon.getId());
                            }
                        }
                    }
                }
                TimeUnit.DAYS.sleep(1);
            } catch (InterruptedException | SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }



    public void stopped(){

        stop=true;
    }
}
