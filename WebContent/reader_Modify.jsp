<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.dao.ReaderDAO" %>
<%@ page import="com.dao.ReaderTypeDAO" %>
<%@ page import="com.actionForm.ReaderForm" %>
<%@ page import="com.actionForm.ReaderTypeForm"%>
<%@ page import="java.util.*"%>
<html>
<%
String str=null;
ReaderTypeDAO readerTypeDAO=new ReaderTypeDAO();
Collection coll=(Collection)readerTypeDAO.query(str);
Iterator it=coll.iterator();
ReaderForm readerForm=(ReaderForm)request.getAttribute("readerQueryif");
%>
<script language="jscript">
function check(form){
	if(form.name.value==""){
		alert("请输入读者姓名!");form.name.focus();return false;
	}
	if(form.paperNO.value==""){
		alert("请输入证件号码!");form.paperNO.focus();return false;
	}
	 var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;      
	    if(reg.test(form.paperNO.value) === false)      
	    {    
	        alert("身份证输入不合法！");  
	        form.paperNO.focus();
	        return  false;    
	    } 
// 	 var reg1 = /^1[3|4|5|7|8][0-9]{9}$/; //验证规则
// 	 if(reg1.test(form.tel.value) == false){
// 		 alert("手机号码输入不合法！");  
// 	        form.tel.focus();
// 	        return  false;  
// 	 }
// 	 var reg2 = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
// 	 if(reg2.test(form.email.value) == false){
// 		 alert("邮箱输入不合法！");  
// 	        form.email.focus();
// 	        return  false;  
// 	 }
}
</script>
<head>
<title>图书馆管理系统</title>
<link href="CSS/style.css" rel="stylesheet">
</head>
<body onLoad="clockon(bgclock)">
<%@include file="banner.jsp"%>
<%@include file="navigation.jsp"%>
<table width="778"  border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td valign="top" bgcolor="#FFFFFF"><table width="99%" height="510"  border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" class="tableBorder_gray">
  <tr>
    <td height="510" valign="top" style="padding:5px;"><table width="98%" height="487"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="22" valign="top" class="word_orange">当前位置：读者管理 &gt; 读者档案管理 &gt; 修改读者信息 &gt;&gt;&gt;</td>
      </tr>
      <tr>
        <td align="center" valign="top"><table width="100%" height="493"  border="0" cellpadding="0" cellspacing="0">
  <tr>
<%  int ID=readerForm.getId().intValue();
  String name=readerForm.getName();
  String sex=readerForm.getSex();  
  String barcode=readerForm.getBarcode();
  String birthday=readerForm.getBirthday();
  String paperType=readerForm.getPaperType();
  String paperNO=readerForm.getPaperNO();
  String tel=readerForm.getTel();
  String email=readerForm.getEmail();
  String createDate=readerForm.getCreateDate();
  String remark=readerForm.getRemark();
  String operator=readerForm.getOperator();
  int typeid=readerForm.getTypeid();
  String typename=readerForm.getTypename();
  String typeName="";
  int typeID=0;
  %> 
    <td align="center" valign="top">
	<form name="form1" method="post" action="reader.do?action=readerModify">
	<table width="600" height="432"  border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
      <tr>
        <td width="173" align="center">姓名：</td>
        <td width="427" height="39">
          <input name="name" type="text" value="<%=name%>"> 
          *         
          <input name="id" type="hidden" id="id" value="<%=ID%>"></td>
      </tr>
      <tr>
        <td width="173" align="center">性别：</td>
        <td height="35"><input name="sex" type="radio" class="noborder" id="radiobutton" value="男" <%if("男".equals(sex)) out.println("checked");%>>
          <label for="radiobutton">男 </label>
          <label>
          <input name="sex" type="radio" class="noborder" value="女" <%if("女".equals(sex)) out.println("checked");%>>
          女</label></td>
      </tr>
      <tr>
        <td align="center">读者编号：</td>
        <td><input name="barcode" type="text" id="barcode" value="<%=barcode%>" readonly="yes"></td>
      </tr>
      <tr>
        <td align="center">读者类型：</td>
        <td>
		<select name="typeid" class="wenbenkuang" id="typeid">
<%
  while(it.hasNext()){
    ReaderTypeForm readerTypeForm=(ReaderTypeForm)it.next();
	typeID=readerTypeForm.getId().intValue();
	typeName=readerTypeForm.getName();
	%> 		
				
          <option value="<%=typeID%>" <%if(typeid==typeID) out.println("selected");%>><%=typeName%></option>
<%}%> 
        </select>        </td>
      </tr>
      <tr>
        <td align="center">出生日期：</td>
        <td><input name="birthday" type="text" id="birthday" value="<%=birthday%>"> 例：1949-01-01</td>
      </tr>
      <tr>
        <td align="center">有效证件：</td>
        <td><select name="paperType" class="wenbenkuang" id="paperType">
          <option value="身份证"<%if("身份证".equals(paperType)) out.println(" selected");%>>身份证</option>
                        </select></td>
      </tr>
      <tr>
        <td align="center">身份证号码：</td>
        <td><input name="paperNO" type="text" id="paperNO" value="<%=paperNO%>"> 
          * </td>
      </tr>
      <tr>
        <td align="center">手机号码：</td>
        <td><input name="tel" type="text" id="tel" value="<%=tel%>"></td>
      </tr>
      <tr>
        <td align="center">Email：</td>
        <td><input name="email" type="text" id="email" value="<%=email%>" size="50">
          <input name="operator" type="hidden" id="operator" value="<%=operator%>"></td>
      </tr>
      <tr>
        <td height="30" align="center">操作员：</td>
        <td><input name="operator" type="text" id="operator" value="<%=operator%>" readonly="yes">
        </td>
      </tr>
      <tr>
        <td align="center">备注：</td>
        <td><textarea name="remark" cols="50" rows="5" class="wenbenkuang" id="remark"><%=remark%></textarea></td>
      </tr>
      <tr>
        <td align="center">&nbsp;</td>
        <td><input name="Submit" type="submit" class="btn_grey" value="保存" onClick="return check(form1)">
&nbsp;
<input name="Submit2" type="button" class="btn_grey" value="返回" onClick="history.back()"></td>
      </tr>
    </table>
	</form>
	</td>
  </tr>
</table></td>
      </tr>
    </table>
</td>
  </tr>
</table><%@ include file="copyright.jsp"%></td>
  </tr>
</table>
</body>
</html>
