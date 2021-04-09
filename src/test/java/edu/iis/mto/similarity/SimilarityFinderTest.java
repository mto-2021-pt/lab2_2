package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    public SearchResult searchResult;

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

}
