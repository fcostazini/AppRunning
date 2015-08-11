package studios.thinkup.com.apprunning.model.entity;

import studios.thinkup.com.apprunning.model.Subcategoria;

/**
 * Created by fcostazini on 22/05/2015.
 */
public class Categoria implements Comparable<Categoria> {

    private String nombre;
    private Integer cantidad;
    private Subcategoria tipo;

    public Categoria(String nombre, Integer cantidad, Subcategoria tipo) {
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public Categoria(String nombre, Subcategoria tipo) {
        this.nombre = nombre;
        this.cantidad = 0;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Categoria categoria = (Categoria) o;

        return nombre.equals(categoria.nombre);

    }

    public Subcategoria getTipo() {
        return tipo;
    }

    public void setTipo(Subcategoria tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        return nombre.hashCode();
    }

    @Override
    public int compareTo(Categoria categoria) {
        if (categoria.equals(this)) {
            return this.getCantidad().compareTo(categoria.getCantidad());
        } else {
            return this.getNombre().compareTo(categoria.getNombre());
        }
    }
}
