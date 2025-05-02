package com.sas.social.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name="media")
public class Media {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer media_id;
	
	@Column(name="image_name")
	private String imageName;
	
	@Column(name="image_type")
	private String imageType;
	
	@Lob
	@Column(name = "image_byte", columnDefinition = "BYTEA")
	private byte[] imageByte;
}
