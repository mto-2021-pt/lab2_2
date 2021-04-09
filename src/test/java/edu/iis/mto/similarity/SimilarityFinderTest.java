package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

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

    @Test
    public void calculateJackardSimilarity_behaviorTest_firstSeqNull() {
        SimilarityFinder similarityFinder = new SimilarityFinder(
                (elem, sequence) -> SearchResult.builder().withFound(false).withPosition(-1).build());
        int[] seq1 = null;
        int[] seq2 = new int[] {1, 2, 3};

        assertThrows(NullPointerException.class, () -> similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    public void calculateJackardSimilarity_behaviorTest_secondSeqNull() {
        SimilarityFinder similarityFinder = new SimilarityFinder(
                (elem, sequence) -> SearchResult.builder().withFound(false).withPosition(-1).build());
        int[] seq1 = new int[] {1, 2, 3};
        int[] seq2 = null;

        assertThrows(NullPointerException.class, () -> similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    public void calculateJackardSimilarity_behaviorTest_nullSequenceSearcher() {
        SimilarityFinder similarityFinder = new SimilarityFinder(
                (elem, sequence) -> null);
        int[] seq1 = new int[] {1, 2, 3};
        int[] seq2 = new int[] {1, 2, 3};

        assertThrows(NullPointerException.class, () -> similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    public void calculateJackardSimilarity_behaviorTest_sequenceSearcherCalled0Times() throws NoSuchFieldException, IllegalAccessException {
        SequenceSearcher sequenceSearcher = new SequenceSearcher() {
            private int callsCounter = 0;

            @Override public SearchResult search(int elem, int[] sequence) {
                callsCounter++;
                return SearchResult.builder().withFound(false).withPosition(-1).build();
            }
        };
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcher);
        int[] seq1 = new int[] {};
        int[] seq2 = new int[] {};

        similarity = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        Field field = sequenceSearcher.getClass().getDeclaredField("callsCounter");
        field.setAccessible(true);
        int numberOfCalls = field.getInt(sequenceSearcher);
        assertEquals(0, numberOfCalls);
    }

    @Test
    public void calculateJackardSimilarity_behaviorTest_sequenceSearcherCalled4Times() throws NoSuchFieldException, IllegalAccessException {
        SequenceSearcher sequenceSearcher = new SequenceSearcher() {
            private int callsCounter = 0;

            @Override public SearchResult search(int elem, int[] sequence) {
                callsCounter++;
                return SearchResult.builder().withFound(true).withPosition(elem - 1).build();
            }
        };
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcher);
        int[] seq1 = new int[] {1, 2, 3, 4};
        int[] seq2 = new int[] {1, 2, 3, 4, 5};

        similarity = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        Field field = sequenceSearcher.getClass().getDeclaredField("callsCounter");
        field.setAccessible(true);
        int numberOfCalls = field.getInt(sequenceSearcher);
        assertEquals(4, numberOfCalls);
    }
}
