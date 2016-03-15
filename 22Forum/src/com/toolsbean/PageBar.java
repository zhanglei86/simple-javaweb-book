package com.toolsbean;

public class PageBar {
	private int allR;				//���м�¼��
	private int perR;				//ÿҳ��ʾ��¼��
	private int perP;				//ÿҳ��ʾҳ����
	private int allP;				//����ҳ��
	private int allG;				//��������	
	private int currentP=1;			//��ǰҳ��
	private int currentG=1;			//��ǰ���	
	private String pageLink;		//��ҳ��������Ϣ
	
	public PageBar(){
		allR=0;				
		perR=50;				
		perP=10;				
		currentP=1;
		currentG=1;
		pageLink="";
	}
	
	/** ��-�����ܼ�¼�� */
	public void setAllR(int allR){
		this.allR=allR;
	}
	/** ��-����ÿҳ��ʾ��¼�� */
	public void setPerR(int perR){
		this.perR=perR;
	}	
	/** ��-����ÿҳ��ʾҳ���� */
	public void setPerP(int perP){
		this.perP=perP;
	}
	/** ��-���ɷ�ҳ�� */
	public void setPageBar(String strcurrentP,String strcurrentG,String goWhich){
		setAllP();								//������ҳ��
		setAllG();								//����������
		setCurrentP(strcurrentP);				//���õ�ǰҳ��
		setCurerntG(strcurrentG);				//���õ�ǰ���
		setCurrent();							//�������յĵ�ǰҳ��͵�ǰ���
		setPageLink(goWhich);					//���ɷ�ҳ������
	}
	/** ��-������ҳ�� */
	private void setAllP(){
		allP=(allR%perR==0)?(allR/perR):(allR/perR+1);
	}
	/** ��-���������� */
	private void setAllG(){
		allG=(allP%perP==0)?(allP/perP):(allP/perP+1);
	}
	/** ��-���õ�ǰҳ�� */
	private void setCurrentP(String currentP) {		
		try{
			this.currentP=Integer.parseInt(currentP);
		}catch(NumberFormatException e){
			this.currentP=-1;
		}	
	}
	/** ��-���õ�ǰ��� */
	private void setCurerntG(String currentG){
		try{
			this.currentG=Integer.parseInt(currentG);
		}catch(Exception e){
			this.currentG=-1;
		}
	}
	/** ��-�������յĵ�ǰҳ�͵�ǰ�� */
	private void setCurrent(){
		if(currentP==-1&&currentG==-1){					//���ݵ�currentP��currentG��������������ʽ
			currentP=1;
			currentG=1;
		}
		else if(currentP!=-1&&currentG!=-1){			//ͬʱ������currentP��currentG���� 
			currentP=1;
			currentG=1;
		}		
		else if(currentP!=-1){							//ֻ������cuttentP����
			if(currentP>allP)
				currentP=allP;
			currentG=(currentP%perP==0)?(currentP/perP):(currentP/perP)+1;
		}
		else if(currentG!=-1){							//ֻ������currentG����
			if(currentG>allG)
				currentG=allG;
			currentP=(currentG-1)*perP+1;
		}
	}
    /** ��-���÷�ҳ��������Ϣ */
	private void setPageLink(String goWhich){
		pageLink="<table border='0' cellpadding='2' cellspacing='0'>";
		pageLink+="<tr class='pager'>";
		pageLink+="<form action='"+goWhich+"' method='post'>";
		pageLink+="<td>";
		pageLink+="��("+allR+")��¼&nbsp;&nbsp;&nbsp;��("+allP+")ҳ&nbsp;&nbsp;&nbsp;";
		pageLink+="<input type='text' name='currentP' size='7'> ";
		pageLink+="<input type='submit' value='��ת'>";
		pageLink+="</td>";
		pageLink+="</form>";
		pageLink+="</tr>";		

		if(goWhich==null)
			goWhich="";
		if(goWhich.indexOf("?")>=0)
			goWhich+="&";
		else
			goWhich+="?";		
		
		pageLink+="<tr class='pager'>";
		pageLink+="<td>[";
		
		if(currentG>1)
			pageLink+="<a href='"+goWhich+"currentG="+(currentG-1)+"#listtop' class='pagertext'>��"+perP+"ҳ</a> ";
		if(currentP>1){
			pageLink+="<a href='"+goWhich+"currentP=1#listtop'>��ҳ</a> ";
			pageLink+="<a href='"+goWhich+"currentP="+(currentP-1)+"#listtop'>��һҳ</a> | ";
		}
		
		if(currentP%perP==0){															//�����ǰҳ�ǵ�ǰ������һҳ
			currentG+=1;																//����ǰ��ӣ�
			pageLink+="<a class='pagerCurrentP'>"+currentP+"</a>&nbsp;&nbsp;";			//�����ҳ��
		}
		
		//--start--�����ǰ���е�ҳ��
		int start=(currentG-1)*perP+1;								//��ȡ��ʼҳ��
		for(int i=0;((i<perP)&&((start+i)<=allP));i++){				//ͨ��ѭ�����������ǰ���е�ҳ��
			if((start+i)==currentP)
				pageLink+="<a class='pagerCurrentP'>"+(start+i)+"</a>&nbsp;&nbsp;";
			else
				pageLink+="<a href='"+goWhich+"currentP="+(start+i)+"#listtop' class='pagerline'>"+(start+i)+"</a>&nbsp;&nbsp;";
		}		
		//--end--
		
		if(currentP<allP){
			pageLink+="| <a href='"+goWhich+"currentP="+(currentP+1)+"#listtop'>��һҳ</a>";
			pageLink+=" <a href='"+goWhich+"currentP="+allP+"#listtop'>βҳ</a>";
		}		
		if(currentG<allG)
			pageLink+=" <a href='"+goWhich+"currentG="+(currentG+1)+"#listtop' class='pagertext'>��"+perP+"ҳ</a>";
		
		pageLink+="]</td>";		
		pageLink+="</tr>";
		
		pageLink+="</table>";	
	}

	public int getAllR() {
		return allR;
	}
	public int getPerR() {
		return perR;
	}
	public int getPerP() {
		return perP;
	}
	public int getAllP() {
		return allP;
	}
	public int getAllG() {
		return allG;
	}
	public int getCurrentP() {
		return currentP;
	}
	public int getCurrentG() {
		return currentG;
	}
	public String getPageLink() {
		return pageLink;
	}	
}
