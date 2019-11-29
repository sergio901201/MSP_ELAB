import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.sql.Connection as Connection
import java.sql.ResultSet as ResultSet
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.InternalData as InternalData
import com.kms.katalon.core.testobject.RequestObject as RequestObject
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.testobject.TestObjectProperty as TestObjectProperty
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import net.sf.cglib.core.ClassesKey.Key as Key

import com.database.FechaHoy

InternalData ID = findTestData('Data Files/Internal Data Login')

WebUI.callTestCase(findTestCase('Login'), [('User') : ID.getValue(1, 5), ('Password') : ID.getValue(2, 5)], FailureHandling.STOP_ON_FAILURE)

not_run: WebUI.selectOptionByValue(findTestObject('Login/Page_login/select_vMENU'), ID.getValue(1, 13), true)

WebUI.executeJavaScript('$("#vMENU").val("101");', null)

WebUI.click(findTestObject('Login/Page_login/input_Men_BTNCONFIRMAR'))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/PRESTADORES/Page_Bienvenida/td_Prestadores'))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/PRESTADORES/Page_Bienvenida/td_Bandeja Solicitudes'))

WebUI.delay(2)

//Insert variable fecha in FechaDesde
WebUI.executeJavaScript('$("#vDESDE").val("'+fecha+'");', null)

//Press Tab
WebUI.sendKeys(findTestObject('Object Repository/PRESTADORES/Page_Bandeja Prestador/input_Fecha Desde_vDESDE'), Keys.chord(Keys.TAB))

//Obtener Fecha de Hoy
FechaHoy f = new FechaHoy()
String result = f.FechaActual2()

//Insert variable fecha in FechaHasta
WebUI.executeJavaScript('$("#vHASTA").val("'+result+'");', null)

//Press Tab
WebUI.click(findTestObject('Object Repository/PRESTADORES/Page_Bandeja Prestador/input_Estudio_vSOL_ESTUDIOID2'))

//Insert Estudio Invalid
WebUI.setText(findTestObject('Object Repository/PRESTADORES/Page_Bandeja Prestador/input_Estudio_vSOL_ESTUDIOID2'), estudio)

boolean errorF = true
String valor = "0"
if(tab){
	//Press click
	WebUI.sendKeys(findTestObject('Object Repository/PRESTADORES/Page_Bandeja Prestador/input_Estudio_vSOL_ESTUDIOID2'), Keys.chord(Keys.TAB))
	
	//Verify Element Present Estudio inválido
	errorF = WebUI.verifyElementPresent(findTestObject('Object Repository/PRESTADORES/Page_Bandeja Prestador/span_El valor no representa un nmero correcto'), 5)
}else{
	//Click Search
	WebUI.click(findTestObject('Object Repository/PRESTADORES/Page_Bandeja Prestador/input_Transporte_BUTTON1'))
	
	WebUI.delay(2)
	
	valor = WebUI.executeJavaScript('return $("#vSOL_ESTUDIOID2").val();', null)
	
	if(valor == "0"){
		errorF = false
	}
}

if(errorF){
	if(valor!="0"){
		println ('Autocompletado, Prueba Correcta')
		}
		println ('Debe especificar un Estudio válido, Prueba Correcta')
}else{
	throw new Exception('Prueba Incorrecta')
}

WebUI.delay(2)

WebUI.closeBrowser()
