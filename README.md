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

### 3. Pueba Inicial e interpretacion

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

### 4. **¿Como lo mejoramos?**

  Para mejorar las medidas se impelemtaron (**varios**) nuevos test en la clase TowerC2Test y en TowerAccetptance2 que se pueden ver en diferentes commits tales como "test para subir cobertura" que fue el commit final pero hubieron varios commit para lograr obtener la covertura deseada, aqui el resultado:
  
![image alt](https://github.com/heverthisday/stackingItems-blueJ/blob/a0bdcceab61d0c81d29be0ff3c80ab0d87989370/coverageFinal.png)

### 5. Conclusiones del Analisis Dinamico

1. Una buena cobertura no solo implica un alto porcentaje sino tambien probar diferentes caminos del programa incluyendo casos limite y errores

2. Jacoco es una gran herramienta para ayudarnos a darnos cuenta de codigo que no hemos cubierto con nuestros test ademas de que nos da informer visualmente bonitos que son faciles de entender y
   nos señala en rojo que partes del codigo son las que nos estan generando conflictos

3. Aprendimos a distinguir que una cobertura grande en una carpeta que incluye al codigo del dominio no es necesariamnete que el codigo del dominio tiene alta covertura ya que hay muchas clases que
   pueden estar inflando esta estadistica

4. Aprendimos sobre que son los branches







## Analisis Estatico

### 1. Introduccion

El analisis estatico es una tecnica de evaluacion de software que se realiza sin ejecutar el programa este analiza el codigo directamente para detectar posibles errores, malas practicas y violaciones a reglas de calidad.

A diferencia del analisis dinamico, no requiere datos de rueba ni ejecucion del sistema sino que IntelliJ nos va mostrando en tiempo real los errores y las warnings

### 2. ¿Como se llevo a cabo?

Durante la migracion del proyecto de BlueJ a IntelliJ, el propio IntelliJ realizo un analisis estatico automatico del codigo, identificando problemas de estructura porque como se menciono antes los archivos de BlueJ eran planos y IntelliJ si exige organizacion de paquetes de manera especifica y calidad del codigo, la ventaja es que IntelliJ cuenta con una herramienta de inspeccion integrada
la cual nos hiba señalando en tiempo real problemas de sintaxis y daba sugerencias con los warnings, logica y convencion, entonces debieron arreglarse manualmente, aqui una foto de apenas se importo
a intelliJ desde BlueJ:

![image alt](https://github.com/heverthisday/stackingItems-blueJ/blob/a1ea073a6794772de65b9858f35c425f2d2e5c41/antes.png)


### 3. Resultado inicial

Al abrir el proyecto en IntelliJ por primera vez se encontraron los 
siguientes problemas principales:

| Desorganizado | Varias warnings |
|-------|---------|
| ![image alt](https://github.com/heverthisday/stackingItems-blueJ/blob/a1ea073a6794772de65b9858f35c425f2d2e5c41/antes.png) | ![image alt](https://github.com/heverthisday/stackingItems-blueJ/blob/deef98ccba45aa518e9eb9bc2573c57b1b7cda5e/warnigs.png) |

1.Teniamos una estructura de directorios incorrecta el proyecto venia en 
  carpetas planas de BlueJ que no corresponden a la estructura 
  estandar de Java y de intelliJ.
  
2. Al no estar organizado en el estilo de IntelliJ pues no podiamos siquiera usar las funciones que este nos ofrecia
   
3. 306 warnings detectados por  IntelliJ 
  
### 4. ¿Como se soluciono?

Al identificar los problemas se tomaron las siguientes decisiones:

1. Borrar todo lo relacionado con BlueJ (Todas esas carpetas que creaba BlueJ) esto porque estbamos migrando a algo totalnmente diferente (En los commits se puede evidenciar esta transicion)

2. Crear las nuevas carpetas asi como nos exigia IntelliJ (Todo esto fue consultado en foros, videos y con ayuda de ver como crea intelliJ un proyecto vacio)

3. Movimos la logica dentro de la carpeta de src/main/java/ y aqui se colocaron los paquetes shapes y tower que contenian las clases
   y los test se dejaron en test/java y dentro la carpeta de tests con sus respectivas clases

4. Se marcaron los directorios java de la logica y los test como Sources Root y Test Sources Root Respectivamente con ayuda de IntelliJ:

   ![image alt](https://github.com/heverthisday/stackingItems-blueJ/blob/dff91dfe60187e187b49a24bfe3fde71f4553263/root.png)

### 5. Resultado final

Despues de aplicar las correcciones:

- La estructura del proyecto quedo organizada correctamente
- Los archivos quedan sin errores rojos
- En setings y luego en coverage, Jacoco quedo Seleccionado como coverage runner correctamente
- Tests se podian ejecutar exitosamente y obtener los informes de manera correcta

  | Antes | Despues |
|-------|---------|
| ![image alt](https://github.com/heverthisday/stackingItems-blueJ/blob/a1ea073a6794772de65b9858f35c425f2d2e5c41/antes.png) | ![image alt](https://github.com/heverthisday/stackingItems-blueJ/blob/a1ea073a6794772de65b9858f35c425f2d2e5c41/despues.png) |

### 6. Conclusiones del Analisis Estatico

1. La migracion de BlueJ a IntelliJ fue el principal reto del analisis estatico ya que implico reorganizar toda la estructura del proyecto pero fue una muy gran ayuda para el analisis dinamico.
  
2. IntelliJ como herramienta de analisis estatico permitio identificar y corregir problemas de estructura
 
3. Tener una estructura de proyecto correcta es fundamental para poder aplicar herramientas de calidad como JaCoCo

4.La correccion de la estructura no solo resolvio los errores sino que tambien preparo el proyecto para seguir creciendo con buenas practicas y nos deja la enseñansa que un buen IDE desde el comienso nos va a ayudar y ahorrar la extencion y el trabajo

