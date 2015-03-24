<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>Pagination Links - jQuery EasyUI Demo</title>
<link href="../css/css_style.css" rel="stylesheet" type="text/css" />
<link href="../css/table_style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="../plugin/jquery-easyui/themes/default/easyui.css">
<script src="../plugin/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="../plugin/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../plugin/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/JavaScript">
<!--
function MM_jumpMenu(targ,selObj,restore){ //v3.0
  eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'");
  if (restore) selObj.selectedIndex=0;
}
function showtest(obj)
{
	if(obj.value=="1")
	{
		document.getElementById("no1").style.display="";
		document.getElementById("no2").style.display="";
		document.getElementById("no3").style.display="";
		document.getElementById("no4").style.display="";
		document.getElementById("no5").style.display="";
	　　document.getElementById("no8").style.display="none";
		document.getElementById("legendUser").innerHTML="用户信息";
	}
	if(obj.value=="2")
	{
		document.getElementById("no1").style.display="";
		document.getElementById("no2").style.display="";
		document.getElementById("no3").style.display="none";
		document.getElementById("no4").style.display="none";
	    document.getElementById("no6").style.display="";
		document.getElementById("no8").style.display="none";
		document.getElementById("legendUser").innerHTML="用户信息";
	}
	if(obj.value=="3")
	{
		document.getElementById("no1").style.display="";
		document.getElementById("no2").style.display="none";
		document.getElementById("no3").style.display="none";
		document.getElementById("no4").style.display="none";
		document.getElementById("no5").style.display="";
		document.getElementById("no8").style.display="";
		document.getElementById("legendUser").innerHTML="设备信息";
	}
	if(obj.value=="4")
	{
		document.getElementById("no1").style.display="";
		document.getElementById("no2").style.display="none";
		document.getElementById("no3").style.display="none";
		document.getElementById("no4").style.display="none";
		document.getElementById("no5").style.display="";
		document.getElementById("no8").style.display="none";
		document.getElementById("legendUser").innerHTML="用户信息";
	}
	
}

//-->
</script>
<script src="../js/form.js" language="javascript"></script>

<SCRIPT LANGUAGE = JavaScript>
<!--
//** Power by xweb(2008-2-22)

var s=["s1","s2","s3","s4","s5","s6"];
var opt0 = ["请选择","请选择","请选择","请选择","请选择","请选择"];
function setup()
{
	for(i=0;i<s.length-1;i++)
	  document.getElementById(s[i]).onchange=new Function("change("+(i+1)+")");
	change(0);
}
//-->
</SCRIPT>
<script language="javascript" type="text/javascript">
	//全选功能
	function selectAll()
	{
		var xueke=document.frm.checkKeMu;
		var selAll="";
		for(var i=0;i<xueke.length;i++)
		{
			xueke[i].checked=true;
		}
		
	}
	//反选功能
	function noSelect()
	{
		var xueke=document.frm.checkKeMu;
		for(var i=0;i<xueke.length;i++)
		{
			if(xueke[i].checked==true)
			{
				xueke[i].checked=false;
			}
		}
	}
	//反选的功能
	function fSelect()
	{
		var xueke=document.frm.checkKeMu;
		for(var i=0;i<xueke.length;i++)
		{
			if(xueke[i].checked==true)
			{
				xueke[i].checked=false;
			}
			else
			{
				xueke[i].checked=true;
			}
		}
	}
</script>

<script>
function loadThreadFollow_02(t_id){
	$(window).resize();
	
	var targetImg =eval("document.all.followImg" + t_id);
	var targetDiv =eval("document.all.follow" + t_id);
	
	if ("object"==typeof(targetImg)){
		if (targetDiv.style.display!=''){
			targetDiv.style.display="";
			targetImg.src="../image/logo_2_02.gif";
			if (targetImg.loaded=="no"){
			}
		}else{
			targetDiv.style.display="none";
			targetImg.src="../image/logo_2_01.gif";
		}
	}
}

/**
 * 身份证号码验证，支持新的带x身份证
 */
function isIdCardNo(obj){
	function checkDate(d){
	  var year;
	  var month;
	  var day;
	  if(d.length==6){
		year = d.substring(0,2);
		month = d.substring(2,4);
		day = d.substring(4,6);
	  }else if(d.length==8){
		year = d.substring(0,4);
		month = d.substring(4,6);
		day = d.substring(6,8);
	  }else{
		return false;
	  }
	  if(month<0 || month>12 || day<0 || day>31){
		return false;
	  }
	  var date = new Date(year,month,day);
	  return true;
	}
    var num = obj.value;
    var factorArr = new Array(7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1);
    var error;
    var varArray = new Array();
    var intValue;
    var lngProduct = 0;
    var intCheckDigit;
    var intStrLen = num.length;
    var idNumber = num;
    // initialize
    if ((intStrLen != 15) && (intStrLen != 18)) {
        //error = "输入身份证号码长度不对";//输入身份证号码长度不对
		//error = "错误的身份证号码";
        //alert(error);
        obj.focus();
        return false;
    }
    // check and set value
    for(i=0;i<intStrLen;i++) {
        varArray[i] = idNumber.charAt(i);
        if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
            //error = "错误的身份证号码";//
			//error = "错误的身份证号码";
            //alert(error);
            obj.focus();
            return false;
        }else if (i < 17){
            varArray[i] = varArray[i]*factorArr[i];
        }
    }
    if (intStrLen == 18) {
        //check date
        var date8 = idNumber.substring(6,14);
        if (checkDate(date8) == false) {
            //error = "身份证中日期信息不正确.";//
			//error = "错误的身份证号码";
            //alert(error);
			obj.focus();
            return false;
        }
        // calculate the sum of the products
        for(i=0;i<17;i++) {
            lngProduct = lngProduct + varArray[i];
        }
        // calculate the check digit
        intCheckDigit = 12 - lngProduct % 11;
        switch (intCheckDigit) {
            case 10:
                intCheckDigit = 'X';
                break;
            case 11:
                intCheckDigit = 0;
                break;
            case 12:
                intCheckDigit = 1;
                break;
        }
        // check last digit
        if (varArray[17].toUpperCase() != intCheckDigit) {
            //error = "身份证效验位错误!正确为： " + intCheckDigit + ".";//
			//error = "错误的身份证号码";
            //alert(error);
			obj.focus();
            return false;
        }
    }else{        //length is 15
        //check date
        var date6 = idNumber.substring(6,12);
        if (checkDate(date6) == false) {
            //alert("身份证日期信息有误！.");//
			//error = "错误的身份证号码";
			//alert(error)
			obj.focus();
            return false;
        }
    }
    return true;
}
</script>
</head>
<body style="margin:5px;">
<form id="frm" name="frm">
<!-------------------------------------- 高级查询 开始 -------------------------------->
<table width='100%' border="0" cellspacing="0" cellpadding="0" class="inquiry" style="margin:0px 0px 5px 0px;">
<tr>
<td align="left" style="padding:0px 0px 2px 0px;">
  <a>&nbsp;<span class="blue">姓名：</span></a>
  <label>
  <input name="textfield" type="text" class="text_box" size="13"/>
  </label>
  <a class="blue"><span class="blue">&nbsp;&nbsp;&nbsp;&nbsp; 身份证号：</span></a>
  <input name="textfield2" type="text" class="text_box" size="13" onchange="isIdCardNo(this)"/>
  <a class="blue"><span class="blue">&nbsp;&nbsp;&nbsp;&nbsp; 证书模版：</span></a>
  <select name="menu1" style="margin-top:1px;" onchange="showtest(this,1)">
	<option value="1">协警外聘非受限人员</option>
	<option value="2">协警外聘受限人员</option>
	<option value="3">专用证书</option>
	<option value="4">外部委人员</option>
  </select>
</td>
<td width=80 align="center">
<img src="../image/logo_2_01.gif" name="followImg38104" width="64" height="23" id=followImg38104 style="CURSOR: hand" onclick=loadThreadFollow_02(38104) ></td>
<td width=130 align="right"  style="padding-right:5px;">
<label>
	<input name="submit" type="submit" class="inp_L1" onclick="alert('对不起，这是演示页面，查询动作取消。')"onmouseover="this.className='inp_L2'" onmouseout="this.className='inp_L1'" value="查 询" />
	<input name="submit2" type="submit" class="inp_L1" onclick="alert('对不起，这是演示页面，重置动作取消。')"onmouseover="this.className='inp_L2'" onmouseout="this.className='inp_L1'" value="重 置" />
</td>
</tr>
<tr id="follow38104">
<td height="23" colspan="4" align="left" bgcolor=#ffffff style="border-top:1px solid #eeeeee">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr><td align="center" valign="middle" style="padding:6px;">
  <fieldset style="color: #B7B7B7;">
    <legend style="font-size:10pt;"> 单位全称 </legend> 

  <table width="98%" border="0" cellpadding="4" cellspacing="1" bgcolor=#ffffff>
  <tbody cellspacing="0" id="no1" name="no1">
  
  <tr bgcolor=#ffffff>
  <td class="color0366CB" width=100 align="right" background="../page/image/table_2.gif" height="23">省：</td>
  <td height="23" align="left" width=137>
	  <select name="select" id="s1">
		<option>请选择</option>
	   </select>
   </td>
  <td class="color0366CB" width=100 align="right">市：</td>
  <td align="left" width=137>
	  <select name="select2" id="s2">
		<option>请选择</option>
	  </select>
  </td>
  <td class="color0366CB" width=100 align="right">县：</td>
  <td align="left">
	  <select name="select3" id="s3">
		<option>请选择</option>
	  </select>
  </td>
  </tr>
  </tbody>
  
  <tbody cellspacing="0"  id="no2" name="no2">
  <tr bgcolor=#ffffff>
  <td class="color0366CB" width=100 align="right" height="23">直属单位：</td>
  <td height="23" align="left" colspan=5>
  <select name="select4" id="s4" style="width:100%">
    <option>请选择</option>
  </select>
  </td>
  </tr>
  <tr bgcolor=#ffffff>
  <td class="color0366CB" width=100 height="23" align="right">直属一级单位：</td>
  <td height="23" align="left" colspan=5>
  <select name="select5" id="s5" style="width:100%">
    <option>请选择</option>
  </select></td>
  </tr>
  <tr bgcolor=#ffffff>
  <td class="color0366CB" width=100 height="23" align="right">直属二级单位：</td>
  <td height="23" align="left" colspan=5>
  <select name="select6" id="s6" style="width:100%">
    <option>请选择</option>
  </select></td>
  </tr>
  </tbody>
  
  </table>

</fieldset>
  </td></tr>
  <tr><td align="center" valign="middle" style="padding:6px;">
  <fieldset style="color: #B7B7B7; border-style: groove;">
    <legend style="font-size:10pt;" id='legendUser'> 用户信息 </legend> 
  <table width="98%" border="0" cellpadding="4" cellspacing="1" bgcolor=#ffffff>
  <tbody cellspacing="0" id="no8" name="no8" style="display:none">
  <tr bgcolor=#ffffff>
	  <td class="color0366CB" width=100 align="right" height="23">设备名称：</td>
	  <td align="left" width=200>
		<input type=text style="width:190px" />
	  </td>
	  <td width=100>&nbsp;</td>
	  <td>&nbsp;</td>
  </tr>
  </tbody>
  
  <tbody cellspacing="0" id="no3" name="no3">
   <tr bgcolor=#ffffff>
  <td class="color0366CB" width=100 align="right" height="23">警种：</td>
  <td height="23" align="left" width=200>
  <select name="select7" style="width:190px">
	<option>请选择</option>
	<option value="01">国内安全保卫</option>
  </select>
  </td>
  <td class="color0366CB" width=100 height="23" align="right">任职：</td>
  <td height="23" align="left">
  <select name="select8" style="width:190px">
    <option>请选择</option>
    <option value="001">正职</option>
    <option value="002">副职</option>
    <option value="003">其他</option>
  </select></td>
  </tr>
</tbody>

  <tbody cellspacing="0" id="no4" name="no4">
   <tr bgcolor=#ffffff>
  <td class="color0366CB" width=100 align="right" height="23">岗位：</td>
  <td height="23" align="left" width=200>
    <select name="select7" style="width:190px">
      <option>请选择</option>
		<option value="0100">单位领导</option>
    </select>
  </td>
  <td class="color0366CB" width=100 height="23" align="right">职级：</td>
  <td height="23" align="left">
  <select name="select8" style="width:180px">
    <option>请选择</option>
	<option value="0130">正部、省职</option>
	<option value="2322">测试</option>
  </select></td>
  </tr>
  </tbody>

  <tbody cellspacing="0" id="no5" name="no5">
   <tr bgcolor=#ffffff>
  <td class="color0366CB" width=100 align="right" height="23">部委：</td>
  <td height="23" align="left" width=200>
  <select name="select7" style="width:190px">
    <option>请选择</option>
    <option>公安部</option>
    <option>水利部</option>
    <option>交通部</option>
  </select>
  </td>
  <td class="color0366CB" width=100 height="23" align="right">类别：</td>
  <td height="23" align="left">
  <select name="select8" style="width:190px">
    <option>请选择</option>
    <option>个人证书</option>
  </select>
  </td>
  </tr>
  </tbody>
</TABLE>
</fieldset>
  </td>
  </tr>
</table>
  </td>
  </tr>
</table>

</form>
  <table id="easyui-datagrid" class="easyui-datagrid" style="width:100%"
			data-options="rownumbers:true,singleSelect:true,url:'datagrid_data1.json',pagination:true,method:'get',fitColumns:'true'">
		<thead>
			<tr>
				<th data-options="field:'itemid',width:80">Item ID</th>
				<th data-options="field:'productid',width:100">Product</th>
				<th data-options="field:'listprice',width:80,align:'right'">List Price</th>
				<th data-options="field:'unitcost',width:80,align:'right'">Unit Cost</th>
				<th data-options="field:'attr1',width:240">Attribute</th>
				<th data-options="field:'status',width:60,align:'center'">Status</th>
			</tr>
		</thead>
	</table>

<script type="text/javascript">
window.onload = function() {
	$('#follow38104').hide();
	
	//设置分页控件 
	var p = $('#easyui-datagrid').datagrid('getPager'); 
	$(p).pagination({ 
	    pageSize: 10,//每页显示的记录条数，默认为10 
	    pageList: [10,30,50],//可以设置每页记录条数的列表 
	    beforePageText: '第',//页数文本框前显示的汉字 
	    afterPageText: '页    共 {pages} 页', 
	    displayMsg: '1当前显示 {from} - {to} 条记录   共 {total} 条记录', 
	    onBeforeRefresh:function(){
	        $(this).pagination('loading');
	        $(this).pagination('loaded');
	    } 
	});  
	
	$(window).resize();
}

</script>
</body>
</html>