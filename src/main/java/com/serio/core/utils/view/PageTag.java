package com.serio.core.utils.view;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;


/**
 * 分页标签
 * 
 * @author tangs
 */
public class PageTag extends BodyTagSupport
{
	private static final long serialVersionUID = 6359665179784503969L;
	private String	currentPage;	// 当前页
	private String	totalPage;		//总页数
	private String	url;			// 请求地址
	private String	cssClass;		//css样式类
	private String	showPageNumber; // 需要显示的页码数
	private String appendToa;		// 添加到a标签的字符串
									
	//定义分页标签的显示类型
	private String	type;
	
	//指定首页,末页,上一页,下一页在页面上显示时的字符串
	private String	firstPage;
	private String	endPage;
	private String	prePage;
	private String	nextPage;
	
	//标识记录在数据库中的位置
	private String	index;
	//总记录条数
	private String	total;
	
	//分页标签标识,当在一个页面上有多个标签时用于区分不同的标签
	private String	id;

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getAppendToa() {
		return appendToa;
	}

	public void setAppendToa(String appendToa) {
		this.appendToa = appendToa;
	}

	public String getShowPageNumber() {
		return showPageNumber;
	}

	public void setShowPageNumber(String showPageNumber) {
		this.showPageNumber = showPageNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(String firstPage) {
		this.firstPage = firstPage;
	}

	public String getEndPage() {
		return endPage;
	}

	public void setEndPage(String endPage) {
		this.endPage = endPage;
	}

	public String getPrePage() {
		return prePage;
	}

	public void setPrePage(String prePage) {
		this.prePage = prePage;
	}

	public String getNextPage() {
		return nextPage;
	}

	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

	@Override
	public int doEndTag() throws JspException {
		JspWriter jspWriter = pageContext.getOut();
		ServletRequest servletRequest = pageContext.getRequest();
		writeHtml(jspWriter, servletRequest);
		// 包含主体内容
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doStartTag() throws JspException {
		JspWriter jspWriter = pageContext.getOut();
		ServletRequest servletRequest = pageContext.getRequest();
		initParameters(jspWriter, servletRequest);
		return EVAL_PAGE;
	}

	/**
	 * 参数处理
	 * @param writer
	 */
	protected void initParameters(Writer writer, ServletRequest servletRequest) {
		// 当前页
		if (StringUtils.isBlank(currentPage)) {
			currentPage = "1";
		}
		//总页数
		if(StringUtils.isBlank(totalPage)) {
			totalPage = "1";
		}
		// 请求地址
//		url = pageContext.getServletContext().getContextPath() + url;
		//css样式类
		if (StringUtils.isBlank(cssClass)) {
			cssClass = "pagination";
		}
		// 处理函数
		if (StringUtils.isBlank(appendToa)) {
			appendToa = "";
		}
		// 需要显示的页码数
		if (StringUtils.isBlank(showPageNumber)) {
			showPageNumber = "5";					
		}
		//定义分页标签的显示类型
		if (StringUtils.isBlank(type)) {
			type = "multi";
		}
		//指定首页,末页,上一页,下一页在页面上显示时的字符串
		if (StringUtils.isBlank(firstPage)) {
			firstPage = "首页";
		}
		if (StringUtils.isBlank(endPage)) {
			endPage = "末页";
		}
		if (StringUtils.isBlank(prePage)) {
			prePage = "上一页";
		}
		if (StringUtils.isBlank(nextPage)) {
			nextPage = "下一页";
		}
		//标识记录在数据库中的位置
		if (StringUtils.isBlank(index)) {
			index = "1";
		}
		//总记录条数
		if(StringUtils.isBlank(total)) {
			total = "1"	;
		}
		//分页标签标识,当在一个页面上有多个标签时用于区分不同的标签
		if(StringUtils.isBlank(id)) {
			id = "";
		}

	}
	
	@SuppressWarnings("unchecked")
	protected void writeHtml(Writer writer, ServletRequest servletRequest) {
		try {
			StringBuilder outputStringBuilder = new StringBuilder();
			StringBuilder paramStringBuilder = new StringBuilder();
			boolean isValid = true;

			if (isValid) {
				Map<String, String[]> parameterMap = servletRequest.getParameterMap();
				Set<String> keys = parameterMap.keySet();
				
				for (String key : keys) {
					if (!"currentPage".equals(key) && !"cssClass".equals(key) && !"showPageNumber".equals(key) && !"type".equals(key)) {
						String[] values = parameterMap.get(key);
						if (values != null && values.length > 0) {
							if (StringUtils.isNotBlank(values[0])) {
								paramStringBuilder.append("&");
								paramStringBuilder.append(key);
								paramStringBuilder.append("=");
								paramStringBuilder.append(values[0]);
							}
						}
					}
				}
			}

			if (isValid) {
				Integer currentPageInt = Integer.valueOf(currentPage);
				Integer totalPageInt = Integer.valueOf(totalPage);
				Integer totalInt = Integer.valueOf(total);
				Integer showPageNumberInt = Integer.valueOf(showPageNumber);
				Integer indexInt = Integer.valueOf(index);
				String current = "";
				String param = paramStringBuilder.toString();

				if (type.equals("multi")) {

					if (totalPageInt != 0) {

						if (totalPageInt <= showPageNumberInt) { // 总的页数小于可以被显示的页码
							if (currentPage.equals(totalPage)) { // 当前页等于总数页
								if ("1".equals(totalPage)) {
									// 如果totalPage = 1，则无需分页,不显示分页信息
								} else { // 总数页大于1
									outputStringBuilder.append("<ol class='" + cssClass + "'>");
									outputStringBuilder.append("<li>");
									outputStringBuilder.append("<a " + appendToa + " href='");
									outputStringBuilder.append(url);
									outputStringBuilder.append("?" + id + "currentPage=1" + param);
									outputStringBuilder.append("'>" + firstPage + "</a></li><li><a " + appendToa + " href='");
									outputStringBuilder.append(url);
									outputStringBuilder.append("?" + id + "currentPage=" + (currentPageInt - 1) + param);
									outputStringBuilder.append("'>" + prePage + "</a></li>");
									for (int i = 1; i <= totalPageInt; i++) {
										current = "";
										if (i == currentPageInt) {	// 当前页
											current = " class='current' ";
											outputStringBuilder.append("<li" + current + "><a " + appendToa + " href='javascript:void(0);'>" + i + "</a></li>");
											continue ;
										}
										outputStringBuilder.append("<li><a " + appendToa + " href='");
										outputStringBuilder.append(url);
										outputStringBuilder.append("?" + id + "currentPage=" + i + param);
										outputStringBuilder.append("'>" + i + "</a></li>");
									}
									outputStringBuilder.append("</ol>");
								}
							} else { // 当前页不等于总数页
								if ("1".equals(currentPage)) {// 当前页为第一页
									outputStringBuilder.append("<ol class='" + cssClass + "'>");
									for (int i = 1; i <= totalPageInt; i++) {
										current = "";
										if (i == currentPageInt) {
											current = " class='current' ";
											outputStringBuilder.append("<li" + current + "><a " + appendToa + " href='javascript:void(0);'>" + i + "</a></li>");
											continue ;
										}
										outputStringBuilder.append("<li><a " + appendToa + " href='");
										outputStringBuilder.append(url);
										outputStringBuilder.append("?" + id + "currentPage=" + i + param);
										outputStringBuilder.append("'>" + i + "</a></li>");
									}
									outputStringBuilder.append("<li><a " + appendToa + " href='");
									outputStringBuilder.append(url);
									outputStringBuilder.append("?" + id + "currentPage=" + (currentPageInt + 1) + param);
									outputStringBuilder.append("'>" + nextPage + "</a></li><li> <a title='共" + totalPageInt + "页' " + appendToa + " href='");
									outputStringBuilder.append(url);
									outputStringBuilder.append("?" + id + "currentPage=" + totalPage + param);
									outputStringBuilder.append("'>" + endPage + "</a></li></ol>");
								} else {// 当前页不为第一页
									outputStringBuilder.append("<ol class='" + cssClass + "'>");
									outputStringBuilder.append("<li>");
									outputStringBuilder.append("<a " + appendToa + " href='");
									outputStringBuilder.append(url);
									outputStringBuilder.append("?" + id + "currentPage=1" + param);
									outputStringBuilder.append("'>" + firstPage + "</a></li><li><a " + appendToa + " href='");
									outputStringBuilder.append(url);
									outputStringBuilder.append("?" + id + "currentPage=" + (currentPageInt - 1) + param);
									outputStringBuilder.append("'>" + prePage + "</a></li>");
									for (int i = 1; i <= totalPageInt; i++) {
										current = "";
										if (i == currentPageInt) {
											current = " class='current' ";
											outputStringBuilder.append("<li" + current + "><a " + appendToa + " href='javascript:void(0);'>" + i + "</a></li>");
											continue ;
										}
										outputStringBuilder.append("<li><a " + appendToa + " href='");
										outputStringBuilder.append(url);
										outputStringBuilder.append("?" + id + "currentPage=" + i + param);
										outputStringBuilder.append("'>" + i + "</a></li>");
									}
									outputStringBuilder.append("<li><a " + appendToa + " href='");
									outputStringBuilder.append(url);
									outputStringBuilder.append("?" + id + "currentPage=" + (currentPageInt + 1) + param);
									outputStringBuilder.append("'>" + nextPage + "</a></li><li> <a title='共" + totalPageInt + "页' " + appendToa + " href='");
									outputStringBuilder.append(url);
									outputStringBuilder.append("?" + id + "currentPage=" + totalPage + param);
									outputStringBuilder.append("'>" + endPage + "</a></li></ol>");
								}
							}
						} else { // 总页数大于可以显示的页码数
							if (currentPage.equals(totalPage)) { // 当前页等于最后页
								outputStringBuilder.append("<ol class='" + cssClass + "'>");
								outputStringBuilder.append("<li>");
								outputStringBuilder.append("<a " + appendToa + " href='");
								outputStringBuilder.append(url);
								outputStringBuilder.append("?" + id + "currentPage=1" + param);
								outputStringBuilder.append("'>" + firstPage + "</a></li><li><a " + appendToa + " href='");
								outputStringBuilder.append(url);
								outputStringBuilder.append("?" + id + "currentPage=" + (currentPageInt - 1) + param);
								outputStringBuilder.append("'>" + prePage + "</a></li>");
								for (int i = totalPageInt - showPageNumberInt; i <= totalPageInt; i++) {
									current = "";
									if (i == currentPageInt) {
										current = " class='current' ";
										outputStringBuilder.append("<li" + current + "><a " + appendToa + " href='javascript:void(0);'>" + i + "</a></li>");
										continue ;
									}
									outputStringBuilder.append("<li><a " + appendToa + " href='");
									outputStringBuilder.append(url);
									outputStringBuilder.append("?" + id + "currentPage=" + i + param);
									outputStringBuilder.append("'>" + i + "</a></li>");
								}
								outputStringBuilder.append("</ol>");
							} else { // 总页数大于可以显示的页码数
								if (currentPage.equals("1")) { // 当前页等于首页
									outputStringBuilder.append("<ol class='" + cssClass + "'>");
									for (int i = 1; i <= showPageNumberInt; i++) {
										current = "";
										if (i == currentPageInt) {
											current = " class='current' ";
											outputStringBuilder.append("<li" + current + "><a " + appendToa + " href='javascript:void(0);'>" + i + "</a></li>");
											continue ;
										}
										outputStringBuilder.append("<li><a " + appendToa + " href='");
										outputStringBuilder.append(url);
										outputStringBuilder.append("?" + id + "currentPage=" + i + param);
										outputStringBuilder.append("'>" + i + "</a></li>");
									}
									outputStringBuilder.append("<li><a " + appendToa + " href='");
									outputStringBuilder.append(url);
									outputStringBuilder.append("?" + id + "currentPage=" + (currentPageInt + 1) + param);
									outputStringBuilder.append("'>" + nextPage + "</a></li><li> <a title='共" + totalPageInt + "页' " + appendToa + " href='");
									outputStringBuilder.append(url);
									outputStringBuilder.append("?" + id + "currentPage=" + totalPage + param);
									outputStringBuilder.append("'>" + endPage + "</a></li></ol>");
								} else if ((currentPageInt - showPageNumberInt / 2) >= 1 && (currentPageInt + showPageNumberInt / 2) <= totalPageInt) {
									outputStringBuilder.append("<ol class='" + cssClass + "'>");
									outputStringBuilder.append("<li>");
									outputStringBuilder.append("<a " + appendToa + " href='");
									outputStringBuilder.append(url);
									outputStringBuilder.append("?" + id + "currentPage=1" + param);
									outputStringBuilder.append("'>" + firstPage + "</a></li><li><a " + appendToa + " href='");
									outputStringBuilder.append(url);
									outputStringBuilder.append("?" + id + "currentPage=" + (currentPageInt - 1) + param);
									outputStringBuilder.append("'>" + prePage + "</a></li>");
									for (int i = currentPageInt - showPageNumberInt / 2; i <= (currentPageInt + showPageNumberInt / 2); i++) {
										current = "";
										if (i == currentPageInt) {
											current = " class='current' ";
											outputStringBuilder.append("<li" + current + "><a " + appendToa + " href='javascript:void(0);'>" + i + "</a></li>");
											continue ;
										}
										outputStringBuilder.append("<li><a " + appendToa + " href='");
										outputStringBuilder.append(url);
										outputStringBuilder.append("?" + id + "currentPage=" + i + param);
										outputStringBuilder.append("'>" + i + "</a></li>");
									}
									outputStringBuilder.append("<li><a " + appendToa + " href='");
									outputStringBuilder.append(url);
									outputStringBuilder.append("?" + id + "currentPage=" + (currentPageInt + 1) + param);
									outputStringBuilder.append("'>" + nextPage + "</a></li><li> <a title='共" + totalPageInt + "页' " + appendToa + " href='");
									outputStringBuilder.append(url);
									outputStringBuilder.append("?" + id + "currentPage=" + totalPage + param);
									outputStringBuilder.append("'>" + endPage + "</a></li></ol>");
								} else if ((currentPageInt - showPageNumberInt / 2) < 1) {
									outputStringBuilder.append("<ol class='" + cssClass + "'>");
									outputStringBuilder.append("<li>");
									outputStringBuilder.append("<a " + appendToa + " href='");
									outputStringBuilder.append(url);
									outputStringBuilder.append("?" + id + "currentPage=1" + param);
									outputStringBuilder.append("'>" + firstPage + "</a></li><li><a " + appendToa + " href='");
									outputStringBuilder.append(url);
									outputStringBuilder.append("?" + id + "currentPage=" + (currentPageInt - 1) + param);
									outputStringBuilder.append("'>" + prePage + "</a></li>");
									for (int i = 1; i <= showPageNumberInt; i++) {
										current = "";
										if (i == currentPageInt) {
											current = " class='current' ";
											outputStringBuilder.append("<li" + current + "><a " + appendToa + " href='javascript:void(0);'>" + i + "</a></li>");
											continue ;
										}
										outputStringBuilder.append("<li><a " + appendToa + " href='");
										outputStringBuilder.append(url);
										outputStringBuilder.append("?" + id + "currentPage=" + i + param);
										outputStringBuilder.append("'>" + i + "</a></li>");
									}
									outputStringBuilder.append("<li><a " + appendToa + " href='");
									outputStringBuilder.append(url);
									outputStringBuilder.append("?" + id + "currentPage=" + (currentPageInt + 1) + param);
									outputStringBuilder.append("'>" + nextPage + "</a></li><li> <a title='共" + totalPageInt + "页' " + appendToa + " href='");
									outputStringBuilder.append(url);
									outputStringBuilder.append("?" + id + "currentPage=" + totalPage + param);
									outputStringBuilder.append("'>" + endPage + "</a></li></ol>");
								} else {
									outputStringBuilder.append("<ol class='" + cssClass + "'>");
									outputStringBuilder.append("<li>");
									outputStringBuilder.append("<a " + appendToa + " href='");
									outputStringBuilder.append(url);
									outputStringBuilder.append("?" + id + "currentPage=1" + param);
									outputStringBuilder.append("'>" + firstPage + "</a></li><li><a " + appendToa + " href='");
									outputStringBuilder.append(url);
									outputStringBuilder.append("?" + id + "currentPage=" + (currentPageInt - 1) + param);
									outputStringBuilder.append("'>" + prePage + "</a></li>");
									for (int i = totalPageInt - showPageNumberInt; i <= totalPageInt; i++) {
										current = "";
										if (i == currentPageInt) {
											current = " class='current' ";
											outputStringBuilder.append("<li" + current + "><a " + appendToa + " href='javascript:void(0);'>" + i + "</a></li>");
											continue ;
										}
										outputStringBuilder.append("<li><a " + appendToa + " href='");
										outputStringBuilder.append(url);
										outputStringBuilder.append("?" + id + "currentPage=" + i + param);
										outputStringBuilder.append("'>" + i + "</a></li>");
									}
									outputStringBuilder.append("<li><a " + appendToa + " href='");
									outputStringBuilder.append(url);
									outputStringBuilder.append("?" + id + "currentPage=" + (currentPageInt + 1) + param);
									outputStringBuilder.append("'>" + nextPage + "</a></li><li> <a title='共" + totalPageInt + "页' " + appendToa + " href='");
									outputStringBuilder.append(url);
									outputStringBuilder.append("?" + id + "currentPage=" + totalPage + param);
									outputStringBuilder.append("'>" + endPage + "</a></li></ol>");
								}
							}
						}
					}
				} else if (type.equals("single")) {
					
					int preIndex = 1;
					int nextIndex = 1;
					
					if(indexInt == 1 && indexInt == totalInt) {
						preIndex = 1;
						nextIndex = 1;
					}
					if(indexInt == 1) {
						if(totalInt != 1) {
							preIndex = totalInt;
							nextIndex = indexInt + 1;
						} else {
							preIndex = 1;
							nextIndex = 1;
						}
					} else if((indexInt-totalInt) == 0) {
						preIndex = indexInt - 1;
						nextIndex = 1;
					} else {
						preIndex = indexInt - 1;
						nextIndex = indexInt + 1;
					}
					
					outputStringBuilder.append("<ol class='" + cssClass + "'>");
					outputStringBuilder.append("<li>");
					outputStringBuilder.append("<a " + appendToa + " href='");
					outputStringBuilder.append(url);
					outputStringBuilder.append("?index=" + preIndex + param);
					outputStringBuilder.append("'>" + prePage + "</a></li>");
					outputStringBuilder.append("<li><a " + appendToa + " href='");
					outputStringBuilder.append(url);
					outputStringBuilder.append("?index=" + nextIndex + param);
					outputStringBuilder.append("'>" + nextPage + "</a></li>");
					outputStringBuilder.append("</ol>");
				}
			}

			writer.write(outputStringBuilder.toString());

		} catch (IOException ex) {
			Logger.getLogger(ex.getMessage());
		}
	}
	
	
}
