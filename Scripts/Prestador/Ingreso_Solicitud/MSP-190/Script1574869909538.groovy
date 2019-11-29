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

WebUI.click(findTestObject('Object Repository/PRESTADORES/Page_Bandeja Prestador/img_Validacin Parcial conLiberacin Parcial_INSERT'))

WebUI.setText(findTestObject('Object Repository/PRESTADORES/Page_Ingreso Solicitud/input_Cdigo_vPEST_ID'), "0")

WebUI.click(findTestObject('Object Repository/PARASITOLOGIA_MICOLOGIA/Page_Ingreso Solicitud/input_Tipo de  muestra_BUTTON3'))

String estudio = WebUI.getText(findTestObject('Object Repository/PRESTADORES/Page_Ingreso Solicitud/td_gxdomsetAttribute(vEST_NOMBREmaxlength200)')).length()

String requisitos = WebUI.getText(findTestObject('Object Repository/PRESTADORES/Page_Ingreso Solicitud/td_gxdomsetAttribute(vTEC_CON_DESCRIPCIONmaxlength1000)')).length()

String transporte = WebUI.getText(findTestObject('Object Repository/PRESTADORES/Page_Ingreso Solicitud/td_gxdomsetAttribute(vTEC_IND_DESCRIPCIONmaxlength1000)')).length()

String indicaciones = WebUI.getText(findTestObject('Object Repository/PRESTADORES/Page_Ingreso Solicitud/td_gxdomsetAttribute(vTEC_REQ_DESCRIPCIONmaxlength1000)')).length()

if(estudio == "0" && requisitos == "0" && transporte == "0" && indicaciones == "0"){
	println ('Debe especificar un Estudio, Prueba Correcta')
}
else{
	throw new Exception('Prueba Incorrecta')
}

WebUI.delay(2)

WebUI.closeBrowser()
