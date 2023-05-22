package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Category;

public class CategoryService {
	public List<Category> findAll() {
		ArrayList<Category> list = new ArrayList<>();
		list.add(new Category(1, "Tododia"));
		list.add(new Category(2, "Ekos"));
		list.add(new Category(3, "Essencial"));
		return list;
	}

}
