function myaddboardsubmit(){
	if (isBlank(adminform.boardname,"版块名称"))
  		return false;
	if(checkLen(adminform.boardinfo)){
		document.all.adminsubmitb.disabled=true;
		adminform.submit();
	}
}
function mymodifyboardsubmit(){
	if (isBlank(adminform.boardname,"版块名称"))
  		return false;
  	if (isBlank(adminform.boardorder,"版块序号"))
  		return false;
  	if(isNaN(adminform.boardorder.value)){
  		document.all.checkmessage.innerHTML="●输入的版块序号不是数字！";
  		return false;
  	}
  	else if(adminform.boardorder.value<0){
  		document.all.checkmessage.innerHTML="●输入的版块序号必须大于0！";
  		return false;
  	}
  	if(checkLen(adminform.boardinfo)){
		document.all.adminsubmitb.disabled=true;
		adminform.submit();
	}
}
function myaddcategorysubmit(){
	if (isBlank(adminform.categoryName,"类别名称"))
  		return false;
	if(checkLen(adminform.categoryInfo)){
		document.all.adminsubmitb.disabled=true;
		adminform.submit();
	}
}
function mymodifycategorysubmit(){
	if (isBlank(adminform.categoryname,"类别名称"))
  		return false;
  	if (isBlank(adminform.categoryorder,"类别序号"))
  		return false;
  	if(isNaN(adminform.categoryorder.value)){
  		document.all.checkmessage.innerHTML="●输入的类别序号不是数字！";
  		return false;
  	}
  	else if(adminform.categoryorder.value<0){
  		document.all.checkmessage.innerHTML="●输入的类别序号必须大于0！";
  		return false;
  	}
  	if(checkLen(adminform.categoryinfo)){
		document.all.adminsubmitb.disabled=true;
		adminform.submit();
	}
}
function myaddmastersubmit(){
	if (isBlank(adminform.newmaster,"版主名称"))
  		return false;
	myvalidateadminpswdsubmit();
}
function myvalidateadminpswdsubmit(){
	var pswdv=adminform.adminpswd.value;	
	if(pswdv==null||pswdv.length==0){
		document.all.checkmessage.innerHTML="●请输入管理员密码！";
		adminform.adminpswd.focus();
		return false;
	}
	else if(pswdv.length<6){
		document.all.checkmessage.innerHTML="●管理员密码长度最小为６！";
		adminform.adminpswd.focus();
		return false;
	}
	else{
		document.all.adminsubmitb.disabled=true;
		adminform.submit();
	}
}
function isBlank(field,title) {
    var temp=tirm(field.value);
    if(temp.length == 0){
    	document.all.checkmessage.innerHTML="●请输入"+title+"！";
	    field.focus();
    	return true;
    }	
}
function tirm(stxt){
	if(stxt!=null&&stxt!=""){
		var re=/(^\s*)|(\s*$)/g;
		stxt=stxt.replace(re,"");
	}
	return stxt;
}

function checkLen(field){
	var maxlen=document.all.ContentAll.innerText;
	var useName=document.all.ContentUse;
	var remName=document.all.ContentRem;
	var inlen=field.value.length;
    
    if(inlen>maxlen){
        field.value=(field.value).substring(0,maxlen);
        document.all.checkmessage.innerHTML="●最多允许输入 "+maxlen+" 个字符！";
        return false;
    }
    else{
        useName.innerText=eval(field.value.length);
        remName.innerText=maxlen-useName.innerText;
        return true;
    }
}