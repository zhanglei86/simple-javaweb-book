function myvalidateadminsubmit(){
	if(!validatepswd(changeform.adminpswd)){
		document.all.checkmessage.innerHTML="�����Ա���������6~20λ���ַ���ɣ�";
		changeform.adminpswd.focus();
  		return false;
	}
	else{
		changeform.editsubmitb.disabled=true;
		changeform.submit();
	}
}
function validatepswd(xpswd){
	var re=/^[^\s*]{6,20}$/;
	if(re.test(xpswd.value))
		return true;
	else
		return false;
}
function myupattachmentsubmit(){
	if(checkattachmentselect()){
		attachmentform.submitb.disabled=true;
		attachmentform.submit();
	}
}
function checkattachmentselect(){
	var st1=document.all.file1.value;
	var st2=document.all.file2.value;
	var st3=document.all.file3.value;
	var st4=document.all.file4.value;
	if((st1==null||st1.length==0)&&(st2==null||st2.length==0)&&(st3==null||st3.length==0)&&(st4==null||st4.length==0)){
		parent.document.all.message.innerHTML="����ѡ���ϴ����ļ���";
		document.all.file1.focus();
		return false;
	}
	return true;
}
function showorhiden(whichTrs,whichImg,webName){
    var tag=document.getElementById(whichTrs);
    var img=document.getElementById(whichImg);
    if(tag==null||img==null)
    	return;
    if(tag.style.display == ""){
    	tag.style.display = "none";
    	img.src=webName+"/images/icon/open.gif";
    }
    else{
    	tag.style.display = "";
    	img.src=webName+"/images/icon/close.gif";
    }    
}
function changesubsizeshow(whichiframe){	
	var tag=parent.document.getElementById(whichiframe);
	tag.height=document.body.scrollHeight;
}
function gotop(where){
	var topurl=parent.location.href;
	var position=topurl.indexOf("#");
	if(position<0)
		topurl+="#"+where;
	else
		topurl=topurl.substring(0,position)+"#"+where;
	parent.location.href=topurl;
}
function showreplyface(){

	var tag=document.getElementById("replyface");
	tag.style.display="";
}
function checkmovetopicselect(selectid,visitboardid){
	if(selectid!=null&&selectid!=""){
		if(selectid==visitboardid){
			document.all.editsubmitb.disabled=true;
			document.all.checkmessage.innerHTML="������ͬһ�������ƶ����ӣ�";
		}
		else{
			document.all.editsubmitb.disabled=false;
			document.all.checkmessage.innerHTML="";
		}
	}
	else{
		document.all.editsubmitb.disabled=true;
		document.all.checkmessage.innerHTML="����ѡ��һ����飡";
	}
}