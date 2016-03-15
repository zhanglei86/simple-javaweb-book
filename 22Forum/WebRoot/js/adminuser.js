function myassignsubmit(){
	document.all.adminsubmitb.disabled=true;
	adminform.submit();
}
function checkassignboard(selectid,assignboardsid){
	if(checkselect(selectid,assignboardsid)){
		if(ishasassign(selectid,assignboardsid)){
			document.all.adminsubmitb.disabled=true;
			document.all.checkmessage.innerHTML="●选择的版块已经分配给了该版主！";
			return false;
		}
		else{
			document.all.adminsubmitb.disabled=false;
			document.all.checkmessage.innerHTML="";
			return true;
		}		
	}
	else{
		document.all.adminsubmitb.disabled=true;
		document.all.checkmessage.innerHTML="●请选择一个版块！";
		return false;
	}
}

function checkassigncategory(selectid,assignboardsid){
	if(checkselect(selectid,assignboardsid)){
		if(ishasassign(selectid,assignboardsid)){
			document.all.adminsubmitb.disabled=true;
			document.all.checkmessage.innerHTML="●选择的类别已经分配给了该类主！";
			return false;
		}
		else{
			document.all.adminsubmitb.disabled=false;
			document.all.checkmessage.innerHTML="";
			return true;
		}	
	}
	else{
		document.all.adminsubmitb.disabled=true;
		document.all.checkmessage.innerHTML="●请选择一个类别！";
		return false;
	}
}
function checkselect(selectid,assignboardsid){
	if(selectid!=null&&selectid!="")
		return true;
	else
		return false;
}
function ishasassign(selectid,assignids){
	if(assignids!=null&&assignids.length!=0){
		strassignids=assignids.split(",");
		for(i=0;i<strassignids.length;i++){
			if(selectid==strassignids[i])
				return true;				
		}
	}
	return false;	
}
function mydeleteusersubmit(){
	if(validatepswd(adminform.adminpswd)){
		document.all.adminsubmitb.disabled=true;
		adminform.submit();
	}
	else{
		adminform.adminpswd.focus();
		document.all.checkmessage.innerHTML="●密码必须由6~20位的字符组成！";
		return false;		
	}
}
function mymodifyuserinfosubmit(){
	if(!isBlank(adminform.age)){
		var agereg=/^([1-9]\d{0,1}|1\d{2})$/;
		if(!agereg.test(adminform.age.value)){
			document.all.checkmessage.innerHTML="●年龄必须是大于等于1，小于等于199之间的整数！";
			adminform.age.focus();
			return false;
		}
	}
	if(!isBlank(adminform.oicq)){
		var oicqreg=/^[1-9]\d{0,14}$/;
		if(!oicqreg.test(adminform.oicq.value)){
			document.all.checkmessage.innerHTML="●OICQ必须由1~15位的的数字组成！";
			adminform.oicq.focus();
			return false;
		}
	}

	adminform.adminsubmitb.disabled=true;
	adminform.submit();
}
function myupdateuserpswdsubmit(){
	if(!validatepswd(adminform.newpswd)){
		document.all.checkmessage.innerHTML="●新密码必须由6~20位的字符组成！";
		adminform.newpswd.focus();
		return false;	
	}	
	if(!validatepswd(adminform.aginpswd)){
		document.all.checkmessage.innerHTML="●确认密码必须由6~20位的字符组成！";
		adminform.aginpswd.focus();
		return false;	
	}
	if(adminform.newpswd.value!=adminform.aginpswd.value){
		document.all.checkmessage.innerHTML="●两次输入的密码不一致！";
		adminform.newpswd.focus();
		return false;
	}
	if(!validatepswd(adminform.adminpswd)){
		document.all.checkmessage.innerHTML="●管理员密码必须由6~20位的字符组成！";
		adminform.adminpswd.focus();
		return false;
	}	
	
	document.all.adminsubmitb.disabled=true;
	adminform.submit();
}
function mycancelmastersubmit(){
	if(validatepswd(adminform.adminpswd)){
		document.all.adminsubmitb.disabled=true;
		adminform.submit();
	}
	else{
		document.all.checkmessage.innerHTML="●管理员密码必须由6~20位的字符组成！";
		adminform.adminpswd.focus();
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
function mysearchsubmit(){
	if(!isBlank(adminform.searchid)){
		if(isNaN(adminform.searchid.value)){
			document.all.checkmessage.innerHTML="●输入会员ID值不是数字！";
			adminform.searchid.focus();
			return false;
		}
		else if(adminform.searchid.value<0){
			document.all.checkmessage.innerHTML="●输入会员ID值必须大于0！";
			adminform.searchid.focus();
			return false;
		}
	}
	else if(isBlank(adminform.searchname)){
		document.all.checkmessage.innerHTML="●请输入搜索条件！";
		adminform.searchid.focus();
		return false;
	}
	
	adminform.adminsubmitb.disabled=true;
	adminform.submit();		
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