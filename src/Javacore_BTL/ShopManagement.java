package Javacore_BTL;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.nio.Buffer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


public class ShopManagement {
	
	//Khai bao list
	private static List<Categories> listC = new ArrayList<Categories>();
	private static List<Product> listP = new ArrayList<Product>();
	
	//Main
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int choice = 0;
		ShopManagement sm = new ShopManagement();
		listC = sm.readFromFileCategories();
		listP = sm.readFromFileProduct();
		while(true) {
			System.out.println("*********************MENU*********************");
			System.out.println("1. Quan ly danh muc");
			System.out.println("2. Quan ly san pham"); //Done
			System.out.println("3. Thoat");
			System.out.println("Nhap lua chon cua ban[1-3]:");
			try {
				choice = Integer.parseInt(sc.nextLine());
			} catch (Exception e) {
			}
			
			switch (choice) {
			case 1:
				quanLyDanhMuc();
				break;
			case 2:
				quanLySanPham();
				break;
			case 3:
				System.out.println("Chuong trinh da tat");
				System.exit(0);
				break;

			default:
				System.out.println("Vui long nhap gia tri so nguyen tu 1 den 3");
				break;
			}
		}
	}
	
	//Quan ly danh muc
	public static void quanLyDanhMuc() {
		boolean check = true;
		Scanner sc = new Scanner(System.in);
		int choice = 0;
		
		while(check) {
			System.out.println("*********************QUAN LY DANH MUC*********************");
			System.out.println("1. Danh sach danh muc"); //Not yet
			System.out.println("2. Them danh muc"); //Done
			System.out.println("3. Xoa danh muc"); //Done
			System.out.println("4. Tim kiem danh muc"); //Done
			System.out.println("5. Quay lai");
			System.out.println("Nhap lua chon cua ban[1-5]:");
			try {
				choice = Integer.parseInt(sc.nextLine());
			} catch (Exception e) {
			}
			
			switch (choice) {
			case 1:
				
				if(listC.size() <= 0) {
					System.out.println("Danh sach danh muc rong");
				}else {
					danhSachDanhMuc();
				}
				break;
			case 2:
				Categories c = new Categories();
				while(true) {
					System.out.println("Nhap ma danh muc:");
					try {
						int id = Integer.parseInt(sc.nextLine());
						if(id <= 0) {
							System.out.println("Ma danh muc can lon hon 0. Nhap lai:");
						}else {
							boolean existed = false;
							
							for (Categories categories : listC) {
								if(categories.getCatalogId() == id) {
									existed = true;
									break;
								}
							}
							
							if(existed) {
								System.out.println("Ma danh muc da ton tai. Nhap ma khac:");
							}else {
								c.setCatalogId(id);
								break;
							}
						}
					} catch (Exception e) {
						System.out.println("Ma danh muc phai lai 1 so nguyen. Nhap lai:");
					}
				}
				
				while(true) {
					System.out.println("Nhap ten danh muc: ");
					String cateName = sc.nextLine();
					if(cateName.length() < 6 || cateName.length() > 30) {
						System.out.println("Ten danh muc co tu 6-30 ky tu. Nhap lai: ");
					}else {
						boolean existed = false;
						
						for (Categories categories : listC) {
							if(categories.getCatalogName().equalsIgnoreCase(cateName)) {
								existed = true;
								break;
							}
						}
						
						if(existed) {
							System.out.println("Ten danh muc da ton tai. Nhap lai:");
						}else {
							c.setCatalogName(cateName);
							break;
						}
					}
				}
				
				c.inputData();
				
				while(true) {
					System.out.println("Nhap ma danh muc cha: ");
					try {
						int parentId = Integer.parseInt(sc.nextLine());
						
						boolean existed = false;
						for (Categories categories : listC) {
							if(categories.getCatalogId() == parentId) {
								existed = true;
							}
						}
						
						if(existed || parentId == 0) {
							c.setParentId(parentId);
							break;
						}else {
							System.out.println("Ma danh muc cha chua ton tai. Nhap lai:");
						}
					} catch (Exception e) {
						System.out.println("Ma danh muc cha phai la 1 so nguyen. Nhap lai:");
					}
				}
				
				listC.add(c);
				
				writeToFileCategories(c);
				
				break;
			case 3:
				if(listC.size() <= 0) {
					System.out.println("Danh sach danh muc rong. Khong the xoa");
				}else {
					System.out.println("Nhap ma danh muc ban muon xoa:");
					while(true) {
						try {
							int cataId = Integer.parseInt(sc.nextLine());
							List<Categories> cateFound = new ArrayList<Categories>();
							boolean existedProduct = false;
							boolean existedCate = false;
							for (Categories categories : listC) {
								if(categories.getCatalogId() == cataId) {
									cateFound.add(categories);
									for (Categories categories2 : listC) {
										if(categories2.getParentId() == cataId) {
											cateFound.add(categories2);
											for (Product product : listP) {
												if(product.getCatalog() == categories2.getCatalogId()) {
													existedProduct = true;
													break;
												}
											}
											
											for (Categories categories3 : listC) {
												if(categories3.getParentId() == categories2.getCatalogId()) {
													cateFound.add(categories3);
													existedCate = true;
													break;
												}
											}
											existedCate = true;
										}
									}
									
									for (Product product : listP) {
										if(product.getCatalog() == cataId) {
											existedProduct = true;
											break;
										}
									}
									existedCate = true;
									break;
								}
							}
							
							if(existedProduct == true) {
								System.out.println("Danh muc co san pham, khong the xoa.");
								break;
							}else if(cateFound != null && existedCate == true) {
								
								listC.removeAll(cateFound);
								System.out.println("Danh muc da duoc xoa");
								
								updateCategoriesFile();
								break;
							}else {
								System.out.println("Ma danh muc khong ton tai. Nhap lai:");
							}
						} catch (Exception e) {
							System.out.println("Ma danh muc la mot so nguyen lon hon 0. Nhap lai:");
						}
					}
				}
				break;
			case 4:
				if(listC.size() <= 0) {
					System.out.println("Danh sach danh muc rong. Khong the tim kiem");
				}else {
					System.out.println("Nhap vao ten danh muc can xem thong tin:");
					while(true) {
						String name = sc.nextLine();
						boolean exsited = false;
						
						System.out.println("Thong tin danh muc:");
						for (Categories categories : listC) {
							if(categories.getCatalogName().equalsIgnoreCase(name)) {
								exsited = true;
								categories.displayData();
							}
						}
						
						if(exsited == false) {
							System.out.println("Ten danh muc khong ton tai. Nhap lai:");
						}else {
							break;
						}
					}
				}
				break;
			case 5:
				check = false;
				break;
			default:
				System.out.println("Vui long nhap gia tri so nguyen tu 1 den 3");
				break;
			}
		}
	}
	
	//Danh sach danh muc
	public static void danhSachDanhMuc() {
		boolean check = true;
		Scanner sc = new Scanner(System.in);
		int choice = 0;
		while(check) {
			System.out.println("*********************DANH SACH DANH MUC*********************");
			System.out.println("1. Danh sach cay danh muc");	
			System.out.println("2. Thong tin chi tiet danh muc"); // Done
			System.out.println("3. Quay lai");
			System.out.println("Nhap lua chon cua ban[1-3]:");
			try {
				choice = Integer.parseInt(sc.nextLine());
			} catch (Exception e) {
			}
			
			switch (choice) {
			case 1:
				//Toi da 3 cap
				int index = 1;
				for (Categories categories : listC) {
					if(categories.getParentId() == 0) {
						System.out.println(index + " " + categories.getCatalogName());
						int index2 = 1;
						for (Categories categories2 : listC) {
							if(categories2.getParentId() == categories.getCatalogId()) {
								System.out.println("\t" + index + "." + index2 + " " + categories2.getCatalogName());
								int index3 = 1;
								for (Categories categories3 : listC) {
									if(categories3.getParentId() == categories2.getCatalogId()) {
										System.out.println("\t\t" + index + "." + index2 + "." + index3 + " " + categories3.getCatalogName());
										int indexP3 = 1;
										for (Product product : listP) {
											System.out.println("\t\t\t" + index + "." + index2 + "." + index3 + "." + indexP3 + " " + product.getProductName());
											indexP3++;
										}
										index3++;
									}
								}
								
								int indexP2 = 1;
								for (Product product : listP) {
									if(product.getCatalog() == categories2.getCatalogId()) {
										System.out.println("\t\t" + index + "." + index2 + "." + indexP2 + " " + product.getProductName());
										indexP2++;
									}
								}
								index2++;
							}
						}
						
						int indexP = 1;
						for (Product product : listP) {
							if(product.getCatalog() == categories.getCatalogId()) {
								System.out.println("\t" + index + "." + indexP + " " + product.getProductName());
							}
						}
						index++;
					}
				}
				break;
			case 2:
				if(listC.size() <= 0) {
					System.out.println("Danh sach danh muc rong. Khong the tim kiem");
				}else {
					System.out.println("Nhap vao ten danh muc can xem thong tin:");
					while(true) {
						String name = sc.nextLine();
						boolean exsited = false;
						
						System.out.println("Thong tin danh muc:");
						for (Categories categories : listC) {
							if(categories.getCatalogName().equalsIgnoreCase(name)) {
								exsited = true;
								categories.displayData();
								break;
							}
						}
						
						if(exsited == false) {
							System.out.println("Ten danh muc khong ton tai. Nhap lai:");
						}else {
							break;
						}
					}
				}
				break;
			case 3:
				check = false;
				break;
				
			default:
				System.out.println("Vui long nhap gia tri so nguyen tu 1 den 3");
				break;
			}
		}
	}
	
	//Quan ly san pham
	public static void quanLySanPham() {
		boolean check = true;
		Scanner sc = new Scanner(System.in);
		int choice = 0;
		
		while(check) {
			System.out.println("*********************QUAN LY SAN PHAM*********************");
			System.out.println("1. Them san pham moi"); //Done
			System.out.println("2. Tinh loi nhuan san pham"); //Done
			System.out.println("3. Hien thi thong tin san pham"); //Done
			System.out.println("4. Sap xep san pham"); //Done
			System.out.println("5. Cap nhat thong tin san pham"); //Done
			System.out.println("6. Cap nhat trang thai san pham"); //Done
			System.out.println("7. Quay lai");
			System.out.println("Nhap lua chon cua ban[1-7]:");
			try {
				choice = Integer.parseInt(sc.nextLine());
			} catch (Exception e) {
			}
			
			switch (choice) {
			case 1:
				if(listC.size() <= 0) {
					System.out.println("Danh sach danh muc rong. Khong the them san pham");
				}else {
					Product p = new Product();
					while(true) {
						System.out.println("Nhap ma san pham:");
						String idProduct = sc.nextLine();
						
						if(idProduct.charAt(0) != 'C') {
							System.out.println("Ma san pham bat dau bang ky tu C. Nhap lai:");
						}else {
							if(idProduct.length() != 4) {
								System.out.println("Ma san phan phai co 4 ky tu. Nhap lai:");
							}else {
								boolean exsited = false;
								for (Product product : listP) {
									if(product.getProductId().equalsIgnoreCase(idProduct)) {
										exsited = true;
										break;
									}
								}
								
								if(exsited) {
									System.out.println("Ma san pham da ton tai. Nhap lai:");
								}else {
									p.setProductId(idProduct);
									break;
								}
							}
						}
					}
					
					while(true) {
						System.out.println("Nhap ten san pham:");
						String nameProduct = sc.nextLine();
						
						if(nameProduct.length() < 6 || nameProduct.length() > 50) {
							System.out.println("Ten danh muc co tu 6-30 ky tu. Nhap lai:");
						}else {
							boolean exsited = false;
							for (Product product : listP) {
								if(product.getProductName().equalsIgnoreCase(nameProduct)) {
									exsited = true;
									break;
								}
							}
							
							if(exsited) {
								System.out.println("Ten san pham da ton tai. Nhap lai:");
							}else {
								p.setProductName(nameProduct);
								break;
							}
						}
					}
					
					p.inputData();
					
					while(true) {
						System.out.println("Nhap danh muc san pham cua san pham:");
						try {
							int catelog = Integer.parseInt(sc.nextLine());
							boolean exsited = false;
							for (Categories categories : listC) {
								if(categories.getCatalogId() == catelog) {
									exsited = true;
									break;
								}
							}
							
							if(exsited) {
								p.setCatalog(catelog);
								break;
							}else {
								System.out.println("Khong ton tai danh muc nay. Nhap lai");
							}
						} catch (Exception e) {
							System.out.println("Ma danh muc cha phai la mot so nguyen");
						}
					}
					listP.add(p);
					writeToFileProduct(p);
				}
				break;
			case 2:
				if(listP.size() <= 0){
					System.out.println("Danh sach san pham rong");
				}else {
					Collections.sort(listP, new Comparator<Product>() {

						@Override
						public int compare(Product p1, Product p2) {
							// TODO Auto-generated method stub
							return p1.getProductId().compareTo(p2.getProductId());
						}
					});
					
					Locale localeVN = new Locale("vi", "VN");
					NumberFormat vn = NumberFormat.getInstance(localeVN);
					System.out.println("Thong tin loi nhuan san pham:");
					System.out.printf("%-13s|%-20s|%-20s|%-15s\n", "Ma san pham", "Ten san pham", "Loi nhuan", "Trang thai");
					System.out.println("-------------------------------------------------------------------------");
					for (Product product : listP) {
						product.calProfit();
						System.out.printf("%-13s|%-20s|%-20s|%-15s\n", product.getProductId(), product.getProductName()
								, vn.format(product.getProfit()), (product.getProductStatus() ?"Hoat dong":"Khong hoat dong"));
					}
					System.out.println("-------------------------------------------------------------------------");
				}
				break;
			case 3:
				if(listP.size() <= 0){
					System.out.println("Danh sach san pham rong");
				}else {
					thongTinSanPham();
				}
				break;
			case 4:
				if(listP.size() <= 0) {
					System.out.println("Danh sach san pham rong. Khong the sap xep");
				}else {
					sapXepSanPham();
				}
				break;
			case 5:
				//Cap nhat thong tin san pham
				while(true) {
					System.out.println("Nhap ma san pham ban muon cap nhat thong tin:");
					String productId = sc.nextLine();
					boolean exsited = false;
					for (Product product : listP) {
						if(product.getProductId().equalsIgnoreCase(productId)) {
							exsited = true;
							while(true) {
								System.out.println("Nhap ten san pham moi:");
								String nameProduct = sc.nextLine();
								
								if(nameProduct.length() < 6 || nameProduct.length() > 50) {
									System.out.println("Ten danh muc co tu 6-30 ky tu. Nhap lai:");
								}else {
									boolean exsited2 = false;
									for (Product product2 : listP) {
										if(product2.getProductName().equalsIgnoreCase(nameProduct)) {
											exsited2 = true;
											break;
										}
									}
									
									if(exsited2) {
										System.out.println("Ten san pham da ton tai. Nhap lai:");
									}else {
										product.setProductName(nameProduct);
										break;
									}
								}
							}
							
							product.inputData();
							
							while(true) {
								System.out.println("Nhap danh muc san pham cua san pham:");
								try {
									int catelog = Integer.parseInt(sc.nextLine());
									boolean exsited3 = false;
									for (Categories categories : listC) {
										if(categories.getCatalogId() == catelog) {
											exsited3 = true;
											break;
										}
									}
									
									if(exsited3) {
										product.setCatalog(catelog);
										break;
									}else {
										System.out.println("Khong ton tai danh muc nay. Nhap lai");
									}
								} catch (Exception e) {
									System.out.println("Ma danh muc cha phai la mot so nguyen");
								}
							}
						}
					}
					
					if(exsited) {
						System.out.println("Da cap nhat thong tin cua san pham");
						updateProductsFile();
						break;
					}else {
						System.out.println("Ma san pham khong ton tai. Nhap lai:");
					}
				}
				break;
			case 6:
				//Cap nhat trang thai san pham
				while(true) {
					System.out.println("Nhap ma san pham ban muon cap nhat trang thai:");
					String idProduct = sc.nextLine();
					boolean existed = false;
					for (Product product : listP) {
						if(product.getProductId().equalsIgnoreCase(idProduct)) {
							existed = true;
							System.out.println("Nhap trang thai moi cho san pham:");
							product.setProductStatus(Boolean.parseBoolean(sc.nextLine()));
							break;
						}
					}
					
					if(existed) {
						System.out.println("Da cap nhat trang thai cua san pham");
						updateProductsFile();
						break;
					}else {
						System.out.println("Ma san pham khong ton tai. Nhap lai:");
					}
				}
				break;
			case 7:
				check = false;
				break;
			default:
				System.out.println("Vui long nhap gia tri so nguyen tu 1 den 3");
				break;
			}
		}
	}
	
	//Thong tin danh muc
	public static void thongTinSanPham() {
		boolean check = true;
		Scanner sc = new Scanner(System.in);
		int choice = 0;
		
		while(check) {
			System.out.println("*********************THONG TIN SAN PHAM*********************");
			System.out.println("1. Hien thi san pham theo danh muc"); //Done
			System.out.println("2. Hien thi chi tiet san pham"); //Done
			System.out.println("3. Quay lai");
			System.out.println("Nhap lua chon cua ban[1-3]:");
			try {
				choice = Integer.parseInt(sc.nextLine());
			} catch (Exception e) {
			}
			
			switch (choice) {
			case 1:
				if(listC.size() <= 0) {
					System.out.println("Danh sach danh muc rong");
				}else if(listP.size() <= 0){
					System.out.println("Danh sach san pham rong");
				}else {
					boolean exsited = false;
					int indexCate = 1;

					for (Categories categories : listC) {
						for (Product product : listP) {
							if(categories.getCatalogId() == product.getCatalog()) {
								exsited = true;
								break;
							}
						}
						if(exsited) {
							System.out.println("Danh muc "+ indexCate + ": " + categories.getCatalogName());
							indexCate++;
						}

						int indexPro = 1;
						for (Product product : listP) {
							if(categories.getCatalogId() == product.getCatalog()) {
								exsited = true;
								System.out.println("\tSan pham " + indexPro + ": " + product.getProductName());
								indexPro++;
							}
						}
					}
				}
				break;
			case 2:
				if(listP.size() <= 0) {
					System.out.println("Danh sach san pham rong. Khong the tim kiem");
				}else {
					System.out.println("Nhap vao ten san pham can xem thong tin:");
					while(true) {
						String nameProduct = sc.nextLine();
						boolean exsited = false;
						System.out.println("Thong tin san pham:");
						for (Product product : listP) {
							if(product.getProductName().contains(nameProduct)) {
								product.displayData();
							}
						}
						
						if(exsited == false) {
							System.out.println("Ten san pham khong ton tai. Nhap lai:");
						}else {
							break;
						}
					}
				}
				break;
			case 3:
				check = false;
				break;
			default:
				System.out.println("Vui long nhap gia tri so nguyen tu 1 den 3");
				break;
			}
		}
	}
	
	//Sap xep san pham
	public static void sapXepSanPham() {
		boolean check = true;
		Scanner sc = new Scanner(System.in);
		int choice = 0;
		
		while(check) {
			Locale localeVN = new Locale("vi", "VN");
			NumberFormat vn = NumberFormat.getInstance(localeVN);
			System.out.println("*********************SAP XEP SAN PHAM*********************");
			System.out.println("1. Sap xep san pham theo gia ban tang dan"); //Done
			System.out.println("2. Sap xep san pham theo loi nhuan giam dan"); //Done
			System.out.println("3. Quay lai");
			System.out.println("Nhap lua chon cua ban[1-3]:");
			try {
				choice = Integer.parseInt(sc.nextLine());
			} catch (Exception e) {
			}
			
			switch (choice) {
			case 1:
				Collections.sort(listP, new Comparator<Product>() {

					@Override
					public int compare(Product p1, Product p2) {
						// TODO Auto-generated method stub
						return p1.getExportPrice().compareTo(p2.getExportPrice());
					}
				});
				
				System.out.println("Thong tin san pham co gia ban tang dan:");
				System.out.printf("%-13s|%-20s|%-15s|%-10s\n", "Ma san pham", "Ten san pham", "Gia ban", "Trang thai");
				System.out.println("-------------------------------------------------------------------------");
				for (Product product : listP) {
					System.out.printf("%-13s|%-20s|%-15s|%-10s\n", product.getProductId(), product.getProductName(), vn.format(product.getExportPrice()), (product.getProductStatus() ? "Hoat dong":"Khong hoat dong"));
				}
				System.out.println("-------------------------------------------------------------------------");
				break;
			case 2:
				for (Product product : listP) {
					product.calProfit();
				}
				Collections.sort(listP, new Comparator<Product>() {

					@Override
					public int compare(Product p1, Product p2) {
						// TODO Auto-generated method stub
						return p2.getProfit().compareTo(p1.getProfit());
					}
				});
				
				System.out.println("Thong tin san pham co loi nhuan giam dan:");
				System.out.printf("%-13s|%-20s|%-15s|%-10s\n", "Ma san pham", "Ten san pham", "Loi nhuan", "Trang thai");
				System.out.println("-------------------------------------------------------------------------");
				for (Product product : listP) {
					System.out.printf("%-13s|%-20s|%-15s|%-10s\n", product.getProductId(), product.getProductName(), vn.format(product.getProfit()), (product.getProductStatus() ? "Hoat dong":"Khong hoat dong"));
				}
				System.out.println("-------------------------------------------------------------------------");
				break;

			case 3:
				check = false;
				break;


			default:
				System.out.println("Vui long nhap gia tri so nguyen tu 1 den 3");
				break;
			}
		}
	}
	
	//Ghi file danh muc
	public static void writeToFileCategories(Categories categories) {
		try {
			FileWriter fw = new FileWriter("categories.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write(categories.toString());
			bw.newLine();
				
			bw.close();
			fw.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//Ghi file san pham
	public static void writeToFileProduct(Product product) {
		try {
			FileWriter fw = new FileWriter("products.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write(product.toString());
			bw.newLine();
			
			bw.close();
			fw.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//Doc file danh muc
	public List<Categories> readFromFileCategories(){
		List<Categories> list = new ArrayList<Categories>();
		try {
			FileReader fr = new FileReader("categories.txt");
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			while(true) {
				line = br.readLine();
				if(line == null) {
					break;
				}
				
				String[] txt = line.split(";");
				Integer cataId = Integer.parseInt(txt[0]);
				String cataName = txt[1];
				String descriptions = txt[2];
				Boolean status = Boolean.parseBoolean(txt[3]);
				Integer parentId = Integer.parseInt(txt[4]);
				
				list.add(new Categories(cataId, cataName, descriptions, status, parentId));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	//Doc file san pham
	public List<Product> readFromFileProduct(){
		List<Product> list = new ArrayList<Product>();
		try {
			FileReader fr = new FileReader("products.txt");
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			while(true) {
				line = br.readLine();
				if(line == null) {
					break;
				}
				
				String[] txt = line.split(";");
				String proId = txt[0];
				String proName = txt[1];
				String proTitle = txt[2];
				Float improtPrice = Float.parseFloat(txt[3]);
				Float exportPrice = Float.parseFloat(txt[4]);
				Float profit = Float.parseFloat(txt[5]);
				String descriptions = txt[6];
				Boolean status = Boolean.parseBoolean(txt[7]);
				Integer catalog = Integer.parseInt(txt[8]);
				
				list.add(new Product(proId, proName, proTitle, improtPrice, exportPrice
						, profit, descriptions, status, catalog));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	//Cap nhat file san pham sau khi sua
	public static void updateProductsFile() {
		try {
			FileWriter fw = new FileWriter("products.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (Product product : listP) {
				bw.write(product.toString());
				bw.newLine();
			}
			
			bw.close();
			fw.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//Cap nhat file danh muc sau khi xoa
	public static void updateCategoriesFile() {
		try {
			FileWriter fw = new FileWriter("categories.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (Categories categories : listC) {
				bw.write(categories.toString());
				bw.newLine();
			}
				
			bw.close();
			fw.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}