package com.test;

import com.google.gson.Gson;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cikai on 2017/4/11.
 */
public class Postlist {
    static String sql = null;
    static DBHelper db = null;
    static ResultSet rs = null;

    public List getPostlist() {
        sql = "select * from t_topic where status = 1 order by uid asc";
        db = new DBHelper(sql);
        List postlist = new ArrayList();
        try {
            rs = db.pst.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setTid(rs.getInt("tid"));
                post.setUid(rs.getInt("uid"));
                post.setNid(rs.getInt("nid"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setIs_top(rs.getInt("is_top"));
                post.setIs_essence(rs.getInt("is_essence"));
                post.setWeight(rs.getDouble("weight"));
                post.setCreate_time(rs.getInt("create_time"));
                post.setUpdate_time(rs.getInt("update_time"));
                post.setStatus(rs.getInt("status"));
                postlist.add(new Gson().toJson(post));
            }
            rs.close();
            return postlist;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return null;
    }
}
