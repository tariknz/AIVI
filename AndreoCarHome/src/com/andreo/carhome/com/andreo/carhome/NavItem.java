package com.andreo.carhome.com.andreo.carhome;

public class NavItem {
	
	private String name;
	private int imageId;
	private String component;
	private String activity;
	
	public NavItem(String name, int imageId){
		this.name = name;
		this.imageId = imageId;
		
	}
	
	public NavItem(String name, int imageId, String component, String activity){
		this.name = name;
		this.imageId = imageId;
		this.component = component;
		this.activity = activity;
		
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

	public String getComponent() {
		return component;
	}

	public String getActivity() {
		return activity;
	}
	

	@Override
	public String toString() {
		return name;
	}

	
	
	
}
