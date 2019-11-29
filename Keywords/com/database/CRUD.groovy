package com.database

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.sql.ResultSet
import com.database.SQLConnect

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class CRUD {
	/**
	 * Open and return a connection to database
	 */
	@Keyword
	def InsertSolicitudCepas(String NoDocumento, String Pais, String codigo1){
		SQLConnect conexion = new SQLConnect();
		conexion.connectDB(GlobalVariable.urlDB, GlobalVariable.portDB, GlobalVariable.nameDB, GlobalVariable.userDB, GlobalVariable.passwordDB);
		/**Obtener ID del Paciente*/
		String query = String.format("SELECT PACIE_ID FROM PACIENTE WHERE PACIE_PAISDOC_ID = '" + Pais + "' AND PACIE_NRODOCUMENTO = '" + NoDocumento + "'")
		ResultSet resultado = conexion.executeQuery(query)
		Object pacienteID = null
		if(resultado.next()){
			pacienteID = resultado.getObject("PACIE_ID")
		}
		println ("El Id del Paciente es:" + pacienteID)
		/**Obtener ID de la Solicitud para el PacienteID*/
		String query2 = String.format("SELECT SOL_ID FROM SOLICITUD WHERE SOL_PACIENTEID = '" + pacienteID + "'")
		ResultSet resultado2 = conexion.executeQuery(query2)
		Object solicitudID = null
		if(resultado2.next()){
			solicitudID = resultado2.getObject("SOL_ID")
		}
		println ("La Solicitud del Estudio es:" + solicitudID)
		/**Obtener la Solicitud del Estudio para el solicitudID + EstudioID*/
		String query3 = String.format("SELECT SOL_ID, SOL_ESTUDIOID FROM SOLICITUDESTUDIOS WHERE SOL_ID = '" + solicitudID + "' AND SOL_ESTUDIOID = '" + codigo1 + "'")
		ResultSet resultado3 = conexion.executeQuery(query3)
		Object Solicitud = null
		Object Estudio = null
		if(resultado3.next()){
			Solicitud = resultado3.getObject("SOL_ID")
			Estudio = resultado3.getObject("SOL_ESTUDIOID")
		}
		println ("El Estudio es:" + Estudio)
		println ("La Solicitud es:" + Solicitud)

		boolean resp = resultado3.getBoolean("SOL_ID")
		boolean resp2 = resultado3.getBoolean("SOL_ESTUDIOID")

		println ("Hay algun Estudio:" + resp)
		println ("Hay alguna Solicitud:" + resp2)

		return resp
	}
}
