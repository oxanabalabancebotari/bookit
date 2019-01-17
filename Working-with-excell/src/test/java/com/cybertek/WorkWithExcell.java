package com.cybertek;

public class WorkWithExcell {

	public static void main(String[] args) {
		
		String path="C:\\Users\\balab\\Desktop\\TestData.xlsx";
		Xls_Reader data = new Xls_Reader(path);
		
		int rcount= data.getRowCount("data");
		System.out.println(rcount);
		
		String cdata= data.getCellData("data", "Name", 2);
		System.out.println(cdata);
		
		int ccount= data.getColumnCount("data");
		System.out.println(ccount);
		
		data.setCellData("data", "Name", 8, "Ozzy");
		data.setCellData("data", "Name", 9, "Oxana");

	}

}
