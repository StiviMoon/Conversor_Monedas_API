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
