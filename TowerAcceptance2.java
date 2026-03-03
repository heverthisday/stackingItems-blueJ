public class TowerAcceptance2 {
    public static void main(String[] args) {
        Tower t = new Tower(5, 20);
        // Agregar cups manualmente en lugar de usar createTowerWithNCups
        for (int i = 1; i <= 4; i++) {
            t.pushCup(i);
        }

        String[][] s = t.stackingItems();
        int idx1 = -1, idx4 = -1;
        for (int i = 0; i < s.length; i++) {
            if (s[i][0].equals("cup") && s[i][1].equals("1")) idx1 = i;
            if (s[i][0].equals("cup") && s[i][1].equals("4")) idx4 = i;
        }
        if (idx1 == -1 || idx4 == -1) {
            System.out.println("ACCEPTANCE2 FAILED: missing cups");
            System.exit(2);
        }

        t.swapItems("cup", 1, "cup", 4);

        s = t.stackingItems();
        int newIdx1 = -1, newIdx4 = -1;
        for (int i = 0; i < s.length; i++) {
            if (s[i][0].equals("cup") && s[i][1].equals("1")) newIdx1 = i;
            if (s[i][0].equals("cup") && s[i][1].equals("4")) newIdx4 = i;
        }

        if (newIdx1 > newIdx4) {
            System.out.println("ACCEPTANCE2 PASSED");
            System.exit(0);
        } else {
            System.out.println("ACCEPTANCE2 FAILED");
            System.exit(2);
        }
    }
}
