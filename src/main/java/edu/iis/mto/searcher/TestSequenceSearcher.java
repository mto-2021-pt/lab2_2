package edu.iis.mto.searcher;

public class TestSequenceSearcher implements SequenceSearcher {
    public TestSequenceSearcher(int[] res){
        this.res = res;
    }
    @Override
    public SearchResult search(int elem, int[] sequence) {
        SearchResult ret = SearchResult.builder().withFound(res[i] == 1).withPosition(res[i] == 0 ? 0 : i).build();
        i++;
        return ret;
    }
    private final int[] res;
    private int i = 0;
}
