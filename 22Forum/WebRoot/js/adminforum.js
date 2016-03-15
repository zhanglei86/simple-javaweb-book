function myaddboardsubmit(){
	if (isBlank(adminform.boardname,"�������"))
  		return false;
	if(checkLen(adminform.boardinfo)){
		document.all.adminsubmitb.disabled=true;
		adminform.submit();
	}
}
function mymodifyboardsubmit(){
	if (isBlank(adminform.boardname,"�������"))
  		return false;
  	if (isBlank(adminform.boardorder,"������"))
  		return false;
  	if(isNaN(adminform.boardorder.value)){
  		document.all.checkmessage.innerHTML="������İ����Ų������֣�";
  		return false;
  	}
  	else if(adminform.boardorder.value<0){
  		document.all.checkmessage.innerHTML="������İ����ű������0��";
  		return false;
  	}
  	if(checkLen(adminform.boardinfo)){
		document.all.adminsubmitb.disabled=true;
		adminform.submit();
	}
}
function myaddcategorysubmit(){
	if (isBlank(adminform.categoryName,"�������"))
  		return false;
	if(checkLen(adminform.categoryInfo)){
		document.all.adminsubmitb.disabled=true;
		adminform.submit();
	}
}
function mymodifycategorysubmit(){
	if (isBlank(adminform.categoryname,"�������"))
  		return false;
  	if (isBlank(adminform.categoryorder,"������"))
  		return false;
  	if(isNaN(adminform.categoryorder.value)){
  		document.all.checkmessage.innerHTML="������������Ų������֣�";
  		return false;
  	}
  	else if(adminform.categoryorder.value<0){
  		document.all.checkmessage.innerHTML="������������ű������0��";
  		return false;
  	}
  	if(checkLen(adminform.categoryinfo)){
		document.all.adminsubmitb.disabled=true;
		adminform.submit();
	}
}
function myaddmastersubmit(){
	if (isBlank(adminform.newmaster,"��������"))
  		return false;
	myvalidateadminpswdsubmit();
}
function myvalidateadminpswdsubmit(){
	var pswdv=adminform.adminpswd.value;	
	if(pswdv==null||pswdv.length==0){
		document.all.checkmessage.innerHTML="�����������Ա���룡";
		adminform.adminpswd.focus();
		return false;
	}
	else if(pswdv.length<6){
		document.all.checkmessage.innerHTML="�����Ա���볤����СΪ����";
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
    	document.all.checkmessage.innerHTML="��������"+title+"��";
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
        document.all.checkmessage.innerHTML="������������� "+maxlen+" ���ַ���";
        return false;
    }
    else{
        useName.innerText=eval(field.value.length);
        remName.innerText=maxlen-useName.innerText;
        return true;
    }
}