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

}

