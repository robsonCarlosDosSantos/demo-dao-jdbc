package application;

import model.dao.FactoryDao;
import model.dao.SellerDao;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = FactoryDao.createSellerDao();
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
	}

}
