package edu.iis.mto.similarity;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Test
    public void testIfMethodDoesntChangeArrays() {
        List<Integer> firstArrayList = new ArrayList<>();
        List<Integer> secondArrayList = new ArrayList<>();

        SequenceSearcher sequenceSearcher = (elem, sequence) -> {
            firstArrayList.add(elem);
            if(secondArrayList.isEmpty())
                secondArrayList.addAll(Arrays.stream(sequence).boxed().collect(Collectors.toList()));
            return SearchResult.builder().withFound(false).build();
        };

        int[] firstTab = {13, 44, 123, 98, 1, 43, 22};
        int[] secondTab = {1, 2, 3, 4, 5, 6, 7};

        SimilarityFinder finder = new SimilarityFinder(sequenceSearcher);
        finder.calculateJackardSimilarity(firstTab, secondTab);

        Assertions.assertArrayEquals(firstArrayList.stream().mapToInt(Integer::intValue).toArray(), firstTab);
        Assertions.assertArrayEquals(secondArrayList.stream().mapToInt(Integer::intValue).toArray(), secondTab);
    }
}
