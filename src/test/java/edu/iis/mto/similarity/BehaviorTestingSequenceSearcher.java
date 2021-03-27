package edu.iis.mto.similarity;

import edu.iis.mto.searcher.SearchResult;

import java.util.ArrayList;

public class BehaviorTestingSequenceSearcher extends StateTestingSequenceSearcher {
    private ArrayList<Integer> elements = new ArrayList<>();

    public BehaviorTestingSequenceSearcher(int[] states) {
        super(states);
    }

    public int getNumberOfSearchCalls() {
        return resultIndex;
    }

    public ArrayList<Integer> getIncludedElements() {
        return elements;
    }

    @Override
    public SearchResult search(int elem, int[] sequence) {
        elements.add(elem);
        return searchResults.get(resultIndex++);
    }
}
