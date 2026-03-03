public class TowerC2Test {
    public static void main(String[] args) {
        int failures = 0;

        // Test 1: createTowerWithNCups
        Tower t = new Tower(10, 100);
        t.createTowerWithNCups(3);
        String[][] s = t.stackingItems();
        if (s.length != 3) {
            System.out.println("FAIL createTowerWithNCups: expected 3 items, got " + s.length);
            failures++;
        } else {
            if (!s[0][0].equals("cup") || !s[0][1].equals("1")) { System.out.println("FAIL createTowerWithNCups: first item not cup 1"); failures++; }
            if (!s[1][0].equals("cup") || !s[1][1].equals("2")) { System.out.println("FAIL createTowerWithNCups: second item not cup 2"); failures++; }
            if (!s[2][0].equals("cup") || !s[2][1].equals("3")) { System.out.println("FAIL createTowerWithNCups: third item not cup 3"); failures++; }
        }

        // Test 2: pushLid + coverAvailableCups should pair cup 2 with lid 2
        t.pushLid(2);
        t.coverAvailableCups();
        s = t.stackingItems();
        boolean paired = false;
        for (int i = 0; i < s.length - 1; i++) {
            if (s[i][0].equals("cup") && s[i][1].equals("2") && s[i+1][0].equals("lid") && s[i+1][1].equals("2")) {
                paired = true;
                break;
            }
        }
        if (!paired) {
            System.out.println("FAIL coverAvailableCups: cup 2 not paired with lid 2");
            failures++;
        }

        // Test 3: swap explicit cup 1 <-> cup 3
        t.swapItems("cup", 1, "cup", 3);
        s = t.stackingItems();
        int idx1 = -1, idx3 = -1;
        for (int i = 0; i < s.length; i++) {
            if (s[i][0].equals("cup") && s[i][1].equals("1")) idx1 = i;
            if (s[i][0].equals("cup") && s[i][1].equals("3")) idx3 = i;
        }
        if (idx1 == -1 || idx3 == -1) {
            System.out.println("FAIL swap: one of the cups is missing after swap");
            failures++;
        } else if (idx1 <= idx3) {
            System.out.println("FAIL swap: cups did not change relative positions as expected (idx1=" + idx1 + ", idx3=" + idx3 + ")");
            failures++;
        }

        if (failures == 0) {
            System.out.println("ALL TESTS PASSED");
            System.exit(0);
        } else {
            System.out.println(failures + " tests failed");
            System.exit(2);
        }
    }
}
