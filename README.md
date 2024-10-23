
# Conversor de Divisas

Este proyecto es una aplicación de consola en Java que permite convertir diferentes monedas utilizando una API de tasas de cambio. La aplicación está dividida en varias clases y realiza las siguientes conversiones: 
- Dólar a Peso Colombiano
- Peso Colombiano a Dólar
- Peso Argentino a Dólar
- Dólar a Peso Argentino

## Estructura del Proyecto

### Main.java
Este archivo contiene el punto de entrada del programa y ejecuta el menú para interactuar con el usuario.

```java
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        try {
            menu.mostrarMenu();
        } catch (IOException e) {
            System.out.println("Error al obtener los datos de la API: " + e.getMessage());
        }
    }
}
```

### Menu.java
La clase `Menu` maneja la interacción con el usuario, mostrando las opciones de conversión, capturando la cantidad a convertir y utilizando la lógica de conversión para mostrar los resultados.

```java
import Logica.Conversor_Monedas;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    // Método para mostrar el menú
    private void mostrarOpciones() {
        System.out.println("--------------------------------------");
        System.out.println("Bienvenido al conversor de divisas.");
        System.out.println("--------------------------------------");

        System.out.println("--------------------------------------");
        System.out.println("Seleccione la conversión que desea realizar:");
        System.out.println("1. Dólar a Peso Colombiano");
        System.out.println("2. Peso Colombiano a Dólar");
        System.out.println("3. Peso Argentino a Dólar");
        System.out.println("4. Dólar a Peso Argentino");
        System.out.println("5. Salir");
        System.out.println("--------------------------------------");

        System.out.print("Opción: ");
    }

    // Método para ejecutar el menú
    public void mostrarMenu() throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        while (continuar) {
            int opcion = 0;
            double cantidad;
            String monedaOrigen = "", monedaDestino = "";
            boolean opcionValida = true;

            // Mostrar opciones de menú
            mostrarOpciones();

            try {
                opcion = scanner.nextInt(); // Captura la opción del usuario
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número válido.");
                scanner.next(); // Limpiar el buffer del scanner
                continue; // Reiniciar el ciclo para mostrar el menú nuevamente
            }

            // Validar la opción seleccionada
            switch (opcion) {
                case 1:
                    monedaOrigen = "USD";
                    monedaDestino = "COP";
                    break;
                case 2:
                    monedaOrigen = "COP";
                    monedaDestino = "USD";
                    break;
                case 3:
                    monedaOrigen = "ARS";
                    monedaDestino = "USD";
                    break;
                case 4:
                    monedaOrigen = "USD";
                    monedaDestino = "ARS";
                    break;
                case 5:
                    System.out.println("Gracias por usar el conversor.");
                    return; // Sale del programa
                default:
                    opcionValida = false; // Marca la opción como inválida
                    System.out.println("Opción no válida. Intente nuevamente...");
                    break;
            }

            // Solo continúa si la opción es válida
            if (opcionValida) {
                System.out.print("Ingrese la cantidad a convertir: ");

                try {
                    cantidad = scanner.nextDouble(); // Captura la cantidad a convertir
                } catch (InputMismatchException e) {
                    System.out.println("Error: Debe ingresar un valor numérico válido.");
                    scanner.next(); // Limpiar el buffer del scanner
                    continue; // Reiniciar el ciclo para mostrar el menú nuevamente
                }

                // Realizar la conversión utilizando el conversor de monedas
                double resultado = Conversor_Monedas.convertirMoneda(monedaOrigen, monedaDestino, cantidad);

                // Mostrar el resultado
                System.out.println("**************************************");
                System.out.printf("Resultado: %.2f %s = %.2f %s%n", cantidad, monedaOrigen, resultado, monedaDestino);
                System.out.println("**************************************");

                System.out.println("¿Desea realizar otra conversion? [1.Si] o [2.No]");
                System.out.print("Opción: ");

                int opcionContinuar = scanner.nextInt();
                if (opcionContinuar == 1){
                    continuar = true;
                }else{
                    continuar = false;
                }
                System.out.println("Gracias por usar el conversor.");
            }
        }
    }
}
```

### Conexion_API/API_Divisas.java
Esta clase se conecta a la API externa para obtener las tasas de cambio de las divisas. Utiliza una URL específica de la API `ExchangeRate-API` para hacer la consulta, y luego parsea el resultado en formato JSON.

```java
package Conexion_API;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class API_Divisas {
    public static double obtenerTasaCambio(String base, String destino) throws IOException {
        String url_str = "https://v6.exchangerate-api.com/v6/77abb01f527280e13bf76ffc/latest/" + base;

        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonobj = root.getAsJsonObject();

        JsonObject conversionRates = jsonobj.getAsJsonObject("conversion_rates");
        return conversionRates.get(destino).getAsDouble();
    }
}
```

### Logica/Conversor_Monedas.java
Esta clase maneja la lógica principal de la conversión de divisas, utilizando la tasa de cambio obtenida de la API. Toma la moneda de origen, la moneda de destino y la cantidad para calcular el valor convertido.

```java
package Logica;

import Conexion_API.API_Divisas;

import java.io.IOException;

public class Conversor_Monedas {
    public static double convertirMoneda(String monedaOrigen, String monedaDestino, double cantidad) throws IOException {
        double tasaCambio = API_Divisas.obtenerTasaCambio(monedaOrigen, monedaDestino);
        return cantidad * tasaCambio;
    }
}
```

## Instalación y Ejecución

1. Clonar el repositorio o descargar los archivos fuente.
2. Asegurarse de tener acceso a la API de `https://v6.exchangerate-api.com`.
3. Ejecutar el archivo `Main.java` para iniciar la aplicación.
4. Interactuar con el menú para realizar conversiones entre monedas.

