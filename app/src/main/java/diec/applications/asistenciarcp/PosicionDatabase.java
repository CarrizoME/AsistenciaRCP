package diec.applications.asistenciarcp;

public class PosicionDatabase {

    private static PosicionDatabase instance;

    private Double latitud = 0.0;
    private Double longitud = 0.0;
    private String hora = "no inicie";
    private String token = "";

    public PosicionDatabase(Double latitud, Double longitud, String hora, String token) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.hora = hora;
        this.token = token;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
