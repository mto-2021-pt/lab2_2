package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    @Test
    void testWhenEmptySequences() {
        SimilarityFinder similarityFinder = new SimilarityFinder(new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                return SearchResult.builder().build();
            }
        });

        int firstTab[] = new int[0];
        int secondTab[] = new int[0];

        double result = similarityFinder.calculateJackardSimilarity(firstTab, secondTab);
        Assertions.assertEquals(1, result);
    }

    @Test
    void testWithTheSameSequences() {
        SimilarityFinder similarityFinder = new SimilarityFinder(new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                SearchResult searchResult = null;
                if (elem == 13) {
                    searchResult = SearchResult.builder().withPosition(0).withFound(true)
                            .build();
                } else if (elem == 4) {
                    searchResult = SearchResult.builder().withPosition(1).withFound(true)
                            .build();
                }
                else if (elem == 29){
                    searchResult = SearchResult.builder().withPosition(2).withFound(true)
                            .build();
                }
                else if (elem == 16){
                    searchResult = SearchResult.builder().withPosition(3).withFound(true)
                            .build();
                }
                return searchResult;
            }
        });

        int tab[] = {13, 4, 29, 16};
        int tab2[] = {13, 4, 29, 16};

        double result = similarityFinder.calculateJackardSimilarity(tab, tab2);
        Assertions.assertEquals(1 , result);
    }
}
