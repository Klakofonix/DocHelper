<?php
	use Psr\Http\Message\ResponseInterface as Response;
	use Psr\Http\Message\ServerRequestInterface as Request;
	use Slim\Factory\AppFactory;

	require '../vendor/autoload.php';
	require '../includes/DbOperations.php';

	$app = AppFactory::create();
	$app->setBasePath("/MyDocApi/public");
	$app->addErrorMiddleware(true, true, true);

	/**
		endpoint		:		createUser
		parameters		:		login, password, name, surname, role
		method			:		POST
	**/

	$app->post('/createuser', function(Request $request, Response $response) {
		if (!haveEmptyParameters(array('login', 'password', 'name', 'surname', 'role'), $response)) {
			$request_data = $request->getParsedBody();

			$login = $request_data['login'];
			$password = $request_data['password'];
			$name = $request_data['name'];
			$surname = $request_data['surname'];
			$role = $request_data['role'];

			$hash_password = password_hash($password, PASSWORD_DEFAULT);

			$db = new DbOperations;

			$result = $db->createUser($login, $hash_password, $name, $surname, $role);

			if ($result == USER_CREATED) {
				$message = array();
				$message['error'] = false;
				$message['message'] = 'User Created Successfully.';

				$response->getBody()->write(json_encode($message));

				return $response
									->withHeader('Content-type', 'application/json')
									->withStatus(201);

			} else if ($result == USER_FAILURE) {
				$message = array();
				$message['error'] = true;
				$message['message'] = 'Some error occurred.';

				$response->getBody()->write(json_encode($message));

				return $response
									->withHeader('Content-type', 'application/json')
									->withStatus(422);

			} else if ($result == USER_EXISTS) {
				$message = array();
				$message['error'] = true;
				$message['message'] = 'User Already Exists.';

				$response->getBody()->write(json_encode($message));

				return $response
									->withHeader('Content-type', 'application/json')
									->withStatus(422);
			}
		}

		return $response
							->withHeader('Content-type', 'application/json')
							->withStatus(422);
	});
	/**
		endpoint		:		userLogin
		parameters		:		login, password
		method			:		POST
	**/

	$app->post('/userlogin',function(Request $request, Response $response){

		if(!haveEmptyParameters(array('login','password'), $response)){
			$request_data = $request->getParsedBody();

			$login = $request_data['login'];
			$password = $request_data['password'];

			$db = new DbOperations;

			$result = $db->userLogin($login, $password);

			if($result == USER_AUTHENTICATED){
				$user = $db->getUserByLogin($login);
				$response_data = array();

				$response_data['error'] = false;
				$response_data['message'] = 'Login Successful';
				$response_data['user'] = $user;

				$response->getBody()->write(json_encode($response_data));

				return $response
					->withHeader('Content-type', 'application/json')
					->withStatus(200);
 

			}else if($result == USER_NOT_FOUND){
				$response_data['error'] = true;
				$response_data['message'] = 'User not exist';

				$response->getBody()->write(json_encode($response_data));

				return $response
					->withHeader('Content-type', 'application/json')
					->withStatus(200);
 

			}else if($result == USER_PASSWORD_DO_NOT_MATCH)
			$response_data['error'] = true;
			$response_data['message'] = 'Invalid password';

			$response->getBody()->write(json_encode($response_data));

			return $response
				->withHeader('Content-type', 'application/json')
				->withStatus(200);


		}
		return $response
							->withHeader('Content-type', 'application/json')
							->withStatus(422);

	});

	/**
		endpoint		:		viewAllPatients
		parameters		:		null
		method			:		GET
	**/

	$app->get('/allpatients',function(Request $request, Response $response){

		$db = new DbOperations;
		
		$patients = $db->getAllPatients();

		$response_data = array();
		
		$response_data['error'] = false;
		$response_data['patients'] = $patients;

		$response->getBody()->write(json_encode($response_data));

			return $response
				->withHeader('Content-type', 'application/json')
				->withStatus(200);

	});

		/**
		endpoint		:		getPatient
		parameters		:		id
		method			:		GET
	**/

	$app->get('/getpatient',function(Request $request, Response $response){

		$request_data = $request->getQueryParams();
		$id = $request_data['id'];
		$db = new DbOperations;
		
		$patients = $db->getPatient($id);

		$response_data = array();
		
		$response_data['error'] = false;
		$response_data['patients'] = $patients;

		$response->getBody()->write(json_encode($response_data));

			return $response
				->withHeader('Content-type', 'application/json')
				->withStatus(200);

	});

	/**
		endpoint		:		viewAllDiseases
		parameters		:		id
		method			:		GET
	**/

	$app->get('/alldiseases',function(Request $request, Response $response){
	
		$request_data = $request->getQueryParams();
		$id = $request_data['id'];
		$db = new DbOperations;		
		$diseases = $db->getAllChronicDiseases($id);

		$response_data = array();
		
		$response_data['error'] = false;
		$response_data['diseases'] = $diseases;

		$response->getBody()->write(json_encode($response_data));

			return $response
				->withHeader('Content-type', 'application/json')
				->withStatus(200);

				
	});
	/**
		endpoint		:		viewAllSensization
		parameters		:		id
		method			:		GET
	**/

	$app->get('/allsensizations',function(Request $request, Response $response){
	
		$request_data = $request->getQueryParams();
		$id = $request_data['id'];
		$db = new DbOperations;		
		$sensizations = $db->getAllSensizations($id);

		$response_data = array();
		
		$response_data['error'] = false;
		$response_data['sensization'] = $sensizations;

		$response->getBody()->write(json_encode($response_data));

			return $response
				->withHeader('Content-type', 'application/json')
				->withStatus(200);

				
	});

	/**
		endpoint		:		viewPatientsCards
		parameters		:		id
		method			:		GET
	**/

	$app->get('/allpatientscards',function(Request $request, Response $response){

		$request_data = $request->getQueryParams();
		$id = $request_data['id'];
		$db = new DbOperations;
		
		$patientscards = $db->getAllPatientsCards($id);

		$response_data = array();
		
		$response_data['error'] = false;
		$response_data['patientscards'] = $patientscards;

		$response->getBody()->write(json_encode($response_data));

			return $response
				->withHeader('Content-type', 'application/json')
				->withStatus(200);

	});

	/**
		endpoint		:		viewMedicines
		parameters		:		id
		method			:		GET
	**/

	$app->get('/medicines',function(Request $request, Response $response){

		$request_data = $request->getQueryParams();
		$id = $request_data['id'];
		$db = new DbOperations;
		
		$medicines = $db->getMedicines($id);

		$response_data = array();
		
		$response_data['error'] = false;
		$response_data['medicines'] = $medicines;

		$response->getBody()->write(json_encode($response_data));

			return $response
				->withHeader('Content-type', 'application/json')
				->withStatus(200);

	});

	/**
		endpoint		:		viewSickLeave
		parameters		:		id
		method			:		GET
	**/

	$app->get('/sickleave',function(Request $request, Response $response){

		$request_data = $request->getQueryParams();
		$id = $request_data['id'];
		$db = new DbOperations;
		
		$sl = $db->getSickLeave($id);

		$response_data = array();
		
		$response_data['error'] = false;
		$response_data['SickLeave'] = $sl;

		$response->getBody()->write(json_encode($response_data));

			return $response
				->withHeader('Content-type', 'application/json')
				->withStatus(200);

	});

	/**
		endpoint		:		newsensitization
		parameters		:		id, sensitization
		method			:		post
	**/

	$app->post('/newsensitization',function(Request $request, Response $response){

			$request_data = $request->getQueryParams();

			$id = $request_data['id'];
			$sensization = $request_data['sensitization'];

			$db = new DbOperations;

			$result = $db->newSensitization($id, $sensization);
			if ($result == CREATED) {
				$message = array();
				$message['error'] = false;
				$message['message'] = 'New sensitization added';

				$response->getBody()->write(json_encode($message));

				return $response
									->withHeader('Content-type', 'application/json')
									->withStatus(201);

			} else if ($result == FAILURE) {
				$message = array();
				$message['error'] = true;
				$message['message'] = 'Some error occurred.';

				$response->getBody()->write(json_encode($message));

				return $response
									->withHeader('Content-type', 'application/json')
									->withStatus(422);

			} else if ($result == EXISTS) {
				$message = array();
				$message['error'] = true;
				$message['message'] = 'Sensitization Already Exists.';

				$response->getBody()->write(json_encode($message));

				return $response
									->withHeader('Content-type', 'application/json')
									->withStatus(422);
			}

		
		return $response
							->withHeader('Content-type', 'application/json')
							->withStatus(422);
	});

	/**
		endpoint		:		newdisease
		parameters		:		id, sensitization
		method			:		post
	**/

	$app->post('/newdisease',function(Request $request, Response $response){

		$request_data = $request->getQueryParams();

		$id = $request_data['id'];
		$sensization = $request_data['disease'];

		$db = new DbOperations;

		$result = $db->newDisease($id, $sensization);
		if ($result == CREATED) {
			$message = array();
			$message['error'] = false;
			$message['message'] = 'New sensitization added';

			$response->getBody()->write(json_encode($message));

			return $response
								->withHeader('Content-type', 'application/json')
								->withStatus(201);

		} else if ($result == FAILURE) {
			$message = array();
			$message['error'] = true;
			$message['message'] = 'Some error occurred.';

			$response->getBody()->write(json_encode($message));

			return $response
								->withHeader('Content-type', 'application/json')
								->withStatus(422);

		} else if ($result == EXISTS) {
			$message = array();
			$message['error'] = true;
			$message['message'] = 'Sensitization Already Exists.';

			$response->getBody()->write(json_encode($message));

			return $response
								->withHeader('Content-type', 'application/json')
								->withStatus(422);
		}

	
	return $response
						->withHeader('Content-type', 'application/json')
						->withStatus(422);
});
	/**
		endpoint		:		newpatientcard
		parameters		:		idDoctor, idPatient, Symptoms, Date, Other
		method			:		post
	**/

	$app->post('/newpatientcard',function(Request $request, Response $response){

		$request_data = $request->getQueryParams();

		$idDoctor = $request_data['idDoctor'];
		$idPatient = $request_data['idPatient'];
		$Symptoms = $request_data['Symptoms'];
		$Date = $request_data['Date'];
		$Other = $request_data['Other'];


		$db = new DbOperations;

		$result = $db->newPatientCard($idDoctor, $idPatient,$Symptoms, $Date,$Other);
		if ($result == CREATED) {
			$message = array();
			$message['error'] = false;
			$message['message'] = 'New Patientcard added';

			$response->getBody()->write(json_encode($message));

			return $response
								->withHeader('Content-type', 'application/json')
								->withStatus(201);

		} else if ($result == FAILURE) {
			$message = array();
			$message['error'] = true;
			$message['message'] = 'Some error occurred.';

			$response->getBody()->write(json_encode($message));

			return $response
								->withHeader('Content-type', 'application/json')
								->withStatus(422);

		} 
	
	return $response
						->withHeader('Content-type', 'application/json')
						->withStatus(422);
});

	/**
		endpoint		:		newmedicine
		parameters		:		idPatientCard, Medicine, Dose
		method			:		post
	**/

	$app->post('/newmedicine',function(Request $request, Response $response){

		$request_data = $request->getQueryParams();

		$idPatientCard = $request_data['idPatientCard'];
		$Medicine = $request_data['Medicine'];
		$Dose = $request_data['Dose'];

		$db = new DbOperations;

		$result = $db->newMedicine($idPatientCard , $Medicine,$Dose);
		if ($result == CREATED) {
			$message = array();
			$message['error'] = false;
			$message['message'] = 'New Medicine added';

			$response->getBody()->write(json_encode($message));

			return $response
								->withHeader('Content-type', 'application/json')
								->withStatus(201);

		} else if ($result == FAILURE) {
			$message = array();
			$message['error'] = true;
			$message['message'] = 'Some error occurred.';

			$response->getBody()->write(json_encode($message));

			return $response
								->withHeader('Content-type', 'application/json')
								->withStatus(422);

		} 
	
	return $response
						->withHeader('Content-type', 'application/json')
						->withStatus(422);
});

/**
		endpoint		:		newsickleave
		parameters		:		idPatientCard, StartDate, EndDate
		method			:		post
	**/

	$app->post('/newsickleave',function(Request $request, Response $response){

		$request_data = $request->getQueryParams();

		$idPatientCard = $request_data['idPatientCard'];
		$StartDate = $request_data['StartDate'];
		$EndDate = $request_data['EndDate'];

		$db = new DbOperations;

		$result = $db->newSickLeave($idPatientCard , $StartDate, $EndDate);
		if ($result == CREATED) {
			$message = array();
			$message['error'] = false;
			$message['message'] = 'New SickLeave added';

			$response->getBody()->write(json_encode($message));

			return $response
								->withHeader('Content-type', 'application/json')
								->withStatus(201);

		} else if ($result == FAILURE) {
			$message = array();
			$message['error'] = true;
			$message['message'] = 'Some error occurred.';

			$response->getBody()->write(json_encode($message));

			return $response
								->withHeader('Content-type', 'application/json')
								->withStatus(422);

		} 
	
	return $response
						->withHeader('Content-type', 'application/json')
						->withStatus(422);
});

	/**
		parameters		:		empty
	**/

	function haveEmptyParameters($required_params, $response) {
		$error = false;
		$error_params = '';
		$request_params = $_REQUEST;

		foreach ($required_params as $param) {
			if(!isset($request_params[$param]) || strlen($request_params[$param]) <= 0) {
				$error = true;
        $error_params .= $param . ', ';
      }
    }

    if($error) {
			$error_detail = array();
      $error_detail['error'] = true;
      $error_detail['message'] = 'Required parameters ' . substr($error_params, 0, -2) . ' are either missing or empty';

			$response->getBody()->write(json_encode($error_detail));
    }

    return $error;
	}

		/**
		endpoint		:		newpatient
		parameters		:		Name, Surname, BirthDate, PESEL, Adress
		method			:		post
	**/

	$app->post('/newpatient',function(Request $request, Response $response){

		$request_data = $request->getQueryParams();

		$Name = $request_data['Name'];
		$Surname = $request_data['Surname'];
		$BirthDate = $request_data['BirthDate'];
		$PESEL = $request_data['PESEL'];
		$Adress = $request_data['Adress'];

		$db = new DbOperations;

		$result = $db->newPatient($Name, $Surname, $BirthDate, $PESEL, $Adress);
		if ($result == CREATED) {
			$message = array();
			$message['error'] = false;
			$message['message'] = 'New patient added';

			$response->getBody()->write(json_encode($message));

			return $response
								->withHeader('Content-type', 'application/json')
								->withStatus(201);

		} else if ($result == FAILURE) {
			$message = array();
			$message['error'] = true;
			$message['message'] = 'Some error occurred.';

			$response->getBody()->write(json_encode($message));

			return $response
								->withHeader('Content-type', 'application/json')
								->withStatus(422);

		} else if ($result == EXISTS) {
			$message = array();
			$message['error'] = true;
			$message['message'] = 'Patient Already Exists.';

			$response->getBody()->write(json_encode($message));

			return $response
								->withHeader('Content-type', 'application/json')
								->withStatus(422);
		}

	
	return $response
						->withHeader('Content-type', 'application/json')
						->withStatus(422);
});

	/**
		endpoint		:		getPatientCardId
		parameters		:		idDoctor, idPatient
		method			:		GET
	**/

	$app->get('/getPatientCardId',function(Request $request, Response $response){
	
		$request_data = $request->getQueryParams();
		$iddoctor = $request_data['iddoctor'];
		$idpatient = $request_data['idpatient'];
		$db = new DbOperations;		
		$PatientCardId = $db->getPatientCardId($iddoctor,$idpatient);

		$response_data = array();
		
		$response_data['error'] = false;
		$response_data['idPatientCard'] = $PatientCardId;

		$response->getBody()->write(json_encode($response_data));

			return $response
				->withHeader('Content-type', 'application/json')
				->withStatus(200);

				
	});
	

	$app->run();
?>
