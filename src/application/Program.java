package application;

import java.util.List;

import model.dao.FactoryDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		
		SellerDao sellerDao = FactoryDao.createSellerDao();
		//findById
		Seller seller = sellerDao.findById(3);
		System.out.println("=============> findById <==============");
		System.out.println(seller);
		
		//findDepartmentById
		Department department = new Department(2,null);
		List<Seller> listSeller = sellerDao.findDepartmentById(department);
		System.out.println("=============> findDepartmentById <==============");
		for (Seller seller2 : listSeller) {
			System.out.println(seller2);
		}
		
		//findAll
		listSeller = sellerDao.findAll();
		System.out.println("=============> findAll <==============");
		for (Seller seller2 : listSeller) {
			System.out.println(seller2);
		}
		
		//insert
		//seller = new Seller(null,"teste teste","teste",new Date(),1000.0,department);
		//sellerDao.insert(seller);
		System.out.println("=============> insert <==============");
		System.out.println(seller);
		
		//update
		seller.setId(11);
		seller.setEmail("teste@teste.com");
		//sellerDao.update(seller);
		System.out.println("=============> update <==============");
		System.out.println(seller);
		
		//delete
		sellerDao.deleteById(seller.getId());
		System.out.println("=============> delete <==============");
		System.out.println("Deleted the seller id: "+seller.getId());

	}

}
