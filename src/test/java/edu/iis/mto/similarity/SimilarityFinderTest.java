package edu.iis.mto.similarity;

import edu.iis.mto.searcher.SearchResult;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimilarityFinderTest {

    private final SearchResult NOT_FOUND = SearchResult.builder().withFound(false).build();
    private final SearchResult FOUND = SearchResult.builder().withFound(true).build();

    // Condition tests

    @Test
    void EmptySequences() {
        int[] seqOne = {};
        int[] seqTwo = {};

        List<SearchResult> results = new ArrayList<>();
        SimilarityFinder finder = new SimilarityFinder(new TestSearcher(results));
        double result = finder.calculateJackardSimilarity(seqOne, seqTwo);
        assertEquals(1, result);
    }

    @Test
    void SequencesWithSameLength_NoSimilarValues() {
        int[] seqOne = {2, 4, 6, 8};
        int[] seqTwo = {1, 3, 5, 7};

        List<SearchResult> results = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            results.add(NOT_FOUND);
        }
        SimilarityFinder finder = new SimilarityFinder(new TestSearcher(results));
        double result = finder.calculateJackardSimilarity(seqOne, seqTwo);
        assertEquals(0, result);
    }

    @Test
    void SequencesWithFourElements_OneSimilarValue() {
        int[] seqOne = {2, 4, 6, 8};
        int[] seqTwo = {2, 3, 5, 7};

        int[] search = {1, 0, 0, 0};
        List<SearchResult> results = Arrays.stream(search)
                .mapToObj(o -> o == 1 ? FOUND : NOT_FOUND)
                .collect(Collectors.toList());

        SimilarityFinder finder = new SimilarityFinder(new TestSearcher(results));
        double result = finder.calculateJackardSimilarity(seqOne, seqTwo);
        double expected = 1.0 / 7;
        assertEquals(expected, result);
    }

    @Test
    void SequencesWithFourElements_TwoSimilarValues() {
        int[] seqOne = {2, 4, 6, 8};
        int[] seqTwo = {4, 5, 8, 10};

        int[] search = {0, 1, 0, 1};
        List<SearchResult> results = Arrays.stream(search)
                .mapToObj(o -> o == 1 ? FOUND : NOT_FOUND)
                .collect(Collectors.toList());

        SimilarityFinder finder = new SimilarityFinder(new TestSearcher(results));
        double result = finder.calculateJackardSimilarity(seqOne, seqTwo);
        double expected = 2.0 / 6;
        assertEquals(expected, result);
    }

    @Test
    void SequencesWithFourElements_FourSimilarValues() {
        int[] seqOne = {2, 4, 6, 8};
        int[] seqTwo = {2, 4, 6, 8};

        List<SearchResult> results = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            results.add(FOUND);
        }

        SimilarityFinder finder = new SimilarityFinder(new TestSearcher(results));
        double result = finder.calculateJackardSimilarity(seqOne, seqTwo);
        assertEquals(1, result);
    }

    @Test
    void SequencesWithDifferentLengths_ThreeSimilarValues() {
        int[] seqOne = {2, 6, 10, 16, 20};
        int[] seqTwo = {1, 2, 5, 10, 14, 15, 20, 24, 30};

        int[] search = {1, 0, 1, 0, 1};
        List<SearchResult> results = Arrays.stream(search)
                .mapToObj(o -> o == 1 ? FOUND : NOT_FOUND)
                .collect(Collectors.toList());

        SimilarityFinder finder = new SimilarityFinder(new TestSearcher(results));
        double result = finder.calculateJackardSimilarity(seqOne, seqTwo);
        double expected = 3.0 / 11;
        assertEquals(expected, result);
    }

    @Test
    void SequencesWithDifferentLengths_FourSimilarValues() {
        int[] seqOne = {1, 2, 5, 10, 14, 15, 20, 24, 30};
        int[] seqTwo = {2, 6, 10, 16, 20, 30};

        int[] search = {0, 1, 0, 1, 0, 0, 1, 0, 1};
        List<SearchResult> results = Arrays.stream(search)
                .mapToObj(o -> o == 1 ? FOUND : NOT_FOUND)
                .collect(Collectors.toList());

        SimilarityFinder finder = new SimilarityFinder(new TestSearcher(results));
        double result = finder.calculateJackardSimilarity(seqOne, seqTwo);
        double expected = 4.0 / 11;
        assertEquals(expected, result);
    }

}
