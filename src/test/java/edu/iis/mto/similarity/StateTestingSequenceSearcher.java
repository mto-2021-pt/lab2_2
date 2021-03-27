package edu.iis.mto.similarity;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;

import java.util.ArrayList;

public class StateTestingSequenceSearcher implements SequenceSearcher {
    protected ArrayList<SearchResult> searchResults = new ArrayList<>();
    protected int resultIndex = 0;

    public StateTestingSequenceSearcher(int[] states) {
        for (int state : states) {
            searchResults.add(state == 0
                    ? SearchResult.builder().withFound(false).build()
                    : SearchResult.builder().withFound(true).build());
        }
    }

    @Override
    public SearchResult search(int elem, int[] sequence) {
        return searchResults.get(resultIndex++);
    }
}
