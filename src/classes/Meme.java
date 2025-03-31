package classes;

/**
 * Author: Ivan
 */

public class Meme {

    private String nombre;
    private int anyoOrigen;
    private int popularidad;
    private String url;
    private boolean esImagen;

    public Meme(String nombre, int anyoOrigen, int popularidad, String etiquetas, boolean esImagen) {
        this.nombre = nombre;
        this.anyoOrigen = anyoOrigen;
        this.popularidad = popularidad;
        this.url = etiquetas;
        this.esImagen = esImagen;
    }

    public Object[] getRow() {
        return new Object[]{nombre, anyoOrigen, popularidad, url, esImagen};
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnyoOrigen() {
        return anyoOrigen;
    }

    public void setAnyoOrigen(int anyoOrigen) {
        this.anyoOrigen = anyoOrigen;
    }

    public int getPopularidad() {
        return popularidad;
    }

    public void setPopularidad(int popularidad) {
        this.popularidad = popularidad;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isEsImagen() {
        return esImagen;
    }

    public void setEsImagen(boolean esImagen) {
        this.esImagen = esImagen;
    }
}
