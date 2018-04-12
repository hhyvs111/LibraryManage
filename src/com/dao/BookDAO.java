package com.dao;

import com.core.ConnDB;
import java.sql.*;
import java.util.*;
import com.actionForm.BookForm;

public class BookDAO {
	private ConnDB conn = new ConnDB();

	// 查询数据
	public Collection query(String strif) {
		BookForm bookForm = null;
		Collection bookColl = new ArrayList();
		String sql = "";
		if (strif != "all" && strif != null && strif != "") {
			sql = "select * from (select b.*,c.name as bookcaseName,p.pubname as publishing,t.typename from tb_bookinfo b left join tb_bookcase c on b.bookcase=c.id join tb_publishing p on b.ISBN=p.ISBN join tb_booktype t on b.typeid=t.id where b.del=0)  as book where book."
					+ strif + "'";
		} else {
			sql = "select b.*,c.name as bookcaseName,p.pubname as publishing,t.typename from tb_bookinfo b left join tb_bookcase c on b.bookcase=c.id join tb_publishing p on b.ISBN=p.ISBN join tb_booktype t on b.typeid=t.id where b.del=0";
		}
		System.out.println("图书查询时的SQL：" + sql);
		ResultSet rs = conn.executeQuery(sql);
		try {
			while (rs.next()) {
				bookForm = new BookForm();
				bookForm.setBarcode(rs.getString(1));
				bookForm.setBookName(rs.getString(2));
				bookForm.setTypeId(rs.getInt(3));
				bookForm.setAuthor(rs.getString(4));
				bookForm.setIsbn(rs.getString(5));
				bookForm.setPrice(Float.valueOf(rs.getString(6))); // 此处必须进行类型转换
				bookForm.setBookcaseid(rs.getInt(7));
				bookForm.setInTime(rs.getString(8));
				bookForm.setOperator(rs.getString(9));
				bookForm.setDel(rs.getInt(10));
				bookForm.setId(Integer.valueOf(rs.getString(11)));
				bookForm.setStock(Integer.valueOf(rs.getString(12)));
				bookForm.setAvailable(Integer.valueOf(rs.getString(13)));
				bookForm.setBookcaseName(rs.getString(14));
				bookForm.setPublishing(rs.getString(15));
				bookForm.setTypeName(rs.getString(16));
				bookColl.add(bookForm);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		conn.close();
		return bookColl;
	}

	// 用于修改的查询
	public BookForm queryM(BookForm bookForm1) {
		BookForm bookForm = null;
		String sql = "select b.*,c.name as bookcaseName,p.pubname as publishing,t.typename from tb_bookinfo b left join tb_bookcase c on b.bookcase=c.id join tb_publishing p on b.ISBN=p.ISBN join tb_booktype t on b.typeid=t.id where b.id="
				+ bookForm1.getId() + "";
		System.out.println("修改时的SQL：" + sql);
		ResultSet rs = conn.executeQuery(sql);
		try {
			while (rs.next()) {
				bookForm = new BookForm();
				bookForm.setBarcode(rs.getString(1));
				bookForm.setBookName(rs.getString(2));
				bookForm.setTypeId(rs.getInt(3));
				bookForm.setAuthor(rs.getString(4));
				bookForm.setIsbn(rs.getString(5));
				bookForm.setPrice(Float.valueOf(rs.getString(6))); // 此处必须进行类型转换
				bookForm.setBookcaseid(rs.getInt(7));
				bookForm.setInTime(rs.getString(8));
				bookForm.setOperator(rs.getString(9));
				bookForm.setDel(rs.getInt(10));
				bookForm.setId(Integer.valueOf(rs.getString(11)));
				bookForm.setStock(Integer.valueOf(rs.getString(12)));
				bookForm.setAvailable(Integer.valueOf(rs.getString(13)));
				bookForm.setBookcaseName(rs.getString(14));
				bookForm.setPublishing(rs.getString(15));
				bookForm.setTypeName(rs.getString(16));
			}
		} catch (SQLException ex) {
		}
		conn.close();
		return bookForm;
	}
	public BookForm queryBarcode (String bookBarcode) {
		BookForm bookForm = null;
		String sql = "select b.*,c.name as bookcaseName,p.pubname as publishing,t.typename from tb_bookinfo b left join tb_bookcase c on b.bookcase=c.id join tb_publishing p on b.ISBN=p.ISBN join tb_booktype t on b.typeid=t.id where b.barcode='"
				+ bookBarcode + "'";
		System.out.println("查询bookbarcode时的SQL：" + sql);
		ResultSet rs = conn.executeQuery(sql);
		try {
			while (rs.next()) {
				bookForm = new BookForm();
				bookForm.setBarcode(rs.getString(1));
				bookForm.setBookName(rs.getString(2));
				bookForm.setTypeId(rs.getInt(3));
				bookForm.setAuthor(rs.getString(4));
				bookForm.setIsbn(rs.getString(5));
				bookForm.setPrice(Float.valueOf(rs.getString(6))); // 此处必须进行类型转换
				bookForm.setBookcaseid(rs.getInt(7));
				bookForm.setInTime(rs.getString(8));
				bookForm.setOperator(rs.getString(9));
				bookForm.setDel(rs.getInt(10));
				bookForm.setId(Integer.valueOf(rs.getString(11)));
				bookForm.setStock(Integer.valueOf(rs.getString(12)));
				bookForm.setAvailable(Integer.valueOf(rs.getString(13)));
				bookForm.setBookcaseName(rs.getString(14));
				bookForm.setPublishing(rs.getString(15));
				bookForm.setTypeName(rs.getString(16));
			}
		} catch (SQLException ex) {
		}
		conn.close();
		return bookForm;
	}
	// 用于借阅的查询
	public BookForm queryB(String f, String key) {
		BookForm bookForm = null;
		String sql = "select b.*,c.name as bookcaseName,p.pubname as publishing,t.typename from tb_bookinfo b left join tb_bookcase c on b.bookcase=c.id join tb_publishing p on b.ISBN=p.ISBN join tb_booktype t on b.typeid=t.id where b."
				+ f + "='" + key + "' and b.del = 0";
		System.out.println("查询借阅信息时的SQL：" + sql);
		ResultSet rs = conn.executeQuery(sql);
		try {
			if (rs.next()) {
				bookForm = new BookForm();
				bookForm.setBarcode(rs.getString(1));
				bookForm.setBookName(rs.getString(2));
				bookForm.setTypeId(rs.getInt(3));
				bookForm.setAuthor(rs.getString(4));
				bookForm.setIsbn(rs.getString(5));
				bookForm.setPrice(Float.valueOf(rs.getString(6))); // 此处必须进行类型转换
				bookForm.setBookcaseid(rs.getInt(7));
				bookForm.setInTime(rs.getString(8));
				bookForm.setOperator(rs.getString(9));
				bookForm.setDel(rs.getInt(10));
				bookForm.setId(Integer.valueOf(rs.getString(11)));
				bookForm.setStock(Integer.valueOf(rs.getString(12)));
				bookForm.setAvailable(Integer.valueOf(rs.getString(13)));
				bookForm.setBookcaseName(rs.getString(14));
				bookForm.setPublishing(rs.getString(15));
				bookForm.setTypeName(rs.getString(16));
			}
		} catch (SQLException ex) {
		}
		conn.close();
		return bookForm;
	}

	// 添加数据
	public int insert(BookForm bookForm) {
		String sql1 = "SELECT * FROM tb_bookinfo WHERE barcode='"
				+ bookForm.getBarcode() + "' or bookname='"
				+ bookForm.getBookName() + "'";
		ResultSet rs = conn.executeQuery(sql1);
		String sql = "";
		int falg = 0;
		try {
			if (rs.next()) {
				falg = 2;
			} else {
				sql = "Insert into tb_bookinfo (barcode,bookname,typeid,author,isbn,price,bookcase,inTime,operator,stock,available) values('"
						+ bookForm.getBarcode()
						+ "','"
						+ bookForm.getBookName()
						+ "',"
						+ bookForm.getTypeId()
						+ ",'"
						+ bookForm.getAuthor()
						+ "','"
						+ bookForm.getIsbn()
						+ "',"
						+ bookForm.getPrice()
						+ ","
						+ bookForm.getBookcaseid()
						+ ",'"
						+ bookForm.getInTime()
						+ "','"
						+ bookForm.getOperator()
						+ "',"
						+ bookForm.getStock()
						+ ","
						+ bookForm.getStock()
						+ ")";
				falg = conn.executeUpdate(sql);
				System.out.println("添加图书的SQL：" + sql);
				conn.close();
			}
		} catch (SQLException ex) {
			falg = 0;
		}
		System.out.println("falg:" + falg);
		return falg;
	}

	// 修改数据
	public int update(BookForm bookForm) {
		String sql = "Update tb_bookinfo set typeid=" + bookForm.getTypeId()
				+ ",author='" + bookForm.getAuthor()  + "',isbn='" + bookForm.getIsbn()
				+ "',price=" + bookForm.getPrice() + ",bookcase=" + bookForm.getBookcaseid() 
				+ ",stock=" + bookForm.getStock() + ",available=" + bookForm.getAvailable()
				+ " where id=" + bookForm.getId() + "";
		int falg = conn.executeUpdate(sql);
		System.out.println("修改数据时的SQL：" + sql);
		conn.close();
		return falg;
	}

	// 删除数据
	public int delete(BookForm bookForm) {
		String sql = "UPDATE tb_bookinfo SET del=1 where id="
				+ bookForm.getId() + "";
		int falg = conn.executeUpdate(sql);
		System.out.println("删除时的SQL：" + sql);
		return falg;
	}

}
