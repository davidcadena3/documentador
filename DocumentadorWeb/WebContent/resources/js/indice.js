/**
 * funcion inicialmente lanzada
 */
$(document).ready(function() {
	$("#documentacionClase").hide();
	loadGtable();
});

function loadGtable() {
	jQuery('#gtreetable').gtreetable(
			{
				'source' : function(id) {
					return {
						type : 'GET',
						url : 'rest/servicios/crearIndice/' + id,
						dataType : 'json',
						async : true,
						success:function(){
							hideActions();
						},
						error : function(XMLHttpRequest) {
							alert("Error in service response!!!");
						}
					}
				},
				  'onSelect':function (oNode) {
					  var pack = oNode.getId();
					  var cla = oNode.getName();
					  if(pack!=cla){
						  $.ajax({
								type : "GET",
								url : ("rest/servicios/consultarDocumentacion/" + pack+"/"+cla),
								success : function(json) {
									loadDocumentation(json);
								},
								error : function(XMLHttpRequest) {
									alert("Error in service response!!!");
								}
							});
					  }
					  },
				  'types': { default: 'fa fa-code', class: 'fa fa-circle', interface: 'fa fa-circle-o', enum: 'fa fa-square-o'},
				  'readonly': true,
				  'showExpandIconOnEmpty' : false
			});
}

function hideActions(){
	$(".btn btn-sm btn-default dropdown-toggle").css("display","none !important");
// $("[data-level='1'] span .node-icon-ce").each(function() {
// $(this).addClass("hidespan");
// });
}

function loadDocumentation(json){
	$("#className").html(json.nombre);
	
	$(".visibilidadClase").hide();
	switch (json.visibilidad) {
	
	case "PRIVADO":
		$("#privateClass").show();
		break;
	case "PROTEGIDO":
		$("#protectedClass").show();
		break;
	case "PUBLICO":
		$("#publicClass").show();
		break;

	}
	
	$(".modificadorClase").hide();
	
	if(json.esInterfaz==true){
		$("#interfaceClase").show();
	}
	if(json.esSerializable==true){
		$("#serializableClase").show();
	}
	if(json.esAbstracta==true){
		$("#abstractClase").show();
	}
	if(json.esFinal==true){
		$("#finalClase").show();
	}
	if(json.esEstatica==true){
		$("#staticClase").show();
	}
	if(json.esEnum==true){
		$("#enumClase").show();
	}
	
	$("#commentClase").html(json.comentario);
	
	
	
	var fields = "";
	for(i = 0; i<json.campos.length;i++){
		var textField = "<tr><th scope=\"row\">"+(i+1)+"</th><td>"+json.campos[i].nombre+"</td><td>"+json.campos[i].tipo+"</td><td>";
		
		switch (json.campos[i].visibilidad) {
		case "PRIVADO":
			textField += "<span class=\"label label-danger style=\"margin-left: 5px;\">Private</span>";
			break;
		case "PROTEGIDO":
			textField += "<span class=\"label label-warning style=\"margin-left: 5px;\">Protected</span>";
			break;
		case "PUBLICO":
			textField += "<span class=\"label label-primary style=\"margin-left: 5px;\">Public</span>";
			break;
		}
		
		if(json.campos[i].esEstatico==true){
			textField += "<span class=\"label label-info\" style=\"margin-left: 5px;\">Static</span>";
		}
		
		if(json.campos[i].esFinal==true){
			textField += "<span class=\"label label-info\" style=\"margin-left: 5px;\">Final</span>";
		}
		
		textField += "</td></tr>";
		fields+=textField;
	}
	
	$("#fieldsClase").html(fields);
	
	var constructors = "";
	for(i = 0; i<json.constructores.length;i++){
		var consJson = json.constructores[i];
		var cons = "<tr><th scope=\"row\">"+(i+1)+"</th><td><b>"+consJson.nombre+"</b><br><small>"+consJson.comentario+"</small></td>";
		cons+="<td>";
		
		for(j = 0; j<consJson.parametros.length; j++){
			var par = consJson.parametros[j];
			if(j>0){
				cons+=", ";
			}
			cons+=(par.tipo+":"+par.nombre);
		}
		cons+="</td><td>";
		
		switch (consJson.visibilidad) {
		case "PRIVADO":
			cons += "<span class=\"label label-danger style=\"margin-left: 5px;\">Private</span>";
			break;
		case "PROTEGIDO":
			cons += "<span class=\"label label-warning style=\"margin-left: 5px;\">Protected</span>";
			break;
		case "PUBLICO":
			cons += "<span class=\"label label-primary style=\"margin-left: 5px;\">Public</span>";
			break;
		}
		
		cons+="</td></tr>";
		constructors+=cons;
	}
	
	$("#constructorsClase").html(constructors);
	
	
	
	
	var methods = "";
	for(i = 0; i<json.metodos.length;i++){
		var metJson = json.metodos[i];
		var mets = "<tr><th scope=\"row\">"+(i+1)+"</th><td><b>"+metJson.nombre+"</b><br><small>"+metJson.comentario+"</small></td>";
		mets+="<td>";
		
		for(j = 0; j<metJson.parametros.length; j++){
			var par = metJson.parametros[j];
			if(j>0){
				mets+=", ";
			}
			mets+=(par.tipo+":"+par.nombre);
		}
		mets+=("</td><td>"+metJson.retorno.tipo+"</td><td>");
		
		switch (metJson.visibilidad) {
		case "PRIVADO":
			mets += "<span class=\"label label-danger style=\"margin-left: 5px;\">Private</span>";
			break;
		case "PROTEGIDO":
			mets += "<span class=\"label label-warning style=\"margin-left: 5px;\">Protected</span>";
			break;
		case "PUBLICO":
			mets += "<span class=\"label label-primary style=\"margin-left: 5px;\">Public</span>";
			break;
		}
		
		if(metJson.esEstatico==true){
			mets += "<span class=\"label label-info\" style=\"margin-left: 5px;\">Static</span>";
		}
		
		if(metJson.esFinal==true){
			mets += "<span class=\"label label-info\" style=\"margin-left: 5px;\">Final</span>";
		}
		
		if(metJson.esSincrono==true){
			mets += "<span class=\"label label-info\" style=\"margin-left: 5px;\">Synchronized</span>";
		}
		
		mets+="</td></tr>";
		methods+=mets;
	}
	
	
	$("#methodsClase").html(methods);
	
	$("#documentacionClase").show();
}