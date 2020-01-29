package application;

import java.util.List;

import model.dao.DepartmentDao;
import model.dao.FactoryDao;
import model.entities.Department;

public class Program1 {

	public static void main(String[] args) {
		
		DepartmentDao departmentDao = FactoryDao.createDepartmentDao();
		
		//findById
		Department department = departmentDao.findById(4);
		System.out.println("=============> findById <==============");
		System.out.println(department);
		
		//findAll
		List<Department> listDepartment = departmentDao.findAll();
		System.out.println("=============> findAll <==============");
		for (Department department2 : listDepartment) {
			System.out.println(department2);
		}
		
		//insert
		Department department2 = new Department(null,"Gamer");
		//departmentDao.insert(department2);
		System.out.println("=============> insert <==============");
		System.out.println(department2);

		//update
		//department2.setId(8);
		//department2.setName("Gamers");
		//departmentDao.update(department2);
		System.out.println("=============> update <==============");
		System.out.println(department2);
		
		//delete
		departmentDao.deleteById(8);
		System.out.println("=============> delete <==============");
		System.out.println("deleted department");
	}

}
