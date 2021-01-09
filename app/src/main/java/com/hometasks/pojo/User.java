package com.hometasks.pojo;

import java.io.Serializable;
import java.sql.Blob;

public class User implements Serializable {
	private String idUser;
	private String name;
	private String date;
	private int points;
	private String telephone;
	private String password;
	private String email;
	private String profile;
	private transient byte[] picture;

	public User() {}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
}
