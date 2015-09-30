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
									console.log("json: " + JSON.stringify(json));
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
		console.error(json.campos[i])
		
		var textField = "<tr><th scope=\"row\">1</th><td>"+json.campos[i].nombre+"</td><td>"+json.campos[i].tipo+"</td><td>";
		
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
	
	$("#documentacionClase").show();
}