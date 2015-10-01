package co.com.documentador.implementacion;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.ConstructorDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Tag;

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

	private static final String RUTA_PROYECTO = "/home/davidcadena/git/telintelsms/TelintelSms_RC3.0/src/main/java";
	private static final String PAQUETE_BASE = "com";

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
				Tag[] parmTag = constructor.tags();
				Map<String, Tag> tags = new HashMap<>();

				for (Tag tag : parmTag) {
					if (tag.name().equals("@param")) {
						ParamTag pTag = (ParamTag) tag;
						tags.put(pTag.kind() + "-" + pTag.parameterName().replace(",", ""), pTag);
					} else if (tag.name().equals("@return") || tag.name().equals("@author")
							|| tag.name().equals("@since")) {
						tags.put(tag.name(), tag);
					}
				}

				for (Parameter parametro : parametros) {
					Parametro auxParametro = new Parametro();
					auxParametro.setNombre(parametro.name());
					auxParametro.setTipo(parametro.type().typeName());
					auxParametro.setComentario(tags.containsKey("@param-" + parametro.name())
							? tags.get("@param-" + parametro.name()).text() : "");

					auxMetodo.getParametros().add(auxParametro);
				}

				auxMetodo.setAutor(tags.containsKey("@author") ? tags.get("@author").text() : "");
				auxMetodo.setDesde(tags.containsKey("@since") ? tags.get("@since").text() : "");

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
				Tag[] parmTag = metodo.tags();
				Map<String, Tag> tags = new HashMap<>();

				for (Tag tag : parmTag) {
					if (tag.name().equals("@param")) {
						ParamTag pTag = (ParamTag) tag;
						tags.put(pTag.kind() + "-" + pTag.parameterName().replace(",", ""), pTag);
					} else if (tag.name().equals("@return") || tag.name().equals("@author")
							|| tag.name().equals("@since")) {
						tags.put(tag.name(), tag);
					}
				}

				for (Parameter parametro : parametros) {
					Parametro auxParametro = new Parametro();
					auxParametro.setNombre(parametro.name());
					auxParametro.setTipo(parametro.type().typeName());
					auxParametro.setComentario(tags.containsKey("@param-" + parametro.name())
							? tags.get("@param-" + parametro.name()).text() : "");

					auxMetodo.getParametros().add(auxParametro);
				}

				Parametro retorno = new Parametro();
				retorno.setTipo(metodo.returnType().typeName());
				retorno.setComentario(tags.containsKey("@return") ? tags.get("@return").text() : "");
				auxMetodo.setRetorno(retorno);

				auxMetodo.setAutor(tags.containsKey("@author") ? tags.get("@author").text() : "");
				auxMetodo.setDesde(tags.containsKey("@since") ? tags.get("@since").text() : "");

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
