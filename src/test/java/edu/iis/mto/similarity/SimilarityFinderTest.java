package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import edu.iis.mto.searcher.TestSequenceSearcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {
    @Test
    void jaccardIndexTestArraysWithTheSameSize() {
        int[] a = {0, 1, 2, 3};
        int[] b = {1, 3, 4, 5};
        int[] searchResults = {0, 1, 0, 0};
        double unique = 7.0;
        double shared = 1.0;
        double exp_res = shared/unique;

        SimilarityFinder sf = new SimilarityFinder(new TestSequenceSearcher(searchResults));
        double res = sf.calculateJackardSimilarity(a, b);
        assertEquals(exp_res, res, 0.0001f);
    }
    @Test
    void jaccardIndexTestArraysWithDifferentSize() {
        int[] a = {0, 1, 2, 3, 4, 5};
        int[] b = {1, 3, 4, 5, 9, 22, 11, 33};
        int[] searchResults = {0, 1, 0, 1, 1, 1};
        double unique = 10.0;
        double shared = 4.0;
        double exp_res = shared/unique;

        SimilarityFinder sf = new SimilarityFinder(new TestSequenceSearcher(searchResults));
        double res = sf.calculateJackardSimilarity(a, b);
        assertEquals(exp_res, res, 0.0001f);
    }
    @Test
    void jaccardIndexTestSameArrays() {
        int[] a = {0, 1, 3, 4, 5};
        int[] b = {0, 1, 3, 4, 5};
        int[] searchResults = {1, 1, 1, 1, 1};
        double unique = 5.0;
        double shared = 5.0;
        double exp_res = shared/unique;

        SimilarityFinder sf = new SimilarityFinder(new TestSequenceSearcher(searchResults));
        double res = sf.calculateJackardSimilarity(a, b);
        assertEquals(exp_res, res, 0.0001f);
    }
    @Test
    void jaccardIndexTestDifferentArrays() {
        int[] a = {0, 1, 3, 4, 5};
        int[] b = {6, 7, 8, 9, 10};
        int[] searchResults = {0, 0, 0, 0, 0};
        double unique = 5.0;
        double shared = 0.0;
        double exp_res = shared/unique;

        SimilarityFinder sf = new SimilarityFinder(new TestSequenceSearcher(searchResults));
        double res = sf.calculateJackardSimilarity(a, b);
        assertEquals(exp_res, res, 0.0001f);
    }
    @Test
    void jaccardIndexTestEmptyArrays() {
        int[] a = { };
        int[] b = { };
        int[] searchResults = { };
        double unique = 1.0;
        double shared = 1.0;
        double exp_res = shared/unique;

        SimilarityFinder sf = new SimilarityFinder(new TestSequenceSearcher(searchResults));
        double res = sf.calculateJackardSimilarity(a, b);
        assertEquals(exp_res, res, 0.0001f);
    }
    @Test
    void sequenceSearcherTestIsFound() {
        int[] a = {1, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] searchResults = {0, 1, 0, 0, 1, 1, 1, 1, 1, 0};
        TestSequenceSearcher seqS = new TestSequenceSearcher(searchResults);
        for (int i = 0; i < a.length; i++) {
            assertEquals(searchResults[i] == 1, seqS.search(a[i], a).isFound());
        }
    }
    @Test
    void sequenceSearcherTestPosition() {
        int[] a = {1, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] searchResults = {0, 1, 0, 0, 1, 1, 1, 1, 1};
        TestSequenceSearcher seqS = new TestSequenceSearcher(searchResults);
        for (int i = 0; i < a.length; i++) {
            assertEquals((searchResults[i] == 1) ? i : 0, seqS.search(a[i], a).getPosition());
        }
    }
    @Test
    void sequenceSearcherTestOutOfBandsAccess() {
        int[] a = {1, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] searchResults = {0, 0, 0};
        TestSequenceSearcher seqS = new TestSequenceSearcher(searchResults);
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            for (int i = 0; i < searchResults.length + 1; i++) {
                seqS.search(a[i], a);
            }
        });
    }
}
