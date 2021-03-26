package edu.iis.mto.similarity;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SimilarityFinderTest {
    SimilarityFinder similarityFinder = new SimilarityFinder(new SequenceSearcher() {
        @Override
        public SearchResult search(int elem, int[] sequence) {
            for(int i = 0; i<sequence.length; i++){
                if(sequence[i] == elem){
                    return SearchResult.builder().withFound(true).withPosition(i).build();
                }
            }
            return SearchResult.builder().withFound(false).build();
        }
    });
    @Test
    void similarityFinderForEmptyArrays(){
        int[] seq1 = {};
        int[] seq2 = {};

        assertThat(similarityFinder.calculateJackardSimilarity(seq1, seq2)==1, Is.is(true));

    }

    @Test
    void similarityFinderForEqualArrays(){
        int[] seq1 = {1,2,3};
        int[] seq2 = {1,2,3};

        assertThat(similarityFinder.calculateJackardSimilarity(seq1, seq2)==1, Is.is(true));

    }

    @Test
    void similarityFinderForSeq1EmptyAndSeq2NotEmpty() {
        int[] seq1 = {};
        int[] seq2 = {1, 2};

        assertThat(similarityFinder.calculateJackardSimilarity(seq1, seq2)==0,Is.is(true));
    }

    @Test
    void similarityFinderForSeq1NotEmptyAndSeq2Empty() {
        int[] seq1 = {1, 2};
        int[] seq2 = {};

        assertThat(similarityFinder.calculateJackardSimilarity(seq1, seq2)==0,Is.is(true));
    }


    @Test
    void similarityFinderForHalfEqualArrays(){
        int[] seq1 = {1 ,2};
        int[] seq2 = {1,2,3,4};

        assertThat(similarityFinder.calculateJackardSimilarity(seq1, seq2)==0.5, Is.is(true));

    }

    @Test
    void similarityFinderForQuarterEqualArrays(){
        int[] seq1 = {1 };
        int[] seq2 = {1,2,3,4};

        assertThat(similarityFinder.calculateJackardSimilarity(seq1, seq2)==0.25, Is.is(true));

    }

    @Test
    public void testForZeroInvokes() throws NoSuchFieldException,IllegalAccessException
    {
        int [] seq1={};
        int [] seq2={};
        int expected_res=0;

        SequenceSearcher sequenceSearcher = new SequenceSearcher() {
            private int count=0;

            @Override
            public SearchResult search(int elem, int[] sequence) {
                count++;
                return SearchResult.builder().withFound(true).build();
            }
        };

        SimilarityFinder similarityFinder= new SimilarityFinder(sequenceSearcher);
        similarityFinder.calculateJackardSimilarity(seq1,seq2);

        assertThat(sequenceSearcher.getClass().getDeclaredField("count").getInt(sequenceSearcher),Is.is(expected_res));
    }

    @Test
    public void testForOneInvoke() throws NoSuchFieldException,IllegalAccessException
    {
        int [] seq1={3};
        int [] seq2={1,2,3,4};
        int expected_res=1;

        SequenceSearcher sequenceSearcher = new SequenceSearcher() {
            private int count=0;

            @Override
            public SearchResult search(int elem, int[] sequence) {
                count++;
                return SearchResult.builder().withFound(true).build();
            }
        };

        SimilarityFinder similarityFinder= new SimilarityFinder(sequenceSearcher);
        similarityFinder.calculateJackardSimilarity(seq1,seq2);

        assertThat(sequenceSearcher.getClass().getDeclaredField("count").getInt(sequenceSearcher),Is.is(expected_res));
    }

    @Test
    public void testForFourInvokes() throws NoSuchFieldException,IllegalAccessException
    {
        int [] seq1={1,2,3,4};
        int [] seq2={1,2,3,4};
        int expected_res=4;

        SequenceSearcher sequenceSearcher = new SequenceSearcher() {
            private int count=0;

            @Override
            public SearchResult search(int elem, int[] sequence) {
                count++;
                return SearchResult.builder().withFound(true).build();
            }
        };

        SimilarityFinder similarityFinder= new SimilarityFinder(sequenceSearcher);
        similarityFinder.calculateJackardSimilarity(seq1,seq2);

        assertThat(sequenceSearcher.getClass().getDeclaredField("count").getInt(sequenceSearcher),Is.is(expected_res));
    }

}
