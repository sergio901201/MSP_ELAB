package com.database

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.sql.ResultSet

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

	/**
	 * Verify Filter Fecha in Virologia funciona ok
	 */
	@Keyword
	def FiltroFechaVirologia(String fechaD, String fechaH){
		String Resultadofinal = ""
		String [] fd
		SQLConnect conexion = new SQLConnect();
		conexion.connectDB(GlobalVariable.urlDB, GlobalVariable.portDB, GlobalVariable.nameDB, GlobalVariable.userDB, GlobalVariable.passwordDB);
		String query
		if(fechaH.length() == 0){
			/**Obtener ID de la Solicitud para una Fecha Desde si fechaH=null*/
			query = String.format("SELECT SOL_ID, SOL_FECHA, SOL_FECHAHORA FROM SOLICITUD WHERE SOL_SOLICITUDESTADO NOT LIKE 'I' AND SOL_HABILITADA = '0' AND SOL_FECHAHORA >= TO_TIMESTAMP('"+fechaD+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') ORDER BY SOL_FECHAHORA")
		}else{
			/**Obtener ID de la Solicitud para una Fecha Desde Y fechaH*/
			query = String.format("SELECT SOL_ID, SOL_FECHA, SOL_FECHAHORA FROM SOLICITUD WHERE SOL_SOLICITUDESTADO NOT LIKE 'I' AND SOL_HABILITADA = '0' AND SOL_FECHAHORA >= TO_TIMESTAMP('"+fechaD+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') AND SOL_FECHAHORA <= TO_TIMESTAMP('"+fechaH+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') ORDER BY SOL_FECHAHORA")
		}
		ResultSet resultado = conexion.executeQuery(query)
		Object solicitudID = null
		while(resultado.next()){
			solicitudID = resultado.getObject("SOL_ID")
			println ("El Id de la Solicitud es:" + solicitudID)

			/**Obtener el ID del Estudio para la solicitudID*/
			String query2 = String.format("SELECT SOL_ESTUDIOID, SOL_ID FROM SOLICITUDESTUDIOS WHERE SOL_ID = '" + solicitudID + "'")
			ResultSet resultado2 = conexion.executeQuery(query2)
			Object Estudio = null
			while(resultado2.next()){
				Estudio = resultado2.getObject("SOL_ESTUDIOID")
				println ("El Id del Estudio es:" + Estudio)

				/**Obtener el ID del Sector para el Estudio*/
				String query3 = String.format("SELECT EST_ID, EST_SEC_ID FROM ESTUDIO WHERE EST_ID = '" + Estudio + "'")
				ResultSet resultado3 = conexion.executeQuery(query3)
				Object Sector = null
				while(resultado3.next()){
					Sector = resultado3.getObject("EST_SEC_ID")
					println ("El Id del Sector es:" + Sector)

					/**Obtener el ID de la Unidad para el SectorID*/
					String query4 = String.format("SELECT SEC_ID, SEC_UNI_ID FROM SECTOR WHERE SEC_ID = '" + Sector + "'")
					ResultSet resultado4 = conexion.executeQuery(query4)
					Object Unidad = null
					while(resultado4.next()){
						Unidad = resultado4.getObject("SEC_UNI_ID")
						println ("El Id de la Unidad es:" + Unidad)
						String cadena = ""
						if(Unidad == 1){
							cadena = resultado.getObject("SOL_FECHAHORA").toString().substring(0, 10)
							String dia = cadena.substring(8, 10)
							String mes = cadena.substring(5, 7)
							String año = cadena.substring(0, 4)
							String fechadesde = dia + '/' + mes + '/' + año
							println ("El fecha desde es:" + fechadesde)
							Resultadofinal = Resultadofinal + " " + fechadesde
							println ("La cadena es:" + cadena)
							println ("El Resultado Final es:" + Resultadofinal)
						}
					}
				}
			}
		}
		if(Resultadofinal.length() != 0){
			Resultadofinal = Resultadofinal.substring(1,Resultadofinal.length())
			fd = Resultadofinal.split(" ")
		}

		return fd
	}

	/**
	 * Verify Filter Sector in Virologia funciona ok para una Fecha desde y Fecha hasta
	 */
	@Keyword
	def FiltroSectorVirologia(String fechaD, String fechaH, String sector){
		String Resultadofinal = ""
		String [] fd
		SQLConnect conexion = new SQLConnect();
		conexion.connectDB(GlobalVariable.urlDB, GlobalVariable.portDB, GlobalVariable.nameDB, GlobalVariable.userDB, GlobalVariable.passwordDB);
		/**Obtener ID de la Solicitud para una Fecha Desde Y fechaH*/
		String query = String.format("SELECT SOL_ID, SOL_FECHA, SOL_FECHAHORA FROM SOLICITUD WHERE SOL_SOLICITUDESTADO NOT LIKE 'I' AND SOL_HABILITADA = '0' AND SOL_FECHAHORA >= TO_TIMESTAMP('"+fechaD+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') AND SOL_FECHAHORA <= TO_TIMESTAMP('"+fechaH+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') ORDER BY SOL_FECHAHORA")
		ResultSet resultado = conexion.executeQuery(query)
		Object solicitudID = null
		while(resultado.next()){
			solicitudID = resultado.getObject("SOL_ID")
			println ("El Id de la Solicitud es:" + solicitudID)

			/**Obtener el ID del Estudio para la solicitudID*/
			String query2 = String.format("SELECT SOL_ESTUDIOID, SOL_ID FROM SOLICITUDESTUDIOS WHERE SOL_ID = '" + solicitudID + "'")
			ResultSet resultado2 = conexion.executeQuery(query2)
			Object Estudio = null
			while(resultado2.next()){
				Estudio = resultado2.getObject("SOL_ESTUDIOID")
				println ("El Id del Estudio es:" + Estudio)

				/**Obtener el ID del Sector para el Estudio*/
				String query3 = String.format("SELECT EST_ID, EST_SEC_ID FROM ESTUDIO WHERE EST_ID = '" + Estudio + "'")
				ResultSet resultado3 = conexion.executeQuery(query3)
				Object Sector = null
				while(resultado3.next()){
					Sector = resultado3.getObject("EST_SEC_ID")
					println ("El Id del Sector es:" + Sector)

					/**Obtener el ID de la Unidad para el SectorID*/
					String query4 = String.format("SELECT SEC_ID, SEC_UNI_ID FROM SECTOR WHERE SEC_ID = '" + Sector + "'")
					ResultSet resultado4 = conexion.executeQuery(query4)
					Object SectorID = null
					Object Unidad = null
					while(resultado4.next()){
						SectorID = resultado4.getObject("SEC_ID")
						Unidad = resultado4.getObject("SEC_UNI_ID")
						println ("El Id del Sector que cumple es:" + SectorID)
						println ("El Id de la Unidad es:" + Unidad)
						String cadena = ""
						if(SectorID.toString().equals(sector) && Unidad == 1){
							cadena = resultado.getObject("SOL_FECHAHORA").toString().substring(0, 10)
							String dia = cadena.substring(8, 10)
							String mes = cadena.substring(5, 7)
							String año = cadena.substring(0, 4)
							String fechadesde = dia + '/' + mes + '/' + año
							println ("El fecha desde es:" + fechadesde)
							Resultadofinal = Resultadofinal + " " + fechadesde
							println ("La cadena es:" + cadena)
							println ("El Resultado Final es:" + Resultadofinal)
						}
					}
				}
			}
		}
		if(Resultadofinal.length() != 0){
			Resultadofinal = Resultadofinal.substring(1,Resultadofinal.length())
			fd = Resultadofinal.split(" ")
		}

		return fd
	}

	/**
	 * Verify Filter Estado in Virologia funciona ok para una Fecha desde y Fecha hasta
	 */
	@Keyword
	def FiltroEstadoVirologia(String fechaD, String fechaH, String estado){
		String Resultadofinal = ""
		String [] fd
		SQLConnect conexion = new SQLConnect();
		conexion.connectDB(GlobalVariable.urlDB, GlobalVariable.portDB, GlobalVariable.nameDB, GlobalVariable.userDB, GlobalVariable.passwordDB);
		/**Obtener ID de la Solicitud para una Fecha Desde Y fechaH y Estado*/
		String query = String.format("SELECT SOL_ID, SOL_FECHA, SOL_FECHAHORA FROM SOLICITUD WHERE SOL_SOLICITUDESTADO NOT LIKE 'I' AND SOL_HABILITADA = '0' AND SOL_FECHAHORA >= TO_TIMESTAMP('"+fechaD+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') AND SOL_FECHAHORA <= TO_TIMESTAMP('"+fechaH+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') AND SOL_SOLICITUDESTADO = '"+estado+"' ORDER BY SOL_FECHAHORA")
		ResultSet resultado = conexion.executeQuery(query)
		Object solicitudID = null
		while(resultado.next()){
			solicitudID = resultado.getObject("SOL_ID")
			println ("El Id de la Solicitud es:" + solicitudID)

			/**Obtener el ID del Estudio para la solicitudID*/
			String query2 = String.format("SELECT SOL_ESTUDIOID, SOL_ID FROM SOLICITUDESTUDIOS WHERE SOL_ID = '" + solicitudID + "'")
			ResultSet resultado2 = conexion.executeQuery(query2)
			Object Estudio = null
			while(resultado2.next()){
				Estudio = resultado2.getObject("SOL_ESTUDIOID")
				println ("El Id del Estudio es:" + Estudio)

				/**Obtener el ID del Sector para el Estudio*/
				String query3 = String.format("SELECT EST_ID, EST_SEC_ID FROM ESTUDIO WHERE EST_ID = '" + Estudio + "'")
				ResultSet resultado3 = conexion.executeQuery(query3)
				Object Sector = null
				while(resultado3.next()){
					Sector = resultado3.getObject("EST_SEC_ID")
					println ("El Id del Sector es:" + Sector)

					/**Obtener el ID de la Unidad para el SectorID*/
					String query4 = String.format("SELECT SEC_ID, SEC_UNI_ID FROM SECTOR WHERE SEC_ID = '" + Sector + "'")
					ResultSet resultado4 = conexion.executeQuery(query4)
					Object Unidad = null
					while(resultado4.next()){
						Unidad = resultado4.getObject("SEC_UNI_ID")
						println ("El Id de la Unidad es:" + Unidad)
						String cadena = ""
						if(Unidad == 1){
							cadena = resultado.getObject("SOL_FECHAHORA").toString().substring(0, 10)
							String dia = cadena.substring(8, 10)
							String mes = cadena.substring(5, 7)
							String año = cadena.substring(0, 4)
							String fechadesde = dia + '/' + mes + '/' + año
							println ("El fecha desde es:" + fechadesde)
							Resultadofinal = Resultadofinal + " " + fechadesde
							println ("La cadena es:" + cadena)
							println ("El Resultado Final es:" + Resultadofinal)
						}
					}
				}
			}
		}
		if(Resultadofinal.length() != 0){
			Resultadofinal = Resultadofinal.substring(1,Resultadofinal.length())
			fd = Resultadofinal.split(" ")
		}

		return fd
	}

	/**
	 * Verify Filter EstadoEstudio in Virologia funciona ok para una Fecha desde y Fecha hasta
	 */
	@Keyword
	def FiltroEstadoEstudioVirologia(String fechaD, String fechaH, String estadoestudio){
		String Resultadofinal = ""
		String [] fd
		SQLConnect conexion = new SQLConnect();
		conexion.connectDB(GlobalVariable.urlDB, GlobalVariable.portDB, GlobalVariable.nameDB, GlobalVariable.userDB, GlobalVariable.passwordDB);
		/**Obtener ID de la Solicitud para una Fecha Desde Y fechaH y Estado*/
		String query = String.format("SELECT SOL_ID, SOL_FECHA, SOL_FECHAHORA FROM SOLICITUD WHERE SOL_SOLICITUDESTADO NOT LIKE 'I' AND SOL_HABILITADA = '0' AND SOL_FECHAHORA >= TO_TIMESTAMP('"+fechaD+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') AND SOL_FECHAHORA <= TO_TIMESTAMP('"+fechaH+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') ORDER BY SOL_FECHAHORA")
		ResultSet resultado = conexion.executeQuery(query)
		Object solicitudID = null
		while(resultado.next()){
			solicitudID = resultado.getObject("SOL_ID")
			println ("El Id de la Solicitud es:" + solicitudID)

			/**Obtener el ID del Estudio para la solicitudID*/
			String query2 = String.format("SELECT SOL_ESTUDIOID, SOL_ID FROM SOLICITUDESTUDIOS WHERE SOL_ID = '" + solicitudID + "' AND SOL_EST_ESTADOESTUDIO = '"+estadoestudio+"' ")
			ResultSet resultado2 = conexion.executeQuery(query2)
			Object Estudio = null
			while(resultado2.next()){
				Estudio = resultado2.getObject("SOL_ESTUDIOID")
				println ("El Id del Estudio es:" + Estudio)

				/**Obtener el ID del Sector para el Estudio*/
				String query3 = String.format("SELECT EST_ID, EST_SEC_ID FROM ESTUDIO WHERE EST_ID = '" + Estudio + "'")
				ResultSet resultado3 = conexion.executeQuery(query3)
				Object Sector = null
				while(resultado3.next()){
					Sector = resultado3.getObject("EST_SEC_ID")
					println ("El Id del Sector es:" + Sector)

					/**Obtener el ID de la Unidad para el SectorID*/
					String query4 = String.format("SELECT SEC_ID, SEC_UNI_ID FROM SECTOR WHERE SEC_ID = '" + Sector + "'")
					ResultSet resultado4 = conexion.executeQuery(query4)
					Object Unidad = null
					while(resultado4.next()){
						Unidad = resultado4.getObject("SEC_UNI_ID")
						println ("El Id de la Unidad es:" + Unidad)
						String cadena = ""
						if(Unidad == 1){
							cadena = resultado.getObject("SOL_FECHAHORA").toString().substring(0, 10)
							String dia = cadena.substring(8, 10)
							String mes = cadena.substring(5, 7)
							String año = cadena.substring(0, 4)
							String fechadesde = dia + '/' + mes + '/' + año
							println ("El fecha desde es:" + fechadesde)
							Resultadofinal = Resultadofinal + " " + fechadesde
							println ("La cadena es:" + cadena)
							println ("El Resultado Final es:" + Resultadofinal)
						}
					}
				}
			}
		}
		if(Resultadofinal.length() != 0){
			Resultadofinal = Resultadofinal.substring(1,Resultadofinal.length())
			fd = Resultadofinal.split(" ")
		}

		return fd
	}

	/**
	 * Verify Ckecked Solo Muestras Compartidas in Virologia funciona ok para una Fecha desde y Fecha hasta
	 */
	@Keyword
	def FiltroSMCVirologia(String fechaD, String fechaH, boolean smc){
		String res = ""
		String [] fd
		if(smc){
			res = 1
		}else{
			res = 0
		}
		String Resultadofinal = ""
		SQLConnect conexion = new SQLConnect();
		conexion.connectDB(GlobalVariable.urlDB, GlobalVariable.portDB, GlobalVariable.nameDB, GlobalVariable.userDB, GlobalVariable.passwordDB);
		/**Obtener ID de la Solicitud para una Fecha Desde Y fechaH y Estado*/
		String query = String.format("SELECT SOL_ID, SOL_FECHA, SOL_FECHAHORA FROM SOLICITUD WHERE SOL_SOLICITUDESTADO NOT LIKE 'I' AND SOL_HABILITADA = '0' AND SOL_FECHAHORA >= TO_TIMESTAMP('"+fechaD+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') AND SOL_FECHAHORA <= TO_TIMESTAMP('"+fechaH+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') AND SOL_MUESTRACOMPARTIDA = '"+res+"' ORDER BY SOL_FECHAHORA")
		ResultSet resultado = conexion.executeQuery(query)
		Object solicitudID = null
		while(resultado.next()){
			solicitudID = resultado.getObject("SOL_ID")
			println ("El Id de la Solicitud es:" + solicitudID)

			/**Obtener los IDs del Estudio para la solicitudID*/
			String query2 = String.format("SELECT SOL_ESTUDIOID, SOL_ID FROM SOLICITUDESTUDIOS WHERE SOL_ID = '" + solicitudID + "'")
			ResultSet resultado2 = conexion.executeQuery(query2)
			Object Estudio = null
			while(resultado2.next()){
				Estudio = resultado2.getObject("SOL_ESTUDIOID")
				println ("El Id del Estudio es:" + Estudio)

				/**Obtener el ID del Sector para el Estudio*/
				String query3 = String.format("SELECT EST_ID, EST_SEC_ID FROM ESTUDIO WHERE EST_ID = '" + Estudio + "'")
				ResultSet resultado3 = conexion.executeQuery(query3)
				Object Sector = null
				while(resultado3.next()){
					Sector = resultado3.getObject("EST_SEC_ID")
					println ("El Id del Sector es:" + Sector)

					/**Obtener el ID de la Unidad para el SectorID*/
					String query4 = String.format("SELECT SEC_ID, SEC_UNI_ID FROM SECTOR WHERE SEC_ID = '" + Sector + "'")
					ResultSet resultado4 = conexion.executeQuery(query4)
					Object Unidad = null
					while(resultado4.next()){
						Unidad = resultado4.getObject("SEC_UNI_ID")
						println ("El Id de la Unidad es:" + Unidad)
						String cadena = ""
						if(Unidad == 1){
							cadena = resultado.getObject("SOL_FECHAHORA").toString().substring(0, 10)
							String dia = cadena.substring(8, 10)
							String mes = cadena.substring(5, 7)
							String año = cadena.substring(0, 4)
							String fechadesde = dia + '/' + mes + '/' + año
							println ("El fecha desde es:" + fechadesde)
							Resultadofinal = Resultadofinal + " " + fechadesde
							println ("La cadena es:" + cadena)
							println ("El Resultado Final es:" + Resultadofinal)
						}
					}
				}
			}
		}
		if(Resultadofinal.length() != 0){
			Resultadofinal = Resultadofinal.substring(1,Resultadofinal.length())
			fd = Resultadofinal.split(" ")
		}

		return fd
	}

	/**
	 * Verify Ckecked Solo Infresadas por DSLP in Virologia funciona ok para una Fecha desde y Fecha hasta
	 */
	@Keyword
	def FiltroDLSPVirologia(String fechaD, String fechaH, boolean dlsp){
		String res = ""
		String [] fd
		if(dlsp){
			res = 1
		}else{
			res = 0
		}
		String Resultadofinal = ""
		SQLConnect conexion = new SQLConnect();
		conexion.connectDB(GlobalVariable.urlDB, GlobalVariable.portDB, GlobalVariable.nameDB, GlobalVariable.userDB, GlobalVariable.passwordDB);
		/**Obtener ID de la Solicitud para una Fecha Desde Y fechaH y Estado*/
		String query = String.format("SELECT SOL_ID, SOL_FECHA, SOL_FECHAHORA FROM SOLICITUD WHERE SOL_SOLICITUDESTADO NOT LIKE 'I' AND SOL_HABILITADA = '0' AND SOL_FECHAHORA >= TO_TIMESTAMP('"+fechaD+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') AND SOL_FECHAHORA <= TO_TIMESTAMP('"+fechaH+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') AND SOL_INGRESODLSP = '"+res+"' ORDER BY SOL_FECHAHORA")
		ResultSet resultado = conexion.executeQuery(query)
		Object solicitudID = null
		while(resultado.next()){
			solicitudID = resultado.getObject("SOL_ID")
			println ("El Id de la Solicitud es:" + solicitudID)

			/**Obtener los IDs del Estudio para la solicitudID*/
			String query2 = String.format("SELECT SOL_ESTUDIOID, SOL_ID FROM SOLICITUDESTUDIOS WHERE SOL_ID = '" + solicitudID + "'")
			ResultSet resultado2 = conexion.executeQuery(query2)
			Object Estudio = null
			while(resultado2.next()){
				Estudio = resultado2.getObject("SOL_ESTUDIOID")
				println ("El Id del Estudio es:" + Estudio)

				/**Obtener el ID del Sector para el Estudio*/
				String query3 = String.format("SELECT EST_ID, EST_SEC_ID FROM ESTUDIO WHERE EST_ID = '" + Estudio + "'")
				ResultSet resultado3 = conexion.executeQuery(query3)
				Object Sector = null
				while(resultado3.next()){
					Sector = resultado3.getObject("EST_SEC_ID")
					println ("El Id del Sector es:" + Sector)

					/**Obtener el ID de la Unidad para el SectorID*/
					String query4 = String.format("SELECT SEC_ID, SEC_UNI_ID FROM SECTOR WHERE SEC_ID = '" + Sector + "'")
					ResultSet resultado4 = conexion.executeQuery(query4)
					Object Unidad = null
					while(resultado4.next()){
						Unidad = resultado4.getObject("SEC_UNI_ID")
						println ("El Id de la Unidad es:" + Unidad)
						String cadena = ""
						if(Unidad == 1){
							cadena = resultado.getObject("SOL_FECHAHORA").toString().substring(0, 10)
							String dia = cadena.substring(8, 10)
							String mes = cadena.substring(5, 7)
							String año = cadena.substring(0, 4)
							String fechadesde = dia + '/' + mes + '/' + año
							println ("El fecha desde es:" + fechadesde)
							Resultadofinal = Resultadofinal + " " + fechadesde
							println ("La cadena es:" + cadena)
							println ("El Resultado Final es:" + Resultadofinal)
						}
					}
				}
			}
		}
		if(Resultadofinal.length() != 0){
			Resultadofinal = Resultadofinal.substring(1,Resultadofinal.length())
			fd = Resultadofinal.split(" ")
		}
		return fd
	}

	/** Verify Filtro Estudio in Virologia funciona ok para una Fecha desde y Fecha hasta
	 */
	@Keyword
	def FiltroEstudioVirologia(String fechaD, String fechaH, String estudio){
		String Resultadofinal = ""
		String [] fd
		SQLConnect conexion = new SQLConnect();
		conexion.connectDB(GlobalVariable.urlDB, GlobalVariable.portDB, GlobalVariable.nameDB, GlobalVariable.userDB, GlobalVariable.passwordDB);
		/**Obtener ID de la Solicitud para una Fecha Desde Y fechaH y Estado*/
		String query = String.format("SELECT SOL_ID, SOL_FECHA, SOL_FECHAHORA FROM SOLICITUD WHERE SOL_SOLICITUDESTADO NOT LIKE 'I' AND SOL_HABILITADA = '0' AND SOL_FECHAHORA >= TO_TIMESTAMP('"+fechaD+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') AND SOL_FECHAHORA <= TO_TIMESTAMP('"+fechaH+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') ORDER BY SOL_FECHAHORA")
		ResultSet resultado = conexion.executeQuery(query)
		Object solicitudID = null
		while(resultado.next()){
			solicitudID = resultado.getObject("SOL_ID")
			println ("El Id de la Solicitud es:" + solicitudID)

			/**Obtener los IDs del Estudio para la solicitudID*/
			String query2 = String.format("SELECT SOL_ESTUDIOID, SOL_ID FROM SOLICITUDESTUDIOS WHERE SOL_ID = '" + solicitudID + "' AND SOL_ESTUDIOID = '" + estudio + "'")
			ResultSet resultado2 = conexion.executeQuery(query2)
			Object Estudio = null
			while(resultado2.next()){
				Estudio = resultado2.getObject("SOL_ESTUDIOID")
				println ("El Id del Estudio es:" + Estudio)

				/**Obtener el ID del Sector para el Estudio*/
				String query3 = String.format("SELECT EST_ID, EST_SEC_ID FROM ESTUDIO WHERE EST_ID = '" + Estudio + "'")
				ResultSet resultado3 = conexion.executeQuery(query3)
				Object Sector = null
				while(resultado3.next()){
					Sector = resultado3.getObject("EST_SEC_ID")
					println ("El Id del Sector es:" + Sector)

					/**Obtener el ID de la Unidad para el SectorID*/
					String query4 = String.format("SELECT SEC_ID, SEC_UNI_ID FROM SECTOR WHERE SEC_ID = '" + Sector + "'")
					ResultSet resultado4 = conexion.executeQuery(query4)
					Object Unidad = null
					while(resultado4.next()){
						Unidad = resultado4.getObject("SEC_UNI_ID")
						println ("El Id de la Unidad es:" + Unidad)
						String cadena = ""
						if(Unidad == 1){
							cadena = resultado.getObject("SOL_FECHAHORA").toString().substring(0, 10)
							String dia = cadena.substring(8, 10)
							String mes = cadena.substring(5, 7)
							String año = cadena.substring(0, 4)
							String fechadesde = dia + '/' + mes + '/' + año
							println ("El fecha desde es:" + fechadesde)
							Resultadofinal = Resultadofinal + " " + fechadesde
							println ("La cadena es:" + cadena)
							println ("El Resultado Final es:" + Resultadofinal)
						}
					}
				}
			}
		}
		println ("La longitud es:" + Resultadofinal.length())
		if(Resultadofinal.length() != 0){
			Resultadofinal = Resultadofinal.substring(1,Resultadofinal.length())
			fd = Resultadofinal.split(" ")
		}

		return fd
	}

	/** Verify Filtro Estudio in Virologia funciona ok para una Fecha desde y Fecha hasta
	 */
	@Keyword
	def FiltroPrestadorVirologia(String fechaD, String fechaH, String prestador){
		String Resultadofinal = ""
		String [] fd
		SQLConnect conexion = new SQLConnect();
		conexion.connectDB(GlobalVariable.urlDB, GlobalVariable.portDB, GlobalVariable.nameDB, GlobalVariable.userDB, GlobalVariable.passwordDB);
		/**Obtener ID de la Solicitud para una Fecha Desde Y fechaH y Estado*/
		String query = String.format("SELECT SOL_ID, SOL_FECHA, SOL_FECHAHORA FROM SOLICITUD WHERE SOL_SOLICITUDESTADO NOT LIKE 'I' AND SOL_HABILITADA = '0' AND SOL_FECHAHORA >= TO_TIMESTAMP('"+fechaD+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') AND SOL_FECHAHORA <= TO_TIMESTAMP('"+fechaH+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') AND SOL_ORGANIZACIONID = '"+prestador+"' ORDER BY SOL_FECHAHORA")
		ResultSet resultado = conexion.executeQuery(query)
		Object solicitudID = null
		while(resultado.next()){
			solicitudID = resultado.getObject("SOL_ID")
			println ("El Id de la Solicitud es:" + solicitudID)

			/**Obtener los IDs del Estudio para la solicitudID*/
			String query2 = String.format("SELECT SOL_ESTUDIOID, SOL_ID FROM SOLICITUDESTUDIOS WHERE SOL_ID = '" + solicitudID + "' ")
			ResultSet resultado2 = conexion.executeQuery(query2)
			Object Estudio = null
			while(resultado2.next()){
				Estudio = resultado2.getObject("SOL_ESTUDIOID")
				println ("El Id del Estudio es:" + Estudio)

				/**Obtener el ID del Sector para el Estudio*/
				String query3 = String.format("SELECT EST_ID, EST_SEC_ID FROM ESTUDIO WHERE EST_ID = '" + Estudio + "'")
				ResultSet resultado3 = conexion.executeQuery(query3)
				Object Sector = null
				while(resultado3.next()){
					Sector = resultado3.getObject("EST_SEC_ID")
					println ("El Id del Sector es:" + Sector)

					/**Obtener el ID de la Unidad para el SectorID*/
					String query4 = String.format("SELECT SEC_ID, SEC_UNI_ID FROM SECTOR WHERE SEC_ID = '" + Sector + "'")
					ResultSet resultado4 = conexion.executeQuery(query4)
					Object Unidad = null
					while(resultado4.next()){
						Unidad = resultado4.getObject("SEC_UNI_ID")
						println ("El Id de la Unidad es:" + Unidad)
						String cadena = ""
						if(Unidad == 1){
							cadena = resultado.getObject("SOL_FECHAHORA").toString().substring(0, 10)
							String dia = cadena.substring(8, 10)
							String mes = cadena.substring(5, 7)
							String año = cadena.substring(0, 4)
							String fechadesde = dia + '/' + mes + '/' + año
							println ("El fecha desde es:" + fechadesde)
							Resultadofinal = Resultadofinal + " " + fechadesde
							println ("La cadena es:" + cadena)
							println ("El Resultado Final es:" + Resultadofinal)
						}
					}
				}
			}
		}
		println ("La longitud es:" + Resultadofinal.length())
		if(Resultadofinal.length() != 0){
			Resultadofinal = Resultadofinal.substring(1,Resultadofinal.length())
			fd = Resultadofinal.split(" ")
		}

		return fd
	}

	/** Verify Filtro No de Solicitud in Virologia funciona ok para una Fecha desde y Fecha hasta
	 */
	@Keyword
	def FiltroSolicitudVirologia(String fechaD, String fechaH, String solicitud){
		String Resultadofinal = ""
		String [] fd
		SQLConnect conexion = new SQLConnect();
		conexion.connectDB(GlobalVariable.urlDB, GlobalVariable.portDB, GlobalVariable.nameDB, GlobalVariable.userDB, GlobalVariable.passwordDB);
		/**Obtener ID de la Solicitud para una Fecha Desde Y fechaH y Estado*/
		String query = String.format("SELECT SOL_ID, SOL_FECHA, SOL_FECHAHORA FROM SOLICITUD WHERE SOL_SOLICITUDESTADO NOT LIKE 'I' AND SOL_HABILITADA = '0' AND SOL_FECHAHORA >= TO_TIMESTAMP('"+fechaD+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') AND SOL_FECHAHORA <= TO_TIMESTAMP('"+fechaH+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') AND SOL_NUMERO = '"+solicitud+"' ORDER BY SOL_FECHAHORA")
		ResultSet resultado = conexion.executeQuery(query)
		Object solicitudID = null
		while(resultado.next()){
			solicitudID = resultado.getObject("SOL_ID")
			println ("El Id de la Solicitud es:" + solicitudID)

			/**Obtener los IDs del Estudio para la solicitudID*/
			String query2 = String.format("SELECT SOL_ESTUDIOID, SOL_ID FROM SOLICITUDESTUDIOS WHERE SOL_ID = '" + solicitudID + "' ")
			ResultSet resultado2 = conexion.executeQuery(query2)
			Object Estudio = null
			while(resultado2.next()){
				Estudio = resultado2.getObject("SOL_ESTUDIOID")
				println ("El Id del Estudio es:" + Estudio)

				/**Obtener el ID del Sector para el Estudio*/
				String query3 = String.format("SELECT EST_ID, EST_SEC_ID FROM ESTUDIO WHERE EST_ID = '" + Estudio + "'")
				ResultSet resultado3 = conexion.executeQuery(query3)
				Object Sector = null
				while(resultado3.next()){
					Sector = resultado3.getObject("EST_SEC_ID")
					println ("El Id del Sector es:" + Sector)

					/**Obtener el ID de la Unidad para el SectorID*/
					String query4 = String.format("SELECT SEC_ID, SEC_UNI_ID FROM SECTOR WHERE SEC_ID = '" + Sector + "'")
					ResultSet resultado4 = conexion.executeQuery(query4)
					Object Unidad = null
					while(resultado4.next()){
						Unidad = resultado4.getObject("SEC_UNI_ID")
						println ("El Id de la Unidad es:" + Unidad)
						String cadena = ""
						if(Unidad == 1){
							cadena = resultado.getObject("SOL_FECHAHORA").toString().substring(0, 10)
							String dia = cadena.substring(8, 10)
							String mes = cadena.substring(5, 7)
							String año = cadena.substring(0, 4)
							String fechadesde = dia + '/' + mes + '/' + año
							println ("El fecha desde es:" + fechadesde)
							Resultadofinal = Resultadofinal + " " + fechadesde
							println ("La cadena es:" + cadena)
							println ("El Resultado Final es:" + Resultadofinal)
						}
					}
				}
			}
		}
		println ("La longitud es:" + Resultadofinal.length())
		if(Resultadofinal.length() != 0){
			Resultadofinal = Resultadofinal.substring(1,Resultadofinal.length())
			fd = Resultadofinal.split(" ")
		}

		return fd
	}

	/** Verify Filtro Paciente in Virologia funciona ok para una Fecha desde y Fecha hasta
	 */
	@Keyword
	def FiltroPacienteVirologia(String fechaD, String fechaH, String paciente){
		String Resultadofinal = ""
		String [] fd
		SQLConnect conexion = new SQLConnect();
		conexion.connectDB(GlobalVariable.urlDB, GlobalVariable.portDB, GlobalVariable.nameDB, GlobalVariable.userDB, GlobalVariable.passwordDB);

		/**Obtener el Paciente para un pacienteID Y NroDocumento*/
		String query5 = String.format("SELECT PACIE_ID, PACIE_NRODOCUMENTO FROM PACIENTE WHERE PACIE_NRODOCUMENTO = '" + paciente + "' ")
		ResultSet resultado5 = conexion.executeQuery(query5)
		Object PacID = null
		if(resultado5.next()){
			PacID = resultado5.getObject("PACIE_ID")
		}

		/**Obtener ID de la Solicitud para una Fecha Desde Y fechaH y Estado*/
		String query = String.format("SELECT SOL_ID, SOL_FECHA, SOL_FECHAHORA, SOL_PACIENTEID FROM SOLICITUD WHERE SOL_SOLICITUDESTADO NOT LIKE 'I' AND SOL_HABILITADA = '0' AND SOL_FECHAHORA >= TO_TIMESTAMP('"+fechaD+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') AND SOL_FECHAHORA <= TO_TIMESTAMP('"+fechaH+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') AND SOL_PACIENTEID = '"+PacID+"' ORDER BY SOL_FECHAHORA")
		ResultSet resultado = conexion.executeQuery(query)
		Object solicitudID = null
		Object pacienteID = null
		while(resultado.next()){
			solicitudID = resultado.getObject("SOL_ID")
			pacienteID = resultado.getObject("SOL_PACIENTEID")
			println ("El Id de la Solicitud es:" + solicitudID)
			println ("El Id del Paciente es:" + pacienteID)

			/**Obtener los IDs del Estudio para la solicitudID*/
			String query2 = String.format("SELECT SOL_ESTUDIOID, SOL_ID FROM SOLICITUDESTUDIOS WHERE SOL_ID = '" + solicitudID + "' ")
			ResultSet resultado2 = conexion.executeQuery(query2)
			Object Estudio = null
			while(resultado2.next()){
				Estudio = resultado2.getObject("SOL_ESTUDIOID")
				println ("El Id del Estudio es:" + Estudio)

				/**Obtener el ID del Sector para el Estudio*/
				String query3 = String.format("SELECT EST_ID, EST_SEC_ID FROM ESTUDIO WHERE EST_ID = '" + Estudio + "'")
				ResultSet resultado3 = conexion.executeQuery(query3)
				Object Sector = null
				while(resultado3.next()){
					Sector = resultado3.getObject("EST_SEC_ID")
					println ("El Id del Sector es:" + Sector)

					/**Obtener el ID de la Unidad para el SectorID*/
					String query4 = String.format("SELECT SEC_ID, SEC_UNI_ID FROM SECTOR WHERE SEC_ID = '" + Sector + "'")
					ResultSet resultado4 = conexion.executeQuery(query4)
					Object Unidad = null
					while(resultado4.next()){
						Unidad = resultado4.getObject("SEC_UNI_ID")
						println ("El Id de la Unidad es:" + Unidad)
						String cadena = ""
						if(Unidad == 1){
							cadena = resultado.getObject("SOL_FECHAHORA").toString().substring(0, 10)
							String dia = cadena.substring(8, 10)
							String mes = cadena.substring(5, 7)
							String año = cadena.substring(0, 4)
							String fechadesde = dia + '/' + mes + '/' + año
							println ("El fecha desde es:" + fechadesde)
							Resultadofinal = Resultadofinal + " " + fechadesde
							println ("La cadena es:" + cadena)
							println ("El Resultado Final es:" + Resultadofinal)
						}
					}
				}
			}
		}
		println ("La longitud es:" + Resultadofinal.length())
		if(Resultadofinal.length() != 0){
			Resultadofinal = Resultadofinal.substring(1,Resultadofinal.length())
			fd = Resultadofinal.split(" ")
		}

		return fd
	}

	/** Verify Filtro Fecha Validada in Virologia funciona ok para una Fecha desde y Fecha hasta
	 */
	@Keyword
	def FiltroFechaValVirologia(String fechaD, String fechaH, String fechaV){
		String Resultadofinal = ""
		String [] fd
		SQLConnect conexion = new SQLConnect();
		conexion.connectDB(GlobalVariable.urlDB, GlobalVariable.portDB, GlobalVariable.nameDB, GlobalVariable.userDB, GlobalVariable.passwordDB);

		/**Obtener ID de la Solicitud para una Fecha Desde Y fechaH*/
		String query = String.format("SELECT SOL_ID, SOL_FECHA, SOL_FECHAHORA FROM SOLICITUD WHERE SOL_SOLICITUDESTADO NOT LIKE 'I' AND SOL_HABILITADA = '0' AND SOL_FECHAHORA >= TO_TIMESTAMP('"+fechaD+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') AND SOL_FECHAHORA <= TO_TIMESTAMP('"+fechaH+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') ORDER BY SOL_FECHAHORA")
		ResultSet resultado = conexion.executeQuery(query)
		Object solicitudID = null
		while(resultado.next()){
			solicitudID = resultado.getObject("SOL_ID")
			println ("El Id de la Solicitud es:" + solicitudID)

			/**Obtener los IDs del Estudio para la solicitudID*/
			String query2 = String.format("SELECT SOL_ESTUDIOID, SOL_ID FROM SOLICITUDESTUDIOS WHERE SOL_ID = '" + solicitudID + "' ")
			ResultSet resultado2 = conexion.executeQuery(query2)
			Object Estudio = null
			while(resultado2.next()){
				Estudio = resultado2.getObject("SOL_ESTUDIOID")
				println ("El Id del Estudio es:" + Estudio)

				/**Obtener el ID del Sector para el Estudio*/
				String query3 = String.format("SELECT EST_ID, EST_SEC_ID FROM ESTUDIO WHERE EST_ID = '" + Estudio + "'")
				ResultSet resultado3 = conexion.executeQuery(query3)
				Object Sector = null
				while(resultado3.next()){
					Sector = resultado3.getObject("EST_SEC_ID")
					println ("El Id del Sector es:" + Sector)

					/**Obtener el ID de la Unidad para el SectorID*/
					String query4 = String.format("SELECT SEC_ID, SEC_UNI_ID FROM SECTOR WHERE SEC_ID = '" + Sector + "'")
					ResultSet resultado4 = conexion.executeQuery(query4)
					Object Unidad = null
					while(resultado4.next()){
						Unidad = resultado4.getObject("SEC_UNI_ID")
						println ("El Id de la Unidad es:" + Unidad)
						String cadena = ""
						if(Unidad == 1){

							/**Obtener si existe solicitud con Fecha de Validación >= que el filtro que viene como parámetro en el método*/
							String query5 = String.format("SELECT COUNT(*) FROM RESULTADOESTANDAREXAMENLIBVAL WHERE EXAMENLIBVALRESEST_SOL_ID = '" + solicitudID + "' AND EXAMENLIBVALRESEST_VAL_FECHA >= TO_TIMESTAMP('"+fechaV+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') ")
							ResultSet resultado5 = conexion.executeQuery(query5)
							Object Cantidad = null
							while(resultado5.next()){
								Cantidad = resultado5.getObject("COUNT(*)")
								println ("La cantidad de fechas Validadas que existen son:" + Cantidad)
							}

							if(Cantidad > 0){
								cadena = resultado.getObject("SOL_FECHAHORA").toString().substring(0, 10)
								String dia = cadena.substring(8, 10)
								String mes = cadena.substring(5, 7)
								String año = cadena.substring(0, 4)
								String fechadesde = dia + '/' + mes + '/' + año
								println ("El fecha desde es:" + fechadesde)
								Resultadofinal = Resultadofinal + " " + fechadesde
								println ("La cadena es:" + cadena)
								println ("El Resultado Final es:" + Resultadofinal)
							}
						}
					}
				}
			}
		}
		println ("La longitud es:" + Resultadofinal.length())
		if(Resultadofinal.length() != 0){
			Resultadofinal = Resultadofinal.substring(1,Resultadofinal.length())
			fd = Resultadofinal.split(" ")
		}

		return fd
	}

	/** Verify Liberación Parcial in Virologia funciona ok
	 */
	@Keyword
	def IngresoLP1Virologia(String fechaD, String fechaH, String numeroSOL, String estudio, String muestra, int longitud){
		String Resultadofinal = ""
		String [] fd
		String muestra2
		muestra2 = "%" + muestra + "%"
		SQLConnect conexion = new SQLConnect();
		conexion.connectDB(GlobalVariable.urlDB, GlobalVariable.portDB, GlobalVariable.nameDB, GlobalVariable.userDB, GlobalVariable.passwordDB);
		String query
		/**Obtener ID de la Solicitud para una Fecha Desde Y fechaH y numeroSOL*/
		query = String.format("SELECT SOL_ID, SOL_FECHA, SOL_FECHAHORA FROM SOLICITUD WHERE SOL_SOLICITUDESTADO NOT LIKE 'I' AND SOL_HABILITADA = '0' AND SOL_FECHAHORA >= TO_TIMESTAMP('"+fechaD+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') AND SOL_FECHAHORA <= TO_TIMESTAMP('"+fechaH+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') AND SOL_NUMERO = '"+numeroSOL+"'")
		ResultSet resultado = conexion.executeQuery(query)
		Object solicitudID = null
		while(resultado.next()){
			solicitudID = resultado.getObject("SOL_ID")
			println ("El Id de la Solicitud es:" + solicitudID)

			/**Obtener el ID del Estudio para el estudio*/
			String query2 = String.format("SELECT EST_ID FROM ESTUDIO WHERE EST_NOMBRE = '" + estudio + "' ")
			ResultSet resultado2 = conexion.executeQuery(query2)
			Object Estudio = null
			resultado2.next()
			Estudio = resultado2.getObject("EST_ID")
			println ("El Id del Estudio es:" + Estudio)

			/**Obtener el ID de la Muestra para la descripcion*/
			String query3 = String.format("SELECT MUE_ID FROM MUESTRA WHERE MUE_DESCRIPCION LIKE '%s'", muestra2.trim())
			ResultSet resultado3 = conexion.executeQuery(query3)
			Object Muestra = null
			resultado3.next()
			Muestra = resultado3.getObject("MUE_ID")
			println ("El Id de la Muestra es:" + Muestra)

			/**Obtener la Descripción del Exámen para la solicitudID*/
			String query4 = String.format("SELECT RESEST_VERSION, RESEST_EXA_ROTULO FROM RESULTADOESTANDAREXAMEN WHERE RESEST_SOL_ID = '" + solicitudID + "' AND RESEST_EST_ID ='" + Estudio + "' AND RESEST_MUE_ID = '" + Muestra + "' ")
			ResultSet resultado4 = conexion.executeQuery(query4)
			Object examen = null
			Object version = null
			while(resultado4.next()){
				examen = resultado4.getObject("RESEST_EXA_ROTULO")
				version = resultado4.getObject("RESEST_VERSION")
				println ("El Examen es:" + examen)
				println ("La Versión es:" + version)

				/**Obtener si está liberado el Examen para el examen, version, muestra y solicitudID*/
				/**String query5 = String.format("SELECT EXAMENLIBVALRESEST_LIBERADO FROM RESULTADOESTANDAREXAMENLIBVAL WHERE WHERE RESEST_SOL_ID = '" + solicitudID + "' AND RESEST_EST_ID ='" + Estudio + "' AND RESEST_MUE_ID = '" + Muestra + "' AND EXAMENLIBVALRESEST_VERSION = '" + version + "'")*/
				String query5 = String.format("SELECT EXAMENLIBVALRESEST_LIBERADO FROM RESULTADOESTANDAREXAMENLIBVAL WHERE EXAMENLIBVALRESEST_SOL_ID = '%s' AND EXAMENLIBVALRESEST_EST_ID = '%s' AND EXAMENLIBVALRESEST_MUE_ID = '%s' AND EXAMENLIBVALRESEST_VERSION = '%s'", solicitudID.toString().trim(), Estudio.toString().trim(), Muestra.toString().trim(), version.toString().trim())
				ResultSet resultado5 = conexion.executeQuery(query5)
				Object liberado = null

				/**Obtener la cantidad de Elementos en la Tabla RESULTADOESTANDAREXAMENLIBVAL*/
				/**String query6 = String.format("SELECT EXAMENLIBVALRESEST_LIBERADO FROM RESULTADOESTANDAREXAMENLIBVAL WHERE WHERE RESEST_SOL_ID = '" + solicitudID + "' AND RESEST_EST_ID ='" + Estudio + "' AND RESEST_MUE_ID = '" + Muestra + "' AND EXAMENLIBVALRESEST_VERSION = '" + version + "'")*/
				String query6 = String.format("SELECT COUNT(*) FROM RESULTADOESTANDAREXAMENLIBVAL WHERE EXAMENLIBVALRESEST_SOL_ID = '%s' AND EXAMENLIBVALRESEST_EST_ID = '%s' AND EXAMENLIBVALRESEST_MUE_ID = '%s'", solicitudID.toString().trim(), Estudio.toString().trim(), Muestra.toString().trim())
				ResultSet resultado6 = conexion.executeQuery(query6)
				Object cantidad = null
				int cantidadT = 0
				resultado6.next()
				cantidad = resultado6.getObject("COUNT(*)")
				cantidadT = cantidad
				String aux = (cantidadT - longitud) + 1
				println ("El valor aux es:" + aux)

				while(resultado5.next()){
					liberado = resultado5.getObject("EXAMENLIBVALRESEST_LIBERADO")
					println ("El examen $version se encuentra liberado o no:" + liberado)
					if(version.toString() == aux){
						Resultadofinal = examen.toString().trim() + ":" + liberado
					}
				}
			}
		}
		if(Resultadofinal.length() != 0){
			fd = Resultadofinal.split(":")
		}
		return fd
	}

	/** Verify Liberación Total in Virologia funciona ok
	 */
	@Keyword
	def IngresoLT1Virologia(String fechaD, String fechaH, String numeroSOL, String estudio, String muestra, int longitud){
		String Resultadofinal = ""
		String [] fd
		String muestra2
		muestra2 = "%" + muestra + "%"
		SQLConnect conexion = new SQLConnect();
		conexion.connectDB(GlobalVariable.urlDB, GlobalVariable.portDB, GlobalVariable.nameDB, GlobalVariable.userDB, GlobalVariable.passwordDB);
		String query
		/**Obtener ID de la Solicitud para una Fecha Desde Y fechaH y numeroSOL*/
		query = String.format("SELECT SOL_ID, SOL_FECHA, SOL_FECHAHORA FROM SOLICITUD WHERE SOL_SOLICITUDESTADO NOT LIKE 'I' AND SOL_HABILITADA = '0' AND SOL_FECHAHORA >= TO_TIMESTAMP('"+fechaD+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') AND SOL_FECHAHORA <= TO_TIMESTAMP('"+fechaH+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') AND SOL_NUMERO = '"+numeroSOL+"'")
		ResultSet resultado = conexion.executeQuery(query)
		Object solicitudID = null
		while(resultado.next()){
			solicitudID = resultado.getObject("SOL_ID")
			println ("El Id de la Solicitud es:" + solicitudID)

			/**Obtener el ID del Estudio para el estudio*/
			String query2 = String.format("SELECT EST_ID FROM ESTUDIO WHERE EST_NOMBRE = '" + estudio + "' ")
			ResultSet resultado2 = conexion.executeQuery(query2)
			Object Estudio = null
			resultado2.next()
			Estudio = resultado2.getObject("EST_ID")
			println ("El Id del Estudio es:" + Estudio)

			/**Obtener el ID de la Muestra para la descripcion*/
			String query3 = String.format("SELECT MUE_ID FROM MUESTRA WHERE MUE_DESCRIPCION LIKE '%s'", muestra2.trim())
			ResultSet resultado3 = conexion.executeQuery(query3)
			Object Muestra = null
			resultado3.next()
			Muestra = resultado3.getObject("MUE_ID")
			println ("El Id de la Muestra es:" + Muestra)

			/**Obtener la Descripción del Exámen para la solicitudID*/
			String query4 = String.format("SELECT RESEST_VERSION, RESEST_EXA_ROTULO FROM RESULTADOESTANDAREXAMEN WHERE RESEST_SOL_ID = '" + solicitudID + "' AND RESEST_EST_ID ='" + Estudio + "' AND RESEST_MUE_ID = '" + Muestra + "' ")
			ResultSet resultado4 = conexion.executeQuery(query4)
			Object examen = null
			Object version = null
			while(resultado4.next()){
				examen = resultado4.getObject("RESEST_EXA_ROTULO")
				version = resultado4.getObject("RESEST_VERSION")
				println ("El Examen es:" + examen)
				println ("La Versión es:" + version)

				/**Obtener si está liberado el Examen para el examen, version, muestra y solicitudID*/
				/**String query5 = String.format("SELECT EXAMENLIBVALRESEST_LIBERADO FROM RESULTADOESTANDAREXAMENLIBVAL WHERE WHERE RESEST_SOL_ID = '" + solicitudID + "' AND RESEST_EST_ID ='" + Estudio + "' AND RESEST_MUE_ID = '" + Muestra + "' AND EXAMENLIBVALRESEST_VERSION = '" + version + "'")*/
				String query5 = String.format("SELECT EXAMENLIBVALRESEST_LIBERADO FROM RESULTADOESTANDAREXAMENLIBVAL WHERE EXAMENLIBVALRESEST_SOL_ID = '%s' AND EXAMENLIBVALRESEST_EST_ID = '%s' AND EXAMENLIBVALRESEST_MUE_ID = '%s' AND EXAMENLIBVALRESEST_VERSION = '%s'", solicitudID.toString().trim(), Estudio.toString().trim(), Muestra.toString().trim(), version.toString().trim())
				ResultSet resultado5 = conexion.executeQuery(query5)
				Object liberado = null

				/**Obtener la cantidad de Elementos en la Tabla RESULTADOESTANDAREXAMENLIBVAL*/
				/**String query6 = String.format("SELECT EXAMENLIBVALRESEST_LIBERADO FROM RESULTADOESTANDAREXAMENLIBVAL WHERE WHERE RESEST_SOL_ID = '" + solicitudID + "' AND RESEST_EST_ID ='" + Estudio + "' AND RESEST_MUE_ID = '" + Muestra + "' AND EXAMENLIBVALRESEST_VERSION = '" + version + "'")*/
				String query6 = String.format("SELECT COUNT(*) FROM RESULTADOESTANDAREXAMENLIBVAL WHERE EXAMENLIBVALRESEST_SOL_ID = '%s' AND EXAMENLIBVALRESEST_EST_ID = '%s' AND EXAMENLIBVALRESEST_MUE_ID = '%s'", solicitudID.toString().trim(), Estudio.toString().trim(), Muestra.toString().trim())
				ResultSet resultado6 = conexion.executeQuery(query6)
				Object cantidad = null
				int cantidadT = 0
				int versionT = 0
				resultado6.next()
				cantidad = resultado6.getObject("COUNT(*)")
				cantidadT = cantidad
				versionT = version
				int aux = (cantidadT - longitud) + 1
				println ("El valor aux es:" + aux)

				while(resultado5.next()){
					liberado = resultado5.getObject("EXAMENLIBVALRESEST_LIBERADO")
					println ("El examen $version se encuentra liberado o no:" + liberado)
					if(versionT >= aux){
						Resultadofinal = Resultadofinal + "-" + examen.toString().trim() + ":" + liberado
						println ("El Resultado Final es:" + Resultadofinal)
					}
				}
			}
			println ("La longitud es:" + Resultadofinal.length())
			if(Resultadofinal.length() != 0){
				Resultadofinal = Resultadofinal.substring(1,Resultadofinal.length())
				fd = Resultadofinal.split("-")
			}
		}
		return fd
	}

	/** Verify Comentarios in Virologia funciona ok
	 */
	@Keyword
	def IngresoComentariosVirologia(String fechaD, String fechaH, String numeroSOL, String estudio, String muestra, int longitud){
		String Resultadofinal = ""
		String [] fd
		String muestra2
		muestra2 = "%" + muestra + "%"
		SQLConnect conexion = new SQLConnect();
		conexion.connectDB(GlobalVariable.urlDB, GlobalVariable.portDB, GlobalVariable.nameDB, GlobalVariable.userDB, GlobalVariable.passwordDB);
		String query
		/**Obtener ID de la Solicitud para una Fecha Desde Y fechaH y numeroSOL*/
		query = String.format("SELECT SOL_ID, SOL_FECHA, SOL_FECHAHORA FROM SOLICITUD WHERE SOL_SOLICITUDESTADO NOT LIKE 'I' AND SOL_HABILITADA = '0' AND SOL_FECHAHORA >= TO_TIMESTAMP('"+fechaD+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') AND SOL_FECHAHORA <= TO_TIMESTAMP('"+fechaH+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') AND SOL_NUMERO = '"+numeroSOL+"'")
		ResultSet resultado = conexion.executeQuery(query)
		Object solicitudID = null
		while(resultado.next()){
			solicitudID = resultado.getObject("SOL_ID")
			println ("El Id de la Solicitud es:" + solicitudID)

			/**Obtener el ID del Estudio para el estudio*/
			String query2 = String.format("SELECT EST_ID FROM ESTUDIO WHERE EST_NOMBRE = '" + estudio + "' ")
			ResultSet resultado2 = conexion.executeQuery(query2)
			Object Estudio = null
			resultado2.next()
			Estudio = resultado2.getObject("EST_ID")
			println ("El Id del Estudio es:" + Estudio)

			/**Obtener el ID de la Muestra para la solicitudID + Estudio*/
			String query3 = String.format("SELECT SOL_EST_MUESTRAID FROM SOLICITUDESTUDIOS WHERE SOL_ID = '%s' AND SOL_ESTUDIOID = '%s'", solicitudID.toString().trim(), Estudio.toString().trim())
			ResultSet resultado3 = conexion.executeQuery(query3)
			Object Muestra = null
			resultado3.next()
			Muestra = resultado3.getObject("SOL_EST_MUESTRAID")
			println ("El Id de la Muestra es:" + Muestra)

			/**Obtener el ID de la Muestra para la Tabla SOLICITUDCOMENTARIO*/
			String query4 = String.format("SELECT SOL_ESTMUES_ID FROM SOLICITUDESTUDIOSMUESTRA WHERE SOL_ID = '" + solicitudID + "' AND SOL_EST_MUESTRAID ='" + Muestra + "' ")
			ResultSet resultado4 = conexion.executeQuery(query4)
			Object EstudioMuestraID = null
			while(resultado4.next()){
				EstudioMuestraID = resultado4.getObject("SOL_ESTMUES_ID")
				println ("El EstudioMuestraID es:" + EstudioMuestraID)

				/**Obtener el Comentario para la solicitudID + el EstudioMuestraID*/
				String query5 = String.format("select DBMS_LOB.substr(SOLICITUDCOMENTARIODESCRIPCION, 3000)  AS SOLICITUDCOMENTARIODESCRIPCION  from SOLICITUDCOMENTARIO WHERE SOLICITUDCOMENTARIOID = '" + solicitudID + "' AND SOLICITUDCOMENTARIOMUESTRAID = '" + EstudioMuestraID + "' ")
				ResultSet resultado5 = conexion.executeQuery(query5)
				Object Comentario = null
				while(resultado5.next()){
					Comentario = resultado5.getObject("SOLICITUDCOMENTARIODESCRIPCION")
					println ("El Comentario es:" + Comentario)
					Resultadofinal = Comentario
				}
			}
		}
		return Resultadofinal
	}
	
	/** Verify Pedido de Información in Virologia funciona ok
	 */
	@Keyword
	def IngresoInformacionVirologia(String fechaD, String fechaH, String numeroSOL, String estudio, String muestra, int longitud){
		String Resultadofinal = ""
		String [] fd
		String muestra2
		muestra2 = "%" + muestra + "%"
		SQLConnect conexion = new SQLConnect();
		conexion.connectDB(GlobalVariable.urlDB, GlobalVariable.portDB, GlobalVariable.nameDB, GlobalVariable.userDB, GlobalVariable.passwordDB);
		String query
		/**Obtener ID de la Solicitud para una Fecha Desde Y fechaH y numeroSOL*/
		query = String.format("SELECT SOL_ID, SOL_FECHA, SOL_FECHAHORA FROM SOLICITUD WHERE SOL_SOLICITUDESTADO NOT LIKE 'I' AND SOL_HABILITADA = '0' AND SOL_FECHAHORA >= TO_TIMESTAMP('"+fechaD+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') AND SOL_FECHAHORA <= TO_TIMESTAMP('"+fechaH+" 00:00:00.000','YYYY-MM-DD HH24:MI:SS.FF') AND SOL_NUMERO = '"+numeroSOL+"'")
		ResultSet resultado = conexion.executeQuery(query)
		Object solicitudID = null
		while(resultado.next()){
			solicitudID = resultado.getObject("SOL_ID")
			println ("El Id de la Solicitud es:" + solicitudID)

			/**Obtener el ID del Estudio para el estudio*/
			String query2 = String.format("SELECT EST_ID FROM ESTUDIO WHERE EST_NOMBRE = '" + estudio + "' ")
			ResultSet resultado2 = conexion.executeQuery(query2)
			Object Estudio = null
			resultado2.next()
			Estudio = resultado2.getObject("EST_ID")
			println ("El Id del Estudio es:" + Estudio)

			/**Obtener el ID de la Muestra para la solicitudID + Estudio*/
			String query3 = String.format("SELECT SOL_EST_MUESTRAID FROM SOLICITUDESTUDIOS WHERE SOL_ID = '%s' AND SOL_ESTUDIOID = '%s'", solicitudID.toString().trim(), Estudio.toString().trim())
			ResultSet resultado3 = conexion.executeQuery(query3)
			Object Muestra = null
			resultado3.next()
			Muestra = resultado3.getObject("SOL_EST_MUESTRAID")
			println ("El Id de la Muestra es:" + Muestra)

			/**Obtener el ID de la Muestra para la Tabla SOLICITUDINFORMACION*/
			String query4 = String.format("SELECT SOL_ESTMUES_ID FROM SOLICITUDESTUDIOSMUESTRA WHERE SOL_ID = '" + solicitudID + "' AND SOL_EST_MUESTRAID ='" + Muestra + "' ")
			ResultSet resultado4 = conexion.executeQuery(query4)
			Object EstudioMuestraID = null
			while(resultado4.next()){
				EstudioMuestraID = resultado4.getObject("SOL_ESTMUES_ID")
				println ("El EstudioMuestraID es:" + EstudioMuestraID)

				/**Obtener el Pedido de Información para la solicitudID + el Estudio + el EstudioMuestraID*/
				String query5 = String.format("select DBMS_LOB.substr(SOLICITUDINFORMACIONMENSAJE, 3000)  AS SOLICITUDINFORMACIONMENSAJE  from SOLICITUDINFORMACION WHERE SOLICITUDINFORMACIONSOL_ID = '" + solicitudID + "' AND SOLICITUDINFORMACIONESTUDIOID ='" + Estudio + "' AND SOLICITUDINFORMACIONMUESTRAID = '" + EstudioMuestraID + "' ")
				ResultSet resultado5 = conexion.executeQuery(query5)
				Object PInformacion = null
				while(resultado5.next()){
					PInformacion = resultado5.getObject("SOLICITUDINFORMACIONMENSAJE")
					println ("El Comentario es:" + PInformacion)
					Resultadofinal = PInformacion
				}
			}
		}
		return Resultadofinal
	}

}
