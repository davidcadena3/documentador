/**
 * funcion inicialmente lanzada
 */
$(document).ready(function() {
	loadGtable();
});

function loadGtable() {
	console.error("init");
	jQuery('#gtreetable').gtreetable(
			{
				'source' : function(id) {
					return {
						type : 'GET',
						url : 'rest/servicios/crearIndice',
						data : {
							'id' : id
						},
						dataType : 'json',
						error : function(XMLHttpRequest) {
							alert(XMLHttpRequest.status + ': '
									+ XMLHttpRequest.responseText);
						}
					}
				}
			});
}