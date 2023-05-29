package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.ProductDao;
import model.entities.Category;
import model.entities.Product;

public class ProductDaoJDBC implements ProductDao {
	
	private Connection conn;
	
	public ProductDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Product obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO product (name, category_id, price) "
					+ "VALUES (?,?,?);",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			st.setInt(2, obj.getCategory().getId());
			st.setDouble(3, obj.getPrice());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
					
			} else {
				throw new DbException("Unexpeted error! No rows affected!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Product obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE product SET name = ?, category_id = ?, price = ? "
					+ "WHERE id = ?; ");
			
			st.setString(1, obj.getName());
			st.setInt(2, obj.getCategory().getId());
			st.setDouble(3, obj.getPrice());
			st.setInt(4, obj.getId());
			
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}
		

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"DELETE FROM product WHERE id = ?; ");
			
			st.setInt(1, id);
			
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public Product findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT p.id, p.name, p.category_id, p.price, c.name AS category_name "
					+ "FROM product p INNER JOIN category c "
					+ "ON c.id = p.category_id "
					+ "WHERE p.id = ?; ");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				Category category = instantiateCategory(rs);
				Product product = instantiateProduct(rs,category);
				return product;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Product instantiateProduct(ResultSet rs, Category category) throws SQLException {
		Product product = new Product();
		product.setId(rs.getInt("id"));
		product.setName(rs.getString("name"));
		product.setCategory(category);
		product.setPrice(rs.getDouble("price"));
		return product;
	}

	private Category instantiateCategory(ResultSet rs) throws SQLException {
		Category category = new Category();
		category.setId(rs.getInt("category_id"));
		category.setName(rs.getString("category_name"));
		return category;
	}

	@Override
	public List<Product> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT p.id, p.name, p.category_id, p.price, c.name AS category_name "
					+ "FROM product p INNER JOIN category c "
					+ "ON c.id = p.category_id "
					+ "ORDER BY p.id; ");
			
			rs = st.executeQuery();
			
			List<Product> list = new ArrayList<>();
			Map<Integer, Category> map = new HashMap<>();
			
			while(rs.next()) {
				Category categoryRs = map.get(rs.getInt("category_id"));
				if (categoryRs == null) {
					categoryRs = instantiateCategory(rs);
					map.put(categoryRs.getId(), categoryRs);
				}

				Product product = instantiateProduct(rs,categoryRs);
				list.add(product);
			}
			
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Product> findByCategory(Category category) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT p.id, p.name, p.category_id, p.price, c.name AS category_name "
					+ "FROM product p INNER JOIN category c "
					+ "ON c.id = p.category_id "
					+ "WHERE c.id = ? "
					+ "ORDER BY p.id; ");
			
			st.setInt(1, category.getId());
			rs = st.executeQuery();
			
			List<Product> list = new ArrayList<>();
			Map<Integer, Category> map = new HashMap<>();
			
			while(rs.next()) {
				Category categoryRs = map.get(rs.getInt("category_id"));
				if (categoryRs == null) {
					categoryRs = instantiateCategory(rs);
					map.put(categoryRs.getId(), categoryRs);
				}

				Product product = instantiateProduct(rs,categoryRs);
				list.add(product);
			}
			
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
