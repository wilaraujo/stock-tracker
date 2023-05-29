package model.services;

import java.util.List;

import model.dao.CategoryDao;
import model.dao.DaoFactory;
import model.entities.Category;

public class CategoryService {
	
	private CategoryDao dao = DaoFactory.createCategoryDao();
	
	public List<Category> findAll() {
		return dao.findAll();
	}

}
