package com.mime.demo.query;

import com.mime.demo.ik.IKAnalyzer6x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author zhangliang
 * @create 2018-11-23 下午 3:06
 */
public class LuceneQueryTest {
    Analyzer analyzer = null;
    Path path = null;
    Directory directory = null;
    IndexReader indexReader = null;
    IndexSearcher indexSearcher = null;

    @Before
    public void init() throws IOException, ParseException {
        analyzer = new IKAnalyzer6x(true);
        path = Paths.get("indexdir");
        directory = FSDirectory.open(path);
        indexReader = DirectoryReader.open(directory);
        indexSearcher = new IndexSearcher(indexReader);
    }

    /**
     * lucene 单域查询
     * @throws IOException
     * @throws ParseException
     */
    @Test
    public void luceneQueryParseTest() throws IOException, ParseException {
        String field = "content";
        QueryParser queryParser = new QueryParser(field,analyzer);
        queryParser.setDefaultOperator(QueryParser.Operator.AND);
        // 查询关键词
        Query query = queryParser.parse("北京学生");
        // 返回前10条
        show(indexSearcher,query);
    }

    /**
     * 多域查询
     * @throws IOException
     */
    @Test
    public void luceneMultiFieldQueryParserTest() throws ParseException {
        String [] fields = {"content","title"};
        MultiFieldQueryParser multiFieldQueryParser = new MultiFieldQueryParser(fields,analyzer);
        Query query = multiFieldQueryParser.parse("美国");
        show(indexSearcher,query);
    }

    /**
     * 词条查询
     * @throws IOException
     */
    @Test
    public void luceneTermQueryTest() {
        Term term = new Term("title","美国");
        Query termQuery = new TermQuery(term);
        show(indexSearcher,termQuery);
    }

    /**
     * 布尔查询
     */
    @Test
    public void luceneBooleanQueryTest(){
        Query query1 = new TermQuery(new Term("title","美国"));
        Query query2 = new TermQuery(new Term("title","中国"));
        BooleanClause booleanClause1 = new BooleanClause(query1, BooleanClause.Occur.MUST);
        BooleanClause booleanClause2 = new BooleanClause(query2, BooleanClause.Occur.MUST_NOT);
        BooleanQuery booleanQuery = new BooleanQuery.Builder()
                .add(booleanClause1).add(booleanClause2).build();
        show(indexSearcher,booleanQuery);
    }

    /**
     * 范围搜索
     */
    @Test
    public void luceneRangeQueryTest(){
        Query query = IntPoint.newRangeQuery("reply",500,2000);
        show(indexSearcher,query);
    }

    /**
     * 前缀搜索
     */
    @Test
    public void lucenePrefixQueryTest(){
        Term term = new Term("title","学");
        Query query = new PrefixQuery(term);
        show(indexSearcher,query);
    }

    /**
     * 多关键字搜索
     */
    @Test
    public void lucenePhraseQueryTest(){
        PhraseQuery.Builder builder = new PhraseQuery.Builder();
        builder.add(new Term("title","大学"),2);
        builder.add(new Term("title","开学"),3);
        PhraseQuery phraseQuery = builder.build();
        show(indexSearcher,phraseQuery);
    }

    /**
     * 模糊搜索
     */
    @Test
    public void luceneFuzzyQueryTest(){
        Term term = new Term("title","Tramp");
        FuzzyQuery fuzzyQuery = new FuzzyQuery(term);
        show(indexSearcher,fuzzyQuery);
    }

    /**
     * 通配符搜索
     */
    @Test
    public void luceneWildcardQueryTest(){
        WildcardQuery wildcardQuery = new WildcardQuery(new Term("title","学?"));
        show(indexSearcher,wildcardQuery);
    }
    /**
     * 打印前10条
     * @param indexSearcher
     * @param query
     * @throws IOException
     */
    public void show(IndexSearcher indexSearcher,Query query){
        System.out.println("Query: " + query.toString());
        TopDocs topDocs = null;
        try {
            topDocs = indexSearcher.search(query,10);
            for (ScoreDoc scoreDoc:topDocs.scoreDocs) {
                Document document = indexSearcher.doc(scoreDoc.doc);
                System.out.println("DocID: " + scoreDoc.doc);
                System.out.println("id: " + document.get("id"));
                System.out.println("title: " + document.get("title"));
                System.out.println("文档评分: " + scoreDoc.score);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void closeResource() throws IOException {
        indexReader.close();
        directory.close();
    }
}
