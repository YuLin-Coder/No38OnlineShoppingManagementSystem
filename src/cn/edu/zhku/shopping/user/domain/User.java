package cn.edu.zhku.shopping.user.domain;
/**
 * 用户模块实体类
 * @author lenovo
 *
 */
public class User {
	// 对应数据库表
	private String uid;//主键
	private String loginname;//登录名
	private String loginpass;//登录密码
	private String email;//邮箱
	private int isStore;//是否开店
	// 注册表单
	private String reloginpass;//确认密码
	private String verifyCode;//验证码
	
	// 修改密码表单
	private String newpass;//新密码

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}


	/**
	 * @return the loginname
	 */
	public String getLoginname() {
		return loginname;
	}

	/**
	 * @param loginname the loginname to set
	 */
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	/**
	 * @return the loginpass
	 */
	public String getLoginpass() {
		return loginpass;
	}

	/**
	 * @param loginpass the loginpass to set
	 */
	public void setLoginpass(String loginpass) {
		this.loginpass = loginpass;
	}

	/**
	 * @return the reloginpass
	 */
	public String getReloginpass() {
		return reloginpass;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	/**
	 * @param reloginpass the reloginpass to set
	 */
	public void setReloginpass(String reloginpass) {
		this.reloginpass = reloginpass;
	}

	/**
	 * @return the verifyCode
	 */
	public String getVerifyCode() {
		return verifyCode;
	}

	/**
	 * @param verifyCode the verifyCode to set
	 */
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	/**
	 * @return the newpass
	 */
	public String getNewpass() {
		return newpass;
	}

	/**
	 * @param newpass the newpass to set
	 */
	public void setNewpass(String newpass) {
		this.newpass = newpass;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	
	@Override
	public String toString() {
		return "User [uid=" + uid + ", loginname=" + loginname + ", loginpass="
				+ loginpass + ", email=" + email + ", reloginpass="
				+ reloginpass + ", verifyCode=" + verifyCode + ", newpass="
				+ newpass + "]";
	}

	public int getIsStore() {
		return isStore;
	}

	public void setIsStore(int isStore) {
		this.isStore = isStore;
	}
}
