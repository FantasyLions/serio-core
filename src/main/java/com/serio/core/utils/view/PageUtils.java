package com.serio.core.utils.view;

public class PageUtils {
  /**
   * 计算 分页 导航条 的 显示内容
   * 
   * @param CurrentPage 当前页
   * @param pageCount 总页数
   * @param showSize 分页条可以显示的页数
   * @return 计算出的导航条需要显示的页码
   */
  public static int[] pageBar(int currentPage, int pageCount, int showSize) {
    int[] result = null;
    if (pageCount <= showSize) {// 总页数 小于 可以显示的总数
      result = new int[pageCount];
      for (int i = 0; i < pageCount; i++) {
        result[i] = i + 1;
      }
    } else {// 总页数 大于等于 可以显示的总数
      int begin = showSize / 2;
      int end = showSize - begin - 1;
      result = new int[showSize];
      if (currentPage <= begin) {// 当页数比较靠前时，显示前面的页码
        for (int i = 0; i < showSize; i++) {
          result[i] = i + 1;
        }
      } else if (currentPage >= (pageCount - end)) {// 当页数比较靠后时，显示后面的页码
        for (int i = 0; i < showSize; i++) {
          result[i] = pageCount - showSize + i + 1;
        }
      } else {// 当页面在中间时，使当前页位于中间位置
        for (int i = 0; i < showSize; i++) {
          result[i] = currentPage - begin + i;
        }
      }
    }
    return result;
  }
}
