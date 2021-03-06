package co.com.documentador.modelo;

import java.util.List;

public class Metodo {

	private Visibilidad visibilidad;
	private Boolean esEstatico;
	private Boolean esFinal;
	private Boolean esSincrono;
	private String nombre;
	private String comentario;
	private String autor;
	private String desde;
	private List<Parametro> parametros;
	private Parametro retorno;

	public Visibilidad getVisibilidad() {
		return visibilidad;
	}

	public void setVisibilidad(Visibilidad visibilidad) {
		this.visibilidad = visibilidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public List<Parametro> getParametros() {
		return parametros;
	}

	public void setParametros(List<Parametro> parametros) {
		this.parametros = parametros;
	}

	public Parametro getRetorno() {
		return retorno;
	}

	public void setRetorno(Parametro retorno) {
		this.retorno = retorno;
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

	public Boolean getEsSincrono() {
		return esSincrono;
	}

	public void setEsSincrono(Boolean esSincrono) {
		this.esSincrono = esSincrono;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getDesde() {
		return desde;
	}

	public void setDesde(String desde) {
		this.desde = desde;
	}

	@Override
	public String toString() {
		StringBuilder stb = new StringBuilder(this.getClass().getName());
		stb.append("[");
		stb.append("visibilidad: " + visibilidad);
		stb.append(", esEstatico: " + esEstatico);
		stb.append(", esFinal: " + esFinal);
		stb.append(", esSincrono: " + esSincrono);
		stb.append(", nombre: " + nombre);
		stb.append(", comentario: " + comentario);
		stb.append(", parametros: " + parametros);
		stb.append(", retorno: " + retorno);
		stb.append("]");

		return stb.toString();
	}

}
