<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ page import="com.dao.BorrowDAO"%>

<%@ page import="com.actionForm.BorrowForm"%>
<%@ page import="java.util.*"%>
<%

%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<head>
<title>图书馆管理系统</title>
<link href="CSS/style.css" rel="stylesheet">
</head>
<body onLoad="clockon(bgclock)">
<%@include file="banner_reader.jsp"%>
<%@include file="navigation_reader.jsp"%>
<%
BorrowDAO borrowDAO=new BorrowDAO();
Collection coll_book=(Collection)borrowDAO.bookBorrowSort(null);

String xuehao = (String)session.getAttribute("barcode");
String black = (String)("black");
Collection remind = (Collection)borrowDAO.bremind(xuehao);
Collection black1 = (Collection)borrowDAO.bremind(black);
%>
<table width="778" height="510"  border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" class="tableBorder_gray">
  <tr>
    <td align="center" valign="top" style="padding:5px;"><table width="100%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="20" align="right" valign="middle" class="word_orange">当前位置：首页 &gt;&gt;&gt;&nbsp;</td>
      </tr>
      <tr>
        <td valign="top"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="57" background="Images/main_booksort.gif">&nbsp;</td>
          </tr>
          <tr>
            <td height="72" valign="top"><table width="100%" height="63"  border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="2%" rowspan="2">&nbsp;</td>
                <td width="96%" align="center" valign="top"><table width="100%"  border="1" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF" bordercolordark="#B7B6B6" bordercolorlight="#FFFFFF">
                  <tr align="center">
                    <td width="5%" height="25">排名</td>
					<td width="10%">图书编号</td>
					<td width="24%">图书名称</td>
					<td width="10%">图书类型</td>
					<td width="10%">书架</td>
					<td width="14%">出版社</td>
					<td width="11%">作者</td>
					<td>定价(元)</td>
				    <td>借阅次数</td>
                  </tr>
					<%if(coll_book!=null && !coll_book.isEmpty()){
						  Iterator it_book=coll_book.iterator();
						  int i=1;
					  int degree=0;
					  String bookname="";
					  String typename="";
					  String barcode_book="";
					  String bookcase="";
					  String pub="";
					  String author="";
					  String translator="";
					  Float price=new Float(0);
						  while(it_book.hasNext() && i<6){
						  BorrowForm borrowForm=(BorrowForm)it_book.next();
						bookname=borrowForm.getBookName();
							barcode_book=borrowForm.getBookBarcode();
							typename=borrowForm.getBookType();
						degree=borrowForm.getDegree();
						bookcase=borrowForm.getBookcaseName();
							pub=borrowForm.getPubName();
							author=borrowForm.getAuthor();
							price=borrowForm.getPrice();
					
						%> 
                  <tr>
                    <td height="25" align="center"><%=i%></td>
					<td style="padding:5px;">&nbsp;<%=barcode_book%></td>
					<td style="padding:5px;"><%=bookname%></td>
					<td style="padding:5px;"><%=typename%></td>
					<td align="center">&nbsp;<%=bookcase%></td>
					<td align="center">&nbsp;<%=pub%></td>
					<td width="11%" align="center"><%=author%></td>
					<td width="8%" align="center"><%=price%></td>
				    <td width="8%" align="center"><%=degree%></td>
                  </tr>
						<%
						i++;
						}
					}%>
                </table>
                  </td>
                  
                <td width="2%" rowspan="2">&nbsp;</td>
              </tr>
              <tr>
                <td height="30" align="right" valign="middle"><a href=borrow.do?action=bookBorrowSort_reader>查看更多图书类型的排行</a></td>
               </tr>  
             
            </table>
            </td>
             <tr>
             
               <%if(remind!=null && !remind.isEmpty()){
			%> 
                <td height="30" align="center" valign="middle">  <a href=borrow.do?action=Bremind_reader>您有书籍快要到期了！</a> </td>
				<% }%>
				</tr>
				<tr>
            <td height="57" background="Images/cuihuan.jpg">&nbsp;</td>
             </tr>
			<%if(black1!=null && !black1.isEmpty()){%> 
		     
                <td width="96%" align="center" valign="top"><table width="100%"  border="1" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF" bordercolordark="#B7B6B6" bordercolorlight="#FFFFFF">
                  <tr align="center">
			    <td width="15%" >图书编号</td>
			    <td width="28%" >图书名称</td>
			    <td width="17%" >读者编号</td>
			    <td width="9%" >读者名称</td>
			    <td width="15%" >借阅时间</td>
			    <td width="16%" >应还时间</td>
                  </tr>
					<%if(black1!=null && !black1.isEmpty()){
						  Iterator it=black1.iterator();
						  String bookname="";
						  String bookbarcode="";
						  String readerbar="";
						  String readername="";
						  String borrowTime="";
						  String backTime="";
						  while(it.hasNext()){
							    BorrowForm borrowForm=(BorrowForm)it.next();
								bookname=borrowForm.getBookName();
								bookbarcode=borrowForm.getBookBarcode();
								readerbar=borrowForm.getReaderBarcode();
								readername=borrowForm.getReaderName();
								borrowTime=borrowForm.getBorrowTime();
								backTime=borrowForm.getBackTime();
						  }
						  
					
						%> 
<tr align="center">
    <td style="padding:5px;">&nbsp;<%=bookbarcode%></td>
    <td style="padding:5px;"><%=bookname%></td>
    <td style="padding:5px;">&nbsp;<%=readerbar%></td>
    <td style="padding:5px;">&nbsp;<%=readername%></td>
    <td style="padding:5px;">&nbsp;<%=borrowTime%></td>
    <td style="padding:5px;">&nbsp;<%=backTime%></td>
    </tr>
					
				
                </table>
                  </td>
                  <% }}
                  else{%>
                  <tr><td align="center">暂无！</td></tr>
                  <%} %>
          </tr>
        </table>
          </td>
      </tr>
    </table>
    
    </td>
  </tr>
</table>

<%@ include file="copyright.jsp"%>
</body>
</html>
