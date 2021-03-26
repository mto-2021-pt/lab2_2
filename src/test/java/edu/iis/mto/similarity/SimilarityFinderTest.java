package edu.iis.mto.similarity;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimilarityFinderTest {

    @Test
    public void shouldReturnOneWhenSequencesAreEmpty() {

        int[] seq1 = {};
        int[] seq2 = {};

        SimilarityFinder similarityFinder = new SimilarityFinder((elem, sequence) -> null);

        double similarity = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(1, similarity);
    }

    @Test
    public void shouldReturnOneWhenNumbersAreTheSame() {

        int[] seq1 = {1, 2, 3};
        int[] seq2 = {1, 2, 3};

        SimilarityFinder similarityFinder = new SimilarityFinder((elem, sequence) -> SearchResult.builder()
                .withFound(true).build());

        double similarity = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(1, similarity);
    }


    @Test
    public void shouldReturnZeroWhenSequencesContainDifferentNumbers() {

        int[] seq1 = {1, 2, 3, 4, 5, 6};
        int[] seq2 = {7, 8, 9, 10, 11, 12};

        SimilarityFinder similarityFinder = new SimilarityFinder((elem, sequence) -> SearchResult.builder()
                .withFound(false).build());

        double similarity = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0, similarity);
    }

    @Test
    public void shouldReturnHalfWhenOneNumberMatchesInSequenceOfThree() {

        int[] seq1 = {1};
        int[] seq2 = {1, 3};

        SearchResult trueSearchResult = SearchResult.builder().withFound(true).build();
        SearchResult falseSearchResult = SearchResult.builder().withFound(false).build();

        SimilarityFinder similarityFinder = new SimilarityFinder(((elem, sequence) -> {
            if (elem == 1) {
                return trueSearchResult;
            } else if (elem == 3) {
                return falseSearchResult;
            } else {
                return null;
            }
        }));

        double similarity = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0.5, similarity);
    }

    @Test
    public void shouldReturnHalfWhenThreeNumbersMatchesInSequenceOfNine() {

        int[] seq1 = {1, 2, 3};
        int[] seq2 = {7, 1, 7, 7, 2, 3};

        SearchResult searchResult = SearchResult.builder().withFound(true).build();

        SimilarityFinder similarityFinder = new SimilarityFinder(((elem, sequence) -> {
            if (elem == 1) {
                return searchResult;
            } else if (elem == 2) {
                return searchResult;
            } else if (elem == 3) {
                return searchResult;
            } else {
                return null;
            }
        }));

        double similarity = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0.5, similarity);
    }


    @Test
    public void shouldReturnZeroWhenOneSequenceIsEmpty() {

        int[] seq1 = {};
        int[] seq2 = {1, 2, 3};

        SimilarityFinder similarityFinder = new SimilarityFinder(((elem, sequence) -> SearchResult.builder()
                .withFound(false).build()));

        double similarity = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0, similarity);
    }

    @Test
    public void shouldReturnZeroWhenSecondSequenceIsEmpty() {

        int[] seq1 = {};
        int[] seq2 = {1, 2, 3};

        SimilarityFinder similarityFinder = new SimilarityFinder(((elem, sequence) -> SearchResult.builder()
                .withFound(false).build()));

        double similarity = similarityFinder.calculateJackardSimilarity(seq2, seq1);
        assertEquals(0, similarity);
    }

    @Test
    public void shouldReturnSevenAfterInvokingSevenTimes() {

        final int[] counter = {0};

        SimilarityFinder similarityFinder =
                new SimilarityFinder(
                        (elem, sequence) -> {
                            counter[0] = counter[0] + 1;
                            return SearchResult.builder().withFound(true).withPosition(0).build();
                        });

        int[] seq1 = {1, 2, 3, 4, 5, 6, 7};
        int[] seq2 = {1, 2, 3, 4, 5, 6, 7};
        similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(counter[0], 7);
    }


}
