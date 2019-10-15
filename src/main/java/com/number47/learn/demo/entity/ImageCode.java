package com.number47.learn.demo.entity;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @author number47
 * @date 2019/10/16 01:06
 * @description 验证码对象
 */
@Data
public class ImageCode {

	private BufferedImage image;

	private String code;

	private LocalDateTime expireTime;

	public ImageCode(BufferedImage image, String code, int expireIn) {
		this.image = image;
		this.code = code;
		this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
	}

	public boolean isExpire() {
		return LocalDateTime.now().isAfter(expireTime);
	}
}
