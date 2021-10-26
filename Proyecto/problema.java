import java.util.Scanner;
import java.lang.Math;
import java.text.DecimalFormat;

/**
 * MÉTODO EMPLEADO: Semilla aleatoria
 * GRUPO: 3CM8
 * INTEGRANTES: 
 * Esquivel Luna Adán 
 * Gracia Barajas Karla Alejandra 
 * Pérez Mejía José Carlos
 */
public class problema {

    DecimalFormat df = new DecimalFormat("#.0000000"); //formato para números decimales
    DecimalFormat df2 = new DecimalFormat("#");

    public void datos() {
        //declaración de variables
        int iteraciones = 0;
        int poblaciones;
        int individuos;
        int puntos = 0;
        double intervalo = 0;
        boolean val = true;

        Scanner entrada = new Scanner(System.in); //lector de datos

        while (val) {
            System.out.println("Ingresa el numero de puntos");
            puntos = entrada.nextInt();
            /*validación para el min y max de puntos*/
            if (puntos < 6 && puntos > 2) {
                val = false;
            } else {
                System.out.println("El rango de puntos es de 3 a 5, ingresa un numero de puntos valido");
            }
        }
        
        val = true;
        while (val) {
            System.out.println("Ingresa el numero de iteraciones");
            iteraciones = entrada.nextInt();
            /*validación para el min y max de iteraciones*/
            if (iteraciones < 101 && iteraciones > 0) {
                val = false;
            } else {
                System.out.println("El maximo de iteraciones es 100, ingresa un numero de puntos valido");
            }
        }

        System.out.println("Ingresa el numero de poblaciones por iteracion");
        poblaciones = entrada.nextInt(); //poblaciones a generar en cada iteración

        System.out.println("Ingresa el numero de individuos por poblacion");
        individuos = entrada.nextInt(); //invididuos que conforman cada población
        
        /*coordenadas de los puntos a evaluar*/
        double x[] = new double[puntos];
        double y[] = new double[puntos];

        /*lectura de los puntos a evaluar*/
        for (int i = 0; i < puntos; i++) {
            System.out.println("Ingresa las coordenadas del punto " + (i + 1));
            System.out.print("x: ");
            x[i] = entrada.nextFloat();
            System.out.print("y: ");
            y[i] = entrada.nextFloat();
        }
        
        System.out.println("\nLineal:");
        lineal(iteraciones, poblaciones, individuos, puntos, intervalo, x, y);//método para ajuste lineal
        System.out.println();
        System.out.println("\nGaussiana:");
        gaussiana(iteraciones, poblaciones, individuos, puntos, intervalo, x, y);//método para ajuste de la función gaussiana
    }

    /*Ajuste función lineal*/
    public void lineal(int iteraciones, int poblaciones, int individuos, int puntos, double intervalo, double[] x, double[] y) {

        double vector[] = new double[6]; //arreglo que guardará el mínimo global de la FO de todos los individuos(vectores) generados 
        double arrayPoblacion[] = new double[6]; //arreglo que guardará el mínimo global de la FO de cada población
        double arrayIteracion[] = new double[6]; //arreglo que guardará el mínimo global de la FO de cada iteración
        double m = 0; //pendiente 
        double b = 0; //termino constante
        double Z = 0; //FO
        
        System.out.println(" | ID ||   b    | |   m    | |   Z    |");
        
        /*cálculo del interbalo para b*/
        for (int i = 0; i < puntos; i++) {
            intervalo += x[i];
        }
        intervalo = Math.pow(intervalo, 2);
        
        /*Algoritmo semilla aleatoria*/
        
        /*Dentro de cada iteración i se generarán 
        j poblaciones con k individuos*/
        for (int i = 0; i < iteraciones; i++) {
            for (int j = 0; j < poblaciones; j++) {
                for (int k = 0; k < individuos; k++) {
                    Z = 0;
                    b = Math.random() * 1; //semilla aleatoria para b
                    m = Math.random() * 1; //semilla aleatoria para m
                    
                    /*restricciones para la FO*/
                    if (-intervalo < b && b < intervalo && -100 < m && m < 100) {
                        for (int l = 0; l < puntos; l++) {
                            Z += Math.pow((y[l] - b - m * x[l]), 2); //cálculo de la FO
                        }
                        if (k != 0) { //si k diferente de 0 entonces compara el valor de Z anterior con el nuevo
                            if (Z < vector[2]) { //si el valor actual de Z es menor al anterior entonces al vector lo remplazamos por los nuevos valores 
                                vector[0] = b;
                                vector[1] = m;
                                vector[2] = Z;
                                vector[3] = i;
                                vector[4] = j;
                                vector[5] = k;
                            }
                        } else { //si k = 0 guarda el primer vector generado
                            vector[0] = b;
                            vector[1] = m;
                            vector[2] = Z;
                            vector[3] = i;
                            vector[4] = j;
                            vector[5] = k;
                        }
                    }
                }
                if (j != 0) { //si j diferente de 0 entonces compara el valor de Z anterior con el nuevo
                    if (vector[2] < arrayPoblacion[2]) { //si el valor actual de Z es menor al anterior entonces al vector lo remplazamos por los nuevos valores 
                        arrayPoblacion[0] = vector[0];
                        arrayPoblacion[1] = vector[1];
                        arrayPoblacion[2] = vector[2];
                        arrayPoblacion[3] = vector[3];
                        arrayPoblacion[4] = vector[4];
                        arrayPoblacion[5] = vector[5];
                    }
                } else { //si j = 0 guarda el primer vector generado de la primera población
                    arrayPoblacion[0] = vector[0];
                    arrayPoblacion[1] = vector[1];
                    arrayPoblacion[2] = vector[2];
                    arrayPoblacion[3] = vector[3];
                    arrayPoblacion[4] = vector[4];
                    arrayPoblacion[5] = vector[5];
                }
            }
                        
            if (i != 0) { //si i diferente de 0 entonces compara el valor de Z anterior con el nuevo
                if (arrayPoblacion[2] < arrayIteracion[2]) { //si el valor actual de Z es menor al anterior entonces al vector lo remplazamos por los nuevos valores 
                    arrayIteracion[0] = arrayPoblacion[0];
                    arrayIteracion[1] = arrayPoblacion[1];
                    arrayIteracion[2] = arrayPoblacion[2];
                    arrayIteracion[3] = arrayPoblacion[3];
                    arrayIteracion[4] = arrayPoblacion[4];
                    arrayIteracion[5] = arrayPoblacion[5];
                }
            } else { //si i = 0 guarda el primer vector generado de la primera iteración
                arrayIteracion[0] = arrayPoblacion[0];
                arrayIteracion[1] = arrayPoblacion[1];
                arrayIteracion[2] = arrayPoblacion[2];
                arrayIteracion[3] = arrayPoblacion[3];
                arrayIteracion[4] = arrayPoblacion[4];
                arrayIteracion[5] = arrayPoblacion[5];
            }
            System.out.println("   " + i + "   " + df.format(arrayIteracion[0]) + "    " + df.format(arrayIteracion[1]) + "    " + df.format(arrayIteracion[2])); //imprime cada iteración
        }
        
        /*imprime datos del vector final obtenido */
        System.out.println("\nb=" + df.format(arrayIteracion[0]) + " m=" + df.format(arrayIteracion[1]) + " Z=" + df.format(arrayIteracion[2]) + " iteracion: " + df2.format(arrayIteracion[3]) + " poblacion: " + df2.format(arrayIteracion[4]) + " individuo: " + df2.format(arrayIteracion[5]));
        /*imprime ajuste de función lineal*/
        System.out.println("Ajuste Funcion Lineal: y=" + df.format(arrayIteracion[0]) + "+" + df.format(arrayIteracion[1]) + "x");

    }
    
     /*Ajuste función gaussiana*/
    public void gaussiana(int iteraciones, int poblaciones, int individuos, int puntos, double intervalo, double[] x, double[] y) {

        double vector[] = new double[6]; //arreglo que guardará el mínimo global de la FO de todos los individuos(vectores) generados 
        double arrayPoblacion[] = new double[6]; //arreglo que guardará el mínimo global de la FO de cada población
        double arrayIteracion[] = new double[6]; //arreglo que guardará el mínimo global de la FO de cada iteración
        double m = 0; //pendiente 
        double k = 0; //termino constante
        double Z = 0; //FO
        
        System.out.println(" | ID ||   k    | | m | |   Z    |");

        /*determinamos el valor de m para el ajuste de gauss*/
        for (int i = 1; i < puntos; i++) {
            if (y[i-1]<y[i]) {
                m = x[i];
            }
        }
        
        /*Algoritmo semilla aleatoria*/
        
        /*Dentro de cada iteración i se generarán 
        j poblaciones con k individuos*/
        for (int i = 0; i < iteraciones; i++) {
            for (int j = 0; j < poblaciones; j++) {
                for (int w = 0; w < individuos; w++) {
                    Z = 0;
                    k = Math.random() * 1; //semilla aleatoria para k
                    
                    /*restricciones para la FO*/
                    if (0 < k && k < 5 && -100 < m && m < 100) {
                        for (int l = 0; l < puntos; l++) {
                            Z += Math.pow(y[l] - (Math.pow(Math.E, -k * Math.pow(x[l] - m, 2))), 2);//cálculo de la FO
                        }
                        if (w != 0) { //si w diferente de 0 entonces compara el valor de Z anterior con el nuevo
                            if (Z < vector[2]) { //si el valor actual de Z es menor al anterior entonces al vector lo remplazamos por los nuevos valores 
                                vector[0] = k;
                                vector[1] = m;
                                vector[2] = Z;
                                vector[3] = i;
                                vector[4] = j;
                                vector[5] = w;
                            }
                        } else { //si w = 0 guarda el primer vector generado
                            vector[0] = k;
                            vector[1] = m;
                            vector[2] = Z;
                            vector[3] = i;
                            vector[4] = j;
                            vector[5] = w;
                        }
                    }
                }
                if (j != 0) { //si j diferente de 0 entonces compara el valor de Z anterior con el nuevo
                    if (vector[2] < arrayPoblacion[2]) { //si el valor actual de Z es menor al anterior entonces al vector lo remplazamos por los nuevos valores 
                        arrayPoblacion[0] = vector[0];
                        arrayPoblacion[1] = vector[1];
                        arrayPoblacion[2] = vector[2];
                        arrayPoblacion[3] = vector[3];
                        arrayPoblacion[4] = vector[4];
                        arrayPoblacion[5] = vector[5];
                    }
                } else { //si j = 0 guarda el primer vector generado de la primera población
                    arrayPoblacion[0] = vector[0];
                    arrayPoblacion[1] = vector[1];
                    arrayPoblacion[2] = vector[2];
                    arrayPoblacion[3] = vector[3];
                    arrayPoblacion[4] = vector[4];
                    arrayPoblacion[5] = vector[5];
                }
            }
                       
            if (i != 0) { //si i diferente de 0 entonces compara el valor de Z anterior con el nuevo
                if (arrayPoblacion[2] < arrayIteracion[2]) { //si el valor actual de Z es menor al anterior entonces al vector lo remplazamos por los nuevos valores 
                    arrayIteracion[0] = arrayPoblacion[0];
                    arrayIteracion[1] = arrayPoblacion[1];
                    arrayIteracion[2] = arrayPoblacion[2];
                    arrayIteracion[3] = arrayPoblacion[3];
                    arrayIteracion[4] = arrayPoblacion[4];
                    arrayIteracion[5] = arrayPoblacion[5];
                }
            } else { //si i = 0 guarda el primer vector generado de la primera iteración
                arrayIteracion[0] = arrayPoblacion[0];
                arrayIteracion[1] = arrayPoblacion[1];
                arrayIteracion[2] = arrayPoblacion[2];
                arrayIteracion[3] = arrayPoblacion[3];
                arrayIteracion[4] = arrayPoblacion[4];
                arrayIteracion[5] = arrayPoblacion[5];
            }
            System.out.println("   " + i + "   " + df.format(arrayIteracion[0]) + "    " + m + "    " + df.format(arrayIteracion[2])); //imprime cada iteración
        }

        /*imprime datos del vector final obtenido */
        System.out.println("\nk=" + df.format(arrayIteracion[0]) + " m=" + arrayIteracion[1] + " Z=" + df.format(arrayIteracion[2]) + " iteracion: " + df2.format(arrayIteracion[3]) + " poblacion: " + df2.format(arrayIteracion[4]) + " individuo: " + df2.format(arrayIteracion[5]));
        /*imprime ajuste de función gaussiana */
        System.out.println("Ajuste Funcion Gaussiana: y=e^(-" + df.format(arrayIteracion[0]) + "(-" + arrayIteracion[1] + "+x)^2)");

    }

}