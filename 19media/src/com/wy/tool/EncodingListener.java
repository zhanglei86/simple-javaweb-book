package com.wy.tool;

import java.io.UnsupportedEncodingException;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

public class EncodingListener implements ServletRequestListener {
	public void requestDestroyed(ServletRequestEvent req) {
	}
	public void requestInitialized(ServletRequestEvent req) {
		try {
			((HttpServletRequest) req.getServletRequest())
					.setCharacterEncoding("gbk");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
