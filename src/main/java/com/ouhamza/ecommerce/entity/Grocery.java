package com.ouhamza.ecommerce.entity;
import java.util.Arrays;
import java.util.Objects;

import javax.persistence.*;
@Entity
@Table(name = "grocery")
public class Grocery {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "brand")
	private String brand;
	
	@Column(name = "price")
	private Float price;

	@Column(name = "picByte", length = 10000)
	private byte[] picByte;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public byte[] getPicByte() {
		return picByte;
	}

	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}

	@Override
	public String toString() {
		return "Grocery [id=" + id + ", name=" + name + ", brand=" + brand + ", price=" + price + ", picByte="
				+ Arrays.toString(picByte) + "]";
	}

	
	
}
