package com.action;

import org.apache.struts.action.*;

import java.util.Collection;

import javax.servlet.http.*;
import com.dao.*;
import com.actionForm.*;

public class Borrow extends Action {
	/******************�ڹ��췽����ʵ����Borrow����Ӧ�õĳ־ò���Ķ���**************************/
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
	            request.setAttribute("error","���Ĳ�������");
	            return mapping.findForward("error");
	        }else if("bookBorrowSort".equals(action)){
	            return bookBorrowSort(mapping,form,request,response); 
	        }else if("bookBorrowSort_reader".equals(action)){
	            return bookBorrowSort_reader(mapping,form,request,response); 
	        }else if("bookborrow".equals(action)){
	            return bookborrow(mapping,form,request,response);  //ͼ�����
	        }else if("bookrenew".equals(action)){
	            return bookrenew(mapping,form,request,response);  //ͼ������
	        }else if("bookrenew_reader".equals(action)){
		        return bookrenew_reader(mapping,form,request,response);  //ͼ������
	        }else if("bookback".equals(action)){
	            return bookback(mapping,form,request,response);  //ͼ��黹
	        }else if("Bremind".equals(action)){
	            return bremind(mapping,form,request,response);  //���ĵ�������
	        }else if("Bremind_reader".equals(action)){
	            return bremind_reader(mapping,form,request,response);  //���ĵ�������
	        }else if("borrowQuery".equals(action)){
	            return borrowQuery(mapping,form,request,response);  //������Ϣ��ѯ
	        }
	        request.setAttribute("error","����ʧ�ܣ�");
	        return mapping.findForward("error");
	    }
    /*********************ͼ���������***********************/
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
    
    /*********************ͼ���������***********************/
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
 /*********************ͼ����Ĳ�ѯ***********************/
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
                System.out.println("����" + str);
            }
            //ͬʱѡ�����ں��������в�ѯ
            if (flag.length == 2) {
                if (request.getParameter("f") != null) {
                    str = request.getParameter("f") + " like '%" +
                          request.getParameter("key") + "%'";
                }
                System.out.println("���ں�����");
                String sdate = request.getParameter("sdate");
                String edate = request.getParameter("edate");
                String str1 = null;
                if (sdate != null && edate != null) {
                    str1 = "borrowTime between '" + sdate + "' and '" + edate +
                           "'";
                }
                str = str + " and borr." + str1;
                System.out.println("���������ڣ�" + str);
            }
        }
        request.setAttribute("borrowQuery",borrowDAO.borrowQuery(str));
       System.out.print("������ѯͼ�������Ϣʱ��str:"+str);
        return mapping.findForward("borrowQuery");
    }
    /*********************��������***********************/
    private ActionForward bremind(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
        request.setAttribute("Bremind",borrowDAO.bremind("wu"));
        return mapping.findForward("Bremind");
    }
    
    /*********************��������_reader***********************/
    private ActionForward bremind_reader(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
    	HttpSession session = request.getSession();
    	String xuehao = (String)session.getAttribute("barcode");
    	System.out.println(xuehao);
        request.setAttribute("Bremind_reader",borrowDAO.bremind(xuehao));
        return mapping.findForward("Bremind_reader");
    }

    /*********************ͼ�����***********************/
    private ActionForward bookborrow(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
        //��ѯ������Ϣ
        //ReaderForm readerForm=(ReaderForm)form;  //�˴�һ������ʹ�ø�������ת��
        readerForm.setBarcode(request.getParameter("barcode"));
        ReaderForm reader = (ReaderForm) readerDAO.queryM(readerForm);
        request.setAttribute("readerinfo", reader);
        //��ѯ���ߵĽ�����Ϣ
        request.setAttribute("borrowinfo",borrowDAO.borrowinfo(request.getParameter("barcode")));
        //��ɽ���
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
                    //���ɽ�����һ
                    int num = bookForm.getAvailable() - 1;
                    bookForm.setAvailable(num);
                    int result = bookDAO.update(bookForm);
                    if(result == 1){
                    request.setAttribute("bar", request.getParameter("barcode"));
                    return mapping.findForward("bookborrowok");
                    }
                 else {
                    request.setAttribute("error", "��ӽ�����Ϣʧ��!");
                    return mapping.findForward("error");
                 }
               }
            }
            	else{
            		request.setAttribute("error", "��ͼ���治��!");
                    return mapping.findForward("error");
            	}
            }else{
                 request.setAttribute("error", "û�и�ͼ��!");
                return mapping.findForward("error");
            }
        }
        	
        
        return mapping.findForward("bookborrow");
    }
    /*********************ͼ��̽�***********************/
    private ActionForward bookrenew(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
        //��ѯ������Ϣ
        readerForm.setBarcode(request.getParameter("barcode"));
        ReaderForm reader = (ReaderForm) readerDAO.queryM(readerForm);
        request.setAttribute("readerinfo", reader);
        //��ѯ���ߵĽ�����Ϣ
        request.setAttribute("borrowinfo",borrowDAO.borrowinfo(request.getParameter("barcode")));
         if(request.getParameter("id")!=null){
             int id = Integer.parseInt(request.getParameter("id"));
             if (id > 0) { //ִ�м̽����
            	 System.out.println(id);
                 int ret = borrowDAO.renew(id);
                 if (ret == 0) {
     				System.out.println("����ʧ��");
     			request.setAttribute("error", "ͼ������ʧ��!");
     			return mapping.findForward("error");
     			}
     			else if(ret == 2){
     				request.setAttribute("error", "ͼ�����������������!");
     				return mapping.findForward("error");
     			}
     			else if(ret == 3){
     				request.setAttribute("error", "��ʱ����Ҫ���裡");
     				return mapping.findForward("error");
     			}
     			else if(ret == 4){
     				request.setAttribute("error", "ͼ���ѵ��ڣ���黹��");
     				return mapping.findForward("error");
     			}
     			else {
     			request.setAttribute("bar", request.getParameter("barcode"));
     			System.out.println("����ɹ�");
     			return mapping.findForward("bookrenewok");
     			}
             }
         }
        return mapping.findForward("bookrenew");
    }
    /*********************ͼ��̽�_reader***********************/
    private ActionForward bookrenew_reader(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response){
//��ѯ������Ϣ
    		HttpSession session = request.getSession();
			readerForm.setBarcode((String)session.getAttribute("barcode"));
			ReaderForm reader = (ReaderForm) readerDAO.queryM(readerForm);
			request.setAttribute("readerinfo", reader);
			//��ѯ���ߵĽ�����Ϣ
			request.setAttribute("borrowinfo",borrowDAO.borrowinfo((String)session.getAttribute("barcode")));
			if(request.getParameter("id")!=null){
			int id = Integer.parseInt(request.getParameter("id"));
			System.out.println(id);
			if (id > 0) { //ִ�м̽����
			int ret = borrowDAO.renew(id);
			if (ret == 0) {
				System.out.println("����ʧ��");
			request.setAttribute("error", "ͼ������ʧ��!");
			return mapping.findForward("error");
			}
			else if(ret == 2){
				request.setAttribute("error", "ͼ�����������������!");
				return mapping.findForward("error");
			}
			else if(ret == 3){
				request.setAttribute("error", "��ʱ����Ҫ���裡");
				return mapping.findForward("error");
			}
			else {
			request.setAttribute("bar", request.getParameter("barcode"));
			System.out.println("����ɹ�");
			return mapping.findForward("bookrenewok_reader");
			}
			}	
			}
				return mapping.findForward("bookrenew_reader");
}
    /*********************ͼ��黹***********************/
    private ActionForward bookback(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
        //��ѯ������Ϣ
        readerForm.setBarcode(request.getParameter("barcode"));
        ReaderForm reader = (ReaderForm) readerDAO.queryM(readerForm);
        request.setAttribute("readerinfo", reader);
        //��ѯ���ߵĽ�����Ϣ
        request.setAttribute("borrowinfo",borrowDAO.borrowinfo(request.getParameter("barcode")));
         if(request.getParameter("id")!=null){
             int id = Integer.parseInt(request.getParameter("id"));
             String operator=request.getParameter("operator");
             String bookBarcode= request.getParameter("bookBarcode");
             System.out.println(bookBarcode);
             if (id > 0) { //ִ�й黹����
            	 System.out.println(id);
            	 BookForm bookForm =(BookForm)bookDAO.queryBarcode(bookBarcode);
            	 System.out.println(bookForm.getAvailable());
            	 if(bookForm != null){
            		 //����1
            		 int num = bookForm.getAvailable() + 1;
            		 //���ɽ�����1��С�ڵ��ڿ����
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
        	                    	 request.setAttribute("error", "ͼ��黹ʧ��!");
        	                         return mapping.findForward("error");
        	                     }
        	                 }
        	                 else{
        	                	 request.setAttribute("error", "ͼ��黹ʧ��!");
                                 return mapping.findForward("error");
        	                 }
                    	 }
                    	 else{
                    		 request.setAttribute("error", "ͼ��黹����������ޣ�");
                             return mapping.findForward("error");
                    	 }
            	 }
                	 
                 } else {
                	 request.setAttribute("error", "ͼ��黹ʧ��!");
                     return mapping.findForward("error");
                     
                 }
             }
        return mapping.findForward("bookback");
    }
}
