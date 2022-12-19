package Javacore_BTL;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Categories implements ICategories{
	private Integer catalogId;
	private String catalogName;
	private String descriptions;
	private Boolean catalogStatus;
	private Integer parentId;
	
	public Categories() {
		// TODO Auto-generated constructor stub
	}
	
	public Categories(Integer catalogId, String catalogName, String descriptions, Boolean catalogStatus,
			Integer parentId) {
		super();
		this.catalogId = catalogId;
		this.catalogName = catalogName;
		this.descriptions = descriptions;
		this.catalogStatus = catalogStatus;
		this.parentId = parentId;
	}

	public Integer getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(Integer catalogId) {
		this.catalogId = catalogId;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public Boolean getCatalogStatus() {
		return catalogStatus;
	}

	public void setCatalogStatus(Boolean catalogStatus) {
		this.catalogStatus = catalogStatus;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Override
	public void inputData() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.println("Nhap mo ta danh muc: ");
			descriptions = sc.nextLine();
			if(descriptions.length() <= 0) {
				System.out.println("Mo ta danh muc khong duoc de trong. Nhap lai: ");
			}else {
				break;
			}
		}
		
		System.out.println("Nhap trang thai danh muc:");
		catalogStatus = Boolean.parseBoolean(sc.nextLine());
		
		
	}

	@Override
	public void displayData() {
		// TODO Auto-generated method stub
		System.out.println("Ma danh muc: " + catalogId + " - Ten danh muc: " + catalogName);
		System.out.println("Mo ta: " + descriptions);
		System.out.println("Danh muc cha: " + parentId + " - Trang thai: " + (catalogStatus ? "Hoat dong":"Khong hoat dong"));
	}

	@Override
	public String toString() {
		return catalogId + ";" + catalogName + ";" + descriptions
				+ ";" + (catalogStatus ? true:false) + ";" + parentId;
	}
	
	
}
