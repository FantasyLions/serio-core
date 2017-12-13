package com.serio.core;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.serio.core.model.Result;
import com.serio.core.parser.DefaultResultFactory;
import com.serio.core.parser.DefaultResultParser;

/**
 * @author zl.shi
 */
public class TestCase extends junit.framework.TestCase {

	@Test
	public void test() {
		
		DefaultResultParser parser	 = new DefaultResultParser();
		DefaultResultFactory factory = new DefaultResultFactory();
		
		parser.setResultFactory(factory);
		
		
		List<String> test = new ArrayList<String>();
		test.add("1");
		test.add("2");
		test.add("3");
		test.add("4");
		test.add("5");
		test.add("6");
		test.add("7");
		test.add("8");
		test.add("9");
		
		Result<TestModel> result = parser.parseResult( test, TestModel.class);
		
		System.out.println(result.getResultCom());
		
		
	}
}
