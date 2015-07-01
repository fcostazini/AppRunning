package studios.thinkup.com.apprunning.model;

/**
 * Created by fcostazini on 01/07/2015.
 * Distancias
 */
public enum Distancias {

TODAS(0,0,1000),DESDE_0_A_9(1,0,9), DESDE_10_A_21(2,10,21),DESDE_21_A_41(3,21,41),MAS_41(4,41,1000);



    private Integer min;
    private Integer max;
    private Integer id;

    Distancias(Integer id,Integer min, Integer max) {
        this.min = min;
        this.max = max;
        this.id = id;
    }

    public Integer getMin() {
        return min;
    }

    public Integer getMax() {
        return max;
    }

    public Integer getId() {
        return id;
    }

    public static Distancias getById(Integer id){
        for(Distancias d : values()){
            if(d.getId().equals(id)){
                return d;
            }
        }
        return null;
    }
}
