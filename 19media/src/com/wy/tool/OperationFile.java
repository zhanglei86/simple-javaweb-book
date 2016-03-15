package com.wy.tool;

import java.io.File;

public class OperationFile {
	
	
	public static boolean deleteFile(String path){
		java.io.File file= new java.io.File(path);
		return file.delete();
	}
	

}
