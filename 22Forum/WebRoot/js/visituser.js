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
		document.all.checkmessage.innerHTML="�����Ա���������6~20λ���ַ���ɣ�";
		updatepswdform.adminpswd.focus();
  		return false;
  	}
	if (!validatepswd(updatepswdform.newpswd)){
		document.all.checkmessage.innerHTML="�����Ա���������6~20λ���ַ���ɣ�";
		updatepswdform.newpswd.focus();
  		return false;
  	}
	if (!validatepswd(updatepswdform.aginnewpswd)){
		document.all.checkmessage.innerHTML="�����Ա���������6~20λ���ַ���ɣ�";
		updatepswdform.aginnewpswd.focus();
  		return false;
	}
	if(isequal())
		return true;
	else{
		document.all.checkmessage.innerHTML="��������������ȷ�����벻һ�£�";
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
		document.all.checkmessage.innerHTML="����������Ϣ�����ˣ�";
		messageform.getter.focus();
		return false;
	}
	if(title==null||title.length==0){
		document.all.checkmessage.innerHTML="����������Ϣ���⣡";
		messageform.title.focus();
		return false;
	}
	if(content==null||content.length==0){
		document.all.checkmessage.innerHTML="����������Ϣ���ݣ�";
		return false;
	}
	return checkLen();
}
function checkLen(){
	var maxlen=document.all.ContentAll.innerText;
	var inlen=messageform.content.value.length;    
    if(inlen>maxlen){
        document.all.checkmessage.innerHTML="����Ϣ���������������"+maxlen+"���ַ���";
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
		parent.document.all.checkmessage.innerHTML="����ѡ��Ҫɾ������Ϣ��";
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
			document.all.checkmessage.innerHTML="����������Ǵ��ڵ���1��С�ڵ���199֮���������";
			editmyinfoform.age.focus();
			return false;
		}
	}
	if(!isBlank(editmyinfoform.oicq)){
		var oicqreg=/^[1-9]\d{0,14}$/;
		if(!oicqreg.test(editmyinfoform.oicq.value)){
			document.all.checkmessage.innerHTML="��OICQ������1~15λ�ĵ�������ɣ�";
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