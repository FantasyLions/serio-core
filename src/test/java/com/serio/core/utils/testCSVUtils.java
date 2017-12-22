package com.serio.core.utils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.opencsv.bean.CsvToBean;
import com.serio.core.model.TestModel;

import junit.framework.TestCase;


/**
 * @author zl.shi
 */
public class testCSVUtils extends TestCase {
	
//	@Test
//	public void testWrite() {
//		try {
//			CSVUtils.writeCSV("D:/DATA/person/csv/yourfile.csv", "first#second#third".split("#"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
//	@Test
	public void testWriteBeans() {
//		try {
//			TestModel model = new TestModel();
//			model.setTest1("setTest1");
//			model.setTest2("setTest2");
//			model.setTest3("setTest3");
//			model.setTest4("setTest4");
//			model.setTest5("setTest5");
//			model.setTest6("setTest6");
//			model.setTest7("setTest7");
//			
//			
//			List<Object> list = new ArrayList<Object>();
//			list.add(model);
//			list.add(model);
//			list.add(model);
//			list.add(model);
//			list.add(model);
//			
//			CSVUtils.witeBeansCSV("D:/DATA/person/csv/yourfile2.csv", list);
//			
			readCVStoBean();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
//	@Test
	public void readCVStoBean() {
		try {
			List<TestModel> entries = CSVUtils.parserCSVBeans("D:/DATA/person/csv/yourfile2.csv", TestModel.class);
			System.out.println(entries.size());
			System.out.println(entries);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
