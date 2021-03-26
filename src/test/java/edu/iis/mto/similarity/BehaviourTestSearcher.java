package edu.iis.mto.similarity;

import edu.iis.mto.searcher.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class BehaviourTestSearcher{
    private SearchResult[] search_results;
    private int current_result = 0;

    private int number_of_searches = 0;
    private List<Integer> search_elements = new ArrayList<>();
    private List<int[]> search_arrays = new ArrayList<>();

    public BehaviourTestSearcher(SearchResult[] search_results) {
        this.search_results = search_results;
    }

    public int getNumberOfSearchCalls(){
        return number_of_searches;
    }

    public List<Integer> getSearchedElements(){
        return search_elements;
    }

    public List<int[]> getSearchedCollections(){
        return search_arrays;
    }

    public SearchResult search(int elem, int[] sequence) {
        number_of_searches++;

        search_elements.add(elem);
        search_arrays.add(sequence);

        return search_results[current_result++];
    }
}
