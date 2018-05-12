package com.wz.business.demo.model;

import java.io.Serializable;


/**
 * 
 *
 * @ClassName:  User   
 * @Description:TODO
 * @author: Angkz
 * @date:   2018年3月14日 下午3:16:49   
 *
 */
public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3848313111193514890L;

	private String id;
	
	private String userName;
	
	private String date;
	
	private String password;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}



	public User() {
		super();
	}

	public User(String id, String userName, String date, String password) {
		super();
		this.id = id;
		this.userName = userName;
		this.date = date;
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", date=" + date
				+ ", password=" + password + "]";
	}

	
	
}
