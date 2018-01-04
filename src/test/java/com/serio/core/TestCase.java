package com.serio.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.serio.core.annotation.parser.ArgName;
import com.serio.core.model.AnnotationTest;
import com.serio.core.model.Result;
import com.serio.core.model.TestModelMutilType;
import com.serio.core.parser.DefaultResultFactory;
import com.serio.core.parser.DefaultResultParser;

/**
 * @author zl.shi
 */
public class TestCase {

//	@Test
//	public void test() {
//		
//		DefaultResultParser parser	 = new DefaultResultParser();
//		DefaultResultFactory factory = new DefaultResultFactory();
//		
//		parser.setResultFactory(factory);
//		
//		
//		List<String> test = new ArrayList<String>();
//		test.add("1");
//		test.add("2");
//		test.add("3");
//		test.add("4");
//		test.add("5");
//		test.add("6");
//		test.add("7");
//		test.add("8");
//		test.add("9");
//		
//		Result<TestModel> result = parser.parseResult( test, TestModel.class);
//		
//		System.out.println(result.getResultCom());
//		
//		
//	}
	
//	@Test
	public void test2() {
		
		DefaultResultParser parser	 = new DefaultResultParser();
		DefaultResultFactory factory = new DefaultResultFactory();
		
		parser.setResultFactory(factory);
		
//		private Double test1;
//		private String test2;
//		private Boolean test3;
//		private Integer test4;
//		private String test5;
//		private Double test6;
		List<String> test = new ArrayList<String>();
		test.add("1");
		test.add("2");
		test.add("true");
		test.add("4");
		test.add("{name:'serio', age:18}");
		test.add("[6,4,5]");
		
		Result<TestModelMutilType> result = parser.parseResult( test, TestModelMutilType.class);
		
		if ( result.getHasException() ) {
			result.getException().printStackTrace();
		}
		System.out.println(result.getResultCom());
		
		
	}
	
	@Test
	public void annotationTest() {
		
		DefaultResultParser parser	 = new DefaultResultParser();
		
		List<String> list = new ArrayList<String>();
		list.add("serio");
		list.add("2.2");
		list.add("true");
		list.add("{name:'serio1', age:18}");
		list.add("18");
		list.add("[6,4,5]");

		Result<AnnotationTest> result = parser.parseResult( list, AnnotationTest.class );
		System.out.println(result.getResultCom());
	}
	
	@Test
	public void listTest() {
//		list.add(1, "20");
		
		String[] strs = new String[3];
		int i = 1;
		strs[i] = "20";
		
		
	}
}
