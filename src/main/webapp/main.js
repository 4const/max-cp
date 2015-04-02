$(function() {

	var MARITAL_STATUSES = {};
	var __selectedRow = undefined;
	var __selectedRecruit = undefined;

	var __editedRecruit = undefined;

	var __recruits = [];


var onLogin = function(user) {
    	$('#main').removeClass('hidden');
		$('#loginModal').modal('hide');

		$('#username').text(user.name);

		$('#userrole').text(user.admin ? 'Администратор' : 'Пользователь');
		if (user.admin) {
            $('#recruitBtnPanel').removeClass('hidden');
		} else {
            $('#recruitBtnPanel').addClass('hidden');
		}

		readMaritalStatuses();
		refreshRecruits();
    };

    var onLogout = function() {
		$('#main').addClass('hidden');
		$('#loginModal').modal('show');

		$('#students').find('tr').remove();
		$('#groups').find('tr').remove();
	};

	var checkLogin = function() {
		$.ajax({
			url: '/user',
			method: 'GET',
		}).done(function(user) {
			if (user) {
				onLogin(user);
			} else {
				onLogout();
			}
		});
	};

	$('#loginBtn').on('click', function() {
		$('#loginFieldError').addClass('hidden');
		$('#loginServerError').addClass('hidden');

		var login = $('#login').val();
		var password = $('#password').val();

		var fd = new FormData();
		fd.append('username', login);
		fd.append('password', password);

		$.ajax({
			url: '/login',
			method: 'POST',
			contentType: false,
			processData: false,
			data: fd
		}).done(function(user) {
			if (user.status === 'Fail') {
				$('#loginFieldError').removeClass('hidden');
				return;
			}
			$('#login').val('');
			$('#password').val('');
            onLogin(user);
		}).fail(function(e) {
			$('#loginServerError').removeClass('hidden');
		});
	});

	$('#logoutBtn').on('click', function() {
		onLogout();

		$.ajax({
			url: 'logout',
			method: 'POST',
			contentType: false,
			processData: false
		});
	});

	var calculateAge = function(birthday) {
		var ageDifMs = Date.now() - birthday.getTime();
		var ageDate = new Date(ageDifMs);
		return Math.abs(ageDate.getUTCFullYear() - 1970);
	}

	var readMaritalStatuses = function() {
		$.ajax({
			url: '/maritalStatus',
			method: 'GET',
			async: false
		}).done(function(statuses) {
			var select = $('#maritalStatus');
			select.find('option').remove();

			statuses.forEach(function(s) {
				MARITAL_STATUSES[s.id] = s.name;

				var option = $('<option>');
				option.text(s.name);
				option.attr('value', s.id);
				select.append(option);
			});
		});
	}

	var fillRecruitTable = function(recruits) {
		var table = $('#recruit-table-body');
		table.find('tr').remove();

		recruits.forEach(function(r) {
			var row = $('<tr>');
			row.append($('<td>').text(r.id));
			row.append($('<td>').text(r.lastName));
			row.append($('<td>').text(r.firstName));
			row.append($('<td>').text(r.dateOfBirth));
			row.append($('<td>').text(calculateAge(new Date(r.dateOfBirth))));
			row.append($('<td>').text(r.address));
			row.append($('<td>').text(MARITAL_STATUSES[r.maritalStatusId]));

			row.data('id', r.id);
			table.append(row);
		});
	}

	var refreshRecruits = function() {
		$.ajax({
			url: '/recruit',
			method: 'GET',
		}).done(function(recruits) {
			__recruits = recruits;
			fillRecruitTable(__recruits);
		});
	};

	var fillRecruitForm = function(r) {
		$('#recruitFieldError').addClass('hidden');
		$('#recruitServerError').addClass('hidden');
		if (!r) { r = {}; }

		$('#lastName').val(r.lastName);
		$('#firstName').val(r.firstName);
		$('#dateOfBirth').val(r.dateOfBirth);
		$('#address').val(r.address);
		$('#maritalStatus').val(r.maritalStatusId);

		__editedRecruit = r;
	};

	$('#recruits').on('click', 'tr', function() {
		if ($(this).hasClass('selected')) {
			$(this).removeClass('selected');

			__selectedRow = undefined;
		} else {
			$('#recruits').find('tr.selected').removeClass('selected');
			$(this).addClass('selected');

			var selectedId = $(this).data('id');
			__selectedRecruit = $.extend({}, $.grep(__recruits, function(r) { return r.id == selectedId })[0]);
		}
	});

	$('#newRecruitBtn').on('click', function() {
		fillRecruitForm();
	});

	$('#editRecruitBtn').on('click', function() {
		if (!__selectedRecruit) {
			return false;
		}

		fillRecruitForm(__selectedRecruit);
	});

	$('#saveRecruitBtn').on('click', function() {
		$('#recruitFieldError').addClass('hidden');
		$('#recruitServerError').addClass('hidden');

		__editedRecruit.lastName = $('#lastName').val();
		__editedRecruit.firstName = $('#firstName').val();
		__editedRecruit.dateOfBirth = $('#dateOfBirth').val();
		__editedRecruit.address = $('#address').val();
		__editedRecruit.maritalStatusId = $('#maritalStatus').val();

		if (!__editedRecruit.lastName ||
			!__editedRecruit.firstName ||
			!__editedRecruit.dateOfBirth ||
			!__editedRecruit.address ||
			isNaN(__editedRecruit.maritalStatusId)) {
			$('#recruitFieldError').removeClass('hidden');
			return false;
		}

		$.ajax({
			url: '/recruit',
			method: 'PUT',
			contentType: 'application/json',
			data: JSON.stringify(__editedRecruit)
		}).done(function(res) {
			refreshRecruits();

			$('#recruitModal').modal('toggle');
		}).fail(function() {
			$('#recruitServerError').removeClass('hidden');
		});
	});

	$('#deleteRecruitBtn').on('click', function(e) {
		if (!__selectedRecruit) {
			return false;
		}

		$.ajax({
			url: '/recruit?id=' + __selectedRecruit.id,
			method: 'DELETE',
		}).done(function(res) {
			refreshRecruits();
		});
	});

	$('#searchBtn').on('click', function() {
		var searchString = $('#search').val();
		searchString = searchString.split('*').join('([а-яА-Я\\d]*)')
		searchString = searchString.split('?').join('([а-яА-Я\\d]?)')
		var re = new RegExp(searchString, 'gi');

		var filtered = __recruits.filter(function(s) {
			return s.lastName.match(re) || s.firstName.match(re);
		});

		fillRecruitTable(filtered);
	});

	$('#searchResetBtn').on('click', function() {
		var searchString = $('#search').val('');
		fillRecruitTable(__recruits);
	});

	checkLogin();
});