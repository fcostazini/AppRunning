package studios.thinkup.com.apprunning.provider;

import java.util.List;

/**
 * Created by fcostazini on 10/08/2015.
 */
public interface IFiltrosProvider {
    List<String> getProvincias();

    List<String> getCiudades(String provincia);
}
