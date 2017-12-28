package com.serio.core.utils.view;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.serio.core.utils.LogUtils;

// 分页标签
public class SimplePageTag extends TagSupport {
  private static final long serialVersionUID = -7668378498580212374L;
  private String url;// url
  private String style;// 当前页样式
  private String clas;// 分页div class属性,默认为page
  private String currentPage;// 当前页,默认为1
  private String showSize;// 网页现实的页数,默认为10
  private String totalPage;// 总页数

  @Override
  public int doStartTag() throws JspException {
    JspContext context = this.pageContext;
    HttpServletRequest request=(HttpServletRequest)this.pageContext.getRequest(); 
    JspWriter out = context.getOut();
    // 第一个请求参数用?号,其余用&号
    String separator = url.lastIndexOf("?") == -1 ? "?" : "&";
    StringBuilder html = new StringBuilder("");
    // 参数初始化
    if (url == null || "".equals(url)) {
      LogUtils.error("url不能为空", SimplePageTag.class);
    }
    if (style == null || "".equals(style)) {
      style = "background:#1E90FF;color:white";
    }
    if (clas == null || "".equals(clas)) {
      clas = "page";
    }
 // 如果没有传入值,从request中获取值,request中也没有,则使用默认值
    String strCurrentPage=getValueFromRequest("currentPage", currentPage, request);
    strCurrentPage=StringUtils.defaultIfBlank(strCurrentPage, "1");
    String strShowSize=getValueFromRequest("showSize", showSize, request);
    strShowSize=StringUtils.defaultIfBlank(strShowSize, "10");
    String strTotalPage=getValueFromRequest("totalPage", totalPage, request);
    if (strTotalPage == null || "".equals(strTotalPage)) {
      LogUtils.error("totalPage不能为空", SimplePageTag.class);
      return EVAL_BODY_INCLUDE;
    }
    // 参数转为对应integer类型
    Integer intCurrentPage = Integer.parseInt(strCurrentPage);
    Integer intTotalPage = Integer.parseInt(strTotalPage);
    Integer intShowSize = Integer.parseInt(strShowSize);
    // 首页
    html.append("<div class=\"" + clas + "\">");
    html.append("<a class=\"first\"href=\"");
    html.append(url);
    html.append(separator);
    html.append("currentPage=1\">首页</a>");
    // 上一页
    if (intCurrentPage > 1) {
      html.append("<a class=\"prev\"href=\"");
      html.append(url);
      html.append(separator);
      html.append("currentPage=");
      html.append((intCurrentPage - 1));
      html.append("\">上一页</a>");
    }
    // 上一页
    if (intCurrentPage == 1) {
      html.append("<a class=\"disabled prev\"\">上一页</a>");
    }
    // 获得分页条
    int[] pageBarAry = PageUtils.pageBar(intCurrentPage, intTotalPage, intShowSize);
    // 显示中间页
    if (pageBarAry != null) {
      for (int i = 0; i < pageBarAry.length; i++) {
        int pageBarItem = pageBarAry[i];
        html.append("<a class=\"num\"href=\"");
        html.append(url);
        html.append(separator);
        html.append("currentPage=");
        html.append(pageBarItem);
        html.append("\" ");
        if (pageBarItem == intCurrentPage) {
          html.append("style=\"");
          html.append(style);
          html.append("\"");
        }
        html.append(">");
        html.append(pageBarItem);
        html.append("</a>");
      }
    } else {
      LogUtils.error("缺少pageBar参数", SimplePageTag.class);
    }
    // 下一页
    if (intCurrentPage < intTotalPage) {
      html.append("<a class=\"next\" href=\"");
      html.append(url);
      html.append(separator);
      html.append("currentPage=");
      html.append((intCurrentPage + 1));
      html.append("\">下一页</a>");
    }
    if (intCurrentPage == intTotalPage) {
      html.append("<a class=\"disabled next\"\">下一页</a>");
    }
    // 尾页
    html.append("<a class=\"last\"href=\"");
    html.append(url);
    html.append(separator);
    html.append("currentPage=");
    html.append(intTotalPage);
    html.append("\">尾页</a>");
    html.append("</div>");
    try {
      out.print(html.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return EVAL_BODY_INCLUDE;
  }
  //如果为空从request中获取参数
  public String getValueFromRequest(String key, String value, HttpServletRequest request) {
    if (value==null) {
      Object val = request.getAttribute(key);
      if (val != null) {
        return val + "";
      } else {
        return null;
      }
    }else{
      return value;
    }
  }
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getStyle() {
    return style;
  }

  public void setStyle(String style) {
    this.style = style;
  }

  public String getClas() {
    return clas;
  }

  public void setClas(String clas) {
    this.clas = clas;
  }

  public String getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(String currentPage) {
    this.currentPage = currentPage;
  }

  public String getShowSize() {
    return showSize;
  }

  public void setShowSize(String showSize) {
    this.showSize = showSize;
  }

  public String getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(String totalPage) {
    this.totalPage = totalPage;
  }
}
