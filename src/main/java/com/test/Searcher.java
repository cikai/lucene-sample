package com.test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cikai on 2017/4/12.
 */
public class Searcher {
    public List<String> searchQuery(String str) {
        List<String> result = new ArrayList<>();
        try {
            IndexSearcher indexSearcher = null;
            IndexReader indexReader = DirectoryReader.open(FSDirectory
                    .open(new File("E:\\lucene")));
            indexSearcher = new IndexSearcher(indexReader);
            Analyzer analyzer = new IKAnalyzer();
            QueryParser queryParser = new QueryParser(Version.LUCENE_46, "title_content", analyzer);
            Query query = queryParser.parse(str);
            TopDocs td = indexSearcher.search(query, 10000);
            for (int i = 0; i < td.totalHits; i++) {
                Document document = indexSearcher.doc(td.scoreDocs[i].doc);
                result.add(document.get("tid"));
            }
        } catch (org.apache.lucene.queryparser.classic.ParseException pe) {
            pe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException ae) {
            ae.printStackTrace();
        }
        return result;
    }
}
