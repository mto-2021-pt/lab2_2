package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.*;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    SimilarityFinder similarityFinder = new SimilarityFinder(new SequenceSearcher() {
        @Override
        public SearchResult search(int elem, int[] sequence) {
            for(int i=0;i<sequence.length;i++)
            {
                if(sequence[i]==elem)
                {
                    return SearchResult.builder().withFound(true).withPosition(i).build();
                }
            }
            return SearchResult.builder().withFound(false).build();
        }
    });

    @Test
    public void twoEmptySequence() {

        int [] seq1={};
        int [] seq2={};

        assertEquals(1.0d,similarityFinder.calculateJackardSimilarity(seq1,seq2));
    }

}
