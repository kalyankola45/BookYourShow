package com.example.ticketsbooking.wrappers;

public class BdataWrapper {
	
	private String Rowname;
	
	private String Seatcolumn;

	public String getRowname() {
		return Rowname;
	}

	public void setRowname(String rowname) {
		Rowname = rowname;
	}

	public String getSeatcolumn() {
		return Seatcolumn;
	}

	public void setSeatcolumn(String seatcolumn) {
		Seatcolumn = seatcolumn;
	}

	public BdataWrapper(String rowname, String seatcolumn) {
		super();
		Rowname = rowname;
		Seatcolumn = seatcolumn;
	}

	public BdataWrapper() {
		super();
	}
	
	

}
