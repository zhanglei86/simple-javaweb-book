<%@ page contentType="text/html; charset=gb2312" language="java"%>
<script language="javascript">
timer = window.setInterval("getScrollScrip()",10000); 
window.onload=function(){
	 getScrollScrip();		//��ҳ����������Ajax��ȡ���µ�10��������Ϣ
}
function getScrollScrip(){
	var loader1=new net.AjaxRequest("scrip.do?action=scrollScrip&nocache="+new Date().getTime(),deal_getScrollScrip,onerror,"GET");
}
//Ajax�Ļص�����
function deal_getScrollScrip(){
	document.getElementById("scrollScripContent").innerHTML=this.req.responseText;
}
</script>
<div id="scrollScrip">����10��������
  <marquee direction="left" scrollamount="2" width="90%" height="30" onMouseMove="this.stop()" onMouseOut="this.start()">
  <span id="scrollScripContent">���ڻ�ȡ��������......</span>
  </marquee>
</div>