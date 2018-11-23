package com.mime.demo.ik;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

/**
 * @author zhangliang
 * @create 2018-11-22 下午 3:37
 */
public class IKAnalyzer6x extends Analyzer {

    private boolean useSmart;

    public boolean useSmart(){
        return useSmart;
    }

    public void setUseSmart(boolean useSmart) {
        this.useSmart = useSmart;
    }

    public IKAnalyzer6x() {
        this(false);
    }

    public IKAnalyzer6x(boolean useSmart) {
        super();
        this.useSmart = useSmart;
    }

    @Override
    protected TokenStreamComponents createComponents(String s) {
        Tokenizer _IKTokenizer = new IKTokenizer6x(this.useSmart);
        return new TokenStreamComponents(_IKTokenizer);
    }
}
