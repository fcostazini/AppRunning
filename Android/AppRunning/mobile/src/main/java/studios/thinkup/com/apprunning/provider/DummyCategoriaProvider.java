package studios.thinkup.com.apprunning.provider;

import java.util.List;
import java.util.Random;
import java.util.Vector;

import studios.thinkup.com.apprunning.model.Categoria;

/**
 * Created by fcostazini on 22/05/2015..
 * Clase Dummy para obtener Categorias
 */
public class DummyCategoriaProvider {


    private Categoria[] zonas =
            {new Categoria("Buenos Aires",6),
                    new Categoria("Catamarca",24),
                    new Categoria("Ciudad Autónoma de Buenos Aires",35),
                    new Categoria("Chaco",44),
                    new Categoria("Chubut",43),
                    new Categoria("Córdoba",18),
                    new Categoria("Corrientes",13),
                    new Categoria("Entre Ríos",32),
                    new Categoria("Formosa",44),
                    new Categoria("Jujuy",5),
                    new Categoria("La Pampa",11),
                    new Categoria("La Rioja",37),
                    new Categoria("Mendoza",5),
                    new Categoria("Misiones",15),
                    new Categoria("Neuquén",14),
                    new Categoria("Río Negro",6),
                    new Categoria("Salta",39),
                    new Categoria("San Juan",39),
                    new Categoria("San Luis",26),
                    new Categoria("Santa Cruz",45),
                    new Categoria("Santa Fe",15),
                    new Categoria("Santiago del Estero",35),
                    new Categoria("Tierra del Fuego, Antártida e Islas del Atlántico Sur",14),
                    new Categoria("Tucumán",43)};

    private Categoria[] generos ={
            new Categoria("Solo Mujer",50),
            new Categoria("Solo Hombre",20),
            new Categoria("Todos",80)
    };

    private Categoria[] distancias = {
            new Categoria("5 Km", 37),
            new Categoria("10 Km", 4),
            new Categoria("15 Km", 10),
            new Categoria("20 Km", 36),
            new Categoria("25 Km", 18),
            new Categoria("30 Km", 26),
            new Categoria("35 Km", 23),
            new Categoria("40 Km", 23),
            new Categoria("45 Km", 42),
            new Categoria("50 Km", 8)
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
