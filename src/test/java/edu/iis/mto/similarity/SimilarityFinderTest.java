package edu.iis.mto.similarity;

import edu.iis.mto.searcher.SearchResult;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void behaviour_test_1() {
        int[] arr1 = {1, 2, 3, 4};
        int[] arr2 = {2, 3, 4, 5};

        int[] search_tests = {
                0, 1, 1, 1
        };

        SearchResult[] search_results = Arrays.stream(search_tests).mapToObj(e -> e == 0 ? sr_not_found : sr_found).toArray(SearchResult[]::new);

        BehaviourTestSearcher searcher = new BehaviourTestSearcher(search_results);
        SimilarityFinder finder = new SimilarityFinder(searcher);

        double result = finder.calculateJackardSimilarity(arr1, arr2);
        double expected_result = 0.6;

        assertEquals(expected_result, result, delta);

        int expected_search_calls = 4;
        assertEquals(expected_search_calls, searcher.getNumberOfSearchCalls());

        int i = 0;
        int[] expected_elements = {1, 2, 3, 4};
        for(int number : searcher.getSearchedElements())
            assertEquals(arr1[i++], number);

        i = 0;
        int[][] expected_collections = {arr2, arr2, arr2, arr2};
        for(int[] arr : searcher.getSearchedCollections())
            assertTrue(expected_collections[i++] == arr);
    }


    @Test
    void behaviour_test_2() {
        int[] arr = {};
        SearchResult[] search_results = {};

        BehaviourTestSearcher searcher = new BehaviourTestSearcher(search_results);
        SimilarityFinder finder = new SimilarityFinder(searcher);

        double result = finder.calculateJackardSimilarity(arr, arr);
        double expected_result = 1.0;

        assertEquals(expected_result, result, delta);

        int expected_search_calls = 0;
        assertEquals(expected_search_calls, searcher.getNumberOfSearchCalls());
    }


    @Test
    void behaviour_test_3() {
        int[] arr1 = {1, 3, 9, 18};
        int[] arr2 = {3, 9, 100, 200, 300, 1000};

        int[] search_tests = {
                0, 1, 1, 0
        };

        SearchResult[] search_results = Arrays.stream(search_tests).mapToObj(e -> e == 0 ? sr_not_found : sr_found).toArray(SearchResult[]::new);


        BehaviourTestSearcher searcher = new BehaviourTestSearcher(search_results);
        SimilarityFinder finder = new SimilarityFinder(searcher);

        double result = finder.calculateJackardSimilarity(arr1, arr2);
        double expected_result = 0.25;

        assertEquals(expected_result, result, delta);

        int expected_search_calls = 4;
        assertEquals(expected_search_calls, searcher.getNumberOfSearchCalls());

        int i = 0;
        int[] expected_elements = {1, 3, 9, 18};
        for(int number : searcher.getSearchedElements())
            assertEquals(arr1[i++], number);

        i = 0;
        int[][] expected_collections = {arr2, arr2, arr2, arr2};
        for(int[] arr : searcher.getSearchedCollections())
            assertTrue(expected_collections[i++] == arr);
    }

}
