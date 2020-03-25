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
                    System.out.println("��ǰ����-----------"+num);
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

    /*�������ת��Ϊlist����*/
    public static List<Map<String,Object>> resultsetToList(ResultSet rs) throws SQLException {
        /*�����洢rs������ļ���*/
        List<Map<String,Object>> resultsetList = new ArrayList();
        /*ResultSet���е����ƺ����͵���Ϣ�������ڻ�ȡ����ResultSet�������е����ͺ�������Ϣ�Ķ���*/
        ResultSetMetaData metaData = rs.getMetaData();
        /*��ȡ�����ֶε��������е�������*/
        int columnCount = metaData.getColumnCount();
        while(rs.next()){
            Map<String,Object> map = new HashMap<>(columnCount);
            /*�������е��ֶΣ��У�*/
            for(int i=0;i<columnCount;i++){
                /*��ȡ�ֶ�������ȡ�ֶ�ֵ������map����*/
                String columnName = metaData.getColumnName(i+1);
                Object value = rs.getObject(i+1);
                map.put(columnName.toLowerCase(),value);
            }
            /*��ÿһ�е����ݴ���list������*/
            resultsetList.add(map);
        }
        return resultsetList;
    }


}
