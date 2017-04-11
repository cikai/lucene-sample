package com.test;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.google.gson.Gson;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * Created by cikai on 2017/4/11.
 */
public class LuceneTest {
    private static final Version version = Version.LUCENE_6_2_0;
    private Directory directory = null;
    private DirectoryReader ireader = null;
    private IndexWriter iwriter = null;
    private IKAnalyzer analyzer = null;

    public LuceneTest() {
        directory = new RAMDirectory();
    }

    public IndexSearcher getSearcher() {
        try {
            if (ireader == null) {
                ireader = DirectoryReader.open(directory);
            } else {
                DirectoryReader tr = DirectoryReader.openIfChanged(ireader);
                if (tr != null) {
                    ireader.close();
                    ireader = tr;
                }
            }
            return new IndexSearcher(ireader);
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private IKAnalyzer getAnalyzer() {
        if (analyzer == null) {
            return new IKAnalyzer();
        } else {
            return analyzer;
        }
    }

    public void createIndex() {
        IndexWriterConfig iwConfig = new IndexWriterConfig(getAnalyzer());
        iwConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        try {
            iwriter = new IndexWriter(directory, iwConfig);
            List postlist = new Postlist().getPostlist();
            for (Object object : postlist) {
                Post post = new Gson().fromJson(object.toString(), Post.class);
                Document document = new Document();
                document.add(new TextField("uid", post.getUid().toString(), Field.Store.YES));
                document.add(new TextField("tid", post.getTid().toString(), Field.Store.YES));
                document.add(new TextField("title", post.getTitle(), Field.Store.YES));
                document.add(new TextField("content", post.getContent(), Field.Store.YES));
                iwriter.addDocument(document);
            }
            iwriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchByTerm(String field, String keyword, int num) throws org.apache.lucene.queryparser.classic.ParseException, ParseException {
        IndexSearcher isearcher = getSearcher();
        Analyzer analyzer = getAnalyzer();
        //使用QueryParser查询分析器构造Query对象
        QueryParser qp = new QueryParser(version, field, analyzer);
        qp.setDefaultOperator(QueryParser.OR_OPERATOR);
        try {
            Query query = qp.parse(keyword);
            ScoreDoc[] hits;
            //注意searcher的几个方法
            hits = isearcher.search(query, num, null).scoreDocs;
            System.out.println("the ids is =");
            for (int i = 0; i < hits.length; i++) {
                Document doc = isearcher.doc(hits[i].doc);
                System.out.print(doc.get("uid") + " ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        try {
            LuceneTest luceneTest = new LuceneTest();
            luceneTest.createIndex();
            luceneTest.searchByTerm("title", "旅鸟", 100);
        } catch (org.apache.lucene.queryparser.classic.ParseException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
