package co.uniongr.scanerradicado.dto;

public class Radicados {
    private String archivo;
    private String ruta;
    private String extension;

    public Radicados(String archivo, String ruta, String extension) {
        this.archivo = archivo;
        this.ruta = ruta;
        this.extension = extension;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
