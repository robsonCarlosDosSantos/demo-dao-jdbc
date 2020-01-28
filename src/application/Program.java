package application;

import java.util.Date;

import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		Department department = new Department(1,"Sport");
		Seller seller = new Seller(1,"Robson","robson@teste.com",new Date(),3000.0,department);
		System.out.println(seller);
	}

}
