package com.toolsbean;

import java.io.File;

public class MyFileHandler {
	/** 
	 * @���ܣ�����ɾ���ļ�
	 * @������String[]���飬�洢Ҫɾ���ļ�������·��
	 */
	public static void deleteFile(String[] destfilepathname){
		if(destfilepathname!=null&&destfilepathname.length!=0){
			for(int i=0;i<destfilepathname.length;i++){
				File deletefile=new File(destfilepathname[i]);
				if(deletefile.exists()){
					deletefile.delete();			
					System.out.println("�ɹ�ɾ�������ļ���"+destfilepathname[i]);				
				}
			}
		}		
	}
}
