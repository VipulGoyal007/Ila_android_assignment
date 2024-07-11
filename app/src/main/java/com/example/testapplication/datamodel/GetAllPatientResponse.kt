package com.example.testapplication.datamodel

data class GetAllPatientResponse(
    val PatientList: List<Patient>?
) {
    data class Patient(
        var patientId: Long = 0,
        var patientName: String = "",
        var patientRelation: String = "",
        var age: String="",
        var type: String = "",
        var gender: Int = 0,
        var genderName: String?,
        var relationId: Int = 0,
        var relationName: String?,
        var selected: Boolean = false,
        var firstName: String?="",
        var lastName: String?="",
        var emailId: String?="",
        var isSelected: Int = 2,

        )
}