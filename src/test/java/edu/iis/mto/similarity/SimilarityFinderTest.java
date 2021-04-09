package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class SimilarityFinderTest {


    @Test
    void shouldReturnOneWhenSequencesAreEmpty() {
        int[] seq1 = {};
        int[] seq2 = {};
        SequenceSearcher sequenceSearcher = new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                return null;
            }
        };
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcher);
        double result = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(1.0d, result);
    }

    @Test
    void shouldReturnZeroWhenOneSequenceIsEmpty() {
        int[] seq1 = {};
        int[] seq2 = {1,2,3};
        SequenceSearcher sequenceSearcher = new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                return null;
            }
        };
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcher);
        double result = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0, result);
    }

    @Test
    void shouldReturnZeroWhenSecondSequenceIsEmpty() {
        int[] seq1 = {8,9,2};
        int[] seq2 = {};
        SequenceSearcher sequenceSearcher = new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                return SearchResult.builder().withFound(false).build();
            }
        };
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcher);
        double result = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0, result);
    }

    @Test
    void shouldReturnValueWhenOneElementIsCommon() {
        int[] seq1 = {4,5,6};
        int[] seq2 = {6,7,8};
        SequenceSearcher sequenceSearcher = new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                if(elem == 4) {
                    return SearchResult.builder().withFound(false).build();
                } else if (elem == 5) {
                    return SearchResult.builder().withFound(false).build();
                } else if (elem == 6) {
                    return SearchResult.builder().withFound(true).withPosition(0).build();
                }
                return null;
            }
        };
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcher);
        double result = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0.2, result);
    }

    @Test
    void shouldReturnValueWhenSequencesAreCommon() {
        int[] seq1 = {1,5,9};
        int[] seq2 = {1,5,9};
        SequenceSearcher sequenceSearcher = new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                if(elem == 1) {
                    return SearchResult.builder().withFound(true).withPosition(0).build();
                } else if (elem == 5) {
                    return SearchResult.builder().withFound(true).withPosition(1).build();
                } else if (elem == 9) {
                    return SearchResult.builder().withFound(true).withPosition(2).build();
                }
                return null;
            }
        };
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcher);
        double result = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(1, result);
    }

    @Test
    void shouldReturnZeroWhenSequencesHaveDifferentElements() {
        int[] seq1 = {2,7,9};
        int[] seq2 = {3,4,8};
        SequenceSearcher sequenceSearcher = new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                if(elem == 2) {
                    return SearchResult.builder().withFound(false).build();
                } else if (elem == 7) {
                    return SearchResult.builder().withFound(false).build();
                } else if (elem == 9) {
                    return SearchResult.builder().withFound(false).build();
                }
                return null;
            }
        };
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcher);
        double result = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0, result);
    }

    @Test
    void shouldInvokeSearchWithElementsFromFirstSequence() {
        int[] seq1 = {1,17,91};
        int[] seq2 = {13,14,18};
        List<Integer> passedElements = new ArrayList<Integer>();
        SequenceSearcher sequenceSearcher = new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                passedElements.add(elem);
                if(elem == 1) {
                    return SearchResult.builder().withFound(false).build();
                } else if (elem == 17) {
                    return SearchResult.builder().withFound(false).build();
                } else if (elem == 91) {
                    return SearchResult.builder().withFound(false).build();
                }
                return null;
            }

        };
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcher);
        double result = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(3, passedElements.size());
        assertEquals(true, passedElements.contains(1));
        assertEquals(true, passedElements.contains(17));
        assertEquals(true, passedElements.contains(91));
    }

    @Test
    void shouldInvokeSearchWithElementsInSecondSequences() {
        int[] seq1 = {2};
        int[] seq2 = {10,11};
        List<Integer> passedSequence = new ArrayList<Integer>();
        SequenceSearcher sequenceSearcher = new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                passedSequence.addAll(Arrays.stream(sequence).boxed().collect(Collectors.toList()));
                if(elem == 2) {
                    return SearchResult.builder().withFound(false).build();
                }
                return null;
            }

        };
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcher);
        double result = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(2, passedSequence.size());
        assertEquals(true, passedSequence.contains(10));
        assertEquals(true, passedSequence.contains(11));
    }
    @Test
    void shouldInvokeSearchFiveTimes() {
        int[] seq1 = {6,7,8,9,0};
        int[] seq2 = {14,15};
        int[] counter = {0};

        SequenceSearcher sequenceSearcher = new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                counter[0]++;
                return SearchResult.builder().withFound(false).build();
            }

        };
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcher);
        double result = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(5, counter[0]);

    }

}
