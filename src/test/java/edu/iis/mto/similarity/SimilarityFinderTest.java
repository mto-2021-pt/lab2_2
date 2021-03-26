package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class SimilarityFinderTest {

    private SearchResult sr_not_found = SearchResult.builder().withFound(false).build();
    private SearchResult sr_found = SearchResult.builder().withFound(true).build();
    private double delta = 1e-6;

    @Test
    void test1() {
        int[] arr1 = {1, 2, 3, 4};
        int[] arr2 = {2, 3, 4, 5};

        int[] search_tests = {
                0, 1, 1, 1
        };

        SearchResult[] search_results = Arrays.stream(search_tests).mapToObj(e -> e == 0 ? sr_not_found : sr_found).toArray(SearchResult[]::new);

        SimilarityFinder finder = new SimilarityFinder(new StateTestSearcher(search_results));
        double result = finder.calculateJackardSimilarity(arr1, arr2);
        double expected = 0.6;

        assertEquals(expected, result, delta);
    }


    @Test
    void test2() {
        int[] arr = {};

        SearchResult[] search_results = {};

        SimilarityFinder finder = new SimilarityFinder(new StateTestSearcher(search_results));
        double result = finder.calculateJackardSimilarity(arr, arr);
        double expected = 1.0;

        assertEquals(expected, result, delta);
    }


    @Test
    void test3() {
        int[] arr1 = {1, 3, 9, 18};
        int[] arr2 = {3, 9, 100, 200, 300, 1000};

        int[] search_tests = {
                0, 1, 1, 0
        };

        SearchResult[] search_results = Arrays.stream(search_tests).mapToObj(e -> e == 0 ? sr_not_found : sr_found).toArray(SearchResult[]::new);

        SimilarityFinder finder = new SimilarityFinder(new StateTestSearcher(search_results));
        double result = finder.calculateJackardSimilarity(arr1, arr2);
        double expected = 0.25;

        assertEquals(expected, result, delta);
    }

}
