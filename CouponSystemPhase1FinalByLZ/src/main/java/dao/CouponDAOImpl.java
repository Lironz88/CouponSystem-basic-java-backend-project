package dao;

import enums.Category;
import beans.Coupon;
import db.ConvertUtils;
import db.JDBCUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouponDAOImpl implements CouponDAO {

    private final static String ADD_COUPON = "INSERT INTO `java-151-cs-3`.`coupons` (`company_id`,`category_id`,`title`,`description`,`start_date`,`end_date`,`amount`,`price`,`image`\n"
            + ") VALUES (? ,? ,?,?,?,?,?,?,?);";
    private final static String UPDATE_COUPON = "UPDATE `java-151-cs-3`.`coupons` SET `company_id` = ?,`category_id`=? ,`title` = ?, `description` = ?, `start_date` = ?, `end_date` = ?, `amount` = ?, `price` = ?, `image` = ? WHERE (`id` = ?)";
    private final static String DELETE_COUPON = "DELETE FROM `java-151-cs-3`.`coupons` WHERE (`id` = ?)";
    private final static String GET_ALL_COUPONS = "SELECT * FROM `java-151-cs-3`.coupons";
    private final static String GET_SINGLE_COUPON = "SELECT * FROM `java-151-cs-3`.coupons where id=?";
    private final static String ADD_PURCHASE_COUPON = "INSERT INTO `java-151-cs-3`.`customer_vs_coupons` (`customer_id`, `coupon_id`) VALUES (?, ?)";
    private final static String DELETE_PURCHASE_COUPON = "DELETE FROM `java-151-cs-3`.`customer_vs_coupons` WHERE (`coupon_id` = ?)";
    private final static String GET_ALL_COUPONS_BY_ID_AND_CATEGORY = "SELECT * FROM `java-151-cs-3`.coupons where company_id=? and category_id=?";
    private final static String GET_ALL_COUPONS_BY_ID = "SELECT * FROM `java-151-cs-3`.coupons where company_id=?";
    private final static String GET_ALL_COUPONS_BY_ID_AND_MAX_PRICE = "SELECT * FROM `java-151-cs-3`.coupons where company_id=? and price<=?";
    private final static String GET_PURCHASED_COUPONS = "SELECT * FROM `java-151-cs-3`.customer_vs_coupons where `customer_id`=?";
    private final static String DECREASE_COUPON_AMOUNT = "UPDATE `java-151-cs-3`.coupons SET amount = amount - 1 WHERE id = ?";

    @Override
    public void add(Coupon t) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, t.getCompanyId());
        params.put(2, t.getCategoryId());
        params.put(3, t.getTitle());
        params.put(4, t.getDescription());
        params.put(5, t.getStartDate());
        params.put(6, t.getEndDate());
        params.put(7, t.getAmount());
        params.put(8, t.getPrice());
        params.put(9, t.getImage());
        JDBCUtils.runQuery(ADD_COUPON, params);
    }


    public void update(Coupon t) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, t.getCompanyId());
        params.put(2, t.getCategoryId());
        params.put(3, t.getTitle());
        params.put(4, t.getDescription());
        params.put(5, t.getStartDate());
        params.put(6, t.getEndDate());
        params.put(7, t.getAmount());
        params.put(8, t.getPrice());
        params.put(9, t.getImage());
        params.put(10, t.getId());
        JDBCUtils.runQuery(UPDATE_COUPON, params);

    }

    @Override
    public void delete(Integer id) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        JDBCUtils.runQuery(DELETE_COUPON, params);

    }

    @Override
    public List<Coupon> getAll() throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        List<?> list = JDBCUtils.runQueryWithResult(GET_ALL_COUPONS);
        for (Object obj : list) {
            coupons.add(ConvertUtils.ObjectToCoupon((Map<String, Object>) obj));
        }
        return coupons;
    }


    @Override
    public Coupon getSingle(Integer id) throws SQLException {
        Coupon coupon = null;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        List<Map<String, Object>> list = (List<Map<String, Object>>) JDBCUtils.runQueryWithResult(GET_SINGLE_COUPON, params);
        if (list != null && !list.isEmpty()) {
            coupon = ConvertUtils.ObjectToCoupon(list.get(0));
        }
        return coupon;
    }

    @Override
    public void addPurchaseCoupon(int customerId, int couponId) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        params.put(2, couponId);
        JDBCUtils.runQuery(ADD_PURCHASE_COUPON, params);

    }

    public void deletePurchaseCoupon(int couponId) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponId);
        JDBCUtils.runQuery(DELETE_PURCHASE_COUPON, params);

    }

    public List<Coupon> getAllByIdAndCategory(int companyId, Category category) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        params.put(2, category.ordinal() + 1);
        List<?> list = JDBCUtils.runQueryWithResult(GET_ALL_COUPONS_BY_ID_AND_CATEGORY, params);
        for (Object obj : list) {
            coupons.add(ConvertUtils.ObjectToCoupon((Map<String, Object>) obj));
        }
        return coupons;
    }

    public List<Coupon> getAllByIdAndMaxPrice(int customerId, double maxPrice) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        params.put(2, maxPrice);
        List<?> list = JDBCUtils.runQueryWithResult(GET_ALL_COUPONS_BY_ID_AND_MAX_PRICE, params);
        for (Object obj : list) {
            coupons.add(ConvertUtils.ObjectToCoupon((Map<String, Object>) obj));
        }
        return coupons;
    }

    public List<Coupon> getAllById(int customerId) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        List<?> list = JDBCUtils.runQueryWithResult(GET_ALL_COUPONS_BY_ID, params);
        for (Object obj : list) {
            coupons.add(ConvertUtils.ObjectToCoupon((Map<String, Object>) obj));
        }
        return coupons;
    }

    public List<Coupon> getPurchasedCoupons(int customerId) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        List<?> list = JDBCUtils.runQueryWithResult(GET_PURCHASED_COUPONS, params);
        for (Object obj : list) {
            coupons.add(ConvertUtils.ObjectToCoupon((Map<String, Object>) obj));
        }
        return coupons;
    }

    public void decreaseCouponAmount(int id) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        JDBCUtils.runQuery(DECREASE_COUPON_AMOUNT, params);
    }
}
