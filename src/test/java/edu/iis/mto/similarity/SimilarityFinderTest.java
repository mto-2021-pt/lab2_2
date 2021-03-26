package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {
    final SequenceSearcher seqS = (elem, sequence) -> {
        for (int i = 0; i < sequence.length; ++i) {
            if (elem == sequence[i]) {
                return SearchResult.builder().withPosition(i).withFound(true).build();
            }
        }
        return SearchResult.builder().withPosition(-1).withFound(false).build();
    };
    private class jaccardGen {
        public final int[] a;
        public final int[] b;
        public final int sample_size_a;
        public final int sample_size_b;
        public final int diff_pos;
        public final double exp_res;
        public jaccardGen(int sample_size_a, int sample_size_b, int diff_pos) {
            this.sample_size_a = sample_size_a;
            this.sample_size_b = sample_size_b;
            this.diff_pos = diff_pos;
            this.a = new int[sample_size_a];
            this.b = new int[sample_size_b];

            // Fill both sets with the same data
            for (int i = 0; i < sample_size_a; i++) {
                a[i] = i;
            }
            for (int i = 0; i < sample_size_b; i++) {
                b[i] = i;
            }
            float shared = Math.min(sample_size_a, sample_size_b);
            float unique = shared + Math.abs(sample_size_a - sample_size_b);
            // Add an n-difference in the second set
            for (int i = 0; i < sample_size_b; i++) {
                if (i % diff_pos == 0) {
                    b[i] = (i + 1)*(sample_size_a + sample_size_b);
                    if (i < sample_size_a) {
                        unique++;
                        shared--;
                    }
                }
            }
            if (sample_size_a <= 0 || sample_size_b <= 0) {
                if (sample_size_a == 0 && sample_size_b == 0) {
                    exp_res = 1.0;
                } else {
                    exp_res = 0;
                }

            } else {
                exp_res = (shared/unique);
            }
        }
    }

    @Test
    void jaccardIndexWithSmallSampleSizeAndSameSetSize() {
        jaccardGen jt = new jaccardGen(10, 10, 2);
        SimilarityFinder simF = new SimilarityFinder(seqS);
        double res = simF.calculateJackardSimilarity(jt.a, jt.b);
        assertEquals(jt.exp_res, res, 0.0001);
    }
    @Test
    void jaccardIndexWithSmallSampleSizeAndDifferentSetSizeA() {
        jaccardGen jt = new jaccardGen(12, 10, 2);
        SimilarityFinder simF = new SimilarityFinder(seqS);
        double res = simF.calculateJackardSimilarity(jt.a, jt.b);
        assertEquals(jt.exp_res, res, 0.0001);
    }
    @Test
    void jaccardIndexWithSmallSampleSizeAndDifferentSetSizeB() {
        jaccardGen jt = new jaccardGen(10, 12, 2);
        SimilarityFinder simF = new SimilarityFinder(seqS);
        double res = simF.calculateJackardSimilarity(jt.a, jt.b);
        assertEquals(jt.exp_res, res, 0.0001);
    }
    @Test
    void jaccardIndexWithBigSampleSizeAndSameSetSize() {
        jaccardGen jt = new jaccardGen(100, 100, 2);
        SimilarityFinder simF = new SimilarityFinder(seqS);
        double res = simF.calculateJackardSimilarity(jt.a, jt.b);
        assertEquals(jt.exp_res, res, 0.0001);
    }
    @Test
    void jaccardIndexWithBigSampleSizeAndDifferentSetSizeA() {
        jaccardGen jt = new jaccardGen(200, 100, 2);
        SimilarityFinder simF = new SimilarityFinder(seqS);
        double res = simF.calculateJackardSimilarity(jt.a, jt.b);
        assertEquals(jt.exp_res, res, 0.0001);
    }
    @Test
    void jaccardIndexWithBigSampleSizeAndDifferentSetSizeB() {
        jaccardGen jt = new jaccardGen(100, 200, 2);
        SimilarityFinder simF = new SimilarityFinder(seqS);
        double res = simF.calculateJackardSimilarity(jt.a, jt.b);
        assertEquals(jt.exp_res, res, 0.0001);
    }
    @Test
    void jaccardIndexWithBigDifferenceOfSetSizeA() {
        jaccardGen jt = new jaccardGen(10000, 200, 2);
        SimilarityFinder simF = new SimilarityFinder(seqS);
        double res = simF.calculateJackardSimilarity(jt.a, jt.b);
        assertEquals(jt.exp_res, res, 0.0001);
    }
    @Test
    void jaccardIndexWithBigDifferenceOfSetSizeB() {
        jaccardGen jt = new jaccardGen(1, 10, 2);
        SimilarityFinder simF = new SimilarityFinder(seqS);
        double res = simF.calculateJackardSimilarity(jt.a, jt.b);
        assertEquals(jt.exp_res, res, 0.0001);
    }
    @Test
    void jaccardIndexWithBothEmptyAArray() {
        jaccardGen jt = new jaccardGen(0, 10, 2);
        SimilarityFinder simF = new SimilarityFinder(seqS);
        double res = simF.calculateJackardSimilarity(jt.a, jt.b);
        assertEquals(jt.exp_res, res, 0.0001);
    }
    @Test
    void jaccardIndexWithBothEmptyBArray() {
        jaccardGen jt = new jaccardGen(10, 0, 2);
        SimilarityFinder simF = new SimilarityFinder(seqS);
        double res = simF.calculateJackardSimilarity(jt.a, jt.b);
        assertEquals(jt.exp_res, res, 0.0001);
    }
    @Test
    void jaccardIndexWithBothEmptyArrays() {
        jaccardGen jt = new jaccardGen(0, 0, 2);
        SimilarityFinder simF = new SimilarityFinder(seqS);
        double res = simF.calculateJackardSimilarity(jt.a, jt.b);
        assertEquals(jt.exp_res, res, 0.0001);
    }
    @Test
    void jaccardIndexWithNullSequenceSearcher() {
        jaccardGen jt = new jaccardGen(12, 10, 2);
        SimilarityFinder simF = new SimilarityFinder(null);
        Assertions.assertThrows(NullPointerException.class, () -> {
            simF.calculateJackardSimilarity(jt.a, jt.b);
        });
    }
}
