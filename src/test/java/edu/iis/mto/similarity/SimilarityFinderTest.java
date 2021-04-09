package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    public SearchResult searchResult;
    public int counter;

    SequenceSearcher sequenceSearcher = new SequenceSearcher() {
        @Override
        public SearchResult search(int elem, int[] sequence) {
            for (int i = 0; i < sequence.length; i++) {
                if (elem == sequence[i]) {
                    return SearchResult.builder().withPosition(i).withFound(true).build();
                }
            }
            return SearchResult.builder().withPosition(-1).withFound(false).build();
        }
    };

    SequenceSearcher countRerun = new SequenceSearcher() {
        @Override
        public SearchResult search(int elem, int[] sequence) {
            counter++;
            return SearchResult.builder().withFound(false).build();
        }
    };

    @Test
    void returnOneSequencesAreEmptyTest(){
        int[] array1 = {}, array2 = {};
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcher);
        assertEquals(similarityFinder.calculateJackardSimilarity(array1, array2), 1);
    }

    @Test
    void returnZeroFirstSequenceEmptyTest(){
        int[] array1 = {}, array2 = {1, 2, 3, 4, 5};
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcher);
        assertEquals(similarityFinder.calculateJackardSimilarity(array1, array2), 0);
    }

    @Test
    void returnZeroSecondSequenceEmptyTest(){
        int[] array1 = {1, 2, 3, 4, 5}, array2 = {};
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcher);
        assertEquals(similarityFinder.calculateJackardSimilarity(array1, array2), 0);
    }

    @Test
    void returnZeroNotSimilarSequencesTest(){
        int[] array1 = {1, 2, 3, 4, 5}, array2 = {6, 7, 8, 9, 0};
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcher);
        assertEquals(similarityFinder.calculateJackardSimilarity(array1, array2), 0);
    }

    @Test
    void returnValueSimilarSequencesTest(){
        int[] array1 = {1, 2, 3, 4, 5}, array2 = {1, 2, 8, 9, 0};
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcher);
        assertEquals(similarityFinder.calculateJackardSimilarity(array1, array2), 0.25);
    }

    @Test
    void returnValueIdenticalSequencesTest(){
        int[] array1 = {1, 2, 3, 4, 5}, array2 = {1, 2, 3, 4, 5};
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcher);
        assertEquals(similarityFinder.calculateJackardSimilarity(array1, array2), 1);
    }

    @Test
    void returnValueIdenticalSequencesFirstRerunTest(){
        int[] array1 = {1, 2, 3, 4, 5}, array2 = {1};
        SimilarityFinder similarityFinder = new SimilarityFinder(countRerun);
        similarityFinder.calculateJackardSimilarity(array1, array2);
        assertEquals(counter, 5);
    }

    @Test
    void returnValueIdenticalSequencesFirstRerunSwappedTest(){
        int[] array1 = {1}, array2 = {1, 2, 3, 4, 5};
        SimilarityFinder similarityFinder = new SimilarityFinder(countRerun);
        similarityFinder.calculateJackardSimilarity(array1, array2);
        assertEquals(counter, 1);
    }

    @Test
    void returnValueIdenticalSequencesSecondRerunTest(){
        int[] array1 = {1, 2, 3, 4, 5, 6}, array2 = {1, 2};
        SimilarityFinder similarityFinder = new SimilarityFinder(countRerun);
        similarityFinder.calculateJackardSimilarity(array1, array2);
        assertEquals(counter, 6);
    }

    @Test
    void returnValueIdenticalSequencesSecondRerunSwappedTest(){
        int[] array1 = {1, 2}, array2 ={1, 2, 3, 4, 5, 6};
        SimilarityFinder similarityFinder = new SimilarityFinder(countRerun);
        similarityFinder.calculateJackardSimilarity(array1, array2);
        assertEquals(counter, 2);
    }

    @Test
    void returnValueIdenticalSequencesThirdRerunTest(){
        int[] array1 = {1, 2, 3, 4, 5}, array2 = {1, 2, 3, 4};
        SimilarityFinder similarityFinder = new SimilarityFinder(countRerun);
        similarityFinder.calculateJackardSimilarity(array1, array2);
        assertEquals(counter, 5);
    }

    @Test
    void returnValueIdenticalSequencesThirdSwappedRerunTest(){
        int[] array1 = {1, 2, 3, 4}, array2 = {1, 2, 3, 4, 5};
        SimilarityFinder similarityFinder = new SimilarityFinder(countRerun);
        similarityFinder.calculateJackardSimilarity(array1, array2);
        assertEquals(counter, 4);
    }

    @Test
    void returnValueIdenticalSequencesLastRerunTest(){
        int[] array1 = {1, 2, 3, 4, 5}, array2 = {1, 2, 3, 4, 5};
        SimilarityFinder similarityFinder = new SimilarityFinder(countRerun);
        similarityFinder.calculateJackardSimilarity(array1, array2);
        assertEquals(counter, 1);
    }

    //no swapped test is needed
}
