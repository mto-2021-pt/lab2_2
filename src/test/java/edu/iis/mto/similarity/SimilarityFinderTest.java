package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    SimilarityFinder similarityFinder = new SimilarityFinder((elem, sequence) -> {
        for(int i = 0; i < sequence.length; i++){
            if(sequence[i] == elem){
                return SearchResult.builder().withFound(true).withPosition(i).build();
            }
        }
        return SearchResult.builder().withFound(false).build();
    });

    @Test
    void testingTheSameArrays(){
        int[] seq1 = {10,20,30,40};
        int[] seq2 = {10,20,30,40};

        assertEquals(1, similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void testingTheSameEmptyArrays(){
        int[] seq1 = {};
        assertEquals(1, similarityFinder.calculateJackardSimilarity(seq1, seq1));
    }

}
