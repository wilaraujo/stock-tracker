package model.dao;

import db.DB;
import model.dao.impl.CategoryDaoJDBC;
import model.dao.impl.ProductDaoJDBC;

public class DaoFactory {

	public static ProductDao createProductDao() {
		return new ProductDaoJDBC(DB.getConnection());
	}
	
	public static CategoryDao createCategoryDao() {
		return new CategoryDaoJDBC(DB.getConnection());
	}
}
