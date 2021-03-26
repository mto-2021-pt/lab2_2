package edu.iis.mto.similarity;
import static org.junit.jupiter.api.Assertions.*;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class SimilarityFinderTest {

    class DoublerSequenceSearcher implements SequenceSearcher{
        @Override
        public SearchResult search(int elem, int[] sequence) {
            int index=0;
            SearchResult.Builder b= new SearchResult.Builder();
            for(int e : sequence){
                if(elem==e){
                    b.withFound(true);
                    b.withPosition(index);
                    return b.build();
                }
                index+=1;
            }
            b.withFound(false);
            b.withPosition(-1);
            return b.build();
        }
    }


    @BeforeEach
    void setUp() throws Exception {}

    @Test
    void Successful_ResultEqualsOne(){
        SimilarityFinder sf=new SimilarityFinder(new DoublerSequenceSearcher());
        int[] seq1={58,1024,36,48,89};
        int[] seq2={58,1024,36,48,89};

        double res=sf.calculateJackardSimilarity(seq1,seq2);
        assertEquals(1,res);
    }

    @Test
    void Successful_ResultEqualsZero(){
        SimilarityFinder sf=new SimilarityFinder(new DoublerSequenceSearcher());
        int[] seq1={58,1024,36,48,89};
        int[] seq2={59,1025,37,49,90};

        double res=sf.calculateJackardSimilarity(seq1,seq2);
        assertEquals(0,res);

    }


}
