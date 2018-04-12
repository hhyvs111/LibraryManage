package com.action;

import org.apache.struts.action.*;

import java.util.Collection;

import javax.servlet.http.*;
import com.dao.*;
import com.actionForm.*;

public class Borrow extends Action {
	/******************在构造方法中实例化Borrow类中应用的持久层类的对象**************************/
	   private BorrowDAO borrowDAO = null;
	   private ReaderDAO readerDAO=null;
	   private BookDAO bookDAO=null;
	   private ReaderForm readerForm=new ReaderForm();
	   public Borrow() {
	       this.borrowDAO = new BorrowDAO();
	       this.readerDAO=new ReaderDAO();
	       this.bookDAO=new BookDAO();
	   }
	/******************************************************************************************/
	    public ActionForward execute(ActionMapping mapping, ActionForm form,
	                                 HttpServletRequest request, HttpServletResponse response) {
	        BorrowForm borrowForm = (BorrowForm) form;
	        String action =request.getParameter("action");
	        if(action==null||"".equals(action)){
	            request.setAttribute("error","您的操作有误！");
	            return mapping.findForward("error");
	        }else if("bookBorrowSort".equals(action)){
	            return bookBorrowSort(mapping,form,request,response); 
	        }else if("bookBorrowSort_reader".equals(action)){
	            return bookBorrowSort_reader(mapping,form,request,response); 
	        }else if("bookborrow".equals(action)){
	            return bookborrow(mapping,form,request,response);  //图书借阅
	        }else if("bookrenew".equals(action)){
	            return bookrenew(mapping,form,request,response);  //图书续借
	        }else if("bookrenew_reader".equals(action)){
		        return bookrenew_reader(mapping,form,request,response);  //图书续借
	        }else if("bookback".equals(action)){
	            return bookback(mapping,form,request,response);  //图书归还
	        }else if("Bremind".equals(action)){
	            return bremind(mapping,form,request,response);  //借阅到期提醒
	        }else if("Bremind_reader".equals(action)){
	            return bremind_reader(mapping,form,request,response);  //借阅到期提醒
	        }else if("borrowQuery".equals(action)){
	            return borrowQuery(mapping,form,request,response);  //借阅信息查询
	        }
	        request.setAttribute("error","操作失败！");
	        return mapping.findForward("error");
	    }
    /*********************图书借阅排行***********************/
    private ActionForward bookBorrowSort(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
    	System.out.println(request.getParameter("typeId"));
    	
    	String typeid = request.getParameter("typeId");
    	if(typeid != null){
    		request.setAttribute("type",typeid);
    	}
    	else{
    		request.setAttribute("type", null);
    	}
        request.setAttribute("bookBorrowSort",borrowDAO.bookBorrowSort(typeid));
        return mapping.findForward("bookBorrowSort");

    }
    
    /*********************图书借阅排行***********************/
    private ActionForward bookBorrowSort_reader(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
    	System.out.println(request.getParameter("typeId"));
    	String typeid = request.getParameter("typeId");
    	if(typeid != null){
    		request.setAttribute("type",typeid);
    	}
    	else{
    		request.setAttribute("type", null);
    	}
        request.setAttribute("bookBorrowSort",borrowDAO.bookBorrowSort(typeid));
        return mapping.findForward("bookBorrowSort_reader");
    }
 /*********************图书借阅查询***********************/
    private ActionForward borrowQuery(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
        String str=null;
        String flag[]=request.getParameterValues("flag");
        if (flag!=null){
            String aa = flag[0];
            if ("a".equals(aa)) {
                if (request.getParameter("f") != null) {
                    str = request.getParameter("f") + " like '%" +
                          request.getParameter("key") + "%'";
                }
            }
            if ("b".equals(aa)) {
                String sdate = request.getParameter("sdate");
                String edate = request.getParameter("edate");
                if (sdate != null && edate != null) {
                    str = "borrowTime between '" + sdate + "' and '" + edate +
                          "'";
                }
                System.out.println("日期" + str);
            }
            //同时选择日期和条件进行查询
            if (flag.length == 2) {
                if (request.getParameter("f") != null) {
                    str = request.getParameter("f") + " like '%" +
                          request.getParameter("key") + "%'";
                }
                System.out.println("日期和条件");
                String sdate = request.getParameter("sdate");
                String edate = request.getParameter("edate");
                String str1 = null;
                if (sdate != null && edate != null) {
                    str1 = "borrowTime between '" + sdate + "' and '" + edate +
                           "'";
                }
                str = str + " and borr." + str1;
                System.out.println("条件和日期：" + str);
            }
        }
        request.setAttribute("borrowQuery",borrowDAO.borrowQuery(str));
       System.out.print("条件查询图书借阅信息时的str:"+str);
        return mapping.findForward("borrowQuery");
    }
    /*********************到期提醒***********************/
    private ActionForward bremind(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
        request.setAttribute("Bremind",borrowDAO.bremind("wu"));
        return mapping.findForward("Bremind");
    }
    
    /*********************到期提醒_reader***********************/
    private ActionForward bremind_reader(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
    	HttpSession session = request.getSession();
    	String xuehao = (String)session.getAttribute("barcode");
    	System.out.println(xuehao);
        request.setAttribute("Bremind_reader",borrowDAO.bremind(xuehao));
        return mapping.findForward("Bremind_reader");
    }

    /*********************图书借阅***********************/
    private ActionForward bookborrow(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
        //查询读者信息
        //ReaderForm readerForm=(ReaderForm)form;  //此处一定不能使用该语句进行转换
        readerForm.setBarcode(request.getParameter("barcode"));
        ReaderForm reader = (ReaderForm) readerDAO.queryM(readerForm);
        request.setAttribute("readerinfo", reader);
        //查询读者的借阅信息
        request.setAttribute("borrowinfo",borrowDAO.borrowinfo(request.getParameter("barcode")));
        //完成借阅
        String f = request.getParameter("f");
        String key = request.getParameter("inputkey");
        if (key != null && !key.equals("")) {
            String operator = request.getParameter("operator");
            BookForm bookForm=bookDAO.queryB(f, key);
            if (bookForm!=null ){
            	if(bookForm.getAvailable() > 0){
                int ret = borrowDAO.insertBorrow(reader, bookDAO.queryB(f, key),
                                                 operator);
                if (ret == 1) {
                    //将可借数减一
                    int num = bookForm.getAvailable() - 1;
                    bookForm.setAvailable(num);
                    int result = bookDAO.update(bookForm);
                    if(result == 1){
                    request.setAttribute("bar", request.getParameter("barcode"));
                    return mapping.findForward("bookborrowok");
                    }
                 else {
                    request.setAttribute("error", "添加借阅信息失败!");
                    return mapping.findForward("error");
                 }
               }
            }
            	else{
            		request.setAttribute("error", "该图书库存不足!");
                    return mapping.findForward("error");
            	}
            }else{
                 request.setAttribute("error", "没有该图书!");
                return mapping.findForward("error");
            }
        }
        	
        
        return mapping.findForward("bookborrow");
    }
    /*********************图书继借***********************/
    private ActionForward bookrenew(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
        //查询读者信息
        readerForm.setBarcode(request.getParameter("barcode"));
        ReaderForm reader = (ReaderForm) readerDAO.queryM(readerForm);
        request.setAttribute("readerinfo", reader);
        //查询读者的借阅信息
        request.setAttribute("borrowinfo",borrowDAO.borrowinfo(request.getParameter("barcode")));
         if(request.getParameter("id")!=null){
             int id = Integer.parseInt(request.getParameter("id"));
             if (id > 0) { //执行继借操作
            	 System.out.println(id);
                 int ret = borrowDAO.renew(id);
                 if (ret == 0) {
     				System.out.println("续借失败");
     			request.setAttribute("error", "图书续借失败!");
     			return mapping.findForward("error");
     			}
     			else if(ret == 2){
     				request.setAttribute("error", "图书续借次数超过上限!");
     				return mapping.findForward("error");
     			}
     			else if(ret == 3){
     				request.setAttribute("error", "暂时不需要续借！");
     				return mapping.findForward("error");
     			}
     			else if(ret == 4){
     				request.setAttribute("error", "图书已到期！请归还！");
     				return mapping.findForward("error");
     			}
     			else {
     			request.setAttribute("bar", request.getParameter("barcode"));
     			System.out.println("续借成功");
     			return mapping.findForward("bookrenewok");
     			}
             }
         }
        return mapping.findForward("bookrenew");
    }
    /*********************图书继借_reader***********************/
    private ActionForward bookrenew_reader(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response){
//查询读者信息
    		HttpSession session = request.getSession();
			readerForm.setBarcode((String)session.getAttribute("barcode"));
			ReaderForm reader = (ReaderForm) readerDAO.queryM(readerForm);
			request.setAttribute("readerinfo", reader);
			//查询读者的借阅信息
			request.setAttribute("borrowinfo",borrowDAO.borrowinfo((String)session.getAttribute("barcode")));
			if(request.getParameter("id")!=null){
			int id = Integer.parseInt(request.getParameter("id"));
			System.out.println(id);
			if (id > 0) { //执行继借操作
			int ret = borrowDAO.renew(id);
			if (ret == 0) {
				System.out.println("续借失败");
			request.setAttribute("error", "图书续借失败!");
			return mapping.findForward("error");
			}
			else if(ret == 2){
				request.setAttribute("error", "图书续借次数超过上限!");
				return mapping.findForward("error");
			}
			else if(ret == 3){
				request.setAttribute("error", "暂时不需要续借！");
				return mapping.findForward("error");
			}
			else {
			request.setAttribute("bar", request.getParameter("barcode"));
			System.out.println("续借成功");
			return mapping.findForward("bookrenewok_reader");
			}
			}	
			}
				return mapping.findForward("bookrenew_reader");
}
    /*********************图书归还***********************/
    private ActionForward bookback(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
        //查询读者信息
        readerForm.setBarcode(request.getParameter("barcode"));
        ReaderForm reader = (ReaderForm) readerDAO.queryM(readerForm);
        request.setAttribute("readerinfo", reader);
        //查询读者的借阅信息
        request.setAttribute("borrowinfo",borrowDAO.borrowinfo(request.getParameter("barcode")));
         if(request.getParameter("id")!=null){
             int id = Integer.parseInt(request.getParameter("id"));
             String operator=request.getParameter("operator");
             String bookBarcode= request.getParameter("bookBarcode");
             System.out.println(bookBarcode);
             if (id > 0) { //执行归还操作
            	 System.out.println(id);
            	 BookForm bookForm =(BookForm)bookDAO.queryBarcode(bookBarcode);
            	 System.out.println(bookForm.getAvailable());
            	 if(bookForm != null){
            		 //库存加1
            		 int num = bookForm.getAvailable() + 1;
            		 //若可借数加1后小于等于库存数
            		 if(num <= bookForm.getStock()){
                         int ret = borrowDAO.back(id,operator);
        	                 if (ret == 1) {
        	                	 bookForm.setAvailable(num);
        	            		 int result = bookDAO.update(bookForm);
        	                     if(result == 1){
        	                    	 request.setAttribute("bar", request.getParameter("barcode"));
        	                         return mapping.findForward("bookbackok");
        	                     }
        	                     else{
        	                    	 request.setAttribute("error", "图书归还失败!");
        	                         return mapping.findForward("error");
        	                     }
        	                 }
        	                 else{
        	                	 request.setAttribute("error", "图书归还失败!");
                                 return mapping.findForward("error");
        	                 }
                    	 }
                    	 else{
                    		 request.setAttribute("error", "图书归还超过库存上限！");
                             return mapping.findForward("error");
                    	 }
            	 }
                	 
                 } else {
                	 request.setAttribute("error", "图书归还失败!");
                     return mapping.findForward("error");
                     
                 }
             }
        return mapping.findForward("bookback");
    }
}
