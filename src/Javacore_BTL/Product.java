package Javacore_BTL;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class Product implements IProduct{
	
	private String productId;
	private String productName;
	private String title;
	private Float importPrice;
	private Float exportPrice;
	private Float profit;
	private String descriptions;
	private Boolean productStatus;
	private Integer catalog;
	
	public Product() {
		// TODO Auto-generated constructor stub
	}
	
	public Product(String productId, String productName, String title, Float importPrice, Float exportPrice,
			Float profit, String descriptions, Boolean productStatus, Integer catalog) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.title = title;
		this.importPrice = importPrice;
		this.exportPrice = exportPrice;
		this.profit = profit;
		this.descriptions = descriptions;
		this.productStatus = productStatus;
		this.catalog = catalog;
	}

	public String getProductId() {
		return productId;
	}



	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public Float getImportPrice() {
		return importPrice;
	}



	public void setImportPrice(Float importPrice) {
		this.importPrice = importPrice;
	}



	public Float getExportPrice() {
		return exportPrice;
	}



	public void setExportPrice(Float exportPrice) {
		this.exportPrice = exportPrice;
	}



	public Float getProfit() {
		return profit;
	}



	public void setProfit(Float profit) {
		this.profit = profit;
	}



	public String getDescriptions() {
		return descriptions;
	}



	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}



	public Boolean getProductStatus() {
		return productStatus;
	}



	public void setProductStatus(Boolean productStatus) {
		this.productStatus = productStatus;
	}



	public Integer getCatalog() {
		return catalog;
	}



	public void setCatalog(Integer catalog) {
		this.catalog = catalog;
	}



	@Override
	public void inputData() {
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.println("Nhap tieu de san pham:");
			title = sc.nextLine();
			if(title.length() < 6 || title.length() > 50) {
				System.out.println("Tieu de phai co tu 6 den 50 ky tu. Nhap lai:");
			}else {
				break;
			}
		}

		while(true) {
			System.out.println("Nhap gia nhap san pham:");
			try {
				importPrice = Float.parseFloat(sc.nextLine());
				if(importPrice <= 0) {
					System.out.println("Gia nhap phai lon hon 0. Nhap lai");
				}else {
					break;
				}
			} catch (Exception e) {
				System.out.println("Gia nhap phai la so thuc. Nhap lai");
			}
		}
		
		while(true) {
			System.out.println("Nhap gia ban san pham:");
			try {
				exportPrice = Float.parseFloat(sc.nextLine());
				if(exportPrice <= ((importPrice * MIN_INTEREST_RATE) + importPrice)) {
					System.out.println("Gia ban phai lon hon gia nhap " + MIN_INTEREST_RATE + " lan. Nhap lai:");
				}else {
					break;
				}
			} catch (Exception e) {
				System.out.println("Gia ban phai la so thuc. Nhap lai");
			}
		}
		
		while(true) {
			System.out.println("Nhap mo ta san pham");
			descriptions = sc.nextLine();
			if(descriptions.length() <= 0) {
				System.out.println("Mo ta san pham khong duoc de trong. Nhap lai:");
			}else {
				break;
			}
		}
		
		System.out.println("Nhap trang thai san pham:");
		productStatus = Boolean.parseBoolean(sc.nextLine());
	}

	@Override
	public void displayData() {
		Locale localeVN = new Locale("vi", "VN");
		NumberFormat vn = NumberFormat.getInstance(localeVN);
		// TODO Auto-generated method stub
		System.out.println("Ma san pham: " + productId + " - Ten san pham: " + productName);
		System.out.println("Tieu de: " + title);
		System.out.println("Mo ta: " + descriptions);
		System.out.println("Gia nhap: " + vn.format(importPrice) + " - Gia ban: " + vn.format(exportPrice));
		System.out.println("Danh muc cha: " + catalog + " - Trang thai: " + (productStatus ? "Hoat dong":"Khong hoat dong"));
	}

	@Override
	public void calProfit() {
		Locale localeVN = new Locale("vi", "VN");
		NumberFormat vn = NumberFormat.getInstance(localeVN);
		// TODO Auto-generated method stub
		profit = exportPrice - importPrice;
		System.out.println("Loi nhuan san pham: " +  vn.format(profit));
	}

	@Override
	public String toString() {
		return productId + ";" + productName + ";" + title
				+ ";" + importPrice + ";" + exportPrice + ";" + (exportPrice - importPrice)
				+ ";" + descriptions + ";" + (productStatus ? true:false) + ";" + catalog;
	}
	
}
