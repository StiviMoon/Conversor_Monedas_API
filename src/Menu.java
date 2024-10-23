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

                System.out.println("¿Desea realizar otra conversion?\n1.Si\n2.No");
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
