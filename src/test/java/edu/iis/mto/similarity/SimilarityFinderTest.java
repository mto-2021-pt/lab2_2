package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    SequenceSearcher sequenceSearcherTrue = (elem, sequence) -> SearchResult.builder().withFound(true).build();
    SequenceSearcher sequenceSearcherFalse = (elem, sequence) -> SearchResult.builder().withFound(false).build();

    @Test
    void test1() {
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcherFalse);

        double result = similarityFinder.calculateJackardSimilarity(new int[]{5}, new int[]{1});

        Assertions.assertEquals(0,result);
    }

    @Test
    void test2() {
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcherTrue);

        double result = similarityFinder.calculateJackardSimilarity(new int[]{2}, new int[]{2});

        Assertions.assertEquals(1,result);
    }

    @Test
    void test3() {
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcherTrue);

        double result = similarityFinder.calculateJackardSimilarity(new int[]{2}, new int[]{2});

        Assertions.assertEquals(1,result);
    }

    @Test
    void test4() {
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcherTrue);

        double result = similarityFinder.calculateJackardSimilarity(new int[]{}, new int[]{});

        Assertions.assertEquals(1,result);
    }

    @Test
    void test5() {
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcherTrue);
        for (int i = 0; i < 10; i++) {
            double result = similarityFinder.calculateJackardSimilarity(new int[]{}, new int[]{});
            Assertions.assertEquals(1,result);
        }

    }

    @Test
    void test6() {
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcherFalse);
        for (int i = 0; i < 10; i++) {
            double result = similarityFinder.calculateJackardSimilarity(new int[]{}, new int[]{1});
            Assertions.assertEquals(0,result);
        }
    }

    @Test
    void test7() {
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcherFalse);
        for (int i = 0; i < 10; i++) {
            double result = similarityFinder.calculateJackardSimilarity(new int[]{}, new int[]{1});
            Assertions.assertEquals(0,result);
        }
    }

    private int executionCounter = 0;
    SequenceSearcher sequenceSearcherExecutionCounter = (elem, sequence) -> {
        executionCounter++;
        return SearchResult.builder().withFound(true).build();
    };

    @Test
    void test8() {
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcherExecutionCounter);

        similarityFinder.calculateJackardSimilarity(new int[]{2,5,42,5},new int[]{2,5,42,5});
        Assertions.assertEquals(4, executionCounter);
    }

    @Test
    void test9() {
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcherExecutionCounter);

        similarityFinder.calculateJackardSimilarity(new int[]{},new int[]{});
        Assertions.assertEquals(0, executionCounter);
    }
}
