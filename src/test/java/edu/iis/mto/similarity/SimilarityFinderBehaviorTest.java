package edu.iis.mto.similarity;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class SimilarityFinderBehaviorTest {

    @Test
    void testMethodThatShouldInvokeSevenTimes() throws NoSuchFieldException, IllegalAccessException {

        SequenceSearcher sequenceSearcher = new SequenceSearcher() {
            int amountOfSearches = 0;

            @Override
            public SearchResult search(int elem, int[] sequence) {
                amountOfSearches++;
                return SearchResult.builder().withPosition(amountOfSearches).withFound(false).build();
            }
        };

        int[] firstTab = {13, 44, 123, 98, 1, 43, 22};
        int[] secondTab = {1, 2, 3, 4, 5, 6, 7, 8};

        SimilarityFinder finder = new SimilarityFinder(sequenceSearcher);
        finder.calculateJackardSimilarity(firstTab, secondTab);

        Field field = sequenceSearcher.getClass().getDeclaredField("amountOfSearches");
        int amountOfSearches = field.getInt(sequenceSearcher);

        Assertions.assertEquals(7, amountOfSearches);

    }
}
