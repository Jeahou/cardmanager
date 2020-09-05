package org.edwith.webbe.cardmanager.dao;

import org.edwith.webbe.cardmanager.dto.BusinessCard;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import org.edwith.webbe.cardmanager.dao.BusinessCardManagerDao;
// 1 입력
// 2 찾기

public class BusinessCardManagerDao {
    private static String dburl = "jdbc:mysql://localhost:3306/business?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static String dbUser = "businessUser";
    private static String dbpasswd = "1234";

    public List<BusinessCard> searchBusinessCard(String keyword) {
        // 구현하세요.
        List<BusinessCard> businessCardsList = new ArrayList<BusinessCard>();
        BusinessCard businessCard1 = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
            String sql = "SELECT name, phone, companyName, createDate FROM businessCard WHERE name= ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, keyword);
            rs = ps.executeQuery();

             while (rs.next()) {
                String name = rs.getString(1);
                String phone = rs.getString(2);
                String companyName = rs.getString(3);
                String createDate = rs.getString(4);
                businessCard1 = new BusinessCard(name, phone, companyName);
                SimpleDateFormat recvSimpleFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                Date dt = recvSimpleFormat.parse(createDate);
                businessCard1.setCreateDate((dt));
                businessCardsList.add(businessCard1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return businessCardsList;
    }

    public BusinessCard addBusinessCard(BusinessCard businessCard) {
        // 구현하세요.
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sql = "INSERT INTO businessCard(name, phone, companyName, createDate) VALUES(?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, businessCard.getName());
            ps.setString(2, businessCard.getPhone());
            ps.setString(3, businessCard.getCompanyName());
            ps.setString(4, businessCard.getCreateDate().toString());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return businessCard;
    }

}
