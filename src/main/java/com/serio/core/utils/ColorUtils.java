package com.serio.core.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ColorUtils {
  /**
   * 判断图片的黑色数量,避免从视频截出黑色预览图片
   * @param fileName 图片文件名称
   * @return 图片黑色数量,0-100之间的整数,100-全为黑色,0-没有黑色
   */
  public static int distinguish(String fileName) {
    BufferedImage bufferedImage = null;
    try {
      bufferedImage = (BufferedImage) ImageIO.read(new File(fileName));
    } catch (IOException e) {
      LogUtils.warn("图片不存在", ColorUtils.class);
    }
    //标准黑色值
    final int blackValue = 5;
    int blackSum = 0;
    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        int pointColor = bufferedImage.getRGB(i, j);
        pointColor = pointColor + 256 * 256 * 256;
        //RGB的平均值小于标准黑色值,则认为是黑色
        if (pointColor / 65536 + pointColor / 256 % 256 + pointColor % 256 < blackValue * 3) {
          blackSum++;
        }
      }
    }
    //黑色的像素所占图片的百分比
    double percentage = ((double) blackSum) / (width * height);
    return (int) (percentage * 100);
  }
}
