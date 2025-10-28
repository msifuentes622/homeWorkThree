import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class CSArrayListTest {
    public static void main(String[] args) {
        Collection<String> testCollection = new CSArrayList<>();
        testCollection.add("A");
        testCollection.add("B");

        Iterator<String> iterator = testCollection.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println(testCollection.size());
        System.out.println(testCollection.contains("B"));
        System.out.println(((CSArrayList<String>) testCollection).indexOf("B"));


        int N = 1_000_00000; // or larger
        CSArrayList<Integer> a = new CSArrayList<>();
        long t0 = System.nanoTime();
        for (int i = 0; i < N; i++) a.add (i);
        long t1 = System.nanoTime();
        java.util.Random r = new java.util.Random(0);
        long s = 0;
        for (int i = 0; i < N; i++) s += a.get(r.nextInt(N));
        long t2 = System.nanoTime() ;
        System.out.printf("append=%.1f ms get=%.1f ms\n", (t1-t0)/1e6, (t2-t1)/1e6) ;
        /*
        *comparing the differences between we see a 1.0 ms in the append at 1_000_00
        *the other differences is the 2.0 ms in the get as well at 1_000_00
        * when we add more 000 to the end of the N we get a larger difference at get
        * and append 933 for util and csArrayList gets 1445 ms
        * and  for get we get around 200 more in CsArrayList come to util
        *
        *
         */
        ArrayList<Integer> list = new ArrayList<>();
        long t3 = System.nanoTime();
        for (int i = 0; i < N; i++) list.add(i);
        long t4 = System.nanoTime();
        java.util.Random r1 = new java.util.Random(0);
        long s1 =  0;
        for (int i = 0; i < N; i++) s1 += list.get(r1.nextInt(N));
        long t5 = System.nanoTime();
        System.out.printf("append=%.1f ms get=%.1f ms\n", (t4-t3)/1e6, (t5-t4)/1e6) ;


    }
}
