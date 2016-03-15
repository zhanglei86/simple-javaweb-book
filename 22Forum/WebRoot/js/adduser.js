function getcheckexistresult(disable){
	if(validatename(regform.name)){
		if(disable=="true"){
			document.all.checknamemessage.innerHTML="●该用户名已经存在，请重新输入！";
			regform.name.focus();
		}
		else{
			document.all.checknamemessage.innerHTML="●该用户名不存在，可以使用！";
			regform.pswd.focus();
		}	
	}
}
function mycheckexistsubmit(formaction){
	if(validatename(regform.name)){		
		regform.action=formaction;
		regform.checknamesubmit.disabled=true;
		regform.submit();	
	}
	else{
		document.all.checkmessage.innerHTML="●用户名只允许由3~25位的英文字母、数字和下划线组成，且由英文字母开头！";
		regform.name.focus();
  		return false;
	}

}
function myaddusersubmit(){
	if(validateForm()){		
		regform.checknamesubmit.disabled=true;
		regform.regsubmitb.disabled=true;
		regform.submit();
	}
	else
		return false;
}
function validateForm() {
	if(!validatename(regform.name)){
		document.all.checkmessage.innerHTML="●用户名只允许由3~25位的英文字母、数字和下划线组成，且由英文字母开头！";
		regform.name.focus();
  		return false;
	}
	if (!validatepswd(regform.pswd)){
		document.all.checkmessage.innerHTML="●密码必须由6~20位的字符组成！";
		regform.pswd.focus();
  		return false;
  	}
	if (!validatepswd(regform.aginpswd)){
		document.all.checkmessage.innerHTML="●密码必须由6~20位的字符组成！";
		regform.aginpswd.focus();
  		return false;
  	}
  	if(regform.pswd.value!=regform.aginpswd.value){
  		document.all.checkmessage.innerHTML="●两次输入的密码不一致！";
		regform.pswd.focus();
  		return false;
  	}
  	if(!isBlank(regform.age)){
		var agereg=/^([1-9]\d{0,1}|1\d{2})$/;
		if(!agereg.test(regform.age.value)){
			document.all.checkmessage.innerHTML="●年龄必须是大于等于1，小于等于199之间的整数！";
			regform.age.focus();
			return false;
		}
	}
	if(!isBlank(regform.oicq)){
		var oicqreg=/^[1-9]\d{0,14}$/;
		if(!oicqreg.test(regform.oicq.value)){
			document.all.checkmessage.innerHTML="●OICQ必须由1~15位的的数字组成！";
			regform.oicq.focus();
			return false;
		}
	}
	return true;
}
function validatename(field){
	var re=/^[a-zA-Z][a-zA-Z0-9_]{2,24}$/;
	if(re.test(field.value))
		return true;
	else
		return false;
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
		var re=/(^\s*)|(\s*$)/;
		stxt=stxt.replace(re,"");
	}
	return stxt;
}