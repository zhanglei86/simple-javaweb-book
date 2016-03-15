package com.wy.sevlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jspsmart.upload.File;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
import com.wy.dao.MediaDao;
import com.wy.model.MediaInfo;

public class MediaServlet extends HttpServlet {

	private MediaDao mediaDao = null;

	private MediaInfo mediaInfo = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		mediaDao = new MediaDao();
		Integer sign = Integer.valueOf(request.getParameter("sign"));
		if (sign == 0) {
			this.doInsert(request, response);
		}

	}

	protected void doInsert(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String message = "";
		try {
			SmartUpload myup = new SmartUpload();
			myup.initialize(this, request, response);
			myup.setAllowedFilesList("avi,asf,asx,3gp,mpg,mov,mp4,wmv,flv");
			myup.upload();
			File upfile = myup.getFiles().getFile(0);
			message = validateUpLoad(upfile);
			if (message.equals("")) {
				String serialName = String.valueOf(System.currentTimeMillis());
				String basePath = getServletContext().getRealPath("\\videos\\")+"\\"; // 获取Web应用的实际存放路径
				String upFilePath = basePath + "temp\\"+serialName + "."
						+ upfile.getFileExt();			
				String flvFilePath = basePath + serialName + ".flv"; // 设置转换为flv格式后文件的保存路径
				String cutPicPath = basePath + serialName + ".jpg"; // 设置从上传的视频中截取的图片的保存路径
				upfile.saveAs(upFilePath, File.SAVEAS_PHYSICAL); // 保存文件到磁盘中，作为临时文件
				boolean mark = convertVideo(upFilePath, flvFilePath, cutPicPath); // 转换视频格式
				if (mark) { // 转换视频格式成功，向数据表中添加该视频信息
					mediaInfo = new MediaInfo();
					mediaInfo.setMediaSrc("videos\\"+serialName + ".flv");
					mediaInfo.setMediaPic("videos\\"+serialName + ".jpg");
					mediaInfo.setMediaInfo(myup.getRequest().getParameter(
							"info"));
					mediaInfo.setMediaTitle(myup.getRequest().getParameter(
							"title"));
					mediaInfo.setMedia_type(myup.getRequest().getParameter(
							"type"));
					mediaDao.media_add(mediaInfo);
				}
			}
		} catch (SecurityException e1) { // 捕获违反了允许上传的文件类型后抛出的异常
			message = "<li>只允许上传 <b>avi、asf、asx、3gp、mpg、mov、mp4、wmv、flv</b> 格式图片！</li>";
			e1.printStackTrace();
		} catch (SmartUploadException e2) {
			message = "<li>视频上传失败！</li>";
			e2.printStackTrace();
		} catch (Exception e3) {
			message = "<li>操作失败！</li>";
			e3.printStackTrace();
		} catch (OutOfMemoryError e4) {
			message = "<li>上传失败！您上传的文件太大！</li>";
			e4.printStackTrace();
		}
		
		request.setAttribute("message", message);
		request.getRequestDispatcher("admin/media_add.jsp").forward(request,
				response);
	}

	/**
	 * @功能：验证是否选择了要上传的文件，以及文件大小
	 * @返回：String型值
	 */
	private String validateUpLoad(File upfile) {
		String message = "";
		long maxLen = 30 * 1024 * 1024; // 设置允许上传的文件的最大长度为30MB
		if (upfile.isMissing()) { // 没有选择文件
			message += "<li>请选择要上传的视频！</li>";
		} else {
			int len = upfile.getSize();
			if (len == 0)
				message = "<li>不允许上传大小为0的空文件！</li>";
			else if (len > maxLen)
				message = "<li>上传的视频最大应为30MB！</li>";
		}
		return message;
	}

	/**
	 * @功能：①转换上传的视频为FLV格式；②从上传的视频中截图。
	 * @参数：①upFilePath： 用于指定要转换格式的文件路径；以及用来指定要截图的视频。<br>
	 * @参数：②flvFilePath：用于指定转换为FLV格式后的文件的保存路径。<br>
	 * @参数：③cutPicPath： 用于指定截取的图片的保存路径
	 * @返回：boolean型值
	 */
	private boolean convertVideo(String upFilePath, String flvFilePath,
			String cutPicPath) {
		String ffmpegPath = getServletContext().getRealPath("\\videos\\")+"\\ffmpeg.exe"; // 获取在web.xml中配置的转换工具（ffmpeg.exe）的存放路径
		// 创建一个List集合来保存转换视频文件为flv格式的命令
		List<String> convert = new ArrayList<String>();
		convert.add(ffmpegPath); // 添加转换工具路径
		convert.add("-i"); // 添加参数＂-i＂，该参数指定要转换的文件
		convert.add(upFilePath); // 添加要转换格式的视频文件的路径
		convert.add("-qscale");
		convert.add("6");
		convert.add("-ab");
		convert.add("64");
		convert.add("-acodec");
		convert.add("mp3");
		convert.add("-ac");
		convert.add("2");
		convert.add("-ar");
		convert.add("22050");
		convert.add("-r");
		convert.add("24");
		convert.add("-y"); // 添加参数＂-y＂，该参数指定将覆盖已存在的文件
		convert.add(flvFilePath);

		// 创建一个List集合来保存从视频中截取图片的命令
		List<String> cutpic = new ArrayList<String>();
		cutpic.add(ffmpegPath);
		cutpic.add("-i");
		cutpic.add(upFilePath); // 同上（指定的文件即可以是转换为flv格式之前的文件，也可以是转换的flv文件）
		cutpic.add("-y");
		cutpic.add("-f");
		cutpic.add("image2");
		cutpic.add("-ss"); // 添加参数＂-ss＂，该参数指定截取的起始时间
		cutpic.add("2"); // 添加起始时间为第9秒
		cutpic.add("-t"); // 添加参数＂-t＂，该参数指定持续时间
		cutpic.add("0.001"); // 添加持续时间为1毫秒
		cutpic.add("-s"); // 添加参数＂-s＂，该参数指定截取的图片大小
		cutpic.add("350*240"); // 添加截取的图片大小为350*240
		cutpic.add(cutPicPath); // 添加截取的图片的保存路径

		boolean mark = true;
		ProcessBuilder builder = new ProcessBuilder();
		try {
			builder.command(convert);
			builder.start();
			builder.command(cutpic);
			builder.start();
		} catch (Exception e) {
			mark = false;
			System.out.println(e);
			e.printStackTrace();
		}
		return mark;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
