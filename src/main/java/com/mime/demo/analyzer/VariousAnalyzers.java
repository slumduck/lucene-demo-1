package com.mime.demo.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;

/**
 * @author zhangliang
 * @create 2018-11-22 下午 2:52
 */
public class VariousAnalyzers {

    private static String str = "中华人民共和国简称中国，是一个有13亿人口的国家";

    public static void printAnalyzer (Analyzer analyzer) throws IOException {
        StringReader stringReader = new StringReader(str);
        TokenStream toStream = analyzer.tokenStream(str, stringReader);
        toStream.reset();
        CharTermAttribute charTermAttribute = toStream.getAttribute(CharTermAttribute.class);
        while (toStream.incrementToken()) {
            System.out.print(charTermAttribute.toString() + "|");
        }
        System.out.println("\n");
        analyzer.close();
    }

    public static void main(String[] args) throws Exception{
        Analyzer analyzer = null;
        //标准分词
        analyzer = new StandardAnalyzer();
        System.out.println("标准分词：" + analyzer.getClass());
        printAnalyzer(analyzer);
        //空格分词
        analyzer = new WhitespaceAnalyzer();
        System.out.println("空格分词：" + analyzer.getClass());
        printAnalyzer(analyzer);
        //简单分词
        analyzer = new SimpleAnalyzer();
        System.out.println("简单分词：" + analyzer.getClass());
        printAnalyzer(analyzer);
        //二分法分词
        analyzer = new CJKAnalyzer();
        System.out.println("二分法分词：" + analyzer.getClass());
        printAnalyzer(analyzer);
        //关键字分词
        analyzer = new KeywordAnalyzer();
        System.out.println("关键字分词：" + analyzer.getClass());
        printAnalyzer(analyzer);
        //停用词分词
        analyzer = new StopAnalyzer();
        System.out.println("停用词分词：" + analyzer.getClass());
        printAnalyzer(analyzer);
        //中文智能分词
        analyzer = new SmartChineseAnalyzer();
        System.out.println("中文智能分词：" + analyzer.getClass());
        printAnalyzer(analyzer);
    }
}
