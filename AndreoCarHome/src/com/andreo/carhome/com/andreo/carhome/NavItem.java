package com.andreo.carhome.com.andreo.carhome;

public class NavItem {
	
	private String name;
	private int imageId;
	
	public NavItem(String name, int imageId){
		this.name = name;
		this.imageId = imageId;
		
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public int getImageId() {
		return imageId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	
	
	
	
}
