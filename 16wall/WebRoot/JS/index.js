var Layer='';
document.onmouseup=moveEnd;
document.onmousemove=moveStart;
var b;
var c;
function Move(Object,event){
	Layer=Object.id;
	if(document.all){
		document.getElementById(Layer).setCapture();
		b=event.x-document.getElementById(Layer).style.pixelLeft;
		c=event.y-document.getElementById(Layer).style.pixelTop;
	}else if(window.captureEvents){
		window.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);
		b=event.layerX;
		c=event.layerY;
	}
	/*********��ѡ�������ö�*********/
		document.getElementById(Layer).style.zIndex=iLayerMaxNum;
		iLayerMaxNum=iLayerMaxNum+1;
	/********************************/
}
function moveStart(){
	if(Layer!=''){
		if(document.all){
			document.getElementById(Layer).style.left=event.x-b;
			document.getElementById(Layer).style.top=event.y-c;
		}else if(window.captureEvents){
			document.getElementById(Layer).style.left=(d.clientX-b)+"px";
			document.getElementById(Layer).style.top=(d.clientY-c)+"px";
		}
	}
}
function moveEnd(){
	if(Layer!=''){
		if(document.all){
			document.getElementById(Layer).releaseCapture();
			Layer='';
		}else if(window.captureEvents){
			window.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);
			Layer='';
		}
	}
}
//�ر�����
function myClose(n){
	var e='scrip'+n;											
	document.getElementById(e).style.display='none';
}
//˫������ʱ��ͻ����ʾ
function Show(n,divName){
	var e='scrip'+n;
	document.getElementById(divName).style.display = "block";
	document.getElementById(divName).style.zIndex = iLayerMaxNum;
	document.getElementById(divName).style.width = document.body.clientWidth;
	document.getElementById(divName).style.height = document.body.clientHeight;	
	document.getElementById(e).style.zIndex =iLayerMaxNum+1;		//��ѡ�������ö�
}	
//�������ղ�
function Hide(){
	document.getElementById("shadeDiv").style.display = "none";
	iLayerMaxNum=iLayerMaxNum+2;
}
/***************************************�������*****************************************************************/
function holdout(id){
	if(id>0){
		var loader=new net.AjaxRequest("scrip.do?action=addHoldout&scripId="+id+"&nocache="+new Date().getTime(),deal_addHoldout,onerror,"GET");
	}
}
function deal_addHoldout(){
	h=this.req.responseText;
	var hitsId="hitsValue"+h.substr(0,h.indexOf("#"));
	hitsId=hitsId.replace(/\s/g,"");	//ȥ���ַ����е�Unicode�հ׷�
	var hitsNum=h.substr(h.indexOf("#")+1,h.length-h.indexOf("#")-1);	
	document.getElementById(hitsId).innerHTML=hitsNum;
}
/*******************************************************************************************************************/