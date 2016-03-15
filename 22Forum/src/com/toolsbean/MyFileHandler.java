package com.toolsbean;

import java.io.File;

public class MyFileHandler {
	/** 
	 * @功能：批量删除文件
	 * @参数：String[]数组，存储要删除文件的物理路径
	 */
	public static void deleteFile(String[] destfilepathname){
		if(destfilepathname!=null&&destfilepathname.length!=0){
			for(int i=0;i<destfilepathname.length;i++){
				File deletefile=new File(destfilepathname[i]);
				if(deletefile.exists()){
					deletefile.delete();			
					System.out.println("成功删除磁盘文件："+destfilepathname[i]);				
				}
			}
		}		
	}
}
