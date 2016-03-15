function myloginsubmit(){
	if(validateForm()){		
		loginform.submitb.disabled=true;
		loginform.submit();
	}
}
function document.onkeypress(){
	if(event.keyCode==13)
		myloginsubmit();
}
function validateForm() {
	if (!validatename()){
	    document.all.checkmessage.innerText="●用户名只允许由3~25位的英文字母、数字和下划线组成，且由英文字母开头！";
	    loginform.membername.focus();
  		return false;
  	}
	if (!validatepswd()){
	 	document.all.checkmessage.innerText="●密码必须由6~20位的字符组成！";
	    loginform.memberpswd.focus();
  		return false;
	}
	if(!validateverifycode())
		return false;
  	return true;
}
function validatename(){
	var re=/^[a-zA-Z][a-zA-Z0-9_]{2,24}$/;
	if(re.test(loginform.membername.value))
		return true;
	else
		return false;
}
function validatepswd(){
	var re=/^[^\s*]{6,20}$/;
	if(re.test(loginform.memberpswd.value))
		return true;
	else
		return false;
}
function validateverifycode(){
	var re=/^[a-zA-Z0-9]{5}$/;
	if(!re.test(loginform.inverifycode.value)){
		document.all.checkmessage.innerText="●验证码必须是5位字符！";
	    loginform.inverifycode.focus();
		return false;
	}	
	return true;
}