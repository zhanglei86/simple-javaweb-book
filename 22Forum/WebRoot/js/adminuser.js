function myassignsubmit(){
	document.all.adminsubmitb.disabled=true;
	adminform.submit();
}
function checkassignboard(selectid,assignboardsid){
	if(checkselect(selectid,assignboardsid)){
		if(ishasassign(selectid,assignboardsid)){
			document.all.adminsubmitb.disabled=true;
			document.all.checkmessage.innerHTML="��ѡ��İ���Ѿ�������˸ð�����";
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
		document.all.checkmessage.innerHTML="����ѡ��һ����飡";
		return false;
	}
}

function checkassigncategory(selectid,assignboardsid){
	if(checkselect(selectid,assignboardsid)){
		if(ishasassign(selectid,assignboardsid)){
			document.all.adminsubmitb.disabled=true;
			document.all.checkmessage.innerHTML="��ѡ�������Ѿ�������˸�������";
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
		document.all.checkmessage.innerHTML="����ѡ��һ�����";
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
		document.all.checkmessage.innerHTML="�����������6~20λ���ַ���ɣ�";
		return false;		
	}
}
function mymodifyuserinfosubmit(){
	if(!isBlank(adminform.age)){
		var agereg=/^([1-9]\d{0,1}|1\d{2})$/;
		if(!agereg.test(adminform.age.value)){
			document.all.checkmessage.innerHTML="����������Ǵ��ڵ���1��С�ڵ���199֮���������";
			adminform.age.focus();
			return false;
		}
	}
	if(!isBlank(adminform.oicq)){
		var oicqreg=/^[1-9]\d{0,14}$/;
		if(!oicqreg.test(adminform.oicq.value)){
			document.all.checkmessage.innerHTML="��OICQ������1~15λ�ĵ�������ɣ�";
			adminform.oicq.focus();
			return false;
		}
	}

	adminform.adminsubmitb.disabled=true;
	adminform.submit();
}
function myupdateuserpswdsubmit(){
	if(!validatepswd(adminform.newpswd)){
		document.all.checkmessage.innerHTML="�������������6~20λ���ַ���ɣ�";
		adminform.newpswd.focus();
		return false;	
	}	
	if(!validatepswd(adminform.aginpswd)){
		document.all.checkmessage.innerHTML="��ȷ�����������6~20λ���ַ���ɣ�";
		adminform.aginpswd.focus();
		return false;	
	}
	if(adminform.newpswd.value!=adminform.aginpswd.value){
		document.all.checkmessage.innerHTML="��������������벻һ�£�";
		adminform.newpswd.focus();
		return false;
	}
	if(!validatepswd(adminform.adminpswd)){
		document.all.checkmessage.innerHTML="�����Ա���������6~20λ���ַ���ɣ�";
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
		document.all.checkmessage.innerHTML="�����Ա���������6~20λ���ַ���ɣ�";
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
			document.all.checkmessage.innerHTML="�������ԱIDֵ�������֣�";
			adminform.searchid.focus();
			return false;
		}
		else if(adminform.searchid.value<0){
			document.all.checkmessage.innerHTML="�������ԱIDֵ�������0��";
			adminform.searchid.focus();
			return false;
		}
	}
	else if(isBlank(adminform.searchname)){
		document.all.checkmessage.innerHTML="������������������";
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