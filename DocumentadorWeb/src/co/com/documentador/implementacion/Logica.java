package co.com.documentador.implementacion;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.ConstructorDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.RootDoc;

import co.com.documentador.modelo.Clase;
import co.com.documentador.modelo.Documento;
import co.com.documentador.modelo.Metodo;
import co.com.documentador.modelo.Parametro;
import co.com.documentador.modelo.Visibilidad;

/**
 * Clase contenedora de la lógica de la aplicación
 * 
 * @author davidcadena
 *
 */
public class Logica {

	private static final String RUTA_PROYECTO = "/home/davidcadena/Documents/Workspaces/LandingPages/DocumentadorWeb/src";
	private static final String PAQUETE_BASE = "co";

	private static TreeMap<String, List<Clase>> indice;

	public TreeMap<String, List<Clase>> crearIndice() {

		indice = new TreeMap<String, List<Clase>>();

		Documentador doclet = new Documentador(new File(RUTA_PROYECTO), PAQUETE_BASE);
		RootDoc doc = doclet.getRootDoc();

		ClassDoc[] clases = doc.classes();

		for (ClassDoc clase : clases) {

			String paquete = clase.containingPackage().name();

			if (!indice.containsKey(paquete)) {
				indice.put(paquete, new ArrayList<Clase>());
			}

			Clase aux = new Clase();
			aux.setNombre(clase.name());

			indice.get(paquete).add(aux);
			Collections.sort(indice.get(paquete));
		}

		return indice;

	}

	public Documento crearEstructura(String nombrePaquete, String nombreClase) {

		Documentador doclet = new Documentador(new File(RUTA_PROYECTO), nombrePaquete, nombreClase);

		RootDoc doc = doclet.getRootDoc();
		ClassDoc[] clases = doc.classes();

		Documento documento = new Documento();
		documento.setClases(new ArrayList<Clase>());

		for (ClassDoc clase : clases) {

			Clase auxClase = new Clase();
			auxClase.setNombre(clase.name());
			auxClase.setComentario(clase.commentText());
			auxClase.setVisibilidad(getVisibilidad(clase));
			auxClase.setCampos(new ArrayList<Parametro>());
			auxClase.setConstructores(new ArrayList<Metodo>());
			auxClase.setMetodos(new ArrayList<Metodo>());
			auxClase.setEsEnum(clase.isEnum());
			auxClase.setEsInterfaz(clase.isInterface());
			auxClase.setEsAbstracta(clase.isAbstract());
			auxClase.setEsFinal(clase.isFinal());
			auxClase.setEsSerializable(clase.isSerializable());
			auxClase.setEsEstatica(clase.isStatic());

			FieldDoc[] campos = clase.fields();

			for (FieldDoc campo : campos) {
				Parametro parametro = new Parametro();
				parametro.setNombre(campo.name());
				parametro.setTipo(campo.type().typeName());
				parametro.setVisibilidad(getVisibilidad(campo));
				parametro.setEsEstatico(campo.isStatic());
				parametro.setEsFinal(campo.isFinal());

				auxClase.getCampos().add(parametro);
			}

			ConstructorDoc[] constructores = clase.constructors();

			for (ConstructorDoc constructor : constructores) {

				Metodo auxMetodo = new Metodo();
				auxMetodo.setNombre(constructor.name());
				auxMetodo.setComentario(constructor.commentText());
				auxMetodo.setVisibilidad(getVisibilidad(constructor));
				auxMetodo.setParametros(new ArrayList<Parametro>());

				Parameter[] parametros = constructor.parameters();

				for (Parameter parametro : parametros) {
					Parametro auxParametro = new Parametro();
					auxParametro.setNombre(parametro.name());
					auxParametro.setTipo(parametro.type().typeName());

					auxMetodo.getParametros().add(auxParametro);
				}

				auxClase.getConstructores().add(auxMetodo);
			}

			MethodDoc[] metodos = clase.methods();

			for (MethodDoc metodo : metodos) {

				Metodo auxMetodo = new Metodo();
				auxMetodo.setNombre(metodo.name());
				auxMetodo.setComentario(metodo.commentText());
				auxMetodo.setVisibilidad(getVisibilidad(metodo));
				auxMetodo.setParametros(new ArrayList<Parametro>());
				auxMetodo.setEsEstatico(metodo.isStatic());
				auxMetodo.setEsFinal(metodo.isFinal());
				auxMetodo.setEsSincrono(metodo.isSynchronized());

				Parameter[] parametros = metodo.parameters();

				for (Parameter parametro : parametros) {
					Parametro auxParametro = new Parametro();
					auxParametro.setNombre(parametro.name());
					auxParametro.setTipo(parametro.type().typeName());

					auxMetodo.getParametros().add(auxParametro);
				}

				Parametro retorno = new Parametro();
				retorno.setNombre(metodo.returnType().typeName());
				auxMetodo.setRetorno(retorno);

				auxClase.getMetodos().add(auxMetodo);

			}

			documento.getClases().add(auxClase);
		}

		return documento;
	}

	private static Visibilidad getVisibilidad(ClassDoc clase) {
		if (clase.isPrivate()) {
			return Visibilidad.PRIVADO;
		} else if (clase.isProtected()) {
			return Visibilidad.PROTEGIDO;
		} else {
			return Visibilidad.PUBLICO;
		}
	}

	private static Visibilidad getVisibilidad(MethodDoc metodo) {
		if (metodo.isPrivate()) {
			return Visibilidad.PRIVADO;
		} else if (metodo.isProtected()) {
			return Visibilidad.PROTEGIDO;
		} else {
			return Visibilidad.PUBLICO;
		}
	}

	private static Visibilidad getVisibilidad(ConstructorDoc metodo) {
		if (metodo.isPrivate()) {
			return Visibilidad.PRIVADO;
		} else if (metodo.isProtected()) {
			return Visibilidad.PROTEGIDO;
		} else {
			return Visibilidad.PUBLICO;
		}
	}

	private static Visibilidad getVisibilidad(FieldDoc campo) {
		if (campo.isPrivate()) {
			return Visibilidad.PRIVADO;
		} else if (campo.isProtected()) {
			return Visibilidad.PROTEGIDO;
		} else {
			return Visibilidad.PUBLICO;
		}
	}

}
