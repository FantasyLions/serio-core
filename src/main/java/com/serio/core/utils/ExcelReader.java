//2008-10-7
package com.serio.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * excel读取类
 * @author serio.shi
 *
 */
public class ExcelReader {  
	protected static Logger logger = LoggerFactory.getLogger(ExcelReader.class);
	
	private HSSFWorkbook workbook;   
	private HSSFSheet sheet;  
	private InputStream fis;   
	private StringBuilder msg = null;   
	// 定义需要特殊处理的列，列数从0计数
	private final int DATE_COL_1 = 2;	// 日期列1
	
	
	public ExcelReader(File excelFile) throws IOException {   
		fis = new FileInputStream(excelFile);      
		workbook = new HSSFWorkbook(fis);    
		msg = new StringBuilder();   
	}   
	
	public ExcelReader(InputStream inputStream) throws IOException {   
		fis = inputStream;      
		workbook = new HSSFWorkbook(fis);    
		msg = new StringBuilder();   
	}  
	
	public void destory() {   
		try {       
			msg = null;        
			fis.close();     
		} catch (Exception ex) {    
			ex.printStackTrace() ;
		}  
	}  
	
	/**
	 * 设置sheet值
	 * @param sheetIndex
	 * @return
	 */
	public boolean setCurSheet(int sheetIndex) {
		//设置当前的sheet     
		if (workbook != null && sheetIndex < workbook.getNumberOfSheets()) {   
			try {         
				sheet = workbook.getSheetAt(sheetIndex);      
				return true;     
			} catch (NullPointerException ex) {      
				ex.printStackTrace() ;
			}     
		}      
		return false;  
	} 
	
	/**
	 * 将一个sheet中的指定内容以一个二维的表格返回    
	 * params 's first index is 0    
	 * @param sheetIndex int    
	 * @param firstrowindex int    
	 * @param firstcolindex int    
	 * @param lastcolindex int     
	 * @return String[][]   
	 */    
	public Object[][] getSheetAsTable(int sheetIndex, int firstrowindex, int firstcolindex, int lastcolindex) { 
		Object[][] cells = new Object[0][0]; 
		if(setCurSheet(sheetIndex)) {  
			int lastRow = sheet.getLastRowNum() ;
			
			cells = new Object[lastRow-firstrowindex+1][lastcolindex-firstcolindex+1];
			int row = 0;       
			for(int c1 = firstrowindex; c1 <= lastRow; c1++, row++) {     
				int col = 0;              
				for (int c2 = firstcolindex; c2 <= lastcolindex; c2++, col++) {
					try {   
						cells[row][col] = getCellAsStringByIndex(c1, c2);     
					} catch(Exception ex) {                   
						cells[row][col] = null;   
						ex.printStackTrace() ;
					}           
				}         
			}    
		}      
		return cells;   
	}  
	
	/**
	 * 获取单元格内容
	 * @param rowIndex
	 * @param colIndex
	 * @return
	 */
	public Object getCellAsStringByIndex(int rowIndex, int colIndex) { 
		Object content = null;     
		if (sheet != null && rowIndex < sheet.getLastRowNum() + 1) {   
			try { //sheet为空将执出错误，就是因为上面的那个+1导致的          
				HSSFRow row = sheet.getRow(rowIndex);   
				if (row != null) {            
					if (colIndex < row.getLastCellNum()) {    
						HSSFCell cell = row.getCell(colIndex); 
						logger.info("cell行："+rowIndex+"列:"+colIndex+"类型："+cell.getCellType());
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						if (cell != null) {            
							try {
								content = cell.getRichStringCellValue().getString();
							} catch (Exception ex) {  
								content = null;
								ex.printStackTrace();
							}                  
						}                
					}             
				}          
			} catch (NullPointerException ex) { 
				ex.printStackTrace();
			}     
		}     
		return content; 
	}   
	
	public Object getCellAsStringByIndex(int sheetIndex, int rowIndex,int colIndex) {    
		if (setCurSheet(sheetIndex)) {   
			return getCellAsStringByIndex(rowIndex, colIndex);      
		}    
		return "";   
	}   
	
	public String getErrorMessage() {    
		return msg.toString();  
	}
	
	public String parseDate2String(Date date, String pattern) {
		String dateStr = null;
		if (date != null) {
			DateFormat df = new SimpleDateFormat(pattern);
			dateStr = df.format(date);
		}
		return dateStr;
	}
	
}
