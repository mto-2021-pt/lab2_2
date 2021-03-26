package edu.iis.mto.similarity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimilarityFinderTest {

    SimilarityFinder sf;
    Search mock;
    int[] emptySequence = new int[]{};
    int[] sequence = new int[]{1,3,7,12};

    @BeforeEach
    void setup(){
        mock = new Search();
        sf = new SimilarityFinder(mock);
    }

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

    @Test
    void emptySequencesTest(){
        assertTrue(sf.calculateJackardSimilarity(emptySequence,emptySequence)==1);
    }

    @Test
    void firstEmptySequence(){
        assertTrue(sf.calculateJackardSimilarity(emptySequence,sequence)==0);
    }

    @Test
    void secondEmptySequence(){
        assertTrue(sf.calculateJackardSimilarity(sequence,emptySequence)==0);
    }

}
