package edu.iis.mto.similarity;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;

public class StateTestSearcher implements SequenceSearcher {

    private SearchResult[] search_results;
    private int current_result = 0;

    public StateTestSearcher(SearchResult[] search_results){
        this.search_results = search_results;
    }

    @Override
    public SearchResult search(int elem, int[] sequence) {
        return search_results[current_result++];
    }
}
