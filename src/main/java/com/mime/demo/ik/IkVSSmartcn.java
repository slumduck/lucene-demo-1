package com.mime.demo.ik;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;

/**
 * @author zhangliang
 * @create 2018-11-22 下午 3:48
 */
public class IkVSSmartcn {

    public static String str1 = "浙江省温州市鹿城区新城大道星泰大厦幺三零幺";

    public static String str2 = "IKAnalyzer 是一个开源的，基于java语言开发的轻量级的中文分词工具包。嗯，";

    public static void printAnalyzer(Analyzer analyzer, String str) throws IOException {
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
        System.out.println("句子一：" + str1);
        System.out.println("SmartChineseAnalyzer 分词器：");
        analyzer = new SmartChineseAnalyzer();
        printAnalyzer(analyzer, str1);
        System.out.println("IKAnalyzer 分词器：");
        analyzer = new IKAnalyzer6x(true);
        printAnalyzer(analyzer, str1);
        System.out.println("-------------------------------------------");
        System.out.println("句子二：" + str2);
        System.out.println("SmartChineseAnalyzer 分词器：");
        analyzer = new SmartChineseAnalyzer();
        printAnalyzer(analyzer, str2);
        System.out.println("IKAnalyzer 分词器：");
        analyzer = new IKAnalyzer6x(true);
        printAnalyzer(analyzer, str2);
    }
}
