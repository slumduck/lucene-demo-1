package com.mime.demo.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;

/**
 * @author zhangliang
 * @create 2018-11-22 下午 2:33
 */
public class StdAnalyzer {
    private static String strCh = "中华人民共和国简称中国，是一个有13亿人口的国家";

    private static String strEn = "Dogs can not achieve a place,eyes can reach;";

    public static void stdAnalyzer(String str) throws IOException {
        Analyzer analyzer = null;
        analyzer = new StandardAnalyzer();
        StringReader stringReader = new StringReader(str);
        TokenStream toStream = analyzer.tokenStream(str, stringReader);
        toStream.reset();
        CharTermAttribute charTermAttribute = toStream.getAttribute(CharTermAttribute.class);
        System.out.println("分词结果：");
        while (toStream.incrementToken()){
            System.out.print(charTermAttribute.toString() + "|");
        }
        System.out.println("\n");
        analyzer.close();
    }

    public static void main(String[] args) throws Exception{
        System.out.println("StandardAnalyzer 对中文分词");
        stdAnalyzer(strCh);
        System.out.println("StandardAnalyzer 对英文分词");
        stdAnalyzer(strEn);
    }
}
