package com.example.maptest.Database.model;

public class Hotspot {
	int H_ID;
	String H_NAME = "name";
	String H_TRANS = "transportation";
	String H_BTF = "butterfly";
	String H_ENVIRO = "environment";
	
	public Hotspot(int h_ID, String h_NAME, String h_TRANS, String h_BTF,
			String h_ENVIRO) {
		super();
		H_ID = h_ID;
		H_NAME = h_NAME;
		H_TRANS = h_TRANS;
		H_BTF = h_BTF;
		H_ENVIRO = h_ENVIRO;
	}

	// Getter
	
	public int getH_ID() {
		return H_ID;
	}

	public String getH_NAME() {
		return H_NAME;
	}

	public String getH_TRANS() {
		return H_TRANS;
	}

	public String getH_BTF() {
		return H_BTF;
	}

	public String getH_ENVIRO() {
		return H_ENVIRO;
	}

	// Setter
	
	public void setH_ID(int h_ID) {
		H_ID = h_ID;
	}

	public void setH_NAME(String h_NAME) {
		H_NAME = h_NAME;
	}

	public void setH_TRANS(String h_TRANS) {
		H_TRANS = h_TRANS;
	}

	public void setH_BTF(String h_BTF) {
		H_BTF = h_BTF;
	}

	public void setH_ENVIRO(String h_ENVIRO) {
		H_ENVIRO = h_ENVIRO;
	}
	
	
}
