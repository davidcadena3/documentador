package co.com.documentador.modelo;

public class Parametro {

	private String nombre;
	private String tipo;
	private Visibilidad visibilidad;
	private Boolean esEstatico;
	private Boolean esFinal;
	private String comentario;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Visibilidad getVisibilidad() {
		return visibilidad;
	}

	public void setVisibilidad(Visibilidad visibilidad) {
		this.visibilidad = visibilidad;
	}

	public Boolean getEsEstatico() {
		return esEstatico;
	}

	public void setEsEstatico(Boolean esEstatico) {
		this.esEstatico = esEstatico;
	}

	public Boolean getEsFinal() {
		return esFinal;
	}

	public void setEsFinal(Boolean esFinal) {
		this.esFinal = esFinal;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	@Override
	public String toString() {
		StringBuilder stb = new StringBuilder(this.getClass().getName());
		stb.append("[");
		stb.append("visibilidad: " + visibilidad);
		stb.append(", esEstatico: " + esEstatico);
		stb.append(", esFinal: " + esFinal);
		stb.append(", nombre: " + nombre);
		stb.append(", tipo: " + tipo);
		stb.append("]");

		return stb.toString();
	}

}
