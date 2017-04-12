package com.test;

import com.google.gson.Gson;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.util.List;

/**
 * Created by cikai on 2017/4/12.
 */
public class Indexer {
    public void createIndex(Directory directory) {
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_46,
                analyzer);
        try {
            IndexWriter indexWriter = new IndexWriter(directory, config);
            List postlist = new Postlist().getPostlist();
            for (Object object : postlist) {
                Document document = new Document();
                Post post = new Gson().fromJson(object.toString(), Post.class);
                document.add(new TextField("uid", post.getUid().toString(), Field.Store.YES));
                document.add(new TextField("tid", post.getTid().toString(), Field.Store.YES));
                document.add(new TextField("title_content", post.getTitle() + " " + post.getContent(), Field.Store.YES));
                indexWriter.addDocument(document);
            }
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
