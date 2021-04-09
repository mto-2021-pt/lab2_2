package edu.iis.mto.similarity;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;

import java.util.ArrayList;
import java.util.List;

public class TestSearcher implements SequenceSearcher {

    private int resultIndex = 0;
    private int searchingAttempts = 0;
    private List<Integer> searchedValues;
    private List<int[]> searchedSequences;
    private List<SearchResult> searchResults;

    TestSearcher(List<SearchResult> searchResults) {
        this.searchedValues = new ArrayList<>();
        this.searchedSequences = new ArrayList<>();
        this.searchResults = searchResults;
    }

    @Override
    public SearchResult search(int elem, int[] sequence) {
        searchingAttempts++;
        searchedValues.add(elem);
        searchedSequences.add(sequence);

        return searchResults.get(resultIndex++);
    }

    int getSearchingAttempts() {
        return searchingAttempts;
    }

    List<Integer> getSearchedValues() {
        return searchedValues;
    }

    List<int[]> getSearchedSequences() {
        return searchedSequences;
    }

}
