public class Example1 {

    public static void main(String[] args) {

        boolean a = true;
        boolean b = false;
        boolean c = false;

        // What will d be?
        boolean d = a && b || ! c;
        System.out.println(d);

        boolean e = !(a && b);
        System.out.println(e);

        boolean f = c && a;
        System.out.println(f);

        int n1 = 3;
        int n2 = 7;

        boolean g = (n1 > n2) || (n1 != 3);
        System.out.println(g);

    }

}
