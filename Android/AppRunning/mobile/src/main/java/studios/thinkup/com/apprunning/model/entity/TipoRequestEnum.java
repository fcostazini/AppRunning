package studios.thinkup.com.apprunning.model.entity;

public enum TipoRequestEnum {
	SOLICITUD_AMIGO(1), ACEPTAR_AMIGO(2), RECHAZAR_AMIGO(3), 
	QUITA_AMIGO(4), BLOQUEAR_AMIGO(5), DESBLOQUEAR_AMIGO(6);
	private Integer id;

	public Integer getId() {
		return id;
	}

	private TipoRequestEnum(Integer id) {
		this.id = id;
	}

	public static TipoRequestEnum getById(int idTipo) {
		for (TipoRequestEnum e : values()) {
			if(e.id == idTipo){
				return e;
			}
		}
		return null;
	}

}
