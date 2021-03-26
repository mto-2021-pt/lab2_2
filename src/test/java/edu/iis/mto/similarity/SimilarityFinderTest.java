package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.*;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {



    @Test
    public void twoEmptySequence() {

        SimilarityFinder similarityFinder = new SimilarityFinder(new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                for(int i=0;i<sequence.length;i++)
                {
                    if(sequence[i]==elem)
                    {
                        return SearchResult.builder().withFound(true).withPosition(i).build();
                    }
                }
                return SearchResult.builder().withFound(false).build();
            }
        });

        int [] seq1={};
        int [] seq2={};

        assertEquals(1.0d,similarityFinder.calculateJackardSimilarity(seq1,seq2));
    }

    @Test
    public void twoEqualsSequence()
    {

        SimilarityFinder similarityFinder = new SimilarityFinder(new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                for(int i=0;i<sequence.length;i++)
                {
                    if(sequence[i]==elem)
                    {
                        return SearchResult.builder().withFound(true).withPosition(i).build();
                    }
                }
                return SearchResult.builder().withFound(false).build();
            }
        });

        int [] seq1={1,2,3,4};
        int [] seq2={1,2,3,4};

        assertEquals(1,similarityFinder.calculateJackardSimilarity(seq1,seq2));
    }

    @Test
    public void twoDifferentSequence()
    {
        SimilarityFinder similarityFinder = new SimilarityFinder(new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                for(int i=0;i<sequence.length;i++)
                {
                    if(sequence[i]==elem)
                    {
                        return SearchResult.builder().withFound(true).withPosition(i).build();
                    }
                }
                return SearchResult.builder().withFound(false).build();
            }
        });

        int [] seq1={1,2,3,4};
        int [] seq2={9,2,8,4};

        assertNotEquals(1,similarityFinder.calculateJackardSimilarity(seq1,seq2));
    }

    @Test
    public void oneEmptySequence()
    {
        SimilarityFinder similarityFinder = new SimilarityFinder(new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                for(int i=0;i<sequence.length;i++)
                {
                    if(sequence[i]==elem)
                    {
                        return SearchResult.builder().withFound(true).withPosition(i).build();
                    }
                }
                return SearchResult.builder().withFound(false).build();
            }
        });

        int [] seq1={1,2,3,4};
        int [] seq2={};

        assertEquals(0,similarityFinder.calculateJackardSimilarity(seq1,seq2));
    }

    @Test
    public void twoHalfEqualsSequence()
    {
        SimilarityFinder similarityFinder = new SimilarityFinder(new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                for(int i=0;i<sequence.length;i++)
                {
                    if(sequence[i]==elem)
                    {
                        return SearchResult.builder().withFound(true).withPosition(i).build();
                    }
                }
                return SearchResult.builder().withFound(false).build();
            }
        });

        int [] seq1={1,2,3,4};
        int [] seq2={1,3};

        assertEquals(0.5,similarityFinder.calculateJackardSimilarity(seq1,seq2));
    }

    @Test
    public void zeroTimesMethod() throws NoSuchFieldException,IllegalAccessException
    {
        SequenceSearcher searcher = new SequenceSearcher() {
            int counter=0;

            @Override
            public SearchResult search(int elem, int[] sequence) {
                counter++;
                return null;
            }
        };
        int [] seq1={};
        int [] seq2={};

        SimilarityFinder similarityFinder= new SimilarityFinder(searcher);

        similarityFinder.calculateJackardSimilarity(seq1,seq2);

        assertEquals(0,searcher.getClass().getDeclaredField("counter").getInt(searcher));
    }





}
