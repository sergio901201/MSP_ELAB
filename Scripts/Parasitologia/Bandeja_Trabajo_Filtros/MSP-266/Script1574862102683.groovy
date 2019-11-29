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

WebUI.callTestCase(findTestCase('Login'), [('User') : ID.getValue(1, 4), ('Password') : ID.getValue(2, 4)], FailureHandling.STOP_ON_FAILURE)

not_run: WebUI.selectOptionByValue(findTestObject('Login/Page_login/select_vMENU'), ID.getValue(1, 13), true)

WebUI.executeJavaScript('$("#vMENU").val("10");', null)

WebUI.click(findTestObject('Login/Page_login/input_Men_BTNCONFIRMAR'))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/PARASITOLOGIA_MICOLOGIA/Page_Bienvenida/td_Solicitudes de Estudios'))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/PARASITOLOGIA_MICOLOGIA/Page_Bienvenida/td_Bandeja Recepcin'))

WebUI.delay(2)

//Insert variable fecha in FechaDesde
WebUI.executeJavaScript('$("#vDESDE").val("'+fecha+'");', null)

//Press Tab
WebUI.sendKeys(findTestObject('Object Repository/PARASITOLOGIA_MICOLOGIA/Page_Bandeja Micologa - Parasitologa/input_Fecha Desde_vDESDE'), Keys.chord(Keys.TAB))

//Verify Element Present Fecha inválida
boolean errorF = WebUI.verifyElementPresent(findTestObject('Object Repository/PARASITOLOGIA_MICOLOGIA/Page_Bandeja Micologa - Parasitologa/span_Fecha invlida'), 30)

if(errorF){
	println ('Debe especificar una Fecha Desde válida, Prueba Correcta')
}
else{
	throw new Exception('Prueba Incorrecta')
}

WebUI.delay(2)

WebUI.closeBrowser()
