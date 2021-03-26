package edu.iis.mto.similarity;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;

public class Search implements SequenceSearcher {

    SearchResult sr = SearchResult.builder().withFound(false).withPosition(-1).build();

    @Override
    public SearchResult search(int elem, int[] sequence) {
        for (int i : sequence){
            if ( i == elem ){
                sr = SearchResult.builder().withFound(true).withPosition(i).build();
                break;
            }
        }
        return sr;
    }

}
