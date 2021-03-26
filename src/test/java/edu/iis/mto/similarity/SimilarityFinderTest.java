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

    SequenceSearcher sequenceSearcherFalse = new SequenceSearcher() {
        @Override
        public SearchResult search(int elem, int[] sequence) {
            SearchResult.Builder builder = SearchResult.builder();
            builder.withFound(false);
            return builder.build();
        }
    };
    @Test
    void test2() {
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcherFalse);

        double result = similarityFinder.calculateJackardSimilarity(new int[]{3},new int[]{4});
        assertEquals(0,result);
    }

    @Test
    void test3() {
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcherFalse);

        double result = similarityFinder.calculateJackardSimilarity(new int[]{3},new int[]{});
        assertEquals(0,result);
    }
    @Test
    void test4() {
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcherTrue);

        double result = similarityFinder.calculateJackardSimilarity(new int[]{},new int[]{});
        assertEquals(1,result);
    }

    @Test
    void test5() {
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcherTrue);

        for(int i=0; i<5; i++){
            double result = similarityFinder.calculateJackardSimilarity(new int[]{},new int[]{});
            assertEquals(1,result);
        }

    }
    @Test
    void test6() {
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcherFalse);

        for(int i=0; i<5; i++){
            double result = similarityFinder.calculateJackardSimilarity(new int[]{5,3},new int[]{1,2});
            assertEquals(0,result);
        }

    }



}
