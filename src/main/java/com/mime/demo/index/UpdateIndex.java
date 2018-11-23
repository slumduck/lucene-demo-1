package com.mime.demo.index;

import com.mime.demo.ik.IKAnalyzer6x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.document.*;
import org.apache.lucene.document.Field.Store;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author zhangliang
 * @create 2018-11-23 上午 10:16
 */
public class UpdateIndex {

    public static void main(String[] args) throws Exception{
        Analyzer analyzer = new IKAnalyzer6x();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        Path path = Paths.get("indexdir");
        Directory directory = null;
        try {
            directory = FSDirectory.open(path);
            IndexWriter indexWriter = new IndexWriter(directory,indexWriterConfig);
            Document doc = new Document();
            doc.add(new TextField("id", "2", Store.YES));
            doc.add(new TextField("title", "北京大学开学迎来4380名新生", Store.YES));
            doc.add(new TextField("content","昨天，北京大学迎来4380名来自全国各地及数十个国家的本科新生。其中，农村学生共700余名，为近年最多...",Store.YES));
            indexWriter.updateDocument(new Term("title","北大"), doc);
            indexWriter.commit();
            indexWriter.close();
            directory.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
