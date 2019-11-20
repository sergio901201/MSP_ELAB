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
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import net.sf.cglib.core.ClassesKey.Key as Key

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
WebUI.setText(findTestObject('Object Repository/RECEPCION/Page_Ingreso Solicitud/input_Cdigo_vPEST_ID'), codigo23)

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
WebUI.selectOptionByValue(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/select_TIPO_DOCUMENTO'), '201',
	true)

WebUI.delay(1)

//Press Tab
WebUI.sendKeys(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/select_TIPO_DOCUMENTO'), Keys.chord(Keys.TAB))

WebUI.delay(1)

//Document Number
WebUI.setText(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/input_-_vSOL_PACIENTENRODOCUMENTO'), '')

WebUI.delay(1)

//Press Tab
WebUI.sendKeys(findTestObject('Object Repository/RECEPCION/Page_Solicitud Cepas/input_-_vSOL_PACIENTENRODOCUMENTO'), Keys.chord(
		Keys.TAB))

WebUI.delay(1)

boolean alert = WebUI.verifyAlertPresent(5, FailureHandling.CONTINUE_ON_FAILURE)

if (alert) {
	println('Debe especificar un pasaporte, Prueba Correcta')
} else {
	println('Prueba incorrecta')
}

WebUI.delay(1)

WebUI.closeBrowser()
