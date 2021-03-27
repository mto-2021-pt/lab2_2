package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class SimilarityFinderTest {

    @Test
    void similarityFinderShouldReturnOneForTwoEmptySequences() {
        int[] states = {};
        int[] seq1 = {};
        int[] seq2 = {};

        SequenceSearcherSimulator ss = new SequenceSearcherSimulator(states);
        SimilarityFinder sf = new SimilarityFinder(ss);

        assertEquals(1.0d, sf.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void similarityFinderShouldReturnOneForTwoEqualSequences() {
        int[] states = {1, 1, 1, 1};
        int[] seq1 = {7, 6, 9, 15};
        int[] seq2 = {7, 6, 9, 15};

        SequenceSearcherSimulator ss = new SequenceSearcherSimulator(states);
        SimilarityFinder sf = new SimilarityFinder(ss);

        assertEquals(1.0d, sf.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void similarityFinderShouldReturnZeroForTwoDifferentSequences() {
        int[] states = {0, 0, 0};
        int[] seq1 = {13, 55, 103};
        int[] seq2 = {-4, 54, 0};

        SequenceSearcherSimulator ss = new SequenceSearcherSimulator(states);
        SimilarityFinder sf = new SimilarityFinder(ss);

        assertEquals(0.0d, sf.calculateJackardSimilarity(seq1, seq2));
    }

}

