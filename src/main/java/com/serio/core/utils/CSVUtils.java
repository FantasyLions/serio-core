package com.serio.core.utils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

/**
 * 基于com.opencsv进行扩展
 * @author zl.shi
 */
public class CSVUtils {
	
	
	/**
	 * 讲字符数组以CSV的格式写到文件里面了
	 * @author zl.shi
	 * @param path
	 * @param entries
	 * @throws IOException
	 */
	@Deprecated
	public static void writeCSV( String path, String[] entries ) throws IOException {

		CSVWriter writer = new CSVWriter(new FileWriter(path), ',');
		
		// feed in your array (or convert your data to an array)
		writer.writeNext(entries);
		writer.close();
	}
	
	
	/**
	 * 将一个beans数组以CSV的格式写到文件里面
	 * @author zl.shi
	 * @param path
	 * @param beans
	 * @throws IOException
	 * @throws CsvDataTypeMismatchException
	 * @throws CsvRequiredFieldEmptyException
	 */
	public static void witeBeansCSV( String path, List<Object> beans ) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		
		Writer writer = new FileWriter(path);
		StatefulBeanToCsv<Object> beanToCsv = new StatefulBeanToCsvBuilder<Object>(writer).build();

		beanToCsv.write(beans);
		writer.close();

	}
	
	
	/**
	 * 读取CSV文件，获取CSV中的每一行数据，写到字符串数组中
	 * @author zl.shi
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static List<String[]> parserCSVList( String path ) throws Exception {
		
		CSVReader reader = new CSVReader( new FileReader(path) );
		return reader.readAll();

	}
	
	
	/**
	 * 将CSV解析成指定对象
	 * @author zl.shi
	 * @param path
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> parserCSVBeans( String path, Class<T> clazz ) throws Exception {
		
		CSVReader reader = new CSVReader( new FileReader(path) );

		Iterator<String[]> itrator = reader.iterator();
		
		String[] fileds = findFileds( itrator );
		
		return buildBeans( itrator, fileds, clazz );
		
	}
	
	
	/**
	 * 默认CSV的第一行就是对应类所拥有的字段，这函数会直接读取Iterator中的第一行
	 * @author zl.shi
	 * @param iterator
	 * @return
	 */
	public static String[] findFileds( Iterator<String[]> iterator ) {
		
		String[] fileds = null;
		
		if ( iterator.hasNext() ) {
			fileds = iterator.next();
		}
		
		return fileds;
	}
	
	
	/**
	 * 以此便利<code>iterator</code>中的每一行，将值设置到对应的对象字段中
	 * @author zl.shi
	 * @param iterator
	 * @param fileds
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> buildBeans( Iterator<String[]> iterator, String[] fileds, Class<T> clazz ) throws Exception {
		
		List<T> list = new ArrayList<T>();
		
		while( iterator.hasNext() ) {
			Object obj = clazz.newInstance();
			ReflectionUtils.setObjectAttrs( Arrays.asList(iterator.next()), fileds, obj, clazz );
			list.add((T)obj);
		}
		
		return list;
	}

}
