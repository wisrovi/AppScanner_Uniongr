package co.uniongr.scanerradicado.dto;

public class Data_send_api {
    private String codigobarras;
    private String usr;
    private String pwd;

    public Data_send_api(String codigobarras, String usr, String pwd) {
        this.codigobarras = codigobarras;
        this.usr = usr;
        this.pwd = pwd;
    }

    public String getCodigobarras() {
        return codigobarras;
    }

    public void setCodigobarras(String codigobarras) {
        this.codigobarras = codigobarras;
    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
