import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.util.TreeMap.KeySet as KeySet
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.InternalData as InternalData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword as WebUIAbstractKeyword
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import net.sf.cglib.core.ClassesKey.Key as Key
import java.util.Date as Date
import com.database.FechaHoy

InternalData ID = findTestData('Data Files/Internal Data Login')

WebUI.callTestCase(findTestCase('Login'), [('User') : ID.getValue(1, 2), ('Password') : ID.getValue(2, 2)], FailureHandling.STOP_ON_FAILURE)

//Execute JavaScript
WebUI.executeJavaScript('$("#vMENU").val("15");', null)

WebUI.click(findTestObject('Object Repository/Login/Page_login/input_Men_BTNCONFIRMAR'))

WebUI.delay(1)

WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Bienvenida/Page_Bienvenida/td_Recepcin'))

WebUI.delay(1)

WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Bienvenida/Page_Bienvenida/td_Bandeja Recepcin'))

WebUI.delay(1)

WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Bandeja Mesa de Entrada/img_Ingrese Nro de Solicitud_INSERT'))

WebUI.delay(1)

//Select Code Study
WebUI.setText(findTestObject('Object Repository/RECEPCION/Page_Ingreso Solicitud/input_Cdigo_vPEST_ID'), '120')

WebUI.delay(1)

//Press Tab
WebUI.sendKeys(findTestObject('Object Repository/RECEPCION/Page_Ingreso Solicitud/input_Cdigo_vPEST_ID'), Keys.chord(Keys.TAB))

WebUI.delay(1)

WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Ingreso Solicitud/input_PLACA O TUBO_CTLSELECCIONA_0001'))

WebUI.delay(1)

WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Ingreso Solicitud/input_Necesita Aprobacin de Comisin_BUTTON1'))

WebUI.delay(1)

//Select Country
WebUI.selectOptionByValue(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/select_PAIS'), '43', true)

WebUI.delay(1)

//Press Tab
WebUI.sendKeys(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/select_PAIS'), Keys.chord(Keys.TAB))

WebUI.delay(1)

//Select Type Document
WebUI.selectOptionByValue(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/select_TIPO_DOCUMENTO'), '1', 
    true)

WebUI.delay(1)

//Press Tab
WebUI.sendKeys(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/select_TIPO_DOCUMENTO'), Keys.chord(Keys.TAB))

WebUI.delay(1)

//Document Number
WebUI.setText(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/input_-_vSOL_PACIENTENRODOCUMENTO'), '62843840')

WebUI.delay(1)

//Insert Centro Asistencial de Procedencia
WebUI.setText(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/Page_Solicitud VIH Mayores/input_Centro asistencial de procedencia_SOL_CENTROASISPROC'), 
    'Centro asistencial de Procedencia de Prueba')

WebUI.delay(1)

//Obtener Fecha de Hoy
FechaHoy f = new FechaHoy()
String result = f.FechaActual()

//Insert Fecha de Recolección de la Muestra
WebUI.executeJavaScript('$("#SOL_FECHAEXTRACCION").val("'+result+'");', null)

//Select option Reactivo in Tamizaje por prueba rápida
WebUI.selectOptionByValue(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/Page_Solicitud VIH Mayores/Page_Solicitud VIH Mayores/select_TAMIZAJEPRUEBARAPIDA'), "N", true)

//Write Marca Comercial
WebUI.setText(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/Page_Solicitud VIH Mayores/input_Marca Comercial_SOL_TAMPRUEBARAPIDAMARCA'), "Marca Comercial de Prueba")

//Select Práctica Sexual Hombre SI
WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/INFECCION POR VIH-1/Page_Solicitud VIH Mayores/input_Si_SOL_PRACTICASEXUALHOMBRE'))

//Select Extranjero NO
WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/INFECCION POR VIH-1/Page_Solicitud VIH Mayores/input_No_SOL_EXTRANJERO'))

//Select Vía Sexual SI
WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/INFECCION POR VIH-1/Page_Solicitud VIH Mayores/input_Si_SOL_VIAADQUISEXUAL'))

//Select Vía Sanguínea NO
WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/INFECCION POR VIH-1/Page_Solicitud VIH Mayores/input_No_SOL_VIAADQUISANGRE'))

//Select Vía Vertical SI
WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/INFECCION POR VIH-1/Page_Solicitud VIH Mayores/input_Si_SOL_VIAADQUIVERTICAL'))

//Select Presencia Enfermedades Oportunistas
WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/ENFERMEDADES OPORTUNISTAS/Page_Solicitud VIH Mayores/input_Si_SOL_ENFOPORTUNISTAS'))

//Verify Ckeckpoint true in Presencia de Enfermedades Oportunistas
boolean PEO = WebUI.verifyElementChecked(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/ENFERMEDADES OPORTUNISTAS/Page_Solicitud VIH Mayores/input_Si_SOL_ENFOPORTUNISTAS'), 10)

if(PEO){
	//Select Tuberculosis Pulmonar NO
	WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/ENFERMEDADES OPORTUNISTAS/Page_Solicitud VIH Mayores/input_No_SOL_ENFOPTUBERCPULMONAR'))
	
	//Select Tuberculosis Extra Pulmonar NO
	WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/ENFERMEDADES OPORTUNISTAS/Page_Solicitud VIH Mayores/input_No_SOL_ENFOPTUBERCEXTRAPULMONAR'))
	
	//Select Tuberculosis Recurrente SI
	WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/ENFERMEDADES OPORTUNISTAS/Page_Solicitud VIH Mayores/input_Si_SOL_ENFOPTUBERCRECURRENTE'))
	
	//Select Pneumocystis jiroveci SI
	WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/ENFERMEDADES OPORTUNISTAS/Page_Solicitud VIH Mayores/input_Si_SOL_ENFOPPNEUMOJIROVECI'))
	
	//Select Neumonía recurrente NO
	WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/ENFERMEDADES OPORTUNISTAS/Page_Solicitud VIH Mayores/input_No_SOL_ENFOPNEUMONIARECURRENTE'))
	
	// Select Criptococosis SI
	WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/ENFERMEDADES OPORTUNISTAS/Page_Solicitud VIH Mayores/input_Si_SOL_ENFOPCRIPTOCOCOSIS'))
	
	//Select Histoplasmosis NO
	WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/ENFERMEDADES OPORTUNISTAS/Page_Solicitud VIH Mayores/input_No_SOL_ENFOPHISTOPLASMOSIS'))
	
	//Select Toxoplasmosis SI
	WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/ENFERMEDADES OPORTUNISTAS/Page_Solicitud VIH Mayores/input_Si_SOL_ENFOPTOXOPLASMOSIS'))
	
	//Select Linfoma primario del SNC NO
	WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/ENFERMEDADES OPORTUNISTAS/Page_Solicitud VIH Mayores/input_No_SOL_ENFOPLINFPRIMSNC'))
	
	//Select Linfoma no Hodking SI
	WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/ENFERMEDADES OPORTUNISTAS/Page_Solicitud VIH Mayores/input_Si_SOL_ENFOPNOHODKING'))
	
	//Select Sarcoma de Kaposi NO
	WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/ENFERMEDADES OPORTUNISTAS/Page_Solicitud VIH Mayores/input_No_SOL_ENFOPSARCOMAKAPOSI'))
	
	//Select Carcinoma cervical invasivo SI
	WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/ENFERMEDADES OPORTUNISTAS/Page_Solicitud VIH Mayores/input_Si_SOL_ENFOPCARCCERVINVASICO'))
	
	//Select Leucoencefalopatía multifocal progresiva NO
	WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/ENFERMEDADES OPORTUNISTAS/Page_Solicitud VIH Mayores/input_No_SOL_ENFOPLEUMULTIPROGRESIVA'))
	
	//Select Herpes simple diseminado SI
	WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/ENFERMEDADES OPORTUNISTAS/Page_Solicitud VIH Mayores/input_Si_SOL_ENFOPHERPESSIMPDISEMINADO'))
	
	//Select Citomegalovirus NO
	WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/ENFERMEDADES OPORTUNISTAS/Page_Solicitud VIH Mayores/input_No_SOL_ENFOPCITOMEGALOVIRUS'))
	
	//Select Candidiasis esofágica SI
	WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/ENFERMEDADES OPORTUNISTAS/Page_Solicitud VIH Mayores/input_Si_SOL_ENFOPCANDIDIASISESOFAGICA'))
	
	//Select Micobacteria atípica NO
	WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/ENFERMEDADES OPORTUNISTAS/Page_Solicitud VIH Mayores/input_No_SOL_ENFOPMICOBACTATIPICA'))
}

//clic BTN Confirmar
WebUI.click(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/input_Para generar un nuevo prrafo en los campos de texto usar CtrlEnter_BTN_ENTER'))

WebUI.delay(1)

boolean alert = WebUI.verifyAlertPresent(5, FailureHandling.CONTINUE_ON_FAILURE)

if (alert && PEO) {
    println('Debe especificar una Diarrea oportunista, Prueba Correcta')
} else {
	println ('Nota aclaratoria: Verificar que el campo sea obligatorio')
	println ('Nota aclaratoria: Verificar que el campo Presencia de Enfermedades Oportunistas haya sido seleccionado (SI)')
    println('Prueba incorrecta')
}

WebUI.delay(1)

WebUI.closeBrowser()

