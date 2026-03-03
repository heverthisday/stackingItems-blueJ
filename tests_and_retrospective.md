Propuesta de casos de prueba (Ciclo 2)

1) Pruebas unitarias (ya implementadas parcialmente en `TowerC2Test.java`):
   - Crear torre con N tazas y verificar conteo y números.
   - Push de tapas y `coverAvailableCups()` empareja correctamente.
   - `swapItems(type, a, type, b)` intercambia posiciones esperadas.

2) Pruebas de aceptación (para presentación):
   - Aceptación 1 (pairing): Crear 5 tazas, agregar tapas 3 y 5, ejecutar `coverAvailableCups()` y verificar que las parejas (cup3,lid3) y (cup5,lid5) estén contiguas y que la altura no aumente.
   - Aceptación 2 (swap visible): Crear 4 tazas, ejecutar `swapItems("cup",1,"cup",4)` y verificar que las posiciones relativas de `cup1` y `cup4` se han invertido.

3) Casos adicionales sugeridos:
   - Intentar agregar una taza que no quepa (validar mensaje/estado `ok`).
   - Búsqueda de intercambio que reduzca altura: construir una configuración y verificar que `findReducingSwap()` devuelve un intercambio y reduce la altura.
   - Ordenar y revertir con truncamiento por altura máxima (validar que se removieron elementos cuando corresponde).

Retrospección breve (Ciclo 2):
- Qué salió bien:
  - Implementación de reorganización (`coverAvailableCups`) y sincronización visual.
  - Búsqueda exhaustiva de `findReducingSwap()` cumple con el objetivo funcional.
  - Estrategia de redibujado total mantiene coherencia visual.

- Qué mejorar:
  - Separar lógica de dominio y visual para facilitar pruebas headless (actualmente `createTowerWithNCups` fuerza `makeVisible`).
  - Añadir más pruebas automatizadas que no dependan de la GUI.
  - Reducir duplicación de cálculo de alturas para ganar rendimiento.

- Próximos pasos sugeridos:
  - Refactor para permitir modos `headless` (sin Canvas) y facilitar testing.
  - Añadir pruebas de `findReducingSwap()` con fixtures deterministas.
  - Preparar la demo de presentación con pasos claros y screenshots.
