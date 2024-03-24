package dao;

import beans.Customer;
import db.ConvertUtils;
import db.JDBCUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDAOImpl implements CustomerDAO {
    private static final String GET_ALL = "SELECT * FROM `java-151-cs-3`.customers";
    private static final String ADD_CUSTOMER = "INSERT INTO `java-151-cs-3`.`customers` (`first_name`, `last_name`, `email`, `password`) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_CUSTOMER = "UPDATE `java-151-cs-3`.`customers` SET `first_name` = ?, `last_name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?);";
    private static final String DELETE_CUSTOMER = "DELETE FROM `java-151-cs-3`.`customers` WHERE (`id` = ?)";
    private static final String GET_SINGLE_CUSTOMER = "SELECT * FROM `java-151-cs-3`.customers WHERE (`id` = ?)";
    private static final String GET_SINGLE_CUSTOMER_BY_MAIL = "SELECT * FROM `java-151-cs-3`.customers WHERE (`email` = ?)";
    private static final String IS_CUSTOMER_EXIST = "SELECT * FROM `java-151-cs-3`.customers where email=? and password=?";
    ;

    public void add(Customer t) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, t.getFirstName());
        params.put(2, t.getLastName());
        params.put(3, t.getEmail());
        params.put(4, t.getPassword());
        JDBCUtils.runQuery(ADD_CUSTOMER, params);

    }

    public void update(Customer t) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, t.getFirstName());
        params.put(2, t.getLastName());
        params.put(3, t.getEmail());
        params.put(4, t.getPassword());
        params.put(5, t.getId());
        JDBCUtils.runQuery(UPDATE_CUSTOMER, params);

    }

    @Override
    public void delete(Integer id) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        JDBCUtils.runQuery(DELETE_CUSTOMER, params);

    }

    @Override
    public List<Customer> getAll() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        List<?> list = JDBCUtils.runQueryWithResult(GET_ALL);
        for (Object obj : list) {
            customers.add(ConvertUtils.ObjectToCustomer((Map<String, Object>) obj));
        }
        return customers;
    }

    @Override
    public Customer getSingle(Integer id) throws SQLException {
        Customer customer = null;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        List<Map<String, Object>> list = (List<Map<String, Object>>) JDBCUtils.runQueryWithResult(GET_SINGLE_CUSTOMER, params);
        if (list != null && !list.isEmpty()) {
            customer = ConvertUtils.ObjectToCustomer(list.get(0));
        }
        return customer;

    }

    @Override
    public boolean isExist(String email, String password) throws SQLException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        params.put(2, password);
        List<?> list = JDBCUtils.runQueryWithResult(IS_CUSTOMER_EXIST, params);
        result = ConvertUtils.ObjectToBool((Map<String, Object>) list.get(0));
        return result;
    }

    public Customer getSingleByMail(String email) throws SQLException {
        Customer customer = null;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        List<Map<String, Object>> list = (List<Map<String, Object>>) JDBCUtils.runQueryWithResult(GET_SINGLE_CUSTOMER_BY_MAIL, params);
        if (list != null && !list.isEmpty()) {
            customer = ConvertUtils.ObjectToCustomer(list.get(0));
        }
        return customer;

    }
}
