package edu.iis.mto.similarity;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    public int counter=0;
    
    final SequenceSearcher simpleSeqSearcher = (elem, sequence) -> {
        for (int i = 0; i < sequence.length; i++) {
            if (elem == sequence[i]) {
                return SearchResult.builder().withPosition(i).withFound(true).build();
            }
        }
        return SearchResult.builder().withPosition(-1).withFound(false).build();
    };
    
    final SequenceSearcher mockCounter = (elem, sequence) -> {
        counter++;
        return SearchResult.builder().withFound(true).withPosition(0).build();
    };
    
    
    @Test
    void test_SimilarityFinder_bothnull() {
        
        int[] seq1 = {};
        int[] seq2={};
        SimilarityFinder finder = new SimilarityFinder(simpleSeqSearcher);
        double res = finder.calculateJackardSimilarity(seq1,seq2);
        assertEquals(res,1.0);
    }
    
    @Test
    void test_SimilarityFinder_firstnull() {
        
        int[] seq1 ={};
        int[] seq2={1};
        SimilarityFinder finder = new SimilarityFinder(simpleSeqSearcher);
        double res = finder.calculateJackardSimilarity(seq1,seq2);
        assertEquals(res,0.0);
    }
    
    @Test
    void test_SimilarityFinder_secondnull() {
        
        int[] seq1 = {1};
        int[] seq2 = {};
        SimilarityFinder finder = new SimilarityFinder(simpleSeqSearcher);
        double res = finder.calculateJackardSimilarity(seq1,seq2);
        assertEquals(res, 0.0);
    }
    
    @Test
    void test_SimilarityFinder_firstsmall_secondbig() {
        int[] seq1 = {1};
        int[] seq2={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
        SimilarityFinder finder = new SimilarityFinder(simpleSeqSearcher);
        double res = finder.calculateJackardSimilarity(seq1,seq2);
        assertEquals(res, 0.0625);
    }
    
    @Test
    void test_SimilarityFinder_firstnull_secondbig() {
        int[] seq1 = {};
        int[] seq2={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
        SimilarityFinder finder = new SimilarityFinder(simpleSeqSearcher);
        double res = finder.calculateJackardSimilarity(seq1,seq2);
        assertEquals(res, 0.0);
    }
    
    @Test
    void test_SimilarityFinder_firstbig_secondsmall() {
        int[] seq1 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
        int[] seq2={1};
        SimilarityFinder finder = new SimilarityFinder(simpleSeqSearcher);
        double res = finder.calculateJackardSimilarity(seq1,seq2);
        assertEquals(res, 0.0625);
    }
    
    @Test
    void test_SimilarityFinder_firstbig_secondnull() {
        int[] seq1 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
        int[] seq2={};
        SimilarityFinder finder = new SimilarityFinder(simpleSeqSearcher);
        double res = finder.calculateJackardSimilarity(seq1,seq2);
        assertEquals(res, 0.0);
    }
    
    @Test
    void test_SimilarityFinder_examplenumbers_1() {
        int[] seq1 = {1};
        int[] seq2={1,2};
        SimilarityFinder finder = new SimilarityFinder(simpleSeqSearcher);
        double res = finder.calculateJackardSimilarity(seq1,seq2);
        assertEquals(res,0.5);
    }
    
    @Test
    void test_SimilarityFinder_examplenumbers_2() {
        int[] seq1 = {1};
        int[] seq2={1,3,6,9};
        SimilarityFinder finder = new SimilarityFinder(simpleSeqSearcher);
        double res = finder.calculateJackardSimilarity(seq1,seq2);
        assertEquals(res,0.25);
    }
    
    @Test
    void test_SimilarityFinder__examplenumbers_3() {
        int[] seq1 = {1,2};
        int[] seq2={1,2,6,9};
        SimilarityFinder finder = new SimilarityFinder(simpleSeqSearcher);
        double res = finder.calculateJackardSimilarity(seq1,seq2);
        assertEquals(res,0.5);
    }
    
    @Test
    void test_SimilarityFinder__examplenumbers_4() {
        int[] seq1 = {1,3,4};
        int[] seq2={1,3,6,9};
        SimilarityFinder finder = new SimilarityFinder(simpleSeqSearcher);
        double res = finder.calculateJackardSimilarity(seq1,seq2);
        assertEquals(res,0.4);
    }
    
    @Test
    void test_SimilarityFinder_counter_1() {
        
        int[] seq1 = {};
        int[] seq2={};
        counter=0;
        SimilarityFinder finder = new SimilarityFinder(mockCounter);
        finder.calculateJackardSimilarity(seq1,seq2);
        
        assertEquals(counter,0);
    }
    
    @Test
    void test_SimilarityFinder_counter_2() {
        
        int[] seq1 = {1,2,3,4,5};
        int[] seq2={};
        counter=0;
        SimilarityFinder finder = new SimilarityFinder(mockCounter);
        finder.calculateJackardSimilarity(seq1,seq2);
        
        assertEquals(counter,5);
    }
    
    @Test
    void test_SimilarityFinder_counter_3() {
        
        int[] seq1 = {1,2,3,4,5,6,7,8,9,10};
        int[] seq2={1};
        counter=0;
        SimilarityFinder finder = new SimilarityFinder(mockCounter);
        finder.calculateJackardSimilarity(seq1,seq2);
        
        assertEquals(counter,10);
    }
    
    @Test
    void test_SimilarityFinder_counter_4() {
        
        int[] seq1 = {1};
        int[] seq2={1,2,3,4,5,6,7,8,9,10};
        counter=0;
        SimilarityFinder finder = new SimilarityFinder(mockCounter);
        finder.calculateJackardSimilarity(seq1,seq2);
        
        assertEquals(counter,1);
    }
}
