<?php
  class DbOperations {
    private $con;

    function __construct() {
      require_once dirname(__FILE__).'/DbConnect.php';
      $db = new DbConnect ;
      $this->con = $db->connect();

    }

	/**
  	createUser
  **/
  
    public function createUser($login, $password, $name, $surname, $role) {
      if (!$this->isLoginExist($login)) {
        $stmt = $this->con->prepare("INSERT INTO doctor (Login, Password, Name, Surname, Role) VALUES (?, ?, ?, ?, ?)");
        $stmt->bind_param("sssss", $login, $password, $name, $surname, $role);

        if ($stmt->execute()) {
          return USER_CREATED;
        } else {
          return USER_FAILURE;
        }
      }

      return USER_EXISTS;
    }

	/**
    userLogin
  **/
  
    public function userLogin($login, $password){
      if($this->isLoginExist($login)){
          $hashed_password = $this->getUsersPasswordByLogin($login);
          if(password_verify($password,$hashed_password)){
              return USER_AUTHENTICATED;
          }else{
              return USER_PASSWORD_DO_NOT_MATCH;
          }

      }else{
        return USER_NOT_FOUND;
      }

    }

    private function getUsersPasswordByLogin($login){
      $stmt = $this->con->prepare("SELECT password  FROM doctor WHERE login = ?");
      $stmt->bind_param("s",$login);
      $stmt->execute();
      $stmt->bind_result($password);
      $stmt->fetch();
      return $password;

    }

    public function getUserByLogin($login){
      $stmt = $this->con->prepare("SELECT idDoctor, name, surname, role FROM doctor WHERE login = ?");
      $stmt-> bind_param("s", $login);
      $stmt-> execute();
      $stmt-> bind_result($id, $name, $surname, $role);
      $stmt-> fetch();
      $user = array();
      $user['id'] = $id;
      $user['name'] = $name;
      $user['surname'] = $surname;
      $user['role'] = $role;
      return $user;
    }

    private function isLoginExist($login) {
      $stmt = $this->con->prepare("SELECT password FROM doctor WHERE login = ?");
      $stmt->bind_param("s", $login);
      $stmt->execute();
      $stmt->bind_result($password);
      $stmt->fetch();
      return $password;
    }

  /**
    getAllPatients
  **/

  public function getAllPatients(){
    $stmt = $this->con->prepare("SELECT idPatient, Name, Surname, BirthDate, PESEL, Adress FROM patient");
    $stmt-> execute();
    $stmt-> bind_result($id, $name, $surname, $birthdate, $pesel, $adress);
    $patients = array();
    while($stmt->fetch()){
      $patient= array();
      $patient['id'] = $id;
      $patient['name'] = $name;
      $patient['surname'] = $surname;
      $patient['birthdate'] = $birthdate;
      $patient['pesel'] = $pesel;
      $patient['adress'] = $adress;
      array_push($patients, $patient);
    }
    return $patients;
  }

    /**
    getPatient
  **/

  public function getPatient($idpatient){
    $stmt = $this->con->prepare("SELECT idPatient, Name, Surname, BirthDate, PESEL, Adress FROM patient WHERE idPatient=?");
    $stmt->bind_param("i", $idpatient);
    $stmt-> execute();
    $stmt-> bind_result($id, $name, $surname, $birthdate, $pesel, $adress);
    $patients = array();
    while($stmt->fetch()){
      $patient= array();
      $patient['id'] = $id;
      $patient['name'] = $name;
      $patient['surname'] = $surname;
      $patient['birthdate'] = $birthdate;
      $patient['pesel'] = $pesel;
      $patient['adress'] = $adress;
      array_push($patients, $patient);
    }
    return $patients;
  }

  /**
    Chronic Disaeses
  **/

  public function getAllChronicDiseases($id){
    $stmt = $this->con->prepare("SELECT p.idPatient, cd.Disease
    FROM Patient as p 
    LEFT JOIN chronicdiseases as cd ON p.idPatient = cd.idPatient
    WHERE p.idPatient = ?
    ");
    $stmt->bind_param("i", $id);
    $stmt-> execute();
    $stmt-> bind_result($idPatient, $chronicdisease);
    $diseases = array();
    while($stmt->fetch()){
      $disease= array();
      $disease['idPatient'] = $idPatient;
      $disease['disease'] = $chronicdisease;
      array_push($diseases, $disease);
    }
    return $diseases;
  }

  
  /**
    Sensizations
  **/

  public function getAllSensizations($id){
    $stmt = $this->con->prepare("SELECT p.idPatient, s.sensization
    FROM Patient as p 
    LEFT JOIN Sensitization as s ON p.idPatient = s.idPatient
    WHERE p.idPatient = ?
    ");
    $stmt->bind_param("i", $id);
    $stmt-> execute();
    $stmt-> bind_result($idPatient, $patientsensization);
    $sensizations = array();
    while($stmt->fetch()){
      $sensization= array();
      $sensization['idPatient'] = $idPatient;
      $sensization['sensization'] = $patientsensization;
      array_push($sensizations, $sensization);
    }
    return $sensizations;
  }

  /**
    getPatientsCards
  **/

  public function getAllPatientsCards($idpatient){
    $stmt = $this->con->prepare("		SELECT pc.idPatientCard, pc.idPatient,pc.Date, 
    pc.Symptoms, pc.Other,
    CONCAT(d.name, ' ', d.surname) AS Doktor
    FROM patientcard as pc 
    LEFT JOIN doctor as d ON pc.idDoctor = d.idDoctor
    WHERE pc.idPatient = ?
    order by pc.Date DESC;");
    
    $stmt->bind_param("i", $idpatient);
    $stmt-> execute();
    $stmt-> bind_result($id, $patient, $date, $symptoms, $other, $doctor);
    $patientscards = array();
    while($stmt->fetch()){
      $patientcard= array();
      $patientcard['id'] = $id;
      $patientcard['patient'] = $patient;
      $patientcard['date'] = $date;
      $patientcard['symptoms'] = $symptoms;
      $patientcard['other'] = $other;
      $patientcard['doctor'] = $doctor;
      array_push($patientscards, $patientcard);
    }
    return $patientscards;
  }

   /**
    getMedicines
   **/

  public function getMedicines($idpatientcard){
    $stmt = $this->con->prepare("SELECT m.medicine, m.dose
    FROM medicines as m 
    WHERE m.idPatientCard = ?");
    $stmt->bind_param("i", $idpatientcard);
    $stmt-> execute();
    $stmt-> bind_result($med, $dose);
    $medicines = array();
    while($stmt->fetch()){
      $medicine= array();
      $medicine['medicine'] = $med;
      $medicine['dose'] = $dose;
      array_push($medicines, $medicine);
    }
    return $medicines;
  }

  /**
    getSickLeave
   **/

  public function getSickLeave($idpatientcard){
    $stmt = $this->con->prepare("SELECT sl.StartDate, sl.EndDate
    FROM sickleave AS sl
    WHERE sl.idPatientCard = ?");
    $stmt->bind_param("i", $idpatientcard);
    $stmt-> execute();
    $stmt-> bind_result($start, $end);
    $sickleave = array();
    while($stmt->fetch()){
      $sl= array();
      $sl['StartDate'] = $start;
      $sl['EndDate'] = $end;
      array_push($sickleave, $sl);
    }
    return $sickleave;
  }

	/**
  	newSensitization
  **/
  
  public function newSensitization($id, $sensitization) {
    if (!$this->isSensitizationExist($id, $sensitization)) {
      $stmt = $this->con->prepare("insert into sensitization (idPatient, Sensization)
      values (?, ?);");
      $stmt->bind_param("is", $id, $sensitization);

      if ($stmt->execute()) {
        return CREATED;
      } else {
        return FAILURE;
      }
    }

    return EXISTS;
  }
  private function isSensitizationExist($id,$sensization) {
    $stmt = $this->con->prepare("SELECT Sensization FROM sensitization
    WHERE idPatient=? and Sensization = ?;");
    $stmt->bind_param("is", $id, $sensization);
    $stmt->execute();
    $stmt->bind_result($sensizationex);
    $stmt->fetch();
    return $sensizationex;
  }

	/**
  	newDisease
  **/
  
  public function newDisease($id, $disease) {
    if (!$this->isDiseaseExist($id, $disease)) {
      $stmt = $this->con->prepare("INSERT INTO chronicdiseases (idPatient, Disease) 
      VALUES (?,?);");
      $stmt->bind_param("is", $id, $disease);

      if ($stmt->execute()) {
        return CREATED;
      } else {
        return FAILURE;
      }
    }

    return EXISTS;
  }
  private function isDiseaseExist($id,$disease) {
    $stmt = $this->con->prepare("SELECT Disease FROM chronicdiseases
    WHERE idPatient=? and Disease = ?;");
    $stmt->bind_param("is", $id, $disease);
    $stmt->execute();
    $stmt->bind_result($diseasex);
    $stmt->fetch();
    return $diseasex;
  }
	/**
  	newPatientCard
  **/
  public function newPatientCard($idDoctor, $idPatient, $symptoms, $date, $other) {
      $stmt = $this->con->prepare("INSERT INTO patientcard (idDoctor, idPatient, Symptoms, Date, Other) 
      VALUES (?,?,?,?,?);");
      $stmt->bind_param("iisss",$idDoctor, $idPatient, $symptoms, $date, $other);

      if ($stmt->execute()) {
        return CREATED;
      } else {
        return FAILURE;
      }
    }
	/**
  	newMedicine
  **/
    public function newMedicine($idPatientCard, $Medicine, $Dose) {
      $stmt = $this->con->prepare("INSERT INTO MEDICINES(idPatientCard, Medicine, Dose)
      VALUES (?,?,?);");
      $stmt->bind_param("iss",$idPatientCard, $Medicine, $Dose);

      if ($stmt->execute()) {
        return CREATED;
      } else {
        return FAILURE;
      }
    }

  /**
  	newSickLeave
  **/
  public function newSickLeave($idPatientCard, $StartDate, $EndDate) {
    $stmt = $this->con->prepare("INSERT INTO SICKLEAVE (idPatientCard, StartDate, EndDate)
    VALUES (?,?,?);");
    $stmt->bind_param("iss",$idPatientCard, $StartDate, $EndDate);

    if ($stmt->execute()) {
      return CREATED;
    } else {
      return FAILURE;
    }
  }

  	/**
  	newPatient
  **/
  
  public function newPatient($Name, $Surname, $BirthDate, $PESEL, $Adress) {
    if (!$this->isPatientExist($PESEL)) {
      $stmt = $this->con->prepare("INSERT INTO Patient(Name, Surname, BirthDate, PESEL, Adress)
      VALUES (?,?,?,?,?);");
      $stmt->bind_param("sssss", $Name, $Surname, $BirthDate, $PESEL, $Adress);

      if ($stmt->execute()) {
        return CREATED;
      } else {
        return FAILURE;
      }
    }

    return EXISTS;
  }

  private function isPatientExist($PESEL) {
    $stmt = $this->con->prepare("SELECT PESEL FROM patient 
    WHERE PESEL = ?");
    $stmt->bind_param("s", $PESEL);
    $stmt->execute();
    $stmt->bind_result($PESELx);
    $stmt->fetch();
    return $PESELx;
  }

  /**
    getPatientCardId
  **/

  public function getPatientCardId($iddoctor, $idpatient){
    $stmt = $this->con->prepare("SELECT distinct idPatientCard from patientcard
    WHERE idDoctor =? and idPatient = ?
    order by idPatientCard desc
    LIMIT 1;");
    $stmt->bind_param("ii",$iddoctor, $idpatient);
    $stmt-> execute();
    $stmt-> bind_result($idPatientCard);
    $stmt->fetch();
    return $idPatientCard;
  }

  }
?>
