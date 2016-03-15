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
				String basePath = getServletContext().getRealPath("\\videos\\")+"\\"; // ��ȡWebӦ�õ�ʵ�ʴ��·��
				String upFilePath = basePath + "temp\\"+serialName + "."
						+ upfile.getFileExt();			
				String flvFilePath = basePath + serialName + ".flv"; // ����ת��Ϊflv��ʽ���ļ��ı���·��
				String cutPicPath = basePath + serialName + ".jpg"; // ���ô��ϴ�����Ƶ�н�ȡ��ͼƬ�ı���·��
				upfile.saveAs(upFilePath, File.SAVEAS_PHYSICAL); // �����ļ��������У���Ϊ��ʱ�ļ�
				boolean mark = convertVideo(upFilePath, flvFilePath, cutPicPath); // ת����Ƶ��ʽ
				if (mark) { // ת����Ƶ��ʽ�ɹ��������ݱ�����Ӹ���Ƶ��Ϣ
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
		} catch (SecurityException e1) { // ����Υ���������ϴ����ļ����ͺ��׳����쳣
			message = "<li>ֻ�����ϴ� <b>avi��asf��asx��3gp��mpg��mov��mp4��wmv��flv</b> ��ʽͼƬ��</li>";
			e1.printStackTrace();
		} catch (SmartUploadException e2) {
			message = "<li>��Ƶ�ϴ�ʧ�ܣ�</li>";
			e2.printStackTrace();
		} catch (Exception e3) {
			message = "<li>����ʧ�ܣ�</li>";
			e3.printStackTrace();
		} catch (OutOfMemoryError e4) {
			message = "<li>�ϴ�ʧ�ܣ����ϴ����ļ�̫��</li>";
			e4.printStackTrace();
		}
		
		request.setAttribute("message", message);
		request.getRequestDispatcher("admin/media_add.jsp").forward(request,
				response);
	}

	/**
	 * @���ܣ���֤�Ƿ�ѡ����Ҫ�ϴ����ļ����Լ��ļ���С
	 * @���أ�String��ֵ
	 */
	private String validateUpLoad(File upfile) {
		String message = "";
		long maxLen = 30 * 1024 * 1024; // ���������ϴ����ļ�����󳤶�Ϊ30MB
		if (upfile.isMissing()) { // û��ѡ���ļ�
			message += "<li>��ѡ��Ҫ�ϴ�����Ƶ��</li>";
		} else {
			int len = upfile.getSize();
			if (len == 0)
				message = "<li>�������ϴ���СΪ0�Ŀ��ļ���</li>";
			else if (len > maxLen)
				message = "<li>�ϴ�����Ƶ���ӦΪ30MB��</li>";
		}
		return message;
	}

	/**
	 * @���ܣ���ת���ϴ�����ƵΪFLV��ʽ���ڴ��ϴ�����Ƶ�н�ͼ��
	 * @��������upFilePath�� ����ָ��Ҫת����ʽ���ļ�·�����Լ�����ָ��Ҫ��ͼ����Ƶ��<br>
	 * @��������flvFilePath������ָ��ת��ΪFLV��ʽ����ļ��ı���·����<br>
	 * @��������cutPicPath�� ����ָ����ȡ��ͼƬ�ı���·��
	 * @���أ�boolean��ֵ
	 */
	private boolean convertVideo(String upFilePath, String flvFilePath,
			String cutPicPath) {
		String ffmpegPath = getServletContext().getRealPath("\\videos\\")+"\\ffmpeg.exe"; // ��ȡ��web.xml�����õ�ת�����ߣ�ffmpeg.exe���Ĵ��·��
		// ����һ��List����������ת����Ƶ�ļ�Ϊflv��ʽ������
		List<String> convert = new ArrayList<String>();
		convert.add(ffmpegPath); // ���ת������·��
		convert.add("-i"); // ��Ӳ�����-i�����ò���ָ��Ҫת�����ļ�
		convert.add(upFilePath); // ���Ҫת����ʽ����Ƶ�ļ���·��
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
		convert.add("-y"); // ��Ӳ�����-y�����ò���ָ���������Ѵ��ڵ��ļ�
		convert.add(flvFilePath);

		// ����һ��List�������������Ƶ�н�ȡͼƬ������
		List<String> cutpic = new ArrayList<String>();
		cutpic.add(ffmpegPath);
		cutpic.add("-i");
		cutpic.add(upFilePath); // ͬ�ϣ�ָ�����ļ���������ת��Ϊflv��ʽ֮ǰ���ļ���Ҳ������ת����flv�ļ���
		cutpic.add("-y");
		cutpic.add("-f");
		cutpic.add("image2");
		cutpic.add("-ss"); // ��Ӳ�����-ss�����ò���ָ����ȡ����ʼʱ��
		cutpic.add("2"); // �����ʼʱ��Ϊ��9��
		cutpic.add("-t"); // ��Ӳ�����-t�����ò���ָ������ʱ��
		cutpic.add("0.001"); // ��ӳ���ʱ��Ϊ1����
		cutpic.add("-s"); // ��Ӳ�����-s�����ò���ָ����ȡ��ͼƬ��С
		cutpic.add("350*240"); // ��ӽ�ȡ��ͼƬ��СΪ350*240
		cutpic.add(cutPicPath); // ��ӽ�ȡ��ͼƬ�ı���·��

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
