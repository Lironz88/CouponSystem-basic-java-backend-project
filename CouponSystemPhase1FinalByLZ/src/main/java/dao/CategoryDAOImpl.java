package dao;

import enums.Category;
import db.ConvertUtils;
import db.JDBCUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryDAOImpl implements CategoryDAO {

    private static final String INSERT_CATEGORY = "INSERT INTO `java-151-cs-3`.`categories` (`name`) VALUES (?)";
    private static final String GET_ALL = "SELECT * FROM `java-151-cs-3`.categories";

    @Override
    public void add(String name) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, name);
        JDBCUtils.runQuery(INSERT_CATEGORY, params);

    }

    @Override
    public List<Category> getAll() throws SQLException {
        List<Category> categories = new ArrayList<>();
        List<?> list = JDBCUtils.runQueryWithResult(GET_ALL);
        for (Object obj : list) {
            categories.add(ConvertUtils.ObjectToCategory((Map<String, Object>) obj));
        }
        return categories;
    }

}
