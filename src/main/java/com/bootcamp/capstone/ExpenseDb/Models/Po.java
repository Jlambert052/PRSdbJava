package com.bootcamp.capstone.ExpenseDb.Models;

public class Po {

	public Vendor vendor;
	
	public Iterable<Poline> polines;
	
	public double poTotal;

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Iterable<Poline> getPolines() {
		return polines;
	}

	public void setPolines(Iterable<Poline> polines) {
		this.polines = polines;
	}

	public double getPoTotal() {
		return poTotal;
	}

	public void setPoTotal(double poTotal) {
		this.poTotal = poTotal;
	}
	
	
}
