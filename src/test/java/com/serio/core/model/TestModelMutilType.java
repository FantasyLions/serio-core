package com.serio.core.model;

import java.util.List;
import java.util.Map;

/**
 * @author zl.shi
 */
public class TestModelMutilType extends ListResultBaseCommon {
	
	private Double test1;
	private String test2;
	private Boolean test3;
	private Integer test4;
	private Map<String, String> test5;
	private List test6;
	

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
			"test6"
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
				"test6"
			};
	}

	public Double getTest1() {
		return test1;
	}

	public void setTest1(Double test1) {
		this.test1 = test1;
	}

	public String getTest2() {
		return test2;
	}

	public void setTest2(String test2) {
		this.test2 = test2;
	}

	public Boolean getTest3() {
		return test3;
	}

	public void setTest3(Boolean test3) {
		this.test3 = test3;
	}

	public Integer getTest4() {
		return test4;
	}

	public void setTest4(Integer test4) {
		this.test4 = test4;
	}

	public Map<String, String> getTest5() {
		return test5;
	}

	public void setTest5(Map<String, String> test5) {
		this.test5 = test5;
	}

	public List getTest6() {
		return test6;
	}

	public void setTest6(List test6) {
		this.test6 = test6;
	}

	@Override
	public String toString() {
		return "TestModelMutilType [test1=" + test1 + ", test2=" + test2 + ", test3=" + test3 + ", test4=" + test4
				+ ", test5=" + test5 + ", test6=" + test6 + "]";
	}
	
}
