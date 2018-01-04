package com.serio.core.model;

import java.util.List;
import java.util.Map;

import com.serio.core.annotation.parser.ArgName;

/**
 * @author zl.shi
 */
public class AnnotationTest {
	
	@ArgName(index=1)
	private Double appId;
	
	@ArgName(index=0)
	private String name;
	
	@ArgName(index=2)
	private Boolean man;
	
	@ArgName(index=4)
	private Integer age;
	
	@ArgName(index=3)
	private Map<String, String> info;
	
	@ArgName(index=5)
	private List childs;

	public Double getAppId() {
		return appId;
	}

	public void setAppId(Double appId) {
		this.appId = appId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getMan() {
		return man;
	}

	public void setMan(Boolean man) {
		this.man = man;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Map<String, String> getInfo() {
		return info;
	}

	public void setInfo(Map<String, String> info) {
		this.info = info;
	}

	public List getChilds() {
		return childs;
	}

	public void setChilds(List childs) {
		this.childs = childs;
	}

	@Override
	public String toString() {
		return "AnnotationTest [appId=" + appId + ", name=" + name + ", man=" + man + ", age=" + age + ", info=" + info
				+ ", childs=" + childs + "]";
	}
	
}
