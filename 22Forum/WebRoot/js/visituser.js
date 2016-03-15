function myupdatepswdsubmit(){
	if(validateForm()){		
		updatepswdform.updateswdsubmitb.disabled=true;
		updatepswdform.submit();
	}
	else 
		return false;
}
function validateForm() {
	if (!validatepswd(updatepswdform.adminpswd)){
		document.all.checkmessage.innerHTML="●管理员密码必须由6~20位的字符组成！";
		updatepswdform.adminpswd.focus();
  		return false;
  	}
	if (!validatepswd(updatepswdform.newpswd)){
		document.all.checkmessage.innerHTML="●管理员密码必须由6~20位的字符组成！";
		updatepswdform.newpswd.focus();
  		return false;
  	}
	if (!validatepswd(updatepswdform.aginnewpswd)){
		document.all.checkmessage.innerHTML="●管理员密码必须由6~20位的字符组成！";
		updatepswdform.aginnewpswd.focus();
  		return false;
	}
	if(isequal())
		return true;
	else{
		document.all.checkmessage.innerHTML="●输入的新密码和确认密码不一致！";
		updatepswdform.newpswd.focus();
		return false;
	}
}
function validatepswd(xpswd){
	var re=/^[^\s*]{6,20}$/;
	if(re.test(xpswd.value))
		return true;
	else
		return false;
}
function isequal(){
	if(updatepswdform.newpswd.value==updatepswdform.aginnewpswd.value)
		return true;
	else
		return false;
}
function mysendmessagesubmit(){
	messageform.submit();
	document.execCommand("stop");
	if(checkInput()){
		messageform.sendsubmitb.disabled=true;
		messageform.submit();
	}
}
function checkInput(){
	var getter=messageform.getter.value;
	var title=messageform.title.value;
	var content=messageform.content.value;
	if(getter==null||getter.length==0){
		document.all.checkmessage.innerHTML="●请输入消息接收人！";
		messageform.getter.focus();
		return false;
	}
	if(title==null||title.length==0){
		document.all.checkmessage.innerHTML="●请输入消息标题！";
		messageform.title.focus();
		return false;
	}
	if(content==null||content.length==0){
		document.all.checkmessage.innerHTML="●请输入消息内容！";
		return false;
	}
	return checkLen();
}
function checkLen(){
	var maxlen=document.all.ContentAll.innerText;
	var inlen=messageform.content.value.length;    
    if(inlen>maxlen){
        document.all.checkmessage.innerHTML="●消息内容最多允许输入"+maxlen+"个字符！";
        return false;
    }
    else
        return true;
}
function selectall(){
	 parent.document.all.checkmessage.innerHTML="";
	tages=document.getElementsByName("selectmessages");
	if(document.all.selectall.checked){
		for(i=0;i<tages.length;i++)
			tages[i].checked=true;
	}
	else if(!document.all.selectall.checked){
		for(i=0;i<tages.length;i++)
			tages[i].checked=false;
	}
}
function oneclick(targetid){
	parent.document.all.checkmessage.innerHTML=""
	tag=document.getElementById(targetid);	
	if(!tag.checked)
		document.all.selectall.checked=false;
}
function mydeletemessagesubmit(){
	if(checkSelect()){
		messageform.submit();
		messageform.deletesubmitb.disabled=true;
	}
	else
		parent.document.all.checkmessage.innerHTML="●请选定要删除的消息！";
}
function checkSelect(){
	tages=document.getElementsByName("selectmessages");
	for(i=0;i<tages.length;i++){
		if(tages[i].checked){
			return true;
		}
	}
	return false;
}
function mymodifymyinfosubmit(){
	if(!isBlank(editmyinfoform.age)){
		var agereg=/^([1-9]\d{0,1}|1\d{2})$/;
		if(!agereg.test(editmyinfoform.age.value)){
			document.all.checkmessage.innerHTML="●年龄必须是大于等于1，小于等于199之间的整数！";
			editmyinfoform.age.focus();
			return false;
		}
	}
	if(!isBlank(editmyinfoform.oicq)){
		var oicqreg=/^[1-9]\d{0,14}$/;
		if(!oicqreg.test(editmyinfoform.oicq.value)){
			document.all.checkmessage.innerHTML="●OICQ必须由1~15位的的数字组成！";
			editmyinfoform.oicq.focus();
			return false;
		}
	}

	editmyinfoform.editmyinfosubmitb.disabled=true;
	editmyinfoform.submit();
}
function isBlank(field) {
    var temp=tirm(field.value);
    if(temp.length == 0){
	    field.focus();
    	return true;
    }
    else
        return false;
}
function tirm(stxt){
	if(stxt!=null&&stxt!=""){
		var re=/(^\s*)|(\s*$)/g;
		stxt=stxt.replace(re,"");
		return stxt;
	}
	else
		return "";
}