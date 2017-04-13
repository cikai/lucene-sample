package com.test;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * Created by cikai on 2017/4/11.
 */
public class LuceneTest {
    public static void main(String args[]) throws ParseException {
        String sql = null;
        DBHelper db = null;
        ResultSet rs = null;
        Directory directory = null;
        try {
            String os = System.getProperty("os.name");
            if (os.toLowerCase().startsWith("win")) {
                new FileUtils().delAllFile("/home/lucene-index");
            } else {
                Runtime.getRuntime().exec("rm -rf /home/lucene-index");
            }
            directory = FSDirectory.open(new File("/home/lucene-index"));
            Indexer indexer = new Indexer();
            indexer.createIndex(directory);

            Searcher searcher = new Searcher();
            String query = "N1";
            List<String> result = searcher.searchQuery(query);
            String tids = "";
            for (int i = 0; i < result.size(); i++) {
                int tid = Integer.parseInt(result.get(i));
                tids += tid + ",";
            }
            sql = "select * from t_topic where tid in (" + tids.substring(0, tids.length() - 1) + ") and status = 1";
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
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.close();
            }
            for (int i = 0; i < postlist.size(); i++) {
                System.out.println(postlist.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
