package com.umf.socket.client;

import com.umf.config.InitConfig;
import com.umf.mapper.dbbase.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:LiuYuKun
 * @Date:2019/11/18
 */
public class Test1c3p0 {
    public static void main(String[] args) {
        System.setProperty("com.mchange.v2.c3p0.cfg.xml","config/c3p0-config.xml");
        ConnectionManager conMan = ConnectionManager.getInstance();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = conMan.getConnection();
            String sql = " select * from " +
                         " (select rownum rn,au.* from auditdata au " +
                         " where rownum <= 20) A where rn>10 ";
            pstmt = connection.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if(rs!=null){
                List<Map<String, Object>> list = resultsetToList(rs);
                Integer num = 1;
                for(Map<String,Object> map:list){
                    System.out.println("当前行数-----------"+num);
                    for(Map.Entry<String,Object> item:map.entrySet()){
                        System.out.println(item.getKey()+"---"+item.getValue());
                    }
                    num++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conMan.closeCon(connection,pstmt,rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*将结果集转换为list集合*/
    public static List<Map<String,Object>> resultsetToList(ResultSet rs) throws SQLException {
        /*创建存储rs结果集的集合*/
        List<Map<String,Object>> resultsetList = new ArrayList();
        /*ResultSet中列的名称和类型的信息，可用于获取关于ResultSet对象中列的类型和属性信息的对象。*/
        ResultSetMetaData metaData = rs.getMetaData();
        /*获取所有字段的数量（列的数量）*/
        int columnCount = metaData.getColumnCount();
        while(rs.next()){
            Map<String,Object> map = new HashMap<>(columnCount);
            /*遍历所有的字段（列）*/
            for(int i=0;i<columnCount;i++){
                /*获取字段名，获取字段值，存入map集合*/
                String columnName = metaData.getColumnName(i+1);
                Object value = rs.getObject(i+1);
                map.put(columnName.toLowerCase(),value);
            }
            /*将每一行的数据存入list集合中*/
            resultsetList.add(map);
        }
        return resultsetList;
    }


}
