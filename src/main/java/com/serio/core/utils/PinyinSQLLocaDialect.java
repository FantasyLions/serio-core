package com.serio.core.utils;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;

public class PinyinSQLLocaDialect extends MySQL5Dialect {

  public PinyinSQLLocaDialect() {
    super();
    registerFunction("convert_gbk", new SQLFunctionTemplate(Hibernate.STRING,
        "convert(?1 using gbk)"));
  }
}
