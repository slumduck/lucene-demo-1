package com.mime.demo.ik;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;

/**
 * @author zhangliang
 * @create 2018-11-22 下午 3:07
 */
public class IKTokenizer6x extends Tokenizer {

    /**
     * IK分词器实现
      */
    private IKSegmenter _IKImplement;

    /**
     * 词元文本属性
     */
    private final CharTermAttribute termAtt;
    /**
     * 词元位移属性
     */
    private final OffsetAttribute offsetAtt;
    /**
     * 词元分类属性
     */
    private final TypeAttribute typeAtt;
    /**
     * 记录最后一个词元的结束位置
     */
    private int endPosition;

    public IKTokenizer6x(boolean useSmart) {
        super();
        offsetAtt = addAttribute(OffsetAttribute.class);
        termAtt = addAttribute(CharTermAttribute.class);
        typeAtt = addAttribute(TypeAttribute.class);
        _IKImplement = new IKSegmenter(input,useSmart);
    }

    @Override
    public final boolean incrementToken() throws IOException {
        // 清空所有的词元属性
        clearAttributes();
        Lexeme nextLexeme = _IKImplement.next();
        if (nextLexeme != null) {
            // 将lexeme转成Attributes
            //设置词元文本
            termAtt.append(nextLexeme.getLexemeText());
            //设置词元长度
            termAtt.setLength(nextLexeme.getLength());
            //设置词元的位移
            offsetAtt.setOffset(nextLexeme.getBeginPosition(),nextLexeme.getEndPosition());
            // 记录分词的最后位置
            endPosition = nextLexeme.getEndPosition();
            // 记录词元分类
            typeAtt.setType(nextLexeme.getLexemeText());
            return true;
        }
        return false;
    }

    @Override
    public void end() throws IOException {
        int finalOffset = correctOffset(this.endPosition);
        offsetAtt.setOffset(finalOffset,finalOffset);
    }

    @Override
    public void reset() throws IOException {
        super.reset();
        _IKImplement.reset(input);
    }
}
