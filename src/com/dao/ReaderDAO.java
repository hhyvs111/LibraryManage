package com.dao;

import com.core.ChStr;
import com.core.ConnDB;
import java.util.*;
import java.sql.*;

import com.actionForm.ManagerForm;
import com.actionForm.ReaderForm;

public class ReaderDAO {
    private ConnDB conn=new ConnDB();
//查询数据
public Collection query(String strif){
    ReaderForm readerForm=null;
    Collection readerColl=new ArrayList();
    String sql="";
    if(strif!="all" && strif!=null && strif!=""){
        sql="select r.*,t.name as typename,t.number from tb_reader r left join tb_readerType t on r.typeid=t.id where "+strif+"";
    }else{
        sql="select r.*,t.name as typename,t.number from tb_reader r left join tb_readerType t on r.typeid=t.id";
    }
    ResultSet rs=conn.executeQuery(sql);
    try {
        while (rs.next()) {
            readerForm=new ReaderForm();
            readerForm.setId(Integer.valueOf(rs.getString(1)));
            readerForm.setName(rs.getString(2));
            readerForm.setSex(rs.getString(3));
            readerForm.setBarcode(rs.getString(4));
            readerForm.setBirthday(rs.getString(5));
            readerForm.setPaperType(rs.getString(6));
            readerForm.setPaperNO(rs.getString(7));
            readerForm.setTel(rs.getString(8));
            readerForm.setEmail(rs.getString(9));
            readerForm.setCreateDate(rs.getString(10));
            readerForm.setOperator(rs.getString(11));
            readerForm.setRemark(rs.getString(12));
            readerForm.setTypeid(rs.getInt(13));
            readerForm.setTypename(rs.getString(14));
            readerForm.setNumber(rs.getInt(15));
            readerColl.add(readerForm);
        }
    } catch (SQLException ex) {
    }
    conn.close();
    return readerColl;
}
//用于修改的查询
public ReaderForm queryM(ReaderForm readerForm){
    ReaderForm readerForm1=null;
    String sql="";
    if(readerForm.getId()!=null){
            sql="select r.*,t.name as typename,t.number from tb_reader r left join tb_readerType t on r.typeid=t.id where r.id="+readerForm.getId()+"";
    }else if(readerForm.getBarcode()!=null){
            sql="select r.*,t.name as typename,t.number from tb_reader r left join tb_readerType t on r.typeid=t.id where r.barcode="+readerForm.getBarcode()+"";
        }
    System.out.println("修改读者信息时的SQL："+sql);
    ResultSet rs=conn.executeQuery(sql);
    try {
        while (rs.next()) {
            readerForm1=new ReaderForm();
            readerForm1.setId(Integer.valueOf(rs.getString(1)));
            readerForm1.setName(rs.getString(2));
            readerForm1.setSex(rs.getString(3));
            readerForm1.setBarcode(rs.getString(4));
            readerForm1.setBirthday(rs.getString(5));
            readerForm1.setPaperType(rs.getString(6));
            readerForm1.setPaperNO(rs.getString(7));
            readerForm1.setTel(rs.getString(8));
            readerForm1.setEmail(rs.getString(9));
            readerForm1.setCreateDate(rs.getString(10));
            readerForm1.setOperator(rs.getString(11));
            readerForm1.setRemark(rs.getString(12));
            readerForm1.setTypeid(rs.getInt(13));
            readerForm1.setTypename(rs.getString(14));
            readerForm1.setNumber(rs.getInt(15));
            System.out.println(rs.getString(2));
        }
    } catch (SQLException ex) {
    }
    conn.close();
    return readerForm1;
}
//读者验证
public int checkReader(ReaderForm readerForm) {
    int flag = 0;
    ChStr chStr=new ChStr();
    String sql = "SELECT * FROM tb_reader where barcode='" +
    chStr.filterStr(readerForm.getBarcode()) + "'";
    ResultSet rs = conn.executeQuery(sql);
    try {
        if (rs.next()) {
            String pwd = chStr.filterStr(readerForm.getPaperNO());		//获取输入的密码并过滤输入字符串中的危险字符
            System.out.println(rs.getString(7).substring(12, 18));
            System.out.println(pwd);
            if (pwd.equals(rs.getString(7).substring(12, 18))) {
                flag = 1;
            } else {
                flag = 0;
            }
        }else{
            flag = 0;
        }
    } catch (SQLException ex) {
        flag = 0;
    }finally{
    	conn.close();
    }
    return flag;
}
//添加数据
public int insert(ReaderForm readerForm){
String sql1="SELECT * FROM tb_reader WHERE barcode='"+readerForm.getBarcode()+"'";
ResultSet rs = conn.executeQuery(sql1);
String sql = "";
int falg = 0;
try {
    if (rs.next()) {
        falg = 2;
    } else {
        sql ="Insert into tb_reader (name,sex,barcode,birthday,paperType,paperNO,tel,email,createDate,operator,remark,typeid) values('"+readerForm.getName()+"','"+readerForm.getSex()+"','"+readerForm.getBarcode()+"','"+readerForm.getBirthday()+"','"+readerForm.getPaperType()+"','"+readerForm.getPaperNO()+"','"+readerForm.getTel()+"','"+readerForm.getEmail()+"','"+readerForm.getCreateDate()+"','"+readerForm.getOperator()+"','"+readerForm.getRemark()+"',"+readerForm.getTypeid()+")";
        falg = conn.executeUpdate(sql);
        System.out.println("添加读者信息的SQL：" + sql);
        conn.close();
    }
} catch (SQLException ex) {
    falg = 0;
}
System.out.println("falg:"+falg);
return falg;
}

//修改数据
public int update(ReaderForm readerForm){
String sql="Update tb_reader set name = '" + readerForm.getName() + "',sex='"+readerForm.getSex()+"',barcode='"+readerForm.getBarcode()+"',birthday='"+readerForm.getBirthday()+"',paperType='"+readerForm.getPaperType()+"',paperNO='"+readerForm.getPaperNO()+"',tel='"+readerForm.getTel()+"',email='"+readerForm.getEmail()+"',remark='"+readerForm.getRemark()+"',typeid="+readerForm.getTypeid()+" where id="+readerForm.getId()+"";
int falg=conn.executeUpdate(sql);
System.out.println("修改数据时的SQL："+sql);
conn.close();
return falg;
}
//删除数据
public int delete(ReaderForm readerForm){
String sql="Delete from tb_reader where id="+readerForm.getId()+"";
int falg=conn.executeUpdate(sql);
System.out.println("删除时的SQL："+sql);
return falg;
}
}
