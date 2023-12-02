package cn.edu.zhku.shopping.store.store.domain;

import cn.edu.zhku.shopping.category.domain.Category;
import cn.edu.zhku.shopping.user.domain.User;

public class Store {

	private String sid;//主键
	private String sname;//店铺名称
	
	private User user;//所属用户
	private Category category;//开店类型
	
	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	
}
