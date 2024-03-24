package db;

import beans.Company;
import beans.Coupon;
import beans.Customer;
import enums.Category;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertUtils {
    public static List<?> toList(ResultSet resultSet) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        ResultSetMetaData resultSetMetaData = (ResultSetMetaData) resultSet.getMetaData();
        final int columnCount = resultSetMetaData.getColumnCount();
        while (resultSet.next()) {
            Object[] values = new Object[columnCount];
            Map<String, Object> keyAndValue = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                values[i - 1] = resultSet.getObject(i);
                keyAndValue.put(resultSetMetaData.getColumnName(i), values[i - 1]);
            }
            list.add(keyAndValue);
        }
        return list;
    }

    public static boolean ObjectToBool(Map<String, Object> map) {
        return !map.isEmpty();
    }

    public static Customer ObjectToCustomer(Map<String, Object> map) {
        int id = (Integer) map.get("id");
        String firstName = (String) map.get("first_name"); // Adjusted to match column name
        String lastName = (String) map.get("last_name"); // Adjusted to match column name
        String email = (String) map.get("email");
        String password = (String) map.get("password");
        return new Customer(id, firstName, lastName, email, password);
    }

    public static Category ObjectToCategory(Map<String, Object> map) {
        String categoryName = (String) map.get("categoryName");
        return Category.valueOf(categoryName.toUpperCase());
    }

    public static Company ObjectToCompany(Map<String, Object> map) {
        int id = (Integer) map.get("id");
        String name = (String) map.get("name");
        String email = (String) map.get("email");
        String password = (String) map.get("password");
        return new Company(id, name, email, password);
    }

    public static Coupon ObjectToCoupon(Map<String, Object> map) {
        int id = (int) map.get("id");
        int companyId = (Integer) map.get("company_id");
        int categoryIndex = (Integer) map.get("category_id") - 1;
        Category category = Category.values()[categoryIndex];
        String title = (String) map.get("title");
        String description = (String) map.get("description");
        Date startDate = (Date) map.get("start_date");
        Date endDate = (Date) map.get("end_date");
        int amount = (Integer) map.get("amount");
        double price = (Double) map.get("price");
        String image = (String) map.get("image");
        return new Coupon(id, companyId, category, title, description, startDate, endDate, amount, price, image);
    }
}
