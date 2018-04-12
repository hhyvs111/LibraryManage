<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.dao.ReaderDAO"%>
<%@ page import="com.actionForm.ReaderForm"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="JS/onclock.JS" charset="gbk"></script>
<script src="JS/menu.JS" charset="gbk"></script>
<div class=menuskin id=popmenu
      onmouseover="clearhidemenu();highlightmenu(event,'on')"
      onmouseout="highlightmenu(event,'off');dynamichide(event)" style="Z-index:100;position:absolute;"></div>
<table width="778"  border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
      <tr bgcolor="#1E90FF">
        <td width="3%" height="27">&nbsp;</td>
        <td width="29%"><div id="bgclock" class="word_white"></div></td>
		<script language="javascript">
			function quit(){
				if(confirm("真的要退出系统吗?")){
					window.location.href="logout_reader.jsp";
				}
			}
		</script>
        <td width="66%" align="right" bgcolor="#1E90FF" class="word_white"><a href="main_reader.jsp" class="word_white">首页</a> |
        <a href="book.do?action=bookifQuery_reader" class="word_white">图书查询</a> | <a href="borrow.do?action=bookrenew_reader" class="word_white">图书续借</a> | <a href="borrow.do?action=Bremind_reader" class="word_white">借阅提醒</a> | <a href="#" onClick="quit()" class="word_white">退出系统</a></td>
        <td width="2%" bgcolor="#1E90FF">&nbsp;</td>
  </tr>
      <tr bgcolor="#00BBFF">
        <td height="9" colspan="4" background="Images/navigation_bg_bottom.gif"></td>
      </tr>
</table>
