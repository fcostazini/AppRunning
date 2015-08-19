package com.thinkup.ranning.dtos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Filtro {
	
	public static final String FILTRO_ID = "FILTRO";
    public static final String[] SENTIDO_ORDEN = {"Ascendente","Descendente"};
    private Boolean recomendadas = null;
    private String nombreCarrera;
    //Fechas en formato yyyy/MM/dd
    private String fechaDesde;
    private String fechaHasta;
    private Integer minDistancia;
    private Integer maxDistancia;
    private String modalidad;
    private String ciudad;
    private String provincia;
    private long idUsuario;
    private Boolean meGusta = null;
    private Boolean inscripto = null;
    private Boolean corrida = null;
    private String ordenarPor = CamposOrdenEnum.NINGUNO.getLabel();
    private String sentido = SENTIDO_ORDEN[0];	
    private String email = "";
	
    
    

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getRecomendadas() {
        return recomendadas;
    }

    public void setRecomendadas(Boolean recomendadas) {
        this.recomendadas = recomendadas;
    }

    public String getOrdenarPor() {
        return ordenarPor;
    }

    public void setOrdenarPor(String ordenarPor) {
        this.ordenarPor = ordenarPor;
    }

    public String getSentido() {
        return sentido;
    }

    public void setSentido(String sentido) {
        this.sentido = sentido;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }


    public Boolean getMeGusta() {
        return meGusta;
    }

    public void setMeGusta(Boolean meGusta) {
        this.meGusta = meGusta;
    }

    public Boolean getInscripto() {
        return inscripto;
    }

    public void setInscripto(Boolean inscripto) {
        this.inscripto = inscripto;
    }

    public Boolean getCorrida() {
        return corrida;
    }

    public void setCorrida(Boolean corrida) {
        this.corrida = corrida;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Filtro() {
        this.minDistancia = 0;
        this.maxDistancia = 100;
    }


    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public String getFechaDesde() {

        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getFechaHasta() {

        return this.fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public String getCiudad() {

        return this.ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Integer getMinDistancia() {
        return minDistancia;
    }

    public void setMinDistancia(Integer minDistancia) {
        this.minDistancia = minDistancia;
    }

    public Integer getMaxDistancia() {
        return maxDistancia;
    }

    public void setMaxDistancia(Integer maxDistancia) {
        this.maxDistancia = maxDistancia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Filtro filtro = (Filtro) o;

        if (corrida != null ? !corrida.equals(filtro.corrida) : filtro.corrida != null)
            return false;
        if (fechaDesde != null ? !fechaDesde.equals(filtro.fechaDesde) : filtro.fechaDesde != null)
            return false;
        if (fechaHasta != null ? !fechaHasta.equals(filtro.fechaHasta) : filtro.fechaHasta != null)
            return false;
        if (modalidad != filtro.modalidad) return false;
        if (idUsuario == filtro.idUsuario)
            return false;
        if (inscripto != null ? !inscripto.equals(filtro.inscripto) : filtro.inscripto != null)
            return false;
        if (meGusta != null ? !meGusta.equals(filtro.meGusta) : filtro.meGusta != null)
            return false;
        if (nombreCarrera != null ? !nombreCarrera.equals(filtro.nombreCarrera) : filtro.nombreCarrera != null)
            return false;
        if (ciudad != null ? !ciudad.equals(filtro.ciudad) : filtro.ciudad != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nombreCarrera != null ? nombreCarrera.hashCode() : 0;
        result = 31 * result + (fechaDesde != null ? fechaDesde.hashCode() : 0);
        result = 31 * result + (fechaHasta != null ? fechaHasta.hashCode() : 0);
        result = 31 * result + (modalidad != null ? modalidad.hashCode() : 0);
        result = 31 * result + (ciudad != null ? ciudad.hashCode() : 0);
        result = 31 * result + (meGusta != null ? meGusta.hashCode() : 0);
        result = 31 * result + (inscripto != null ? inscripto.hashCode() : 0);
        result = 31 * result + (corrida != null ? corrida.hashCode() : 0);
        return result;
    }


}
