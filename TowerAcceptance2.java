/**
 * Prueba de aceptacion 2 - Swap de cups.
 * Verifica que al intercambiar cup 1 y cup 4,
 * sus posiciones se invierten correctamente.
 * Se ejecuta en modo invisible.
 * 
 * @author Hever
 * @version 2.0
 */
public class TowerAcceptance2 {
    public static void main(String[] args) {
        Tower t = new Tower(5, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.pushCup(3);
        t.pushCup(4);

        String[][] s = t.stackingItems();
        if (s.length != 4) {
            System.out.println("ACCEPTANCE2 FAILED: se esperaban 4 elementos, obtuve " + s.length);
            System.exit(2);
        }

        int origIdx1 = findIndex(s, "cup", "1");
        int origIdx4 = findIndex(s, "cup", "4");
        if (origIdx1 == -1 || origIdx4 == -1) {
            System.out.println("ACCEPTANCE2 FAILED: faltan tazas antes del intercambio");
            System.exit(2);
        }

        t.swapItems("cup", 1, "cup", 4);

        if (!t.ok()) {
            System.out.println("ACCEPTANCE2 FAILED: el intercambio reportó error");
            System.exit(2);
        }

        s = t.stackingItems();
        int newIdx1 = findIndex(s, "cup", "1");
        int newIdx4 = findIndex(s, "cup", "4");

        if (newIdx1 == origIdx4 && newIdx4 == origIdx1) {
            System.out.println("ACCEPTANCE2 PASSED");
            System.exit(0);
        } else {
            System.out.println("ACCEPTANCE2 FAILED: posiciones no intercambiadas");
            System.exit(2);
        }
    }

    private static int findIndex(String[][] items, String type, String number) {
        for (int i = 0; i < items.length; i++) {
            if (items[i][0].equals(type) && items[i][1].equals(number)) {
                return i;
            }
        }
        return -1;
    }
}