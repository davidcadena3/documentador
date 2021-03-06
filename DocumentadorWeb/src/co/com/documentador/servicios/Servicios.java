package co.com.documentador.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import co.com.documentador.implementacion.Logica;
import co.com.documentador.modelo.Clase;
import co.com.documentador.modelo.User;
import co.com.documentador.modelo.json.IndiceResponse;
import co.com.documentador.modelo.json.Node;

@Path("/servicios")
public class Servicios {

	private static Map<String, List<Clase>> indice;

	@GET
	@Path("/crearIndice/{id}")
	@Produces("application/json")
	public IndiceResponse crearIndice(@PathParam(value = "id") String id) {

		System.out.println("INICIOOOOOOOOOOOO id-" + id);

		if (indice == null) {
			Logica logica = new Logica();
			indice = logica.crearIndice();
		}

		IndiceResponse retorno = new IndiceResponse();
		retorno.nodes = new ArrayList<>();

		if (id == null || id.equals("0")) {
			for (Entry<String, List<Clase>> entry : indice.entrySet()) {
				Node node = new Node();
				node.id = entry.getKey();
				node.level = "0";
				node.name = entry.getKey();
				node.type = "default";

				retorno.nodes.add(node);
			}
		} else if (indice.containsKey(id)) {
			for (Clase clase : indice.get(id)) {
				Node node = new Node();
				node.id = id + "@node";
				node.level = "1";
				node.name = clase.getNombre();
				node.type = (clase.getEsInterfaz() != null && clase.getEsInterfaz()) ? "interface"
						: (clase.getEsEnum() != null && clase.getEsEnum()) ? "enum" : "class";

				retorno.nodes.add(node);
			}
		}

		System.out.println(retorno.nodes.size());
		return retorno;
	}

	@GET
	@Path("/consultarDocumentacion/{paquete}/{clase}")
	@Produces("application/json")
	public Clase consultarDocumentacion(@PathParam(value = "paquete") String paquete,
			@PathParam(value = "clase") String clase) {
		System.out.println("paquete: " + paquete + ", clase: " + clase);
		paquete = paquete.replace("@node", "");
		Logica logica = new Logica();

		List<Clase> aux = logica.crearEstructura(paquete, clase).getClases();
		for (Clase claseAux : aux) {
			if (claseAux.getNombre().equals(clase)) {
				return claseAux;
			}
		}

		return null;
	}

	@GET
	@Path("/testRepo/{ruta}/{paquete}")
	@Produces("application/json")
	public Boolean testRepo(@PathParam(value = "ruta") String ruta, @PathParam(value = "paquete") String paquete) {
		System.out.println("ruta: " + ruta + ", paquete: " + paquete);

		ruta = ruta.replaceAll("@", "/");
		Logica logica = new Logica();
		return logica.testRepositorio(ruta, paquete);
	}

	@GET
	@Path("/login/{username}/{password}")
	@Produces("application/json")
	public User login(@PathParam(value = "username") String username, @PathParam(value = "password") String password) {
		System.out.println("username: " + username + ", password: " + password);

		if (username.equals("admin@telintel.net")) {
			return new User("1", username, "admin");
		} else if (username.equals("editor@telintel.net")) {
			return new User("2", username, "editor");
		} else if (username.equals("landingmaster@telintel.net")) {
			return new User("3", username, "landingmaster");
		} else if (username.equals("user@telintel.net")) {
			return new User("4", username, "user");
		} else {
			return null;
		}

	}

}
