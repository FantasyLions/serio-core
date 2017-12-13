package com.serio.core;

import com.serio.core.model.ListResultBaseCommon;

/**
 * @author zl.shi
 */
public class TestModel extends ListResultBaseCommon {
	
	private String test1;
	private String test2;
	private String test3;
	private String test4;
	private String test5;
	private String test6;
	private String test7;
	private String test8;
	private String test9;
	
	/* (non-Javadoc)
	 * @see com.serio.core.model.ListResultBaseCommon#getFieldsSuccessNames()
	 */
	@Override
	public String[] getFieldsSuccessNames() {
		return new String[]{
				"test1",
				"test2",
				"test3",
				"test4",
				"test5",
				"test6",
				"test7",
				"test8",
				"test9",
		};
	}

	/* (non-Javadoc)
	 * @see com.serio.core.model.ListResultBaseCommon#getFieldsErrorNames()
	 */
	@Override
	public String[] getFieldsErrorNames() {
		return new String[]{
				"test1",
				"test2",
				"test3",
				"test4",
				"test5",
				"test6",
				"test7",
				"test8",
				"test9",
		};
	}

	public String getTest1() {
		return test1;
	}

	public void setTest1(String test1) {
		this.test1 = test1;
	}

	public String getTest2() {
		return test2;
	}

	public void setTest2(String test2) {
		this.test2 = test2;
	}

	public String getTest3() {
		return test3;
	}

	public void setTest3(String test3) {
		this.test3 = test3;
	}

	public String getTest4() {
		return test4;
	}

	public void setTest4(String test4) {
		this.test4 = test4;
	}

	public String getTest5() {
		return test5;
	}

	public void setTest5(String test5) {
		this.test5 = test5;
	}

	public String getTest6() {
		return test6;
	}

	public void setTest6(String test6) {
		this.test6 = test6;
	}

	public String getTest7() {
		return test7;
	}

	public void setTest7(String test7) {
		this.test7 = test7;
	}

	public String getTest8() {
		return test8;
	}

	public void setTest8(String test8) {
		this.test8 = test8;
	}

	public String getTest9() {
		return test9;
	}

	public void setTest9(String test9) {
		this.test9 = test9;
	}

	@Override
	public String toString() {
		return "TestModel [test1=" + test1 + ", test2=" + test2 + ", test3=" + test3 + ", test4=" + test4 + ", test5="
				+ test5 + ", test6=" + test6 + ", test7=" + test7 + ", test8=" + test8 + ", test9=" + test9 + "]";
	}

}
