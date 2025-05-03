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
	
	public Media() {}

	public Media(Integer media_id, String imageName, String imageType, byte[] imageByte) {
		this.media_id = media_id;
		this.imageName = imageName;
		this.imageType = imageType;
		this.imageByte = imageByte;
	}

	public Integer getMedia_id() {
		return media_id;
	}

	public void setMedia_id(Integer media_id) {
		this.media_id = media_id;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public byte[] getImageByte() {
		return imageByte;
	}

	public void setImageByte(byte[] imageByte) {
		this.imageByte = imageByte;
	}

}
