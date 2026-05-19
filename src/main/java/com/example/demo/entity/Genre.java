package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "genres")
public class Genre {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "genre_name")
	private String genreName;
	
	private Boolean isIncome;
	
	
	public String getGenreName() {
		return genreName;
	}

	public Boolean getIsIncome() {
		return isIncome;
	}
	
	public Genre() {
		
	}
	

}