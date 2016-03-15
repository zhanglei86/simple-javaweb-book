package com.toolsbean;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StringHandler {
	public static boolean isexist(int id,int[] ids){
		for(int i=0;i<ids.length;i++){
			if(id==ids[i])
				return true;
		}
		return false;
	}
	public static String getQuoteText(String author,String content){
		String quote="[q="+author+"]<br>"+content+"[/q]";
		return quote;
	}
	public static String changeQuoteSign(String quote){
		String str=""+quote;
		String quotestart1="<center><fieldset style=\"width: 98%; text-align: left\">";
		String quotestart2="<legend>引用<font color=\"#ff0000\">(";
		String quoteend2=")</font>回复</legend>";
		String quoteend1="</fieldset></center>";
		if(quote!=null&&!quote.equals("")){
			str=quote.replace("[q=",quotestart1+quotestart2)
					 .replace("]<br>",quoteend2)
					 .replace("[/q]",quoteend1);
		}
		return str;
	}
	
	public static String saveQuoteText(String savequote){
		String str="";
		String quotestart1="<center><fieldset style=\"width: 98%; text-align: left\">";
		String quotestart2="<legend>引用<font color=\"#ff0000\">(";
		String quoteend2=")</font>回复</legend>";
		String quoteend1="</fieldset></center>";
		
		str=savequote.replace(quotestart1+quotestart2,"[q=");
		str=str.replace(quoteend2,"]<br>");	
		str=str.replace(quoteend1,"[/q]");
		return str;
	}

	public static String delBlank(String str){
		if(str!=null)
			return str.replace(" ","");
		return null;
	}
	public static Integer strToint(String str){
		if(str!=null&&!str.equals("")){
			try{
				return Integer.parseInt(str);
			}catch(NumberFormatException e){
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	public static String timeTostr(Date date){
		String strDate="";
		if(date!=null){
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			strDate=format.format(date);		
		}
		return strDate;
	}
	public static String[] getSerial(Date date,int count){
		String[] serials=new String[count];
		long msel=date.getTime();		
		SimpleDateFormat fm=new SimpleDateFormat("MMddyyyyHHmmssSS");
		
		for(int i=0;i<count;i++){
			msel++;		
			date.setTime(msel);
			serials[i]=fm.format(date);
		}
		return serials;
	}
	public static int[] changeToIntArray(String[] strs){
		int[] nums=null;
		if(strs!=null){
			nums=new int[strs.length];
			for(int i=0;i<strs.length;i++)
				nums[i]=Integer.parseInt(strs[i]);
		}
		return nums;
	}
	public static String changeToChinese(String str){
		String change="";
		if(str!=null){
			try {
				change=new String(str.getBytes("ISO-8859-1"),"gb2312");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return change;
	}
	public static List StringToList(String str){
		String[] arraystrs=null;
		List liststrs=null;
		       
		if(str!=null&&!str.equals("")){
			liststrs=new ArrayList();
			arraystrs=str.split("\r\n");
			for(int i=0;i<arraystrs.length;i++){
				if(arraystrs[i]!=null&&!arraystrs[i].equals(""))
					liststrs.add(arraystrs[i]);
			}
		}
		return liststrs;
	}
	public static String ArrayToString(int[] array){
		String str="";
		if(array!=null&&array.length!=0){
			for(int i=0;i<array.length;i++)
				str+=array[i]+",";
		}
		str=str.substring(0,str.lastIndexOf(","));
		return str;
	}
	public static String changehtml(String str){
		String change="";
		if(str!=null&&!str.equals("")){
			change=str.replace("&","&amp;");			
			change=change.replace(" ","&nbsp;");
			change=change.replace("<","&lt;");
			change=change.replace(">","&gt;");
			change=change.replace("\"","&quot;");
			change=change.replace("\r\n","<br>");
		}
		return change;
	}
}