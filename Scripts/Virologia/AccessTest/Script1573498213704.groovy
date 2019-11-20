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
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable

InternalData ID = findTestData('Data Files/Internal Data Login')

WebUI.callTestCase(findTestCase('Login'), [('User') : ID.getValue(1, 1), ('Password') : ID.getValue(
            2, 1)], FailureHandling.STOP_ON_FAILURE)

not_run: WebUI.selectOptionByValue(findTestObject('Login/Page_login/select_vMENU'), ID.getValue(1, 13), true)

WebUI.executeJavaScript('$("#vMENU").val("6");', null)

WebUI.click(findTestObject('Login/Page_login/input_Men_BTNCONFIRMAR'))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Virologia/Page_Bienvenida/span_Virologia'))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Virologia/Page_Bienvenida/td_Bandeja de Trabajo'))

WebUI.delay(2)

WebUI.click(findTestObject('Login/Page_Bienvenida/img_VIROLOGA_IMAGE2_MPAGE'))

WebUI.delay(2)

WebUI.closeBrowser()

