package studios.thinkup.com.apprunning.model;

/**
 * Created by fcostazini on 22/05/2015.
 */
public class Categoria implements Comparable<Categoria>{

    private String nombre;
    private Integer cantidad;

    public Categoria( String nombre, Integer cantidad) {
        this.cantidad = cantidad;
        this.nombre = nombre;
    }

    public Categoria( String nombre) {
        this.nombre = nombre;
        this.cantidad = 0;
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

    @Override
    public int hashCode() {
        return nombre.hashCode();
    }

    @Override
    public int compareTo(Categoria categoria) {
        if(categoria.equals(this)){
            return this.getCantidad().compareTo(categoria.getCantidad());
        }else{
            return this.getNombre().compareTo(categoria.getNombre());
        }
    }
}
