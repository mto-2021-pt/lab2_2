package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {


    SequenceSearcher sequenceSearcherTrue = new SequenceSearcher() {
        @Override
        public SearchResult search(int elem, int[] sequence) {
             SearchResult.Builder builder = SearchResult.builder();
             builder.withFound(true);
             return builder.build();
        }
    };


    @Test
    void test1() {
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcherTrue);

        double result = similarityFinder.calculateJackardSimilarity(new int[]{3},new int[]{3});
        assertEquals(1,result);
    }

}
