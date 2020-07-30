package com.example.springboot.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-06-24 09:23
 */
@RestController
@RequestMapping("/image")
public class ImgDemo {

  @GetMapping("get")
  public void testImg() throws Exception{
    File picture = new File("F:/壁纸/20190806135946314.jpg");
    BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));
    // 源图大小
    System.out.println(String.format("%.1f",picture.length()/1024.0));
    // 源图宽度
    System.out.println(sourceImg.getWidth());
    // 源图高度
    System.out.println(sourceImg.getHeight());
  }


}

