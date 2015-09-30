package co.com.documentador.modelo;

import java.util.List;

public class Clase implements Comparable<Clase> {

	private Boolean esInterfaz;
	private Boolean esEnum;
	private Boolean esAbstracta;
	private Boolean esFinal;
	private Boolean esSerializable;
	private Boolean esEstatica;
	private Visibilidad visibilidad;
	private String nombre;
	private String comentario;
	private List<Parametro> campos;
	private List<Metodo> constructores;
	private List<Metodo> metodos;

	public Boolean getEsInterfaz() {
		return esInterfaz;
	}

	public void setEsInterfaz(Boolean esInterfaz) {
		this.esInterfaz = esInterfaz;
	}

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

	public List<Parametro> getCampos() {
		return campos;
	}

	public void setCampos(List<Parametro> campos) {
		this.campos = campos;
	}

	public List<Metodo> getConstructores() {
		return constructores;
	}

	public void setConstructores(List<Metodo> constructores) {
		this.constructores = constructores;
	}

	public List<Metodo> getMetodos() {
		return metodos;
	}

	public void setMetodos(List<Metodo> metodos) {
		this.metodos = metodos;
	}

	public Boolean getEsAbstracta() {
		return esAbstracta;
	}

	public void setEsAbstracta(Boolean esAbstracta) {
		this.esAbstracta = esAbstracta;
	}

	public Boolean getEsFinal() {
		return esFinal;
	}

	public void setEsFinal(Boolean esFinal) {
		this.esFinal = esFinal;
	}

	public Boolean getEsSerializable() {
		return esSerializable;
	}

	public void setEsSerializable(Boolean esSerializable) {
		this.esSerializable = esSerializable;
	}

	public Boolean getEsEstatica() {
		return esEstatica;
	}

	public void setEsEstatica(Boolean esEstatica) {
		this.esEstatica = esEstatica;
	}

	public Boolean getEsEnum() {
		return esEnum;
	}

	public void setEsEnum(Boolean esEnum) {
		this.esEnum = esEnum;
	}

	@Override
	public String toString() {
		StringBuilder stb = new StringBuilder(this.getClass().getName());
		stb.append("[");
		stb.append("esInterfaz: " + esInterfaz);
		stb.append(", esAbstracta: " + esAbstracta);
		stb.append(", esFinal: " + esFinal);
		stb.append(", esSerializable: " + esSerializable);
		stb.append(", esEstatica: " + esEstatica);
		stb.append(", nombre: " + nombre);
		stb.append(", comentario: " + comentario);
		stb.append(", campos: " + campos);
		stb.append(", constructores: " + constructores);
		stb.append(", metodos: " + metodos);
		stb.append("]");

		return stb.toString();
	}

	@Override
	public int compareTo(Clase o) {
		return this.nombre.toLowerCase().compareTo(o.nombre.toLowerCase());
	}

}
