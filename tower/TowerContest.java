package tower;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class TowerContest {

    public static String solve(int n, int h) {
        int minH = 2 * n - 1;
        int maxH = n * n;

        // Validación
        if (h < minH || h > maxH || (n >= 3 && h == maxH - 2)) {
            mostrarError("impossible: altura no alcanzable");
            return "impossible";
        }

        // Caso especial: altura mínima
        if (h == minH) {
            StringBuilder sb = new StringBuilder();
            for (int i = n; i >= 1; i--) {
                if (i != n) sb.append(" ");
                sb.append(2 * i - 1);
            }
            return sb.toString();
        }

        // Buscar la solución
        List<Integer> order = construirOrden(n, h);
        
        if (order == null) {
            mostrarError("No se encontro una solucion");
            return "impossible";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < order.size(); i++) {
            if (i > 0) sb.append(" ");
            sb.append(order.get(i));
        }
        return sb.toString();
    }

    /**
     * Simulación: Primero hace visible todo y luego aplica la lógica.
     */
    public static void simulate(int n, int h) {
        // 1. CREAR E INICIALIZAR LA TORRE PRIMERO
        Tower tower = new Tower(n);
        
        // 2. HACER VISIBLE TODO ANTES DE CALCULAR (Se abre el canvas vacío)
        tower.makeVisible();
        
        // 3. COMENZAR EL PROCESO DE SOLUCIÓN
        String resultado = solve(n, h);
        
        if (resultado.equals("impossible")) {
            return;
        }
        
        // 4. PARSEAR RESULTADOS
        String[] partes = resultado.split(" ");
        int[] alturas = new int[partes.length];
        for (int i = 0; i < partes.length; i++) {
            alturas[i] = Integer.parseInt(partes[i]);
        }
        
        // Convertir alturas a números de taza
        int[] ordenTazas = new int[alturas.length];
        for (int i = 0; i < alturas.length; i++) {
            ordenTazas[i] = (alturas[i] + 1) / 2;
        }
        
        // 5. LIMPIAR LA TORRE (Si el constructor ya las creó, las quitamos)
        for (int i = n; i >= 1; i--) {
            tower.removeCup(i);
        }
        
        // 6. APILAR EN EL ORDEN ENCONTRADO
        // Como 'tower' ya es visible, cada pushCup se verá reflejado en tiempo real
        for (int numeroDeTaza : ordenTazas) {
            tower.pushCup(numeroDeTaza);
        }
    }

    private static List<Integer> construirOrden(int n, int h) {
        List<Integer> order = new ArrayList<>();
        
        for (int b = 1; b <= n; b++) {
            int t = h - (n - b);
            if (t < 0 || t > b * b) continue;
            
            int a = isqrt(t);
            if ((a & 1) != (t & 1)) a--;
            if (a < 0) continue;
            
            if (t <= a * (2 * b - a)) {
                int R = (t + a) / 2;
                List<Integer> xs = new ArrayList<>();
                for (int i = 1; i <= a; i++) xs.add(i);
                
                int cur = a * (a + 1) / 2;
                for (int i = a - 1; i >= 0; i--) {
                    int maxv = b - (a - 1 - i);
                    int add = Math.min(maxv - xs.get(i), R - cur);
                    if (add > 0) {
                        xs.set(i, xs.get(i) + add);
                        cur += add;
                    }
                }
                
                for (int i = n; i > b; i--) {
                    order.add(2 * i - 1);
                }
                
                boolean[] chosen = new boolean[b + 1];
                for (int v : xs) {
                    chosen[v] = true;
                }
                
                for (int v : xs) {
                    order.add(2 * v - 1);
                }
                
                for (int i = b; i >= 1; i--) {
                    if (!chosen[i]) {
                        order.add(2 * i - 1);
                    }
                }
                
                return order;
            }
        }
        
        return null;
    }

    private static int isqrt(int x) {
        int r = (int) Math.sqrt(x);
        while ((r + 1) * (r + 1) <= x) r++;
        while (r * r > x) r--;
        return r;
    }

    private static void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(
            null,
            mensaje,
            "Torre imposible",
            JOptionPane.WARNING_MESSAGE
        );
    }
}