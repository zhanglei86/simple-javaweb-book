function mymodifygroupinfosubmit(){
	if(isBlank(adminform.groupInfo)){
		document.all.checkmessage.innerHTML="���������û���������Ϣ��";
		adminform.groupInfo.focus();
		return false;
	}
	adminform.adminsubmitb.disabled=true;
	adminform.submit();
}
function myaddnewmembertogroupsubmit(){
	if(isBlank(adminform.inputmembernames)){
		document.all.checkmessage.innerHTML="���������Ա���ƣ�";
		adminform.inputmembernames.focus();
		return false;
	}
	if(!validatepswd(adminform.adminpswd)){
		document.all.checkmessage.innerHTML="�����������6~20λ���ַ���ɣ�";
		adminform.adminpswd.focus();
		return false;
	}
	adminform.adminsubmitb.disabled=true;
	adminform.submit();
}
function myremovememberfromgroupsubmit(){
	if(!validatepswd(adminform.adminpswd)){
		document.all.checkmessage.innerHTML="�����������6~20λ���ַ���ɣ�";
		adminform.adminpswd.focus();
		return false;
	}
	adminform.adminsubmitb.disabled=true;
	adminform.submit();
}
function validatepswd(xpswd){
	var re=/^[^\s*]{6,20}$/;
	if(re.test(xpswd.value))
		return true;
	else
		return false;
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