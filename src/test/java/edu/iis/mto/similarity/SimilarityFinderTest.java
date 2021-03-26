package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    SimilarityFinder similarityFinder = new SimilarityFinder((elem, sequence) -> {
        for(int i = 0; i < sequence.length; i++){
            if(sequence[i] == elem){
                return SearchResult.builder().withFound(true).withPosition(i).build();
            }
        }
        return SearchResult.builder().withFound(false).build();
    });

    @Test
    void testingTheSameArrays(){
        int[] seq1 = {10,20,30,40};
        int[] seq2 = {10,20,30,40};

        assertEquals(1, similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void testingTheSameEmptyArrays(){
        int[] seq1 = {};
        assertEquals(1, similarityFinder.calculateJackardSimilarity(seq1, seq1));
    }

    @Test
    void testingArraysWithDifferentElements(){
        int[] seq1 = {10,20,30};
        int[] seq2 = {40,50,60};
        assertEquals(0, similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void testingArraysWithHalfTheSameElements(){
        int[] seq1 = {10,20,30,40};
        int[] seq2 = {10,30};

        assertEquals(0.5, similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void firstSequenceEmptyAndSecondNot(){
        int[] seq1 = {};
        int[] seq2 = {9,8,7};

        assertEquals(0, similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

    SequenceSearcher sequenceSearcher = new SequenceSearcher() {
        private int counter = 0;
        @Override
        public SearchResult search(int elem, int[] sequence) {
            counter++;
            return SearchResult.builder().withFound(true).build();
        }
    };

    @Test
    public void testingForFiveInvokes() throws NoSuchFieldException, IllegalAccessException {
        int [] seq1={1,2,3,4};
        int [] seq2={66,77,88,99,99};

        SimilarityFinder similarityFinder= new SimilarityFinder(sequenceSearcher);
        similarityFinder.calculateJackardSimilarity(seq2,seq1);
        assertEquals(5, sequenceSearcher.getClass().getDeclaredField("counter").getInt(sequenceSearcher));
    }

    @Test
    public void testingForNoneInvokes() throws NoSuchFieldException,IllegalAccessException {
        int [] seq1={};
        int [] seq2 = {2,4,6,8};

        SimilarityFinder similarityFinder= new SimilarityFinder(sequenceSearcher);
        similarityFinder.calculateJackardSimilarity(seq1,seq2);
        assertEquals(0, sequenceSearcher.getClass().getDeclaredField("counter").getInt(sequenceSearcher));
    }

}
