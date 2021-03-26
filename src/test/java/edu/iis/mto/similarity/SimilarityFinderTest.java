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

    @Test
    public void twoEqualsSequence()
    {
        int [] seq1={1,2,3,4};
        int [] seq2={1,2,3,4};

        assertEquals(1,similarityFinder.calculateJackardSimilarity(seq1,seq2));
    }

    @Test
    public void twoDifferentSequence()
    {
        int [] seq1={1,2,3,4};
        int [] seq2={9,2,8,4};
        System.out.println(similarityFinder.calculateJackardSimilarity(seq1,seq2));

        assertNotEquals(1,similarityFinder.calculateJackardSimilarity(seq1,seq2));
    }




}
