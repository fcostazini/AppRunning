package studios.thinkup.com.apprunning.provider;

import java.util.List;

import studios.thinkup.com.apprunning.model.IObservadorCarrera;
import studios.thinkup.com.apprunning.model.entity.Carrera;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.provider.exceptions.EntityNotFoundException;

/**
 * Created by fcostazini on 22/05/2015.
 * <p/>
 * Provider de las carreras
 */
public class CarrerasProvider implements ICarrerasProvider, IObservadorCarrera {


    @Override
    public UsuarioCarrera getByIdCarrera(long carrera) {
        List<UsuarioCarrera> result = UsuarioCarrera.find(UsuarioCarrera.class, "carrera = ?", String.valueOf(carrera));
        if(result==null ||result.isEmpty()){
            Carrera c = Carrera.findById(Carrera.class,carrera);
            if(c!=null){
                UsuarioCarrera cu = new UsuarioCarrera();
                cu.setCarrera(c);
                cu.registrarObservador(this);
                return cu;
            }

        }else{
            UsuarioCarrera cu =result.get(0);
            cu.registrarObservador(this);
            return cu;
        }
        return null;
    }

    @Override
    public UsuarioCarrera actualizarCarrera(UsuarioCarrera usuarioCarrera) {
        usuarioCarrera.save();
        return usuarioCarrera;
    }


}
