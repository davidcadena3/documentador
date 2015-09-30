package co.com.documentador.modelo;

import java.util.List;

public class Documento {

	private List<Clase> clases;

	public List<Clase> getClases() {
		return clases;
	}

	public void setClases(List<Clase> clases) {
		this.clases = clases;
	}

	@Override
	public String toString() {
		StringBuilder stb = new StringBuilder(this.getClass().getName());
		stb.append("[");
		stb.append("clases: " + clases);
		stb.append("]");
		return stb.toString();
	}
}
