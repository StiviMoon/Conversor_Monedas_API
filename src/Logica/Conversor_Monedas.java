package Logica;

import Conexion_API.API_Divisas;

import java.io.IOException;

public class Conversor_Monedas {
    public static double convertirMoneda(String monedaOrigen, String monedaDestino, double cantidad) throws IOException, IOException {
        double tasaCambio = API_Divisas.obtenerTasaCambio(monedaOrigen, monedaDestino);
        return cantidad * tasaCambio;
    }
}
