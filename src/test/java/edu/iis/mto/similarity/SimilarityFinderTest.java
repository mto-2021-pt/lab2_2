package edu.iis.mto.similarity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class SimilarityFinderTest {


    SimilarityFinder sf;
    int[] sequence = new int[]{1,3,7,12};

    @Test
    void nullsTest(){
       assertThrows(NullPointerException.class,()->{
           sf.calculateJackardSimilarity(null,null);
        });
    }

    @Test
    void firstNullTest(){
        assertThrows(NullPointerException.class,()->{
            sf.calculateJackardSimilarity(sequence,null);
        });
    }

    @Test
    void secondNullTest(){
        assertThrows(NullPointerException.class,()->{
            sf.calculateJackardSimilarity(null,sequence);
        });
    }


}
