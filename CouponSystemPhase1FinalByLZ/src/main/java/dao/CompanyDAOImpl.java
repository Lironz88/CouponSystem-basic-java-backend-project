package dao;

import beans.Company;
import db.ConvertUtils;
import db.JDBCUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanyDAOImpl implements CompanyDAO {
    private static final String ADD_COMPANY = "INSERT INTO `java-151-cs-3`.`companies` (`name`, `email`, `password`) VALUES (?, ?, ?)";
    private static final String UPDATE_COMPANY_STRING = "UPDATE `java-151-cs-3`.`companies` SET `email` = ?, `password` = ? WHERE `id` = ? AND `name` = ?";
    private static final String DELETE_COMPANY_STRING = "DELETE FROM `java-151-cs-3`.`companies` WHERE (`id` = ?)";
    private static final String GET_ALL_COMPANIES = "SELECT * FROM `java-151-cs-3`.companies";
    private static final String GET_SINGLE_COMPANY = "SELECT * FROM `java-151-cs-3`.companies where id= ?";
    private static final String GET_SINGLE_COMPANY_BY_MAIL = "SELECT * FROM `java-151-cs-3`.companies where `email`= ?";
    private static final String IS_COMPANY_EXIST = "SELECT * FROM `java-151-cs-3`.companies where email=? and password=?";
    private static final String IS_COMPANY_EXIST_BY_EMAIL = "SELECT * FROM `java-151-cs-3`.companies where email=?";
    private static final String IS_COMPANY_EXIST_BY_NAME_STRING = "SELECT * FROM `java-151-cs-3`.companies where name=?";

    @Override
    public void add(Company t) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, t.getName());
        params.put(2, t.getEmail());
        params.put(3, t.getPassword());
        JDBCUtils.runQuery(ADD_COMPANY, params);

    }


    public void update(Company t) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, t.getEmail());
        params.put(2, t.getPassword());
        params.put(3, t.getId());
        params.put(4, t.getName());
        JDBCUtils.runQuery(UPDATE_COMPANY_STRING, params);
    }

    @Override
    public void delete(Integer id) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        JDBCUtils.runQuery(DELETE_COMPANY_STRING, params);
    }

    @Override
    public List<Company> getAll() throws SQLException {
        List<Company> companies = new ArrayList<>();
        List<?> list = JDBCUtils.runQueryWithResult(GET_ALL_COMPANIES);
        for (Object obj : list) {
            companies.add(ConvertUtils.ObjectToCompany((Map<String, Object>) obj));
        }
        return companies;
    }


    public Company getSingle(Integer id) throws SQLException {
        Company company = null;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        List<Map<String, Object>> list = (List<Map<String, Object>>) JDBCUtils.runQueryWithResult(GET_SINGLE_COMPANY, params);
        if (list != null && !list.isEmpty()) {
            company = ConvertUtils.ObjectToCompany(list.get(0));
        }
        return company;
    }

    @Override
    public boolean isExist(String email, String password) throws SQLException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        params.put(2, password);
        List<?> list = JDBCUtils.runQueryWithResult(IS_COMPANY_EXIST, params);
        result = ConvertUtils.ObjectToBool((Map<String, Object>) list.get(0));
        return result;
    }

    @Override
    public Boolean isExistByEmail(String email) throws SQLException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        List<?> list = JDBCUtils.runQueryWithResult(IS_COMPANY_EXIST_BY_EMAIL, params);
        if (!list.isEmpty()) {
            result = ConvertUtils.ObjectToBool((Map<String, Object>) list.get(0));
        }
        return result;
    }

    public Boolean isExistByName(String name) throws SQLException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, name);
        List<?> list = JDBCUtils.runQueryWithResult(IS_COMPANY_EXIST_BY_NAME_STRING, params);
        if (!list.isEmpty()) {
            result = ConvertUtils.ObjectToBool((Map<String, Object>) list.get(0));
        }

        return result;
    }

    public Company getSingleByMail(String email) throws SQLException {
        Company company = null;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        List<Map<String, Object>> list = (List<Map<String, Object>>) JDBCUtils.runQueryWithResult(GET_SINGLE_COMPANY_BY_MAIL, params);
        if (list != null && !list.isEmpty()) {
            company = ConvertUtils.ObjectToCompany(list.get(0));
        }
        return company;
    }

}
