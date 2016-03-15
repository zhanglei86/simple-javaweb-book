function mysimplesubmit(){
	if(checkInput()){
		if(checkLen()){
			postform.postsubmitb.disabled=true;
			postform.submit();		
		}
	}
}
function myoleditsubmit(){
	var intitle=postform.title.value;
	if(intitle==null||intitle.length==0){
		document.all.checkmessage.innerHTML="●请输入标题！";
		postform.title.focus();
		return false;
	}
	
	//判断fck编辑器中内容是否为空，对IE浏览器有效
	var oEditor=FCKeditorAPI.GetInstance("content");
	var oDom=oEditor.EditorDocument;
	var incontentlen;
	if(document.all){
		incontentlen=oDom.body.innerText.length;
	}
	oEditor.InsertHtml("");	
	if(incontentlen==0){
		document.all.checkmessage.innerHTML="●请输入内容！";
		return false;
	}
	
	var maxlen=document.all.ContentAll.innerText;
	if(incontentlen>maxlen){
       	document.all.checkmessage.innerHTML="●内容最多允许输入"+maxlen+"个字符！";
       	return false;
    }
   	else{
   		document.all.checkmessage.innerHTML="";
		postform.postsubmitb.disabled=true;
		postform.submit();    	
   	}		
}
function checkInput(){
	var intitle=postform.title.value;
	var incontent=postform.content.value;
	if(intitle==null||intitle.length==0){
		document.all.checkmessage.innerHTML="●请输入标题！";
		postform.title.focus();
		return false;
	}
	if(incontent==null||incontent.length==0){
		document.all.checkmessage.innerHTML="●请输入内容！";
		return false;
	}
	return true;
}
function checkLen(){
	var fieldName=postform.content;
	var maxlen=document.all.ContentAll.innerText;
	var useName=document.all.ContentUse;
	var remName=document.all.ContentRem;
	var inlen=fieldName.value.length;
    
    if(inlen>maxlen){
        fieldName.value=(fieldName.value).substring(0,maxlen);
        document.all.checkmessage.innerHTML="●内容最多允许输入"+maxlen+"个字符！";
        return false;
    }
    else{
        useName.innerText=eval(fieldName.value.length);
        remName.innerText=maxlen-useName.innerText;
        return true;
    }
}
function myReset(oldTxt){
	var oEditor=FCKeditorAPI.GetInstance("content");
	var oDom=oEditor.EditorDocument;
	oDom.body.innerHTML=oldTxt;
}