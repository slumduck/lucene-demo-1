package com.mime.demo.query;

import com.mime.demo.ik.IKAnalyzer6x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author zhangliang
 * @create 2018-11-23 下午 5:03
 */
public class HighLight {

    public static void main(String[] args) throws IOException, ParseException {
        String field = "title";
        Path path = Paths.get("indexdir");
        Directory directory = FSDirectory.open(path);
        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        Analyzer analyzer = new IKAnalyzer6x();
        QueryParser queryParser = new QueryParser(field,analyzer);
        Query query = queryParser.parse("北京大学");
        System.out.println("Query: " + query.toString());
        QueryScorer queryScorer = new QueryScorer(query,field);
        // 定制高亮标签
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style=\"color:red;\">","</span>");
        // 高亮分词器
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter,queryScorer);
        System.out.println("Query: " + query.toString());
        TopDocs topDocs = null;
        try {
            topDocs = indexSearcher.search(query,10);
            for (ScoreDoc scoreDoc:topDocs.scoreDocs) {
                Document document = indexSearcher.doc(scoreDoc.doc);
                System.out.println("DocID: " + scoreDoc.doc);
                System.out.println("id: " + document.get("id"));
                System.out.println("title: " + document.get("title"));
                //获取tokenstream
                TokenStream tokenStream = TokenSources.getAnyTokenStream(indexSearcher.getIndexReader(),scoreDoc.doc,field,analyzer);
                Fragmenter fragmenter = new SimpleSpanFragmenter(queryScorer);
                highlighter.setTextFragmenter(fragmenter);
                //获取高亮片段
                String str = highlighter.getBestFragment(tokenStream,document.get(field));
                System.out.println("高亮的片段: " + str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidTokenOffsetsException e) {
            e.printStackTrace();
        } finally {
            indexReader.close();
            directory.close();
        }
    }
}
