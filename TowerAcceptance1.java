public class TowerAcceptance1 {
    public static void main(String[] args) {
        Tower t = new Tower(5, 20);
        // Agregar cups que quepan en altura máxima 20
        for (int i = 1; i <= 3; i++) {
            t.pushCup(i);
        }

        t.pushLid(2);
        t.pushLid(3);

        int before = t.height();
        t.coverAvailableCups();
        int after = t.height();

        String[][] s = t.stackingItems();
        boolean pair2 = false, pair3 = false;
        for (int i = 0; i < s.length - 1; i++) {
            if (s[i][0].equals("cup") && s[i][1].equals("2") && s[i+1][0].equals("lid") && s[i+1][1].equals("2")) pair2 = true;
            if (s[i][0].equals("cup") && s[i][1].equals("3") && s[i+1][0].equals("lid") && s[i+1][1].equals("3")) pair3 = true;
        }

        if (pair2 && pair3 && after <= 20) {
            System.out.println("ACCEPTANCE1 PASSED");
            System.exit(0);
        } else {
            System.out.println("ACCEPTANCE1 FAILED: pair2=" + pair2 + ", pair3=" + pair3 + ", height=" + after);
            System.exit(2);
        }
    }
}
