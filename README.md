# Informes Proyecto Final

## Analisis Dinamico

**Como iniicio de nuestro informe vamos a dar una pequeña introduccion**

### 1. Introduccion

¿Que es el analisis dinamico?

El analisis dinamico es una técnica de evaluación de software que idealmete se realiza durante la ejecución del programa, cabe aclarar que en nuestro caso se esta haciendo en la entrega final la evlaucion con una herramienta especializada llamda jacoco, el analisis dinamico mide el comportamiento real del sistema cuando corre con datos de prueba concretos. Su principal objetivo es detectar que partes del código se ejecutan efectivamente durante las pruebas y cuales permanecen sin ejercitar, lo que permite identificar zonas de riesgo no validadas que podrian generar errores que no hemos previsto

**¿Que es Jacoco?**

Jacoco (Java Code Coverage) es una biblioteca gratuita y de código abierto para medir y reportar la cobertura de pruebas en aplicaciones Java

### 2. ¿Como se llevo a cabo las pruebas con Jacoco?

Para usar Jacoco se necesita migrar el proyecto de BlueJ a un IDE que nos permita usar esta herramienta, optamos por IntelliJ ya que esta nos permite usar Jacoco dentro de ella de maneras mas facil y nos genera un reporte en html con graficas el cual es mas facil de ver e interpretar (Estar cargado ya ne el repositorio).

Tuvimos que volver a organizar el proyecto en nuevas carpetas por que este IDE a diferencia de BlueJ que guarda en carpetas planas IntelliJ si nos exige manejar una mejor extructura de proyectos, lo cual es muy bueno para proyetos futuros ya que asi es como se maneja en el mundo laboral ademas que IntelliJ si es una herramienta moderna.


| Antes | Despues |
|-------|---------|
| ![image alt](https://github.com/heverthisday/stackingItems-blueJ/blob/a1ea073a6794772de65b9858f35c425f2d2e5c41/antes.png) | ![image alt](https://github.com/heverthisday/stackingItems-blueJ/blob/a1ea073a6794772de65b9858f35c425f2d2e5c41/despues.png) |

**Nota:**Sobre las carpetas java en colores son poque IntelliJ necesite que se marquen de la siguiente manera para distinguir de que clase son, el color azul denota Sources Root y el color verde Test Source Roote

### Pueba Inicial e interpretacion

Luego de tener el proyecto organizado de la manera adecuada que nos exige IntelliJ y tener a Jacoco seleccionado como Coverage se ejecutaron las prubas y se obtuvo el siguiente resumen:

![image alt](https://github.com/heverthisday/stackingItems-blueJ/blob/e97b493c2f8b13c1ace4a50648505b8f8ae93a2e/Coveregue%20Inicial%20con%20Jacoco.png)

**¿Como lo interpretamos?**

Tenemos una Cobertura del 63% pero eso es sobre toda la carpeta Tower y los requisitos son claros, nos dicen que necesitamos mas de un 75% de cubvrimiento en el codigo del dominio entonces debemos enfocarnos en la carpeta Tower y dentro de ella debemos fijarnos en la logica de dominio del proyecto que la tiene la clase Tower, entonces debemos mejorar el cubrimiento de esta clase por eso vamos a profundizar en Tower.

Cobertura dentro de Tower:

![image alt](https://github.com/heverthisday/stackingItems-blueJ/blob/92a5845f1826fe137b7eb921dec7b19a38e35c5c/coverageTower.png)

## Interpretacion de columnas en el reporte de cobertura JaCoCo

| Columna | Definicion |
|--------|-----------|
| Element |Es el nombre de la clase |
| Missed Instructions | Cantidad de instrucciones de bytecode que no fueron ejecutadas durante las pruebas |
| Cov Instructions | Porcentaje de instrucciones que si fueron ejecutadas |
| Missed Branches | Numero de caminos logicos no ejecutados en estructuras como if o else etc |
| Cov Branches | Porcentaje de caminos logicos cubiertos por los tests |
| Missed Cxty | Cantidad de caminos de ejecucion no cubiertos segun la complejidad |
| Cxty | Complejidad ciclomatica Numero de caminos independientes en el codigo |
| Missed Lines | Numero de lineas de codigo que no fueron ejecutadas |
| Lines | Total de lineas de codigo analizadas |
| Missed Methods | Cantidad de metodos que nunca fueron llamados en los tests |
| Methods | Numero total de metodos en la clase |
| Missed Classes | Numero de clases completas que no fueron usadas en pruebas |
| Classes | Total de clases analizadas |

Nos vamos a enfocar en la clase Tower, tenemos un cubrimeinto real del dominio de un **60%** no se ejecutaron **46%** de los caminos logicos (Missed Branches) por lo que vamos a mejorarlos.

**¿Como lo mejoramos?**

  Para mejorar las medidas se impelemtaron (**varios**) nuevos test en la clase TowerC2Test y en TowerAccetptance2 que se pueden ver en diferentes commits tales como "test para subir cobertura"
![image alt](https://github.com/heverthisday/stackingItems-blueJ/blob/a0bdcceab61d0c81d29be0ff3c80ab0d87989370/coverageFinal.png)
### 7. Conclusiones del Analisis Dinamico

- Partir de 120 tests con 60% de cobertura nos permitio identificar 
  rapidamente las zonas criticas sin cubrir
- La clase Tower concentra la mayor parte de la logica de dominio y 
  por eso fue la que mas esfuerzo requirio para mejorar su cobertura
- Agregar 58 tests nuevos (178 en total) subio el cubrimiento de lines 
  de 60% a 78%, superando la meta del 75%
- El analisis dinamico demostro que habia ramas enteras del codigo 
  (como TowerContest) que nunca se ejecutaban en las pruebas originales
- JaCoCo fue una herramienta fundamental para visualizar exactamente 
  que lineas y ramas del codigo no estaban siendo validadas

---
## Analisis Estatico

### 1. Introduccion

El analisis estatico es una tecnica de evaluacion de software que se 
realiza sin ejecutar el programa. Analiza el codigo fuente directamente 
para detectar posibles errores, malas practicas y violaciones a reglas 
de calidad. A diferencia del analisis dinamico, no requiere datos de 
prueba ni ejecucion del sistema.

### 2. ¿Como se llevo a cabo?

Durante la migracion del proyecto de BlueJ a IntelliJ, el propio IDE 
realizo un analisis estatico automatico del codigo, identificando 
problemas de estructura, organizacion de paquetes y calidad del codigo 
fuente. IntelliJ cuenta con un motor de inspeccion integrado que señala 
en tiempo real problemas de sintaxis, logica y convencion.

### 3. Resultado inicial

Al abrir el proyecto en IntelliJ por primera vez se encontraron los 
siguientes problemas principales:

- **Estructura de directorios incorrecta**: el proyecto venia en 
  carpetas planas de BlueJ que no corresponden a la estructura 
  estandar de Java (src/main/java y test/java)
- **Problemas de paquetes**: las clases no tenian declaraciones de 
  paquete correctas al reorganizar los directorios
- **Errores de sintaxis**: algunos archivos presentaban advertencias 
  de compilacion al cambiar de entorno
- **306 warnings detectados** por el inspector de IntelliJ al 
  cargar el proyecto

### 4. Decisiones tomadas

Al identificar los problemas tomamos las siguientes decisiones:

1. **Reorganizar la estructura de directorios** al estandar de IntelliJ: 
   src/main/java para codigo de dominio y test/java para pruebas
2. **Corregir las declaraciones de paquete** en todas las clases para 
   que coincidieran con la nueva estructura de carpetas
3. **Resolver los problemas de dependencias** agregando JUnit 5 
   como libreria del modulo desde el repositorio Maven
4. **Marcar correctamente las carpetas** como Sources Root y 
   Test Sources Root en la configuracion del modulo

### 5. Resultado final

Despues de aplicar las correcciones:

- La estructura del proyecto quedo organizada correctamente
- Todos los archivos compilan sin errores
- Las dependencias de JUnit 5 quedaron correctamente configuradas
- Los 178 tests ejecutan exitosamente sin errores de compilacion

### 6. Conclusiones del Analisis Estatico

- La migracion de BlueJ a IntelliJ fue el principal reto del analisis 
  estatico ya que implico reorganizar toda la estructura del proyecto
- IntelliJ como herramienta de analisis estatico integrado permitio 
  identificar y corregir problemas de estructura antes de ejecutar 
  cualquier prueba
- Tener una estructura de proyecto correcta es fundamental para poder 
  aplicar herramientas de calidad como JaCoCo
- La correccion de la estructura no solo resolvio los errores sino que 
  tambien preparo el proyecto para seguir creciendo con buenas practicas

