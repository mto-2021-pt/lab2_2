package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class SimilarityFinderTest {
    // State tests
    @Test
    void similarityFinderShouldReturnOneForTwoEmptySequences() {
        int[] states = {};
        int[] seq1 = {};
        int[] seq2 = {};

        StateTestingSequenceSearcher ss = new StateTestingSequenceSearcher(states);
        SimilarityFinder sf = new SimilarityFinder(ss);

        assertEquals(1.0d, sf.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void similarityFinderShouldReturnOneForTwoEqualSequences() {
        int[] states = {1, 1, 1, 1};
        int[] seq1 = {7, 6, 9, 15};
        int[] seq2 = {7, 6, 9, 15};

        StateTestingSequenceSearcher ss = new StateTestingSequenceSearcher(states);
        SimilarityFinder sf = new SimilarityFinder(ss);

        assertEquals(1.0d, sf.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void similarityFinderShouldReturnZeroForTwoDifferentSequences() {
        int[] states = {0, 0, 0};
        int[] seq1 = {13, 55, 103};
        int[] seq2 = {-4, 54, 0};

        StateTestingSequenceSearcher ss = new StateTestingSequenceSearcher(states);
        SimilarityFinder sf = new SimilarityFinder(ss);

        assertEquals(0.0d, sf.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void similarityFinderShouldReturnHalfForTwoHalfEqualSequences() {
        int[] states = {0, 1, 1};
        int[] seq1 = {1, 2, 3};
        int[] seq2 = {2, 3, 4};

        StateTestingSequenceSearcher ss = new StateTestingSequenceSearcher(states);
        SimilarityFinder sf = new SimilarityFinder(ss);

        assertEquals(0.5d, sf.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void similarityFinderShouldReturnOneThirdForSequencesOfDifferentLengthEqualInTwoOutOfSixElements() {
        int[] states = {0, 1, 1};
        int[] seq1 = {1, 2, 3};
        int[] seq2 = {0, 3, 2, 7, 9};

        StateTestingSequenceSearcher ss = new StateTestingSequenceSearcher(states);
        SimilarityFinder sf = new SimilarityFinder(ss);

        assertEquals(1.0d / 3, sf.calculateJackardSimilarity(seq1, seq2));
    }

    // Behavior tests
    @Test
    void searchMethodShouldBeCalledZeroTimesForEmptySequences() {
        int[] states = {};
        int[] seq1 = {};
        int[] seq2 = {};

        BehaviorTestingSequenceSearcher ss = new BehaviorTestingSequenceSearcher(states);
        SimilarityFinder sf = new SimilarityFinder(ss);

        sf.calculateJackardSimilarity(seq1, seq2);

        assertEquals(0, ss.getNumberOfSearchCalls());
    }

    @Test
    void searchMethodShouldBeCalledOneTimeForTheFirstSequenceOfLengthOne() {
        int[] states = {0};
        int[] seq1 = {3};
        int[] seq2 = {4, 5, -3};

        BehaviorTestingSequenceSearcher ss = new BehaviorTestingSequenceSearcher(states);
        SimilarityFinder sf = new SimilarityFinder(ss);

        sf.calculateJackardSimilarity(seq1, seq2);

        assertEquals(1, ss.getNumberOfSearchCalls());
    }

    @Test
    void searchMethodShouldBeCalledFourTimesForTheFirstSequenceOfLengthFour() {
        int[] states = {0, 1, 1, 0};
        int[] seq1 = {3, 4, 5, 2};
        int[] seq2 = {4, 5, -3};

        BehaviorTestingSequenceSearcher ss = new BehaviorTestingSequenceSearcher(states);
        SimilarityFinder sf = new SimilarityFinder(ss);

        sf.calculateJackardSimilarity(seq1, seq2);

        assertEquals(4, ss.getNumberOfSearchCalls());
    }

    @Test
    void similarityFinderShouldIncludeEveryElementOfTheFirstSequence() {
        int[] states = {0, 0, 0, 0};
        int[] seq1 = {7, 2, 1, 5};
        int[] seq2 = {};

        ArrayList<Integer> expectedElements = new ArrayList<>();

        for (int element : seq1) {
            expectedElements.add(element);
        }

        BehaviorTestingSequenceSearcher ss = new BehaviorTestingSequenceSearcher(states);
        SimilarityFinder sf = new SimilarityFinder(ss);

        sf.calculateJackardSimilarity(seq1, seq2);

        assertEquals(expectedElements, ss.getIncludedElements());
    }

}

