package studios.thinkup.com.apprunning.provider;

import java.util.List;
import java.util.Random;
import java.util.Vector;

import studios.thinkup.com.apprunning.model.Categoria;
import studios.thinkup.com.apprunning.model.Subcategoria;

/**
 * Created by fcostazini on 22/05/2015..
 * Clase Dummy para obtener Categorias
 */
public class DummyCategoriaProvider {


    private Categoria[] zonas =
            {new Categoria("Buenos Aires",6,Subcategoria.ZONA),
                    new Categoria("Catamarca",24, Subcategoria.ZONA),
                    new Categoria("Ciudad Autónoma de Buenos Aires",35, Subcategoria.ZONA),
                    new Categoria("Chaco",44, Subcategoria.ZONA),
                    new Categoria("Chubut",43, Subcategoria.ZONA),
                    new Categoria("Córdoba",18, Subcategoria.ZONA),
                    new Categoria("Corrientes",13, Subcategoria.ZONA),
                    new Categoria("Entre Ríos",32, Subcategoria.ZONA),
                    new Categoria("Formosa",44, Subcategoria.ZONA),
                    new Categoria("Jujuy",5, Subcategoria.ZONA),
                    new Categoria("La Pampa",11, Subcategoria.ZONA),
                    new Categoria("La Rioja",37, Subcategoria.ZONA),
                    new Categoria("Mendoza",5, Subcategoria.ZONA),
                    new Categoria("Misiones",15, Subcategoria.ZONA),
                    new Categoria("Neuquén",14, Subcategoria.ZONA),
                    new Categoria("Río Negro",6, Subcategoria.ZONA),
                    new Categoria("Salta",39, Subcategoria.ZONA),
                    new Categoria("San Juan",39, Subcategoria.ZONA),
                    new Categoria("San Luis",26, Subcategoria.ZONA),
                    new Categoria("Santa Cruz",45, Subcategoria.ZONA),
                    new Categoria("Santa Fe",15, Subcategoria.ZONA),
                    new Categoria("Santiago del Estero",35, Subcategoria.ZONA),
                    new Categoria("Tierra del Fuego, Antártida e Islas del Atlántico Sur",14, Subcategoria.ZONA),
                    new Categoria("Tucumán",43, Subcategoria.ZONA)};

    private Categoria[] generos ={
            new Categoria("Solo Mujer",50,Subcategoria.GENERO),
            new Categoria("Solo Hombre",20,Subcategoria.GENERO),
            new Categoria("Todos",80,Subcategoria.GENERO)
    };

    private Categoria[] distancias = {
            new Categoria("5 Km", 37, Subcategoria.DISTANCIA),
            new Categoria("10 Km", 4, Subcategoria.DISTANCIA),
            new Categoria("15 Km", 10, Subcategoria.DISTANCIA),
            new Categoria("20 Km", 36, Subcategoria.DISTANCIA),
            new Categoria("25 Km", 18, Subcategoria.DISTANCIA),
            new Categoria("30 Km", 26, Subcategoria.DISTANCIA),
            new Categoria("35 Km", 23, Subcategoria.DISTANCIA),
            new Categoria("40 Km", 23, Subcategoria.DISTANCIA),
            new Categoria("45 Km", 42, Subcategoria.DISTANCIA),
            new Categoria("50 Km", 8, Subcategoria.DISTANCIA)
    };

    public Categoria getRandomCategoriaZona() {
        return this.getRandomCategoria(zonas);

    };

    public Categoria getRandomCategoriaDistancia() {
        return this.getRandomCategoria(distancias);

    };



    public Categoria getRandomCategoriaGenero() {
        return this.getRandomCategoria(generos);

    };

    private Categoria getRandomCategoria(Categoria[] categorias){
        Random r = new Random();
        return categorias[r.nextInt(((categorias.length-1)) + 1)];
    }

    public Categoria getCategoriaZonaByName(String nombre){
        return this.getCategoriaByName(zonas,nombre);
    }

private Categoria getCategoriaByName(Categoria[] categorias, String nombre){
    for(Categoria c : categorias){
        if(c.getNombre().toLowerCase().equals(nombre.toLowerCase())){
            return c;
        }
    }
    return null;
}
    public Categoria getCategoriaDistanciaByName(String nombre){
        return this.getCategoriaByName(distancias,nombre);
    }
    public Categoria getCategoriaGeneroByName(String nombre){
        return this.getCategoriaByName(generos,nombre);
    }

    public List<Categoria> getAllZona(){
        return this.getAll(zonas);
    }

    public List<Categoria> getAllDistancia(){
        return this.getAll(distancias);
    }

    public List<Categoria> getAllGenero(){
        return this.getAll(generos);
    }

    private List<Categoria> getAll(Categoria[] categorias) {
        List<Categoria> lista = new Vector<>();
        for(Categoria c : categorias){
            lista.add(c);
        }
        return lista;

    }
}
