package studios.thinkup.com.apprunning.model.entity;

import com.github.gorbin.asne.core.persons.SocialPerson;

import java.io.Serializable;

public class AmigosDTO implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 7297125409717679879L;
    public static final String FIELD_ID = "amigo";
    private Integer idOwner;
    private Integer idAmigo;
    private String socialId;
    private String nick;
    private String grupo;
    private String urlFoto;
    private Boolean esAmigo = false;
    private Boolean esPendiente = false;
    private String email;
    private Boolean esBloqueado = false;

    public AmigosDTO() {

    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public Integer getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(Integer idOwner) {
        this.idOwner = idOwner;
    }

    public Integer getIdAmigo() {
        return idAmigo;
    }

    public void setIdAmigo(Integer idAmigo) {
        this.idAmigo = idAmigo;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public Boolean getEsAmigo() {
        return esAmigo;
    }

    public void setEsAmigo(Boolean esAmigo) {
        this.esAmigo = esAmigo;
    }

    public Boolean getEsPendiente() {
        return esPendiente;
    }

    public void setEsPendiente(Boolean esPendiente) {
        this.esPendiente = esPendiente;
    }

    public Boolean getEsBloqueado() {
        return esBloqueado;
    }

    public void setEsBloqueado(Boolean esBloqueado) {
        this.esBloqueado = esBloqueado;
    }

    @Override
    public boolean equals(Object o) {

       if (o instanceof AmigosDTO) {
            AmigosDTO a = (AmigosDTO) o;
           if(a.getSocialId()!=null){
               return a.getSocialId().equals(this.socialId);
           }
            return a.email.equals(this.email);
        } else {
            return false;
        }

    }

    @Override
    public int hashCode() {
        int result = getNick().hashCode();
        result = 31 * result + (getUrlFoto() != null ? getUrlFoto().hashCode() : 0);
        return result;
    }
}
