package com.mime.demo.ik;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.StringReader;

/**
 * @author zhangliang
 * @create 2018-11-22 下午 4:20
 */
public class ExtDict {

    private static String str = "厉害了我的哥！中国环保部门即将发布治理北京雾霾的方法。嗯，好好美";


    public static void main(String[] args) throws Exception{
        Analyzer analyzer = null;
        analyzer = new IKAnalyzer6x(true);
        StringReader stringReader = new StringReader(str);
        TokenStream toStream = analyzer.tokenStream(str,stringReader);
        toStream.reset();
        CharTermAttribute charTermAttribute = toStream.getAttribute(CharTermAttribute.class);
        System.out.println("分词结果：");
        while (toStream.incrementToken()) {
            System.out.print(charTermAttribute.toString() + "|");
        }
        System.out.println("\n");
        analyzer.close();
    }
}
