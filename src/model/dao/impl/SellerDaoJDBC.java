package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DBException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{
	
	private Connection conn = null;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("insert into seller (name, email, birthDate, baseSalary, departmentId) "
									 + "values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, obj.getName());
			ps.setString(2, obj.getEmail());
			ps.setDate(3, new Date(obj.getBirthDate().getTime()));
			ps.setDouble(4, obj.getBaseSalary());
			ps.setInt(5,obj.getDepartment().getId());
			
			int resultRow = ps.executeUpdate();
			if(resultRow > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DBException("Unexpected error! no rows affected!");
			}
			
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closePreparedStatement(ps);
		}
		
	}

	@Override
	public void update(Seller obj) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("update seller set name = ?, email = ?, birthDate = ?, baseSalary = ?, departmentId = ? "
									 + "where id = ?");
			
			ps.setString(1, obj.getName());
			ps.setString(2, obj.getEmail());
			ps.setDate(3, new Date(obj.getBirthDate().getTime()));
			ps.setDouble(4, obj.getBaseSalary());
			ps.setInt(5,obj.getDepartment().getId());
			ps.setInt(6, obj.getId());
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closePreparedStatement(ps);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("delete from seller where id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch(SQLException e) {
			throw new DBException(e.getMessage()); 
		} finally {
			DB.closePreparedStatement(ps);
		}
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("Select seller.*, department.name as DepName from seller"
					                                    + " inner join department on seller.departmentId = department.id"
					                                    + " where seller.id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				Department department = instantiateDepartment(rs);
				Seller seller  = instantiateSeller(rs, department);
				return seller;
			}
			return null;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closePreparedStatement(ps);
		}
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("select seller.*, department.Name as DepName from seller "
					+ "inner join department on seller.DepartmentId = department.Id order by name");

			rs = ps.executeQuery();
			
			List<Seller> listSeller = new ArrayList<>();
			Map<Integer,Department> map = new HashMap<>();
			
			while(rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				if(dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				} 
				
				listSeller.add(instantiateSeller(rs, dep));	
			}
			return listSeller;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closePreparedStatement(ps);
		}
	}
	
	@Override
	public List<Seller> findDepartmentById(Department department) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("select seller.*, department.Name as DepName from seller "
					+ "inner join department on seller.DepartmentId = department.Id "
					+ "where seller.DepartmentId = ? order by name");
			ps.setInt(1, department.getId());
			rs = ps.executeQuery();
			
			List<Seller> listSeller = new ArrayList<>();
			Map<Integer,Department> map = new HashMap<>();
			
			while(rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				if(dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				} 
				
				listSeller.add(instantiateSeller(rs, dep));	
			}
			return listSeller;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closePreparedStatement(ps);
		}
	}
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department department = new Department();
		department.setId(rs.getInt("departmentId"));
		department.setName(rs.getString("depName"));
		return department;
	}
	
	private Seller instantiateSeller(ResultSet rs, Department department) throws SQLException {
		Seller seller = new Seller();
		seller.setId(rs.getInt("id"));
		seller.setName(rs.getString("name"));
		seller.setEmail(rs.getString("email"));
		seller.setBirthDate(rs.getDate("birthDate"));
		seller.setBaseSalary(rs.getDouble("baseSalary"));
		seller.setDepartment(department);
		return seller;
	}

}
