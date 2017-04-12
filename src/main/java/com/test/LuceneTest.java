package com.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * Created by cikai on 2017/4/11.
 */
public class LuceneTest {
    public static void main(String args[]) throws ParseException {
        Directory directory = null;
        try {
            directory = FSDirectory.open(new File("E:\\lucene"));
            Indexer indexer = new Indexer();
            indexer.createIndex(directory);

            Searcher searcher = new Searcher();
            String query = "test";
            List<String> result = searcher.searchQuery(query);

            System.out.print(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}



















