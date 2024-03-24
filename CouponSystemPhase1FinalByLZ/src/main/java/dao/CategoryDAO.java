package dao;

import enums.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDAO {
	void add(String name) throws SQLException;
	List<Category> getAll() throws SQLException;

}
