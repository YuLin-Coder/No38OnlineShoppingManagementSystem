package cn.edu.zhku.shopping.goods.domain;

import cn.edu.zhku.shopping.category.domain.Category;
import cn.edu.zhku.shopping.store.store.domain.Store;


/**
 * 商品模块实体层
 * @author Administrator
 *
 */
public class Goods {

	private String gid;//主键
	private String gname;//商品名称
	private double price;//定价
	private double currPrice;//当前价
	private double discount;//折扣
	private String description;//商品描述
	private String pro_area;//商品产地
	private String units;//商品规格
	
	private Category category;//所属分类
	
	private String image_w;//大图路径
	private String image_b;//小图路径
	
	private Store store;//所属店铺

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getCurrPrice() {
		return currPrice;
	}

	public void setCurrPrice(double currPrice) {
		this.currPrice = currPrice;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPro_area() {
		return pro_area;
	}

	public void setPro_area(String pro_area) {
		this.pro_area = pro_area;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getImage_w() {
		return image_w;
	}

	public void setImage_w(String image_w) {
		this.image_w = image_w;
	}

	public String getImage_b() {
		return image_b;
	}

	public void setImage_b(String image_b) {
		this.image_b = image_b;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}
	
	
	
	
}
