package quicksortoptimized;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;

class Quick {
    static int m;
    
    public static void sort(Comparable a[], int cutoff) {
        m = cutoff;
        sort(a, 0, a.length-1);
    }
    
    private static void sort(Comparable a[], int lo, int hi) {
        if(hi-lo <= m) {                     // instead of "if(lo >= hi) return;"
            insertionSort(a, lo, hi);       // cutoff to insertion sort
            return;                         
        }
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
    }
    
    @SuppressWarnings("empty-statement")
    private static int partition(Comparable a[], int lo, int hi) {
        getPivot(a, lo, hi);        // make a[lo] the median of lo-mid-hi
        int i=lo; int j=hi+1;
        while(true) {
            while( less(a[++i], a[lo]) )  if(i==hi) break;
            while( less(a[lo], a[--j]) ) ; // if(j==lo) break; not needed
            if(j <= i) break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }
    
    private static void getPivot(Comparable a[], int lo, int hi) {
        int mid = (hi - lo)/2 + lo;
        
        if(less(a[lo], a[mid])) {
            if(less(a[mid], a[hi])) exch(a, mid, lo);    // mid is median of 3
            else if(less(a[lo], a[hi])) exch(a, hi, lo); // hi is median of 3
              // else keep a[lo] where it is
        }
        else {  // mid <= lo
            if(less(a[hi], a[mid]))  exch(a, mid, lo);
            else if(less(a[hi], a[lo])) exch(a, hi, lo);
              // else keep a[lo] where it is
        }
    }
    
    private static void insertionSort(Comparable a[], int lo, int hi) {
        for(int i=lo; i<hi; i++)
            for(int j=i+1; j>lo && less(a[j], a[j-1]); j--)
                exch(a, j, j-1);
    }
    
    private static boolean less(Comparable v, Comparable w) 
    {   return v.compareTo(w) < 0;   }
    
    private static void exch(Comparable a[], int i, int j)
    {   Comparable temp = a[i];  
        a[i] = a[j];  
        a[j] = temp;   
    }
    
    // Test quicksort for N=a to N=b and from M=0 to M=cutoff
    public static void testAll(int a, int b, int cutoff) {
        Double[] d;
        DataInputStream dataIn;
        long start, stop;
        
        System.out.println("This tester will print the # of milliseconds taken"
                + " to sort N double values,\nswitching to insertion sort when "
                + "partitions are <= m...\n");
        System.out.printf("%-10s| m=\t0\t5\t10\t15\t20\t25\t30\n", "N=");
        System.out.println();
        for(int N=a; N<=b; N=N*10) {             // N = size of array to be sorted
            System.out.printf("%-10d|", N);
            for(int M=0; M<=cutoff; M=M+5) {     // M = cutoff point to insertion sort
                d = new Double[N];          // new array for each N,M combination 
                try {
                    // read in double values to array d
                    dataIn = new DataInputStream(new FileInputStream("bases.txt"));
                    for(int i=0; i<N; i++) 
                        d[i] = dataIn.readDouble();
                    dataIn.close();
                } catch(IOException exc) { System.out.println(exc); }
        
                start = Calendar.getInstance().getTimeInMillis();
                Quick.sort(d, M);                                   // sort
                stop = Calendar.getInstance().getTimeInMillis();
                System.out.printf("\t%d", (stop-start));
            }
            System.out.println();
        }
    }
}

public class QuicksortOptimized {

    public static void main(String[] args) {
        int a,b;
        int m;
        
        a=1000; b=1000000;
        m=30;
        Quick.testAll(a, b, m);
    }
}
/*      
This tester will print the # of milliseconds taken to sort N double values,
switching to insertion sort when partitions are <= m...

N=        | m=	0	5	10	15	20	25	30
1000      |	25	1	1	1	0	0	0
10000     |	7	7	4	6	8	7	2
100000    |	40	39	34	30	38	34	32
1000000   |	864	697	702	717	696	720	709
*/