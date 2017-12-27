package com.serio.core.utils;

/**
 * 有关汉字排序的工具类
 * 
 * @author Administator
 * 
 */
public class PinyinUtils {
  public static final Class clazz = PinyinUtils.class;

  /**
   * 查询以某个字母开头的汉字hql where语句
   * 
   * @param columnName 列名
   * @param letter 需要寻找的开始字母
   * @return 拼好的hql语句
   */
  public static String pinyinHql(String columnName, String letter) {
    if (letter.length() == 0) {
      LogUtils.error("传入参数必须大于一个字符", clazz);
      return null;
    }
    if("all".equals(letter)){
      return "";
    }
    char ch = letter.toLowerCase().charAt(0);
    StringBuilder hql = new StringBuilder();
    //对其他非字母开头的字符 过滤
    if (!Character.isLetter(ch)||"other".equals(letter)) {
      hql.append(" and (");
      hql.append(columnName);
      hql.append("<'0'");
      hql.append(" or ");
      hql.append(columnName);
      hql.append(">'9'");
      hql.append(" and ");
      hql.append(columnName);
      hql.append("<'A'");
      hql.append(" or ");
      hql.append(columnName);
      hql.append(">'Z'");
      hql.append(" and ");
      hql.append(columnName);
      hql.append("<'a'");
      hql.append(" or ");
      hql.append(columnName);
      hql.append(">'z'");
      hql.append(" and ");
      hql.append("convert_gbk(");
      hql.append(columnName);
      hql.append(")<'啊')");
      return hql.toString();
    }
    char[] charTable =
        {'啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈', '击', '击', '喀', '垃', '妈', '拿', '哦', '啪', '期', '然',
            '撒', '塌', '挖', '挖', '挖', '昔', '压', '匝',};
    // 对汉字字母的过滤,没有声母的不加入过滤
    hql.append(" and (");
    if (!"iuv".contains(letter)) {
      hql.append("convert_gbk(");
      hql.append(columnName);
      hql.append(") >=");
      int index = ch - 'a';
      hql.append("'");
      hql.append(charTable[index]);
      hql.append("'");
      if (ch != 'z') {
        hql.append(" and ");
        hql.append("convert_gbk(");
        hql.append(columnName);
        hql.append(") <");
        hql.append("'");
        hql.append(charTable[index + 1]);
        hql.append("'");
      }
      hql.append(" or ");
    }
    // 对小写字母的过滤
    createHql4Letter(hql, columnName, ch);
    // 对大写字母的过滤
    ch = String.valueOf(ch).toUpperCase().charAt(0);
    hql.append(" or ");
    createHql4Letter(hql, columnName, ch);
    hql.append(") ");
    return hql.toString();
  }

  private static void createHql4Letter(StringBuilder hql, String columnName, char ch) {
    hql.append(columnName);
    hql.append(">=");
    hql.append("'");
    hql.append(ch);
    hql.append("'");
    hql.append(" and ");
    hql.append(columnName);
    hql.append("<");
    hql.append("'");
    // 当前字母的下一个字母
    hql.append((char) (ch + 1));
    hql.append("'");
  }
}
