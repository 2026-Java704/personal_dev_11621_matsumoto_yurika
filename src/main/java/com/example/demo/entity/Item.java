package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "items")
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "item_name")
	private String itemName;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "genre_id")
	private Genre genre;

	private Integer price;
	private LocalDate addDate;
	private String comment;

	
	//コンストラクタ
	public Item() {
	}

	public Item(User user, LocalDate addDate, String itemName,Genre genreName, Integer price,  String comment) {
		super();
		this.user = user;
		this.addDate = addDate;
		this.itemName = itemName;
		this.genre = genreName;
		this.price = price;
		this.comment = comment;
	}


//	public Item(Integer targetPrice) {
//	}

	//ゲッターセッター
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public LocalDate getAddDate() {
		return addDate;
	}

	public void setAddDate(LocalDate addDate) {
		this.addDate = addDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getId() {
		return id;
	}



}
