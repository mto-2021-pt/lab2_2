package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class SimilarityFinderTest {

    class SequenceSearcherTest implements SequenceSearcher {
        boolean[] expectedResult = {};
        int index = 0;
        List<Integer> searchedElems = new ArrayList<>();

        public int getNoOfCalls() {
            return noOfCalls;
        }

        int noOfCalls = 0;

        SequenceSearcherTest(boolean[] expectedResult) {
            this.expectedResult = expectedResult;
        }

        @Override
        public SearchResult search(int elem, int[] sequence) {
            noOfCalls++;
            searchedElems.add(elem);
            return SearchResult.builder().withFound(expectedResult[index++]).build();
        }

        public int[] getSearchedElems() {
            return searchedElems.stream().mapToInt(i->i).toArray();
        }
    }

  @Test
  void calculateJackardSimilarityWhenSeq1And2IsZero() {
      boolean[] searchResults = {};
      SequenceSearcherTest searcher = new SequenceSearcherTest(searchResults);
      int[] seq1 = {};
      int[] seq2 = {};
      SimilarityFinder similarityFinder = new SimilarityFinder(searcher);
      assertTrue(1.0d == similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void calculateJackardSimilarityWhenSeq1EqualsSeq2() {
        boolean[] searchResults = {true, true, true};
        SequenceSearcherTest searcher = new SequenceSearcherTest(searchResults);
        int[] seq1 = {1, 2, 3};
        int[] seq2 = {1, 2, 3};
        SimilarityFinder similarityFinder = new SimilarityFinder(searcher);
        assertTrue(1.0d == similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void calculateJackardSimilarityWhenSeq1ElementsNotEqualsSeq2Elements() {
        boolean[] searchResults = {false, false, false};
        SequenceSearcherTest searcher = new SequenceSearcherTest(searchResults);
        int[] seq1 = {1, 2, 3};
        int[] seq2 = {7, 8, 9};
        SimilarityFinder similarityFinder = new SimilarityFinder(searcher);
        assertTrue(0.0d == similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void calculateJackardSimilarityWhenSeq1OneElementsEqualsSeq2Element() {
        boolean[] searchResults = {true, false, false};
        SequenceSearcherTest searcher = new SequenceSearcherTest(searchResults);
        int[] seq1 = {1, 2, 3};
        int[] seq2 = {1, 8, 9, 0};
        SimilarityFinder similarityFinder = new SimilarityFinder(searcher);
        assertTrue(0.16666666666666666d == similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void calculateJackardSimilarityNumberOfSearchCalls() {
        boolean[] searchResults = {true, false, false};
        SequenceSearcherTest searcher = new SequenceSearcherTest(searchResults);
        int[] seq1 = {1, 2, 3};
        int[] seq2 = {1, 8, 9, 0};
        SimilarityFinder similarityFinder = new SimilarityFinder(searcher);
        similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(seq1.length, searcher.getNoOfCalls());
    }

    @Test
    void calculateJackardSimilarityFoundElements() {
        boolean[] searchResults = {true, false, false};
        SequenceSearcherTest searcher = new SequenceSearcherTest(searchResults);
        int[] seq1 = {1, 2, 3};
        int[] seq2 = {1, 8, 9, 0};
        SimilarityFinder similarityFinder = new SimilarityFinder(searcher);
        similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertTrue(Arrays.equals(seq1, searcher.getSearchedElems()));
    }
}