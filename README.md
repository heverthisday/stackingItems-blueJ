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

  Para mejorar las medidas se impelemtaron (**varios**) nuevos test en la clase TowerC2Test y en TowerAccetptance2 que se pueden ver en diferentes commits tales como "", "", ""


## Analisis Estatico
