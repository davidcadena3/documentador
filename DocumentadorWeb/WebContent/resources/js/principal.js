$(document).ready(function() {
	$("#addRepository").click(loadModal);
	$("#testButton").click(testRepo);
	$("#addButton").click(addRepo);
	$("#cancelButton").click(closeModal);

});

function testRepo() {
	if (validateAddRepo()) {
		callTestRepoService();
	}
}

function addRepo() {
	if (validateAddRepo()) {
		callAddRepoService();
	}
}

function loadModal() {
	$("#addRepoModal").show();
}

function closeModal() {
	$("#addRepoModal").hide();
}

function validateAddRepo() {
	var response = true;
	var repoName = $("#repoName").val();
	var repoPath = $("#repoPath").val();
	var repoPackage = $("#repoPackage").val();
	if (!repoName || repoName == undefined) {
		response = false;
		$("#testResponse").html(
				'<span class="label label-danger">Name is required</span>');
	} else if (!repoPath || repoPath == undefined) {
		response = false;
		$("#testResponse").html(
				'<span class="label label-danger">Path is required</span>');
	} else if (!repoPackage || repoPackage == undefined) {
		response = false;
		$("#testResponse")
				.html(
						'<span class="label label-danger">Main package is required</span>');
	}
	return response;
}

function callTestRepoService() {
	var repoPath = $("#repoPath").val();
	var repoPackage = $("#repoPackage").val();

	repoPath = repoPath.replace(/\//g, "@");

	$("#pleaseWaitDialog").show();
	$("#addRepoModal").hide();

	$
			.get(
					("rest/servicios/testRepo/" + repoPath + "/" + repoPackage),
					function(data) {
						if (data == true || data == "true") {
							$("#testResponse")
									.html(
											'<span class="label label-success">Successful test</span>');
						} else {
							$("#testResponse")
									.html(
											'<span class="label label-danger">Error in test</span>');
						}

						$("#pleaseWaitDialog").hide();
						$("#addRepoModal").show();
					});
}

function callAddRepoService() {
	var repoName = $("#repoName").val();
	var repoPath = $("#repoPath").val();
	var repoPackage = $("#repoPackage").val();
	var oriPath = repoPath;

	repoPath = repoPath.replace(/\//g, "@");

	$("#pleaseWaitDialog").show();
	$("#addRepoModal").hide();

	$
			.get(
					("rest/servicios/testRepo/" + repoPath + "/" + repoPackage),
					function(data) {
						if (data == true || data == "true") {
							var oldRepo = $(("#id_" + repoName)).attr('id');
							if (!oldRepo && oldRepo !== 'undefined') {
								console.log("add new repo");
								$("#testResponse")
										.html(
												'<span class="label label-success">Successful test</span>');
								var newRepo = '<tr id="id_'
										+ repoName
										+ '"><td>'
										+ repoName
										+ '</td><td><small>'
										+ oriPath
										+ '</small></td><td><small>'
										+ repoPackage
										+ '</small></td><td><button id="btn_'
										+ repoName
										+ '" type="button" class="btn btn-danger outline deleteRepo" style="outline: none;" dataid="id_'
										+ repoName
										+ '"><i class="fa fa-trash-o"></i></button></td></tr>';

								$("#repositories").append(newRepo);

								$(("#btn_" + repoName)).click(function() {
									var delId = ("#" + $(this).attr('dataid'));
									$(delId).remove();
								});

								$("#addRepoModal").hide();
							} else {
								console.log("invalid repo name");
								$("#testResponse")
										.html(
												'<span class="label label-danger">Invalid repository name</span>');
								$("#addRepoModal").show();
							}

						} else {
							$("#testResponse")
									.html(
											'<span class="label label-danger">Error in test</span>');
							$("#addRepoModal").show();
						}
						$("#pleaseWaitDialog").hide();

					});
}