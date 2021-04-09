package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {
    private int executionCounter = 0;
    SequenceSearcher sequenceSearcherTrue = (elem, sequence) -> SearchResult.builder().withFound(true).build();
    SequenceSearcher sequenceSearcherFalse = (elem, sequence) -> SearchResult.builder().withFound(false).build();
    SequenceSearcher sequenceSearcherExecutionCounter = (elem, sequence) -> {
        executionCounter++;
        return SearchResult.builder().withFound(true).build();
    };

    @Test
    void differentArrays() {
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcherFalse);

        double result = similarityFinder.calculateJackardSimilarity(new int[]{5}, new int[]{1});

        Assertions.assertEquals(0,result);
    }

    @Test
    void sameArrays() {
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcherTrue);

        double result = similarityFinder.calculateJackardSimilarity(new int[]{2}, new int[]{2});

        Assertions.assertEquals(1,result);
    }

    @Test
    void sameArraysMultipleTimes() {
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcherTrue);
        for (int i = 0; i < 10; i++) {
            double result = similarityFinder.calculateJackardSimilarity(new int[]{2}, new int[]{2});
            Assertions.assertEquals(1,result);
        }
    }

    @Test
    void twoEmptyArrays() {
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcherTrue);

        double result = similarityFinder.calculateJackardSimilarity(new int[]{}, new int[]{});

        Assertions.assertEquals(1,result);
    }

    @Test
    void twoEmptyArraysMultipleTimes() {
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcherTrue);
        for (int i = 0; i < 10; i++) {
            double result = similarityFinder.calculateJackardSimilarity(new int[]{}, new int[]{});
            Assertions.assertEquals(1,result);
        }

    }

    @Test
    void oneEmptyArrayMultipleTimes() {
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcherFalse);
        for (int i = 0; i < 10; i++) {
            double result = similarityFinder.calculateJackardSimilarity(new int[]{}, new int[]{1});
            Assertions.assertEquals(0,result);
        }
    }

    @Test
    void nonEmptyArraysMultipleTimes() {
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcherTrue);
        for (int i = 0; i < 10; i++) {
            double result = similarityFinder.calculateJackardSimilarity(new int[]{1,2}, new int[]{1,3});
            Assertions.assertEquals(1,result);
        }
    }



    @Test
    void numberOfExecutionsNonEmptyArrays() {
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcherExecutionCounter);

        similarityFinder.calculateJackardSimilarity(new int[]{2,5,42,5},new int[]{2,5,42,5});
        Assertions.assertEquals(4, executionCounter);
    }

    @Test
    void numberOfExecutionsEmptyArrays() {
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcherExecutionCounter);

        similarityFinder.calculateJackardSimilarity(new int[]{},new int[]{});
        Assertions.assertEquals(0, executionCounter);
    }
}
