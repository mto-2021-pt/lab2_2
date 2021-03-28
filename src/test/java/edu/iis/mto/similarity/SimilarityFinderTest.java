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

        Assertions.assertEquals(0,result);
    }
}
