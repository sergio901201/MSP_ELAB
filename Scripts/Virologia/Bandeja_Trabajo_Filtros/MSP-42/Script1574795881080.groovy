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

WebUI.callTestCase(findTestCase('Login'), [('User') : ID.getValue(1, 3), ('Password') : ID.getValue(2, 3)], FailureHandling.STOP_ON_FAILURE)

not_run: WebUI.selectOptionByValue(findTestObject('Login/Page_login/select_vMENU'), ID.getValue(1, 13), true)

WebUI.executeJavaScript('$("#vMENU").val("6");', null)

WebUI.click(findTestObject('Login/Page_login/input_Men_BTNCONFIRMAR'))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Virologia/Page_Bienvenida/span_Virologia'))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Virologia/Page_Bienvenida/td_Bandeja de Trabajo'))

WebUI.delay(2)

//Insert variable fecha in FechaDesde
WebUI.executeJavaScript('$("#vFECHADESDE").val("'+fecha+'");', null)

//Press Tab
WebUI.sendKeys(findTestObject('Object Repository/Virologia/Page_Bienvenida/Page_Bandeja de trabajo de Unidades (1)/input_Fecha desde_vFECHADESDE'), Keys.chord(Keys.TAB))

//Obtener Fecha de Hoy
FechaHoy f = new FechaHoy()
String result = f.FechaActual2()

//Insert variable fecha in FechaHasta
WebUI.executeJavaScript('$("#vFECHAHASTA").val("'+result+'");', null)

//Press Tab
WebUI.sendKeys(findTestObject('Object Repository/Virologia/Page_Bienvenida/Page_Bandeja de trabajo de Unidades (1)/input_Fecha hasta_vFECHAHASTA'), Keys.chord(Keys.TAB))

//Insert Estudio Invalid
WebUI.setText(findTestObject('Object Repository/Virologia/Page_Bienvenida/Page_Bandeja de trabajo de Unidades (1)/input_Estudio_vEST_ID'), estudio)

boolean errorF = true
String valor = "0"
if(tab){
	//Press Tab
	WebUI.sendKeys(findTestObject('Object Repository/Virologia/Page_Bienvenida/Page_Bandeja de trabajo de Unidades (1)/input_Fecha hasta_vFECHAHASTA'), Keys.chord(Keys.TAB))
	
	//Verify Element Present Estudio inválido
	errorF = WebUI.verifyElementPresent(findTestObject('Object Repository/Virologia/Page_Bienvenida/Page_Bandeja de trabajo de Unidades (1)/span_El valor no representa un nmero correcto'), 5)
}else{
	//Click Search
	WebUI.click(findTestObject('Object Repository/Virologia/Page_Bienvenida/Page_Bandeja de trabajo de Unidades (1)/img_Sector_IMAGE1'))
	
	WebUI.delay(2)
	
	valor = WebUI.executeJavaScript('return $("#vEST_ID").val();', null)
	
	if(valor == "0"){
		errorF = false
	}
}

if(errorF){
	if(valor != "0"){
		println ('Prueba Correcta')
	}else{
		println ('Debe especificar un Estudio válido, Prueba Correcta')
	}
}
else{
	throw new Exception('Prueba Incorrecta')
}

WebUI.delay(2)

WebUI.closeBrowser()
