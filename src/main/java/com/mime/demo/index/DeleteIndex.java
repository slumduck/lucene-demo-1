package com.mime.demo.index;

import com.mime.demo.ik.IKAnalyzer6x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author zhangliang
 * @create 2018-11-23 上午 11:14
 */
public class DeleteIndex {

    public static void main(String[] args) throws Exception{
        Analyzer analyzer = new IKAnalyzer6x();
        Path path = Paths.get("indexdir");
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        Directory directory = FSDirectory.open(path);
        IndexWriter indexWriter = new IndexWriter(directory,indexWriterConfig);
        indexWriter.deleteDocuments(new Term("title","北京"));
        indexWriter.commit();
        indexWriter.close();
        directory.close();
        System.out.println("删除完成");
    }
}
