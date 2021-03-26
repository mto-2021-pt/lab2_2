package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    private double similarity;

    @Test
    public void calculateJackardSimilarity_stateTest_bothSeqEmpty() {
        SimilarityFinder similarityFinder = new SimilarityFinder(
                (elem, sequence) -> SearchResult.builder().withFound(false).withPosition(-1).build());
        int[] seq1 = new int[] {};
        int[] seq2 = new int[] {};

        similarity = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(1.0d, similarity);
    }

    @Test
    public void calculateJackardSimilarity_stateTest_firstSeqEmpty() {
        SimilarityFinder similarityFinder = new SimilarityFinder(
                (elem, sequence) -> SearchResult.builder().withFound(false).withPosition(-1).build());
        int[] seq1 = new int[] {};
        int[] seq2 = new int[] {1, 2, 3};

        similarity = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0.0d, similarity);
    }

    @Test
    public void calculateJackardSimilarity_stateTest_secondSeqEmpty() {
        SimilarityFinder similarityFinder = new SimilarityFinder(
                (elem, sequence) -> SearchResult.builder().withFound(false).withPosition(-1).build());
        int[] seq1 = new int[] {1, 2, 3};
        int[] seq2 = new int[] {};

        similarity = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0.0d, similarity);
    }

    @Test
    public void calculateJackardSimilarity_stateTest_completelyDiffSeq() {
        SimilarityFinder similarityFinder = new SimilarityFinder(
                (elem, sequence) -> SearchResult.builder().withFound(false).withPosition(-1).build());
        int[] seq1 = new int[] {1, 2, 3};
        int[] seq2 = new int[] {4, 5, 6};

        similarity = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0.0d, similarity);
    }

    @Test
    public void calculateJackardSimilarity_stateTest_identicalSeq() {
        SimilarityFinder similarityFinder = new SimilarityFinder(
                (elem, sequence) -> SearchResult.builder().withFound(true).withPosition(elem - 1).build());
        int[] seq1 = new int[] {1, 2, 3};
        int[] seq2 = new int[] {1, 2, 3};

        similarity = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(1.0d, similarity);
    }

    @Test
    public void calculateJackardSimilarity_stateTest_differentSeq_oneElemMatch() {
        SimilarityFinder similarityFinder = new SimilarityFinder(new SequenceSearcher() {

            @Override public SearchResult search(int elem, int[] sequence) {
                if (elem == 1) {
                    return SearchResult.builder().withFound(true).withPosition(elem - 1).build();
                } else {
                    return SearchResult.builder().withFound(false).withPosition(-1).build();
                }
            }
        });
        int[] seq1 = new int[] {1};
        int[] seq2 = new int[] {1, 2, 3, 4};

        similarity = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0.25d, similarity);
    }
}
