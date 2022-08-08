import java.util.concurrent.RecursiveAction;



public class MeanFilterParrallel extends RecursiveAction{

    //arguments
    int lo;
    int hi;
    int [] arr;
    static final int seqCut = 5000;

    public MeanFilterParrallel(int l, int h, int [] a){
        lo = l; hi = h; arr = a;

    }

    //instantiates mean filtering algorithm
    @Override
    protected Integer compute() {
        if((hi - lo) < seqCut){
            //mean algorithm from sequential code
        }else{
            
            MeanFilterParrallel left = new MeanFilterParrallel(lo, (hi+lo)/2, arr);
            MeanFilterParrallel right = new MeanFilterParrallel((hi+lo)/2, hi, arr);
            left.fork();

                int rAns = right.compute(); 
                int lAns = left.join();

                return lAns + rAns ;
        }
        
    }

    
}
